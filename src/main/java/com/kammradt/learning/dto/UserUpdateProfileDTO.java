package com.kammradt.learning.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter @Setter
public class UserUpdateProfileDTO {

    private String name;

    @Email(message = "Invalid email")
    private String email;

}
