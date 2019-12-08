package com.kammradt.learning.service.util;

import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    public Boolean isNotNullAndNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
