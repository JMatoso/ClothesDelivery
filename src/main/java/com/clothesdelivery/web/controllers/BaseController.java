package com.clothesdelivery.web.controllers;

import com.clothesdelivery.web.helpers.AuthUserManager;

public class BaseController extends AuthUserManager {
    public final String notFound = redirect("notfound");
    public final String login = redirect("login");

    public String redirect(String path) {
        return "redirect:/" + path;
    }
}
