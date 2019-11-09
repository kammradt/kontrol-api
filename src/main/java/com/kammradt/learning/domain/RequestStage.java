package com.kammradt.learning.domain;

import com.kammradt.learning.domain.enums.RequestState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity(name = "request_stage")
public class RequestStage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "realization_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date realizationDate;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private Request request;

    @Column(length = 12, nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestState state;



}
