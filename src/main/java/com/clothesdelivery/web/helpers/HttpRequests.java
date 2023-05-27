package com.clothesdelivery.web.helpers;

import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URISyntaxException;

public final class HttpRequests {
    public static @NotNull String getRequestUrl(@NotNull HttpServletRequest httpRequest) {
        return httpRequest.getRequestURL().toString();
    }

    public static String getRequestUrlWithQuery(@NotNull HttpServletRequest httpRequest) {
        var url = httpRequest.getRequestURL().toString();
        var queryString = httpRequest.getQueryString();

        if (queryString != null) {
            url += "?" + queryString;
        }

        return url;
    }

    public static @NotNull String getHostUrl(@NotNull HttpServletRequest httpRequest) {
        URI url = null;

        try {
            url = new URI(httpRequest.getRequestURL().toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return url.getScheme() + "://" + url.getHost();
    }
}
