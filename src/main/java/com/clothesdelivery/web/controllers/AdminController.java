package com.clothesdelivery.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/products")
    public String products() {
        return "admin/products";
    }

    @GetMapping("/admin/products/edit")
    public String productEdit() {
        return "admin/product-edit";
    }
}
