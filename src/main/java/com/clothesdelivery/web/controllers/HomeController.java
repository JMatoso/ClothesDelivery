package com.clothesdelivery.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
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
}
