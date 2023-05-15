package com.clothesdelivery.web.controllers;

import com.clothesdelivery.web.enums.ProductFilters;
import com.clothesdelivery.web.repositories.IProductRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ClothesController {
    @Autowired
    private IProductRepository _products;

    @GetMapping("/")
    public String index(@NotNull Model model) {
        var products = _products.findAll();

        var bestProducts = products.stream().filter(e -> e.getFilter() == ProductFilters.Best).limit(12);
        var newestProducts = products.stream().filter(e -> e.getFilter() == ProductFilters.New).limit(12);
        var hottestProducts = products.stream().filter(e -> e.getFilter() == ProductFilters.Hot).limit(12);

        model.addAttribute("best_products", bestProducts);
        model.addAttribute("newest_products", newestProducts);
        model.addAttribute("hottest_products", hottestProducts);

        return "index";
    }

    @GetMapping("/detail/{friendlyUrl}")
    public String detail(@PathVariable String friendlyUrl) {
        return "detail";
    }
}
