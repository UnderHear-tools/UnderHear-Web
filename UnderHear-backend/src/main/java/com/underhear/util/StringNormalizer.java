package com.underhear.util;

public final class StringNormalizer {

    private StringNormalizer() {
    }

    public static String normalizeNullable(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
