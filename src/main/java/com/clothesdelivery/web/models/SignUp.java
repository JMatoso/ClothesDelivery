package com.clothesdelivery.web.models;

import com.clothesdelivery.web.entities.Address;
import com.clothesdelivery.web.entities.Customer;
import com.clothesdelivery.web.services.Crypto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SignUp {
    @NotEmpty(message = "Name is a required field.")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 chars.")
    private String name;

    @Email(message = "Insert a valid email address.")
    @NotEmpty(message = "Email is a required field.")
    private String email;

    @NotEmpty(message = "Password is a required field.")
    private String password;

    private LocalDate birthdate;

    @NotEmpty(message = "Phone Number is a required field.")
    @Size(min = 6, max = 15, message = "Phone must be between 6 and 15 chars.")
    private String phoneNumber;

    @NotEmpty(message = "Address is a required field.")
    @Size(max = 100, message = "Address must be below 100 chars.")
    private String address;

    @NotEmpty(message = "City is a required field.")
    @Size(max = 50, message = "City must be below 50 chars.")
    private String city;

    @NotEmpty(message = "Country is a required field.")
    @Size(max = 50, message = "Country must be below 50 chars.")
    private String country = "Angola";

    public Customer toCustomerEntity() {
        var customer = new Customer();

        customer.setName(name);
        customer.setEmail(email);
        customer.setBirthdate(birthdate);
        customer.setPhoneNumber(phoneNumber);
        customer.setPassword(Crypto.apply(password));

        return customer;
    }

    public Address toAddressEntity() {
        var addressEntity = new Address();

        addressEntity.setCity(city);
        addressEntity.setCountry(country);
        addressEntity.setAddress(address);
        addressEntity.setCreatedTime(LocalDateTime.now());

        return addressEntity;
    }
}
