package com.kammradt.learning.project.dtos;

import com.kammradt.learning.file.entities.File;
import com.kammradt.learning.task.entities.Status;
import com.kammradt.learning.task.entities.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectUpdateDTO {

    @NotBlank(message = "Subject is required")
    private String title;
    private String description;

    @NotNull(message = "State is required")
    private Status status;

    @Future
    private Date start;

    @Future
    private Date end;

    private List<Task> tasks = new ArrayList<>();
    private List<File> files = new ArrayList<>();

}
