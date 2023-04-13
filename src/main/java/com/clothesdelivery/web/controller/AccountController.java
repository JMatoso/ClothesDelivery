package com.clothesdelivery.web.controller;

import com.clothesdelivery.web.entities.Customer;
import com.clothesdelivery.web.models.Login;
import com.clothesdelivery.web.repositories.IEntityRepository;
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
    private IEntityRepository<Customer> _customerRepository;

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/login")
    public String login(@NotNull Model model) {
        model.addAttribute("login", new Login());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("login") @NotNull Login login) {

        var customer = _customerRepository.findByEmailAndPassword(login.getEmail(), login.getPassword());

        if(customer == null) {
            //model.addAttribute("Result", "Wrong Credentials!");
            return "login";
        }

        return "redirect:/clothes/index";
    }
}
