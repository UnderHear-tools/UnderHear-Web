package com.onlikee.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ShortUuidGeneratorTest {

    @Test
    // 生成结果应保持当前实现约定的 11 位数字格式。
    void nextShouldReturnElevenDigitNumericString() {
        String shortUuid = ShortUuidGenerator.next();

        assertEquals(11, shortUuid.length());
        assertTrue(shortUuid.matches("\\d{11}"));
    }

    @Test
    // 连续调用时至少应返回不同的值。
    void nextShouldReturnDifferentValuesAcrossConsecutiveCalls() {
        String first = ShortUuidGenerator.next();
        String second = ShortUuidGenerator.next();

        assertNotEquals(first, second);
    }
}
