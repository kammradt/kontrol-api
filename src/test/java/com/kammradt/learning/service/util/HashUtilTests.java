package com.kammradt.learning.service.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static com.kammradt.learning.security.HashUtil.generateHash;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class HashUtilTests {

    @Nested
    @DisplayName("Testing generateHash()")
    class testingGenerateHash {

        @Test
        @DisplayName("Testing generateHash() for long texts")
        void generateHashTestForLongTexts() {
            String hashed = generateHash("LONG_TEXT_WITH_MORE_CHARACTERS_THAN_AVERAGE_ONES");
            assertThat(hashed).hasSize(64);
        }

        @Test
        @DisplayName("Testing generateHash() for small texts")
        void generateHashTestForSmallTexts() {
            String hashed = generateHash("SMALL");
            assertThat(hashed).hasSize(64);
        }


    }
}
