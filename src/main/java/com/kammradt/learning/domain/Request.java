package com.kammradt.learning.domain;

import com.kammradt.learning.domain.enums.RequestState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Request {
    private Long id;
    private String subject;
    private String description;
    private Date creationDate;

    private User user;
    private RequestState state;
    private List<RequestStage> stages = new ArrayList<>();
}
