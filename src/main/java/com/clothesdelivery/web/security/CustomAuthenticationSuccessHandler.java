package com.clothesdelivery.web.security;

import com.clothesdelivery.web.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(
            @NotNull HttpServletRequest request,
            HttpServletResponse response,
            @NotNull Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        String continueUrl = request.getParameter("continue");

        if (continueUrl != null && !continueUrl.isEmpty()) {
            continueUrl = response.encodeRedirectURL(continueUrl);
            response.sendRedirect(continueUrl);
            return;
        }

        if (roles.contains(String.valueOf(Role.ROLE_ADMIN))) {
            response.sendRedirect("/admin/dashboard");
            return;
        }

        if (roles.contains(String.valueOf(Role.ROLE_CUSTOMER))) {
            response.sendRedirect("/");
        }
    }
}
