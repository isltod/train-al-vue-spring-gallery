package com.gallery.backend.dto;

import lombok.Getter;

// DTO는 getter만 있어도 작동하는데 문제가 없나?
@Getter
public class OrderDto {

    private String name;
    private String address;
    private String payment;
    private String cardNumber;
    private String items;
}
