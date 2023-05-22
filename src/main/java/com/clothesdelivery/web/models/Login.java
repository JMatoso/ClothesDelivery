package com.clothesdelivery.web.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Login {
    @Email(message = "Insert a valid email address.")
    @NotEmpty(message = "Email is a required field.")
    private String email;

    @NotEmpty(message = "Password is a required field.")
    private String password;
}
