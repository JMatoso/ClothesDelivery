package com.clothesdelivery.web.controllers;

import com.clothesdelivery.web.enums.Role;
import com.clothesdelivery.web.repositories.IProductRepository;
import com.clothesdelivery.web.repositories.IUserRepository;
import jakarta.websocket.server.PathParam;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController extends Base {
    @Autowired
    private IProductRepository _products;

    @Autowired
    private IUserRepository _users;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/products")
    public String products(@NotNull Model model) {
        model.addAttribute("products", _products.findAll());
        return "admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String productEdit(@PathVariable(value = "id", required = false) Long id) {
        return "admin/product-edit";
    }

    @GetMapping("/users/edit/{id}")
    public String usersEdit(@NotNull @PathVariable("id") Long id) {
        return "admin/users-edit";
    }

    @GetMapping("/users")
    public String users(@NotNull Model model) {
        model.addAttribute("users", _users.findAll().stream().filter(e -> e.getRole().equals(Role.Admin)).toList());
        return "admin/users";
    }

    @GetMapping("/customers")
    public String customers(@NotNull Model model) {
        model.addAttribute("customers", _users.findAll().stream().filter(e -> e.getRole().equals(Role.Customer)).toList());
        return "admin/customers";
    }
}
