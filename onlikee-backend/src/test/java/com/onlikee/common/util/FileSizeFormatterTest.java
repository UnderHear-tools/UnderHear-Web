package com.onlikee.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FileSizeFormatterTest {

    @Test
    void formatShouldKeepBytesWithoutDecimalPlaces() {
        assertEquals("512 B", FileSizeFormatter.format(512));
    }

    @Test
    void formatShouldRoundMegabytesToTwoDecimalPlaces() {
        assertEquals("1.50 MB", FileSizeFormatter.format(1572864));
    }

    @Test
    void formatShouldRejectNegativeBytes() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FileSizeFormatter.format(-1));

        assertEquals("bytes cannot be negative", exception.getMessage());
    }
}
