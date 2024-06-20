package com.bharani.movie.Service;
import com.bharani.movie.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    public JwtService() {
    }

    public String extractUserName(String token) {
        return (String)this.extractClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails user) {
        return this.extractUserName(token).equals(user.getUsername()) && !this.expired(token);
    }

    private boolean expired(String token) {
        return this.expirationDate(token).before(new Date());
    }

    private Date expirationDate(String token) {
        return (Date)this.extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = this.extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return (Claims)Jwts.parserBuilder()
                .setSigningKey(this.getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(User user) {
        return Jwts.builder().
                setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000L))
                .signWith(this.getSigninKey()).compact();
    }

    private SecretKey getSigninKey() {
        String SECRET_KEY = "33e09f21e18a17847232f94c8f67e64cd93d03a690e89e3fd9502601ecdcd790";
        byte[] keyBytes = (byte[])Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
