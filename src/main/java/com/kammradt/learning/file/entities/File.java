package com.kammradt.learning.file.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kammradt.learning.file.FileListener;
import com.kammradt.learning.project.entities.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(FileListener.class)
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String S3Name;

    @Column(nullable = false, columnDefinition = "text")
    private String location;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @Getter(onMethod = @__({@JsonIgnore}))
    private Project project;

}
