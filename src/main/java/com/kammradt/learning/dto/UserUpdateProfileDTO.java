package com.kammradt.learning.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class UserUpdateProfileDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    @Email(message = "Invalid email")
    private String email;

}
