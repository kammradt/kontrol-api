package com.kammradt.learning.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class UserUpdatePasswordDTO {

    @NotBlank(message = "Insert a valid password")
    @Size(min = 8, max = 99, message = "Password must be between 8 and 99 characters")
    private String password;

    @NotBlank(message = "Insert a valid password confirmation")
    @Size(min = 8, max = 99, message = "Confirmation Password must be between 8 and 99 characters")
    private String confirmationPassword;

}
