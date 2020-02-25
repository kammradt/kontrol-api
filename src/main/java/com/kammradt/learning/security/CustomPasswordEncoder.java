package com.kammradt.learning.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence raw) {
        return HashUtil.generateHash(raw.toString());
    }

    @Override
    public boolean matches(CharSequence raw, String encoded) {
        return HashUtil.generateHash(raw.toString()).equals(encoded);
    }
}
