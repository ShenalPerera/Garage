package org.isa.garage.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.isa.garage.config.GarageUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.validity}")
    private Long JWT_TOKEN_VALIDITY;
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(Decoders.BASE64.decode(SECRET)).build().parseClaimsJws(token).getBody();
    }

    public Claims validateToken(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);

            if (claims.getExpiration().before(new Date())) {
                throw new AuthenticationCredentialsNotFoundException("JWT token has expired");
            }

            return claims;
        } catch (ExpiredJwtException ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT token has expired", ex);
        } catch (UnsupportedJwtException | MalformedJwtException | SecurityException | IllegalArgumentException ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT token is not valid", ex);
        }
    }

    public String generateToken(GarageUserDetails garageUserDetails){
        Map<String, Object> claims = new HashMap<>();
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));

        claims.put("id",garageUserDetails.getId());
        claims.put("email",garageUserDetails.getUsername());
        claims.put("active",garageUserDetails.isEnabled());
        List<String> roles = garageUserDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("authorities", roles);

        System.out.println(roles);
        return Jwts.builder()
                .setSubject(garageUserDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .addClaims(claims)
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();

    }
}
