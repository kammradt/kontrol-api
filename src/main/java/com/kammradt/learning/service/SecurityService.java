package com.kammradt.learning.service;

import com.kammradt.learning.dto.UserLoginDTO;
import com.kammradt.learning.security.JwtManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SecurityService {

    private AuthenticationManager authenticationManager;
    private JwtManager jwtManager;

    public HashMap<String, String> generateJWTToken(UserLoginDTO user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        Authentication auth = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);

        User userSpring = (User) auth.getPrincipal();
        String email = userSpring.getUsername();
        List<String> roles = getRolesBySpringUser(userSpring);

        return createResponse(email, roles);
    }

    private HashMap<String, String> createResponse(String email, List<String> roles) {
        String jwt = jwtManager.createToken(email, roles);

        HashMap<String, String> response = new HashMap<>();
        response.put("token", jwt);

        return response;
    }

    private List<String> getRolesBySpringUser(User userSpring) {
        return userSpring.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
