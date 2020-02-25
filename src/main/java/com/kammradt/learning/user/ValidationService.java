package com.kammradt.learning.user;

import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    public Boolean isNotNullAndNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
