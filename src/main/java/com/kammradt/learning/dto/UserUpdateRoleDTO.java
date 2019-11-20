package com.kammradt.learning.dto;

import com.kammradt.learning.domain.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class UserUpdateRoleDTO {

    @NotNull(message = "Role is required")
    private Role role;
}
