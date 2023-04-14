package com.clothesdelivery.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClothesController {
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }
}
