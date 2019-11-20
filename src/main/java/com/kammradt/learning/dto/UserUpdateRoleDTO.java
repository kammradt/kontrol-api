package com.kammradt.learning.dto;

import com.kammradt.learning.domain.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserUpdateRoleDTO {
    private Role role;
}
