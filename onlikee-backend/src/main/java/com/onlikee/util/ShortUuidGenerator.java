package com.onlikee.util;

public final class ShortUuidGenerator {

    private static long lastSecond = -1L;
    private static int sequence = 0;

    private ShortUuidGenerator() {
    }

    public static synchronized String next() {
        long nowSecond = System.currentTimeMillis() / 1000;
        if (nowSecond == lastSecond) {
            sequence = (sequence + 1) % 10;
            if (sequence == 0) {
                while ((nowSecond = System.currentTimeMillis() / 1000) == lastSecond) {
                    Thread.onSpinWait();
                }
            }
        } else {
            sequence = 0;
        }
        lastSecond = nowSecond;
        long value = nowSecond * 10 + sequence;
        return String.format("%011d", value);
    }
}
