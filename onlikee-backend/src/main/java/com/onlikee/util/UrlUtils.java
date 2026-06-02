package com.onlikee.util;

import java.net.URI;
import java.net.URISyntaxException;

public final class UrlUtils {

    private UrlUtils() {
    }

    public static boolean isValid(String url) {
        if (url == null || url.isBlank()) {
            return false;
        }

        try {
            URI uri = new URI(url);
            String scheme = uri.getScheme();
            String host = uri.getHost();
            // connect flow 会按提交值落库，因此这里只接受前端已规范化的 HTTP(S) URL。
            return ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme))
                    && host != null
                    && !host.isBlank();
        } catch (URISyntaxException ex) {
            return false;
        }
    }
}
