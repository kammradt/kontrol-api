package com.kammradt.learning.dto;

import com.kammradt.learning.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter @Setter
public class UserUpdateProfileDTO {

    private String name;

    @Email(message = "Invalid email")
    private String email;

    public User toUser() {
        return new User(null, this.name, this.email, null, null, null, null);
    }

}
