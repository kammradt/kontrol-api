package com.kammradt.learning.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kammradt.learning.file.entities.RequestFile;
import com.kammradt.learning.task.entities.Status;
import com.kammradt.learning.task.entities.Task;
import com.kammradt.learning.user.entities.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String subject;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;


    @Getter(onMethod = @__({@JsonIgnore}))
    @Setter(onMethod = @__({@JsonProperty}))
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 12, nullable = false)
    @Enumerated(EnumType.STRING)
    private Status state; // TODO change to status

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Task> stages = new ArrayList<>(); // TODO change to tasks

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<RequestFile> files = new ArrayList<>();

}
