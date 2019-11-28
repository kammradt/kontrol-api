package com.kammradt.learning.service;

import com.kammradt.learning.dto.UserLoginDTO;
import com.kammradt.learning.security.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtManager jwtManager;

    public String generateJWTToken(UserLoginDTO user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        Authentication auth = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(auth);

        org.springframework.security.core.userdetails.User userSpring =
                (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        String email = userSpring.getUsername();
        List<String> roles = userSpring.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return jwtManager.createToken(email, roles);
    }
}
