package com.clothesdelivery.web.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@Controller
public class HomeController {
    @Value("${clothes.whatsapp.number}")
    private String shoppingWhatsappNumber;

    @Value("${clothes.email.address}")
    private String shoppingEmailAddress;

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact(@NotNull Model model) {
        model.addAttribute("email", shoppingEmailAddress);
        model.addAttribute("phone", shoppingWhatsappNumber);
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
