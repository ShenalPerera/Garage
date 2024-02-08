package org.isa.garage.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.isa.garage.config.GarageUserDetails;
import org.isa.garage.config.GarageUserDetailsService;
import org.isa.garage.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final GarageUserDetailsService garageUserDetailsService;

    @Autowired
    public JWTFilter(JwtTokenUtil jwtTokenUtil, GarageUserDetailsService garageUserDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.garageUserDetailsService = garageUserDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        Claims claims = jwtTokenUtil.validateToken(token);

        if (!claims.isEmpty()) {
            GarageUserDetails garageUserDetails = this.garageUserDetailsService.loadUserByUsername(claims.get("email", String.class));
            UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(garageUserDetails, null, null);

            userAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(userAuthToken);
        }
        filterChain.doFilter(request, response);
    }
}
