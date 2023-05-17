package com.clothesdelivery.web.controllers;

import com.clothesdelivery.web.enums.Role;
import com.clothesdelivery.web.models.Result;
import com.clothesdelivery.web.models.SignUp;
import com.clothesdelivery.web.repositories.IAddressRepository;
import com.clothesdelivery.web.repositories.IUserRepository;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {
    @Autowired
    private IUserRepository _user;

    @Autowired
    private IAddressRepository _address;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(@NotNull Model model) {
        model.addAttribute("result", new Result());
        model.addAttribute("signup", new SignUp());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("signup") SignUp signup, @NotNull BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("result", new Result());
            return "signup";
        }

        var address = signup.toAddressEntity();

        signup.setPassword(passwordEncoder.encode(signup.getPassword()));

        var user = signup.toUserEntity();

        if(_user.existsByEmailOrPhoneNumber(user.getEmail(), user.getPhoneNumber())) {
            model.addAttribute("result", new Result(false, "Phone number or email already in use."));
            return "signup";
        }

        var savedAddress = _address.save(address);

        user.setRole(Role.Customer);
        user.setAddressId(savedAddress.getId());

        _user.save(user);

        model.addAttribute("result", new Result(true, "Account created successfully."));
        return "redirect:/login";
    }
}