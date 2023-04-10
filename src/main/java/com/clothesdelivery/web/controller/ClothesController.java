package com.clothesdelivery.web.controller;

import com.clothesdelivery.web.models.Category;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClothesController {
    @GetMapping("/")
    public String index(@NotNull Model model) {
        model.addAttribute("categories", Category.values());
        return "index";
    }

    @GetMapping("/bag")
    public String bag() {
        return "bag";
    }

    @GetMapping("/products")
    public String products(@RequestParam(required = false) String category) {
        return "products";
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
