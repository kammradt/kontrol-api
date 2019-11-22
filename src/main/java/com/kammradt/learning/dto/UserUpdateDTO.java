package com.kammradt.learning.dto;

import com.kammradt.learning.domain.Request;
import com.kammradt.learning.domain.RequestStage;
import com.kammradt.learning.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class UserUpdateDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email")
    private String email;

    @Size(min = 8, max = 99, message = "Password must be between 8 and 99 characters")
    private String password;

    private List<Request> requests = new ArrayList<>();
    private List<RequestStage> stages = new ArrayList<>();


    public User toUser() {
        return new User(null, this.name, this.email, this.password, null, this.requests, this.stages);
    }
}