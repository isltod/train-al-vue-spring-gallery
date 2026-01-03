package com.gallery.backend.controller;

import com.gallery.backend.dto.OrderDto;
import com.gallery.backend.entity.Order;
import com.gallery.backend.repository.CartRepository;
import com.gallery.backend.repository.OrderRepository;
import com.gallery.backend.service.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrderController {

    OrderRepository orderRepository;
    CartRepository cartRepository;
    JwtService jwtService;

    @Autowired
    public OrderController(OrderRepository orderRepository, CartRepository cartRepository, JwtService jwtService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.jwtService = jwtService;
    }

    @GetMapping("/api/orders")
    public ResponseEntity getOrder(@CookieValue(value = "token", required = false) String token) {

        // 이건 계속 반복인데...뺄 수도 없나?
        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        List<Order> orders = orderRepository.findByMemberIdOrderByIdDesc(memberId);
        return new ResponseEntity(orders, HttpStatus.OK);
    }

    @Transactional
    @PostMapping(path = "/api/orders")
    public ResponseEntity createOrder(
            @CookieValue(value = "token", required = false) String token, @RequestBody OrderDto dto) {

        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);

        Order order = new Order();
        order.setMemberId(memberId);
        order.setName(dto.getName());
        order.setAddress(dto.getAddress());
        order.setPayment(dto.getPayment());
        order.setCardNumber(dto.getCardNumber());
        order.setItems(dto.getItems());

        orderRepository.save(order);
        cartRepository.deleteByMemberId(memberId);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
