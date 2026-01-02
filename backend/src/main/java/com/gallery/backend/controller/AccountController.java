package com.gallery.backend.controller;

import com.gallery.backend.entity.Member;
import com.gallery.backend.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class AccountController {

    private MemberRepository memberRepository;

    @Autowired
    public AccountController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostMapping(path = "/api/account/login")
    public int login(@RequestBody Map<String, String> params) {
        Member member = memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return member.getId();
    }
}
