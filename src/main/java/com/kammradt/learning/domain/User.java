package com.kammradt.learning.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;

    private List<Request> requests = new ArrayList<>();
    private List<RequestStage> stages = new ArrayList<>();


}
