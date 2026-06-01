package com.onlikee.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.onlikee.exception.BizException;
import com.onlikee.exception.ErrorCode;

public record ApplicationUrl(String value, String sitePrefix) {
    private static final Pattern ONLIKEE_SITE_URL_PATTERN = Pattern.compile(
            "^https://([a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?)\\.onlikee\\.cn/$");
    private static final Pattern URI_SCHEME_PATTERN = Pattern.compile("^[A-Za-z][A-Za-z0-9+.-]*:");
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s");

    public static ApplicationUrl parse(String appUrl) {
        Matcher matcher = ONLIKEE_SITE_URL_PATTERN.matcher(appUrl);
        if (!matcher.matches()) {
            throw new BizException(ErrorCode.APP_URL_INVALID);
        }
        return new ApplicationUrl(appUrl, matcher.group(1));
    }

    public static String normalizeExternal(String appUrl) {
        String value = appUrl == null ? "" : appUrl.trim();
        if (value.isBlank() || WHITESPACE_PATTERN.matcher(value).find()) {
            throw new BizException(ErrorCode.APP_URL_INVALID);
        }

        if (hasScheme(value) && !hasHttpScheme(value)) {
            throw new BizException(ErrorCode.APP_URL_INVALID);
        }

        if (!hasScheme(value)) {
            value = "https://" + value;
        }

        try {
            URI uri = new URI(value);
            String scheme = uri.getScheme();
            String host = uri.getHost();
            if (!isHttpScheme(scheme) || host == null || host.isBlank()) {
                throw new BizException(ErrorCode.APP_URL_INVALID);
            }
            return uri.toString();
        } catch (URISyntaxException | IllegalArgumentException ex) {
            throw new BizException(ErrorCode.APP_URL_INVALID);
        }
    }

    private static boolean hasScheme(String value) {
        return URI_SCHEME_PATTERN.matcher(value).find();
    }

    private static boolean hasHttpScheme(String value) {
        return value.regionMatches(true, 0, "http://", 0, "http://".length())
                || value.regionMatches(true, 0, "https://", 0, "https://".length());
    }

    private static boolean isHttpScheme(String scheme) {
        return "http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme);
    }
}
