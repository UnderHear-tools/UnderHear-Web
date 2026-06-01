package com.onlikee.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.onlikee.exception.BizException;
import com.onlikee.exception.ErrorCode;

public record ApplicationUrl(String value, String sitePrefix) {
    private static final Pattern ONLIKEE_SITE_URL_PATTERN = Pattern.compile(
            "^https://([a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?)\\.onlikee\\.cn/$");

    public static ApplicationUrl parse(String appUrl) {
        Matcher matcher = ONLIKEE_SITE_URL_PATTERN.matcher(appUrl);
        if (!matcher.matches()) {
            throw new BizException(ErrorCode.APP_URL_INVALID);
        }
        return new ApplicationUrl(appUrl, matcher.group(1));
    }
}
