package com.clothesdelivery.web.controller;

import com.clothesdelivery.web.entities.Customer;
import com.clothesdelivery.web.entities.Product;
import com.clothesdelivery.web.models.Category;
import com.clothesdelivery.web.repositories.IEntityRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClothesController {
    @Autowired
    private IEntityRepository<Product> _productRepository;

    @GetMapping("/")
    public String index(@NotNull Model model) {
        model.addAttribute("products", _productRepository.findAll());
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
