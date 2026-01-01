package com.gallery.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;
}
