package com.kammradt.learning.domain;

import com.kammradt.learning.domain.enums.RequestState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RequestStage {
    private Long id;
    private String description;
    private Date realizationDate;

    private User user;
    private Request request;
    private RequestState state;



}
