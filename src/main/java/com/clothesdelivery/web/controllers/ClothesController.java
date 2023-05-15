package com.clothesdelivery.web.controllers;

import com.clothesdelivery.web.enums.Category;
import com.clothesdelivery.web.enums.ClothesSize;
import com.clothesdelivery.web.enums.GenreStyle;
import com.clothesdelivery.web.enums.ProductFilters;
import com.clothesdelivery.web.repositories.IProductRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/products")
    public String products(
            @RequestParam(value = "category", required = false) Category category,
            @RequestParam(value = "style", required = false) GenreStyle style,
            @RequestParam(value = "size", required = false) ClothesSize size,
            @RequestParam(value = "search", required = false) String search,
            @NotNull Model model) {
        var products = _products.findAll();

        model.addAttribute("products", products);

        return "products";
    }

    @GetMapping("/detail/{friendlyUrl}")
    public String detail(@PathVariable @NotNull String friendlyUrl, @NotNull Model model) {
        if(friendlyUrl.isEmpty()) {
            return "redirect:/products";
        }

        var product = _products.findByFriendlyUrl(friendlyUrl);

        if(product == null) {
            return "redirect:/notfound";
        }

        model.addAttribute("product", product);

        // todo: recommended products

        return "detail";
    }
}
