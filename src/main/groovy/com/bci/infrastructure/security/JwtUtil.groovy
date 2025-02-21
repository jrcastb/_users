package com.bci.infrastructure.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtUtil {

    @Value("\${jwt.secret}")
    String secret

    @Value("\${jwt.expiration}")
    long expiration

    String generateToken(String subject) {
        try {
            Map<String, Object> claims = new HashMap<>()
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact()
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el token JWT", e)
        }
    }

    Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody()
        } catch (Exception e) {
            throw new RuntimeException("Token JWT inválido o no se puede parsear", e);
        }
    }

    boolean isTokenExpired(String token) {
        Claims claims = getAllClaimsFromToken(token)
        Date expirationDate = claims.getExpiration()
        return expirationDate.before(new Date())
    }

    String getEmailFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token)
        return claims.getSubject()
    }
}
