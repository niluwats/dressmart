package com.nw.dressmart.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterRequestDto {
    @NotBlank(message = "field firstName cannot be empty")
    @NotNull(message = "field firstName is null")
    @Size(min = 2,message = "name should have at least 2 characters")
    private String firstName;

    @NotBlank(message = "field lastName cannot be empty")
    @NotNull(message = "field lastName is null")
    @Size(min = 2,message = "name should have at least 2 characters")
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 8,message = "password should have at least 8 characters")
    private String password;
}
