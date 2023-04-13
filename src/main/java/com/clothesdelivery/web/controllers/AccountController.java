package com.clothesdelivery.web.controllers;

import com.clothesdelivery.web.models.Login;
import com.clothesdelivery.web.repositories.ICustomerRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {
    @Autowired
    private ICustomerRepository _customer;

    @GetMapping("/login")
    public String login(@NotNull Model model) {
        model.addAttribute("login", new Login());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("login") @NotNull Login login, Model model)
    {
        var customer = _customer.findByEmailAndPassword(login.getEmail(), login.getPassword());

        if(customer == null) {
            model.addAttribute("message", "Wrong Credentials");
            return "login";
        }

        // Authentication code

        return "redirect:/clothes/index";
    }
}
