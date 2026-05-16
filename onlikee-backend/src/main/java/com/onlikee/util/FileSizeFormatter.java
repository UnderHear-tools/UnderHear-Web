package com.onlikee.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class FileSizeFormatter {

    private static final BigDecimal UNIT_BASE = BigDecimal.valueOf(1024);
    private static final String[] UNITS = { "B", "KB", "MB", "GB" };

    private FileSizeFormatter() {
    }

    public static String format(long bytes) {
        if (bytes < 0) {
            throw new IllegalArgumentException("bytes cannot be negative");
        }

        BigDecimal size = BigDecimal.valueOf(bytes);
        int unitIndex = 0;

        while (size.compareTo(UNIT_BASE) >= 0 && unitIndex < UNITS.length - 1) {
            size = size.divide(UNIT_BASE, 10, RoundingMode.HALF_UP);
            unitIndex++;
        }

        if (unitIndex == 0) {
            return bytes + " " + UNITS[unitIndex];
        }

        BigDecimal scaledSize = size.setScale(2, RoundingMode.HALF_UP);
        if (scaledSize.stripTrailingZeros().scale() <= 0) {
            return scaledSize.toBigIntegerExact() + " " + UNITS[unitIndex];
        }

        return scaledSize.toPlainString() + " " + UNITS[unitIndex];
    }
}
