package com.underhear.util;

import java.util.UUID;

public final class ApplicationUuidGenerator {

    private ApplicationUuidGenerator() {
    }

    public static String next() {
        return UUID.randomUUID().toString();
    }
}
