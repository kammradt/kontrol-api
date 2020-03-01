package com.kammradt.learning.user.dtos;

import com.kammradt.learning.user.entities.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class UserUpdateProfileDTO {

    private String name;

    @Email(message = "Invalid email")
    private String email;

    public User toUser() {
        return new User(null, this.name, this.email, null, null, null, null);
    }

}
