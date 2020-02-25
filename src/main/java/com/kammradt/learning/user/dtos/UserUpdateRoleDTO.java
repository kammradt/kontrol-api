package com.kammradt.learning.user.dtos;

import com.kammradt.learning.user.entities.Role;
import com.kammradt.learning.user.entities.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class UserUpdateRoleDTO {

    @NotNull(message = "Role is required")
    private Role role;

    public User toUser() {
        return new User(null, null, null, null, this.role, null, null);
    }
}
