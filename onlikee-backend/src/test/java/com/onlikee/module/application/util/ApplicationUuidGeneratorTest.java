package com.onlikee.module.application.util;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class ApplicationUuidGeneratorTest {

    @Test
    // 应用标识应满足标准 UUID 格式。
    void nextShouldReturnValidUuidString() {
        String applicationId = ApplicationUuidGenerator.next();

        assertNotNull(applicationId);
        UUID.fromString(applicationId);
    }

    @Test
    // 连续生成的应用标识应保持基本唯一性。
    void nextShouldReturnDifferentValuesAcrossConsecutiveCalls() {
        String first = ApplicationUuidGenerator.next();
        String second = ApplicationUuidGenerator.next();

        assertNotEquals(first, second);
    }
}
