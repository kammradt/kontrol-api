package com.kammradt.learning.stage.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kammradt.learning.request.entities.Request;
import com.kammradt.learning.user.entities.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@Entity(name = "request_stage")
public class RequestStage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text", nullable = false)
    private String description;

    @Column(name = "realization_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date realizationDate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    @Getter(onMethod = @__({@JsonIgnore}))
    @Setter(onMethod = @__({@JsonProperty}))
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Getter(onMethod = @__({@JsonIgnore}))
    @Setter(onMethod = @__({@JsonProperty}))
    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private Request request;

    @Column(length = 12, nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestState state;



}
