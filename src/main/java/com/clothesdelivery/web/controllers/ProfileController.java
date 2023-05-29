package com.clothesdelivery.web.controllers;

import com.clothesdelivery.web.entities.Address;
import com.clothesdelivery.web.repositories.IAddressRepository;
import com.clothesdelivery.web.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Controller
public class ProfileController extends BaseController {
    @Autowired
    private IUserRepository _user;

    @Autowired
    private IAddressRepository _addresses;

    @GetMapping("/profile")
    public String profile(Model model) {
        var user = getAuthenticatedUser();

        if(user == null) return login;

        var address = _addresses.findById(user.getAddressId());
        model.addAttribute("authenticatedUser", user);
        model.addAttribute("address", address.orElse(null));
        return "profile/profile";
    }

    @PostMapping("/profile")
    public String profile(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "birthdate") String birthdate,
            @RequestParam(value = "phoneNumber") String phoneNumber,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "city") String city,
            @RequestParam(value = "address") String address) {
        var user = getAuthenticatedUser();

        if(user == null) return login;

        var userSavedAddress = _addresses.findById(user.getAddressId());

        if(userSavedAddress.isEmpty()) {
            var newUserAddress = new Address();

            newUserAddress.setCreatedTime(LocalDateTime.now());
            newUserAddress.setAddress(address);
            newUserAddress.setCity(city);
            newUserAddress.setCountry("Angola");

            var newUserSavedAddress = _addresses.save(newUserAddress);
            user.setAddressId(newUserSavedAddress.getId());
        } else {
            userSavedAddress.get().setCity(city);
            userSavedAddress.get().setAddress(address);
            _addresses.save(userSavedAddress.get());
        }

        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setBirthdate(LocalDate.parse(birthdate));

        if(!Objects.equals(user.getEmail(), email)) {
            user.setEmail(email);
            _user.save(user);
            return redirect("logout?logout");
        }

        _user.save(user);

        return redirect("profile");
    }
}
