package com.gallery.backend.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("jwtService")
public class JwtServiceImpl implements JwtService {

    private String secret = "few@#$gad$h46ysdagh$@^&&$sdaseg#$2235#36eesdadasgvyh";

    @Override
    public String getToken(String key, Object value) {
        // 1. 헤더 만들기
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("alg", "HS256");
        headerMap.put("typ", "JWT");

        // 2. 사용자 페이로드? 만들어?
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        // 3. 만료 기간
        Date expTime = new Date();
        expTime.setTime(expTime.getTime() + 1000 * 60 * 30);

        // 4. 서명 키 만들기
        // byte[] bytes = DatatypeConverter.parseBase64Binary(secret);
        // Key secretKey = new SecretKeySpec(bytes, "HmacSHA256");
        // 이것도 버려져서 위처럼 하면 안된다..아래처럼 하는게 맞고, 더 간단하다...
        Key secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        // 5. 그리고 토큰인가? 문법이 바뀌어서...
        String jws = Jwts.builder()
                .header().add(headerMap)
                .and()
                .claim(key, value)
                .expiration(expTime)
                .signWith(secretKey)
                .compact();
        return jws;
    }

    @Override
    public Claims getClaims(String token) {
        if (token != null && !token.equals("")) {
            try {
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                return Jwts.parser().verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();
            } catch (ExpiredJwtException e) {
                // 만료된 토큰 오류
                e.printStackTrace();
            } catch (JwtException e) {
                // 유효하지 않은 토큰 오류
                e.printStackTrace();
            }
        }
        // 암튼 뭔가 잘못되면 null 반환
        return null;
    }

    // 아래 두 개가 로그인이 된 사용자인지 백엔드에서 보는 기능인가보다...
    @Override
    public boolean isValid(String token) {
        if (getClaims(token) != null) {
            return true;
        }
        return false;
    }

    @Override
    public int getId(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return (int) claims.get("id");
        }
        return 0;
    }
}
