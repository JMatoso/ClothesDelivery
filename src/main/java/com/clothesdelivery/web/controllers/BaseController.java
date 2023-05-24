package com.clothesdelivery.web.controllers;

import com.clothesdelivery.web.entities.User;
import com.clothesdelivery.web.repositories.IUserRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {
    @Autowired
    private IUserRepository _userManager;

    public final String notFound = redirect("notfound");
    public final String login = redirect("login");

    public String redirect(String path) {
        return "redirect:/" + path;
    }

    public @Nullable User getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            if(!authentication.getName().isEmpty())
            {
                var user = _userManager.findByEmail(authentication.getName());
                user.setPassword("");

                return user;
            }
        }

        return null;
    }

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
}
