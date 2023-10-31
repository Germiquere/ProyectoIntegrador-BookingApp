package com.dh.bmn.security.jwt;

import com.dh.bmn.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class JwtService {

    //private static final String SECRET_KEY="586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.tokenExpirationSeconds}")
    private int tokenExpirationSeconds;

    public String getToken(UserDetails usuario) {
        List<String> roles = getRolesFromUser(usuario);

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", usuario.getUsername());
        claims.put("roles", roles);
        return getToken(claims, usuario);
    }

    private List<String> getRolesFromUser(UserDetails usuario) {
        List<String> roles = new ArrayList<>();

        if (usuario instanceof Usuario customUser) {
            roles.add(customUser.getRol().name());
        }

        return roles;
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails usuario) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpirationSeconds * 1000L))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUserNameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = getUserNameFromToken(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
