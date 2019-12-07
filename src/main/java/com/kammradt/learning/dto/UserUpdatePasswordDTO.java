package com.kammradt.learning.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class UserUpdatePasswordDTO {

    @Size(min = 8, max = 99, message = "Password must be between 8 and 99 characters")
    private String password;

    @Size(min = 8, max = 99, message = "Confirmation Password must be between 8 and 99 characters")
    private String confirmationPassword;

}
