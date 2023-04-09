package com.clothesdelivery.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClothesController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/bag")
    public String bag() {
        return "bag";
    }

    @GetMapping("/order")
    public String order() {
        return "order";
    }

    @GetMapping("/orders")
    public String orders() {
        return "orders";
    }
}
