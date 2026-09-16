package com.example.springsecurity.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey signingKey() {
       return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    //login
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("Roles", userDetails.getAuthorities() // take the role of user //[ GrantedAuthority("ROLE_ADMIN"), GrantedAuthority("ROLE_USER") ]
                .stream()
                .map(GrantedAuthority::getAuthority) // take of role ["ROLE_ADMIN", "ROLE_USER"]
        );

        return Jwts.builder()
                .claims(claims) // Role
                .subject(userDetails.getUsername()) // get from Username
                .signWith(signingKey()) // throw for token
                .issuedAt(new Date()) // Create token
                .expiration(new Date(System.currentTimeMillis() + expiration)) // expiration tokend date
                .compact(); //  prepare
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // <T>  = Geric Type  // like list for store type
        // T = return back for that type
        // check for token , verity
        Claims claims = Jwts.parser()
                .verifyWith(signingKey()) // verify with Singinkey = check with token
                .build() // prepare for create a full object
                .parseSignedClaims(token) // or divide it 3 (Header.Payload.Signature) chheck for Signature
                .getPayload(); //get data like admin for role

        return claimsResolver.apply(claims); // return back for that type
    }
}
