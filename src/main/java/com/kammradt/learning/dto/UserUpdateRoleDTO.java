package com.kammradt.learning.dto;

import com.kammradt.learning.domain.User;
import com.kammradt.learning.domain.enums.Role;
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
