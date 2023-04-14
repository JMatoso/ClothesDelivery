package com.clothesdelivery.web.controllers;

import com.clothesdelivery.web.models.SignUp;
import com.clothesdelivery.web.models.Login;
import com.clothesdelivery.web.models.Result;
import com.clothesdelivery.web.repositories.IAddressRepository;
import com.clothesdelivery.web.repositories.ICustomerRepository;
import com.clothesdelivery.web.services.Crypto;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {
    @Autowired
    private ICustomerRepository _customer;

    @Autowired
    private IAddressRepository _address;

    @GetMapping("/login")
    public String login(@NotNull Model model) {
        model.addAttribute("login", new Login());
        model.addAttribute("result", new Result());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("login") Login login, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("result", new Result());
            return "login";
        }

        var customer = _customer.findByEmail(login.getEmail());

        if(customer == null || Crypto.compare(customer.getPassword(), login.getPassword())) {
            model.addAttribute("result", new Result(false, "Wrong Credentials."));
            return "login";
        }

        // todo: Authentication code

        return "redirect:/clothes/index";
    }

    @GetMapping("/signup")
    public String signup(@NotNull Model model) {
        model.addAttribute("result", new Result());
        model.addAttribute("signup", new SignUp());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("signup") SignUp signup, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("result", new Result());
            return "signup";
        }

        var address = signup.toAddressEntity();
        var customer = signup.toCustomerEntity();

        if(_customer.existsByEmailOrPhoneNumber(customer.getEmail(), customer.getPhoneNumber())) {
            model.addAttribute("result", new Result(false, "Phone number or email already in use."));
            return "signup";
        }

        var savedAddress = _address.save(address);

        customer.setAddressId(savedAddress.getId());

        _customer.save(customer);

        model.addAttribute("result", new Result(true, "Account created successfully."));
        return "signup";
    }
}