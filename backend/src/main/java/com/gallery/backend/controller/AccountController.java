package com.gallery.backend.controller;

import com.gallery.backend.entity.Member;
import com.gallery.backend.repository.MemberRepository;
import com.gallery.backend.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class AccountController {

    private MemberRepository memberRepository;
    private JwtService jwtService;

    @Autowired
    public AccountController(MemberRepository memberRepository, JwtService jwtService) {
        this.memberRepository = memberRepository;
        this.jwtService = jwtService;
    }

    @PostMapping(path = "/api/account/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> params, HttpServletResponse response) {
        Member member = memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        // 성공하면 그냥 ID 번호가 아니라 토큰을 보낸다...뭐가 달라지는 건지...
        int id = member.getId();
        String token = jwtService.getToken("id", id);

        // 쿠키를 사용한다는데...
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        // 이걸로 하면 토큰 쿠키가 클라이언트로 간다...
        // return ResponseEntity.ok().build();
        // 그래서 이걸 서버에 두려고 새로 만들어 id만 넣어서 보낸다?
        // 그럼 다음에 서버에 뭘 달라고 할 때는 로그인 한 상태인지 어떻게 알지?
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping(path = "/api/account/check")
    public ResponseEntity<Object> check(@CookieValue(value = "token", required = false) String token) {
        int memberId = jwtService.getId(token);
        return new ResponseEntity<>(memberId, HttpStatus.OK);
    }

    @PostMapping(path = "/api/account/logout")
    public ResponseEntity<Object> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
