package com.clothesdelivery.web.helpers;

import com.clothesdelivery.web.entities.User;
import com.clothesdelivery.web.repositories.IUserRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUserManager {
    @Autowired
    private IUserRepository _userManager;

    public @Nullable User getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            if(!authentication.getName().isEmpty())
            {
                return _userManager.findByEmail(authentication.getName());
            }
        }

        return null;
    }

    public boolean isAuthenticated() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser");
    }
}
