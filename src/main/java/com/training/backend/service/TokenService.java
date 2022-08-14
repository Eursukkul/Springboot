package com.training.backend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.training.backend.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {        // หน้าที่สร้าง token

    @Value("${app.token.secret}")  // หน้าที่ BackEnd set ควรจะเปลี่ยนบ่อยๆ
    private String secret;

    @Value("${app.token.issuer}") // ใครคือคนสร้าง token
    private String issuer;

    public String tokenize(User user) {         // Token Expire
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60);  // Expire 60 min
        Date expiresAt = calendar.getTime();

        return JWT.create()
                .withIssuer(issuer)
                .withClaim("principal", user.getId())
                .withClaim("role", "USER")
                .withExpiresAt(expiresAt)
                .sign(algorithm());
    }
    // check ว่าถูกต้องไหม หมดอายุหรือยัง
    public DecodedJWT verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm())
                    .withIssuer(issuer)
                    .build();

            return verifier.verify(token); //ถ้าผ่าน

        } catch (Exception e) {
            return null;   // ถ้าไม่ผ่าน
        }
    }

    private Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }

}
