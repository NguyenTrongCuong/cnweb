package com.example.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public class TokenUtils {

    private static Algorithm algorithm = Algorithm.HMAC256("123gdgsdgsgdgsdggsdg");

    public static String generateToken(Long accountId) {
        return JWT.create()
                .withIssuer("cnweb.com")
                .withSubject(String.valueOf(accountId))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(
                        new Date(System.currentTimeMillis() + 1800000)
                )
                .withPayload(
                        Map.of("roles", Arrays.asList("USER"))
                )
                .sign(algorithm);
    }

    public static DecodedJWT decodeToken(String token) {
        return JWT.decode(token);
    }

    public static void verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        verifier.verify(token);
    }

}
