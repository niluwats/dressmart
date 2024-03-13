package com.nw.dressmart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Oauth2UserDto {
    private String firstName;
    private String lastName;
    private String email;
}
