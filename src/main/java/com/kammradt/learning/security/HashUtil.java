package com.kammradt.learning.security;

import org.apache.commons.codec.digest.DigestUtils;

public class HashUtil {

    public static String generateHash(String text) {
        return DigestUtils.sha256Hex(text);
    }
}
