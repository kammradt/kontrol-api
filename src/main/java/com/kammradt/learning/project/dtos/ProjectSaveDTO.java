package com.kammradt.learning.project.dtos;

import com.kammradt.learning.file.entities.File;
import com.kammradt.learning.task.entities.Task;
import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectSaveDTO {

    @NotBlank(message = "Subject is required")
    private String title;
    private String description;

    @Future
    private Date start;

    @Future
    private Date end;

    private List<Task> tasks = new ArrayList<>();
    private List<File> files = new ArrayList<>();

}
