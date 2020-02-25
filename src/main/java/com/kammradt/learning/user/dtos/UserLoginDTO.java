package com.kammradt.learning.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UserLoginDTO {

    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Password required")
    private String password;
}
