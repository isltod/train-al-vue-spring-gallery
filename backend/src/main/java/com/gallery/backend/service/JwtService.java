package com.gallery.backend.service;

public interface JwtService {

    String getToken(String key, Object value);
}
