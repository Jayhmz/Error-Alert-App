package com.plantaccion.alartapp.authentication.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plantaccion.alartapp.authentication.provider.UsernamePasswordAuthenticationProvider;
import com.plantaccion.alartapp.common.model.AppUser;
import com.plantaccion.alartapp.common.enums.Roles;
import com.plantaccion.alartapp.exception.TokenExpiredException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
public class JWTService {

    @Autowired
    private UsernamePasswordAuthenticationProvider authenticationProvider;
    @Autowired
    private ObjectMapper mapper;
    @Value("${SECRET-KEY}")
    private String secretKey;

    public String generateToken(String username, AppUser appUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", Roles.valueOf(appUser.getRole().name()));
        claims.put("staffId", appUser.getStaffId());
        claims.put("firstname", appUser.getFirstname());
        claims.put("lastname", appUser.getLastname());
        claims.put("id", appUser.getId());
        claims.put("email", appUser.getEmail());
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignWithKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignWithKey() {
        byte[] keyByte = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignWithKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public HashMap<String, Object> getTokenDetails(String token) {
        return new HashMap<>(extractAllClaims(token));
    }

    public JwtTokenDetails getBasicDetails(HashMap<String, Object> details) {
        return mapper.convertValue(details, JwtTokenDetails.class);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public JwtTokenDetails validateToken(String token) {
        if (!isTokenExpired(token)) {
            System.out.println("inside the jwt service validate token");
            HashMap<String, Object> tokenDetails = getTokenDetails(token);
            return getBasicDetails(tokenDetails);
        } else {
            throw new TokenExpiredException("The token has expired");
        }
    }


//        public String generateToken(String username) {
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, username);
//    }

//public String extractUsername(String token) {
//    return extractClaim(token, Claims::getSubject);
//}


}