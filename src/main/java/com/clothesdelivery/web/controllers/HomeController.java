package com.clothesdelivery.web.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends BaseController{
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact(@NotNull Model model) {
        var userName = "";
        var userEmail = "";
        var userPhoneNumber = "";

        if(isAuthenticated()) {
            var user = getAuthenticatedUser();

            if(user != null) {
                userName = user.getName();
                userEmail = user.getEmail();
                userPhoneNumber = user.getPhoneNumber();
            }
        }

        model.addAttribute("userName", userName);
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("userPhoneNumber", userPhoneNumber);

        return "contact";
    }

    @GetMapping("/terms")
    public String terms() {
        return "terms";
    }

    @GetMapping("/notfound")
    public String notfound() {
        return "notfound";
    }

    @GetMapping("/forbidden")
    public String forbidden() {
        return "forbidden";
    }
}
