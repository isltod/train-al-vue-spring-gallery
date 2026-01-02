package com.gallery.backend.controller;

import com.gallery.backend.entity.Cart;
import com.gallery.backend.entity.Item;
import com.gallery.backend.repository.CartRepository;
import com.gallery.backend.repository.ItemRepository;
import com.gallery.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CartController {

    private CartRepository cartRepository;
    private JwtService jwtService;
    private ItemRepository itemRepository;

    @Autowired
    public void setItemRepository(CartRepository cartRepository, JwtService jwtService, ItemRepository itemRepository) {
        this.cartRepository = cartRepository;
        this.jwtService = jwtService;
        this.itemRepository = itemRepository;
    }

    @PostMapping(path = "/api/cart/items/{itemId}")
    public ResponseEntity putItemtoCart(
            @PathVariable("itemId") int itemId, @CookieValue(value = "token", required = false) String token) {
        // 토큰 없이(로그인 없이) 카트 접근하면 401 오류
        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        // 일단 사용자 아이디, 상품 아이디로 기존 카트를 찾는다? 뭔가 이상한데...
        // 예를 들어 좀 전에 1번 사용자가 3번 물건을 샀는데, 또 사려고 오면 이번에는 저장 안되는 로직 아닌가?
        int memberId = jwtService.getId(token);
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setItemId(itemId);
            newCart.setMemberId(memberId);
            cartRepository.save(newCart);
        }
        // 저장만 하고 빈 깡통 대답? 저장했으면 그 카트를 다시 줘야 하는거 아닌가?
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/api/cart/items")
    public ResponseEntity getCartItems(@CookieValue(value = "token", required = false) String token) {
        // 이건 계속 반복인데...뺄 수도 없나?
        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        List<Cart> carts = cartRepository.findByMemberId(memberId);
        List<Integer> itemIds = carts.stream().map(Cart::getItemId).toList();
        List<Item> items = itemRepository.findByIdIn(itemIds);

        return new ResponseEntity(items, HttpStatus.OK);
    }

    @DeleteMapping(path = "/api/cart/items/{itemId}")
    public ResponseEntity deleteItemfromCart(
            @PathVariable("itemId") int itemId, @CookieValue(value = "token", required = false) String token) {
        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        if (cart != null) {
            cartRepository.delete(cart);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
