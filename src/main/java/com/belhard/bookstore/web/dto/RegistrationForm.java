package com.belhard.bookstore.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
public class RegistrationForm {

    @NotBlank(message = "Name: ")
    @Size(max = 100, message = "Name is too long")
    private String name;

    @NotBlank(message = "Surname: ")
    @Size(max = 100, message = "Surname is too long")
    private String surname;

    @NotBlank(message = "Email: ")
    @Email(message = "It is not Email")
    @Size(max = 255, message = "Email is too long")
    private String email;

    @ToString.Exclude
    @NotBlank(message = "Password")
    @Size(min = 8, max = 80, message = "Password need to be betweeen 8 and 80 symbols")
    private String password;

    @ToString.Exclude
    @NotBlank(message = "Repeat password")
    private String confirmPassword;
}
