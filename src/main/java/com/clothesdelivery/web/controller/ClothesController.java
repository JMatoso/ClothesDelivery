package com.clothesdelivery.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClothesController {
    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
