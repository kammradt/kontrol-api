package com.kammradt.learning.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kammradt.learning.constants.SecurityConstants;
import com.kammradt.learning.domain.exception.ErrorResponse;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String jwt = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwt == null || !jwt.startsWith(SecurityConstants.JWT_PROVIDER)) {
            ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), SecurityConstants.ERROR_INVALID_JWT, new Date());

            ObjectMapper mapper = new ObjectMapper();
            String stringError = mapper.writeValueAsString(error);

            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(stringError);

            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        jwt = jwt.replace(SecurityConstants.JWT_PROVIDER, "");

        try {
            Claims claims = new JwtManager().parseToken(jwt);
            String email = claims.getSubject();
            List<String> roles = (List<String>) claims.get(SecurityConstants.JWT_ROLE_KEY);

            List<GrantedAuthority> authorities = new ArrayList<>();
            roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

            Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), new Date());

            ObjectMapper mapper = new ObjectMapper();
            String stringError = mapper.writeValueAsString(error);

            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(stringError);

            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
