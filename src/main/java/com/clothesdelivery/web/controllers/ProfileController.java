package com.clothesdelivery.web.controllers;

import com.clothesdelivery.web.repositories.IAddressRepository;
import com.clothesdelivery.web.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController extends BaseController {
    @Autowired
    private IUserRepository _user;

    @Autowired
    private IAddressRepository _addresses;

    @GetMapping("/profile")
    public String profile(Model model) {
        var user = getAuthenticatedUser();

        if(user == null) return notFound;

        var address = _addresses.findById(user.getAddressId());

        model.addAttribute("user", user);
        model.addAttribute("address", address.orElse(null));
        return "profile";
    }
}
