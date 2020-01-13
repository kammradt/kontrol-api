package com.kammradt.learning.dto;

import com.kammradt.learning.domain.User;
import com.kammradt.learning.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email")
    private String email;

    @Size(min = 7, max = 99, message = "Password must be between 8 and 99 characteres")
    private String password;

    public User toUser() {
        return new User(null, this.name, this.email, this.password, Role.REGULAR, null, null);
    }

}
