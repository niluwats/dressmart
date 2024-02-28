package com.nw.dressmart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequestDto {
    @NotBlank(message = "field email cannot be empty")
    @NotNull(message = "field email is null")
    private String email;

    @NotBlank(message = "field password cannot be empty")
    @NotNull(message = "field password is null")
    private String password;
}
