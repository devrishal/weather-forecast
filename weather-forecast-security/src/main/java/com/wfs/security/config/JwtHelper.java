package com.wfs.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtHelper {

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    public JwtHelper(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String createJwtForClaims(String subject, Map<String, String> claims) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date notBefore = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date issuedJwtTokenExpiry = Date.from(localDateTime.plusHours(3).atZone(ZoneId.systemDefault()).toInstant());
        JWTCreator.Builder jwtBuilder = JWT.create().withSubject(subject);

        // Add claims
        claims.forEach(jwtBuilder::withClaim);

        // Add expiredAt and etc
        return jwtBuilder
                .withNotBefore(notBefore)
                .withExpiresAt(issuedJwtTokenExpiry)
                .sign(Algorithm.RSA256(publicKey, privateKey));
    }
}