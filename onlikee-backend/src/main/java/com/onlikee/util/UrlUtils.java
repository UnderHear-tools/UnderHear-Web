package com.onlikee.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Locale;

public class UrlUtils {
    private static final String ONLIKEE_APP_DOMAIN_SUFFIX = ".onlikee.com";

    /**
     * 判断给定字符串是否为 HTTP/HTTPS URL 或裸域名。
     */
    public static boolean isUrl(String url) {
        if (url == null || url.isBlank()) {
            return false;
        }

        // 先按原字符串判断；如果没有协议前缀，再补 http:// 判断一次裸域名。
        String[] candidates = {url, "http://" + url};

        for (String candidate : candidates) {
            try {
                URI uri = new URI(candidate);

                String scheme = uri.getScheme();
                String host = uri.getHost();
                int port = uri.getPort();

                // 基础 URL 限制：只允许 HTTP/HTTPS，且拒绝 userInfo、异常端口和明显不规范的 host。
                if (scheme == null
                        || (!scheme.equalsIgnoreCase("http") && !scheme.equalsIgnoreCase("https"))
                        || host == null
                        || !host.contains(".")
                        || host.length() > 253
                        || host.endsWith(".")
                        || uri.getUserInfo() != null
                        || (port != -1 && (port < 1 || port > 65535))) {
                    continue;
                }

                // 按 label 继续校验，允许合法域名或合法 IPv4，避免 a.b、example.、-example.com 等误判。
                String[] labels = host.split("\\.", -1);
                String topLevelDomain = labels[labels.length - 1];
                boolean validDomain = topLevelDomain.matches("[A-Za-z]{2,}");
                boolean validIpv4 = labels.length == 4;

                for (String label : labels) {
                    if (label.isEmpty()
                            || label.length() > 63
                            || label.startsWith("-")
                            || label.endsWith("-")
                            || !label.matches("[A-Za-z0-9-]+")) {
                        validDomain = false;
                        validIpv4 = false;
                        break;
                    }

                    if (validIpv4) {
                        if (!label.matches("\\d{1,3}") || Integer.parseInt(label) > 255) {
                            validIpv4 = false;
                        }
                    }
                }

                if (validDomain || validIpv4) {
                    return true;
                }
            } catch (Exception e) {
                // 尝试下一个候选 URL。
            }
        }

        return false;
	}

    /**
     * 智能补全 URL 协议。
     *
     * 调用方已保证 url 能通过 isUrl(url)，因此这里专注处理协议补全：
     * 1. 已经带 HTTP/HTTPS 协议的 URL 不做改写；
     * 2. 裸域名优先尝试 HTTPS；
     * 3. HTTPS 无法连通时回退到 HTTP。
     */
    public static String smartCompleteUrl(String url) {
        // 已显式带协议时直接返回，避免改变调用方传入的大小写、路径、查询参数或锚点。
        if (url.regionMatches(true, 0, "http://", 0, "http://".length())
                || url.regionMatches(true, 0, "https://", 0, "https://".length())) {
            return url;
        }

        // 裸域名默认优先使用 HTTPS；只有 HTTPS 无法拿到响应时才降级为 HTTP。
        String httpsUrl = "https://" + url;
        try {
            HttpURLConnection connection = (HttpURLConnection) URI.create(httpsUrl).toURL().openConnection();
            // 只验证连通性，不读取响应体，减少对目标服务的请求成本。
            connection.setRequestMethod("HEAD");
            // 避免目标站点不可达时长时间阻塞调用方。
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            // 允许常见的 HTTPS 跳转链路，例如从根域跳转到 www 子域。
            connection.setInstanceFollowRedirects(true);
            // 只要能拿到合法 HTTP 状态码就认为 HTTPS 可连通，403/404 也不降级。
            if (connection.getResponseCode() > 0) {
                return httpsUrl;
            }
        } catch (IOException e) {
            // HTTPS 不可连通时回退到 HTTP。
        }

        return "http://" + url;
    }

    /**
     * 规范化 URL：调用方已确保 URL 通过 isUrl(url)，且已经过 smartCompleteUrl(url) 补全。
     * 这里只把协议和域名转成小写，路径、查询参数和锚点保持原样。
     */
    public static String normalizeUrl(String url) {
        URI uri = URI.create(url);
        String host = uri.getHost();
        String authority = uri.getRawAuthority();

        return uri.getScheme().toLowerCase(Locale.ROOT)
                + "://"
                + host.toLowerCase(Locale.ROOT)
                + authority.substring(host.length())
                + (uri.getRawPath() == null ? "" : uri.getRawPath())
                + (uri.getRawQuery() == null ? "" : "?" + uri.getRawQuery())
                + (uri.getRawFragment() == null ? "" : "#" + uri.getRawFragment());
    }

    public static String buildOnlikeeAppDomain(String appSubDomain) {
        return appSubDomain + ONLIKEE_APP_DOMAIN_SUFFIX;
    }

    public static String buildOnlikeeAppUrl(String appSubDomain) {
        return "https://" + buildOnlikeeAppDomain(appSubDomain) + "/";
    }
}
