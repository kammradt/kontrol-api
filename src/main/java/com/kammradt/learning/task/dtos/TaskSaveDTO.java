package com.kammradt.learning.task.dtos;

import com.kammradt.learning.project.entities.Project;
import com.kammradt.learning.task.entities.Status;
import com.kammradt.learning.task.entities.Task;
import com.kammradt.learning.user.entities.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TaskSaveDTO {

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "User is required")
    private User user;

    @NotNull(message = "Project is required")
    private Project project;

    @NotNull(message = "State is required")
    private Status status;

    @NotNull(message = "Start date is required")
    private Date start;

    @NotNull(message = "End date is required")
    private Date end;

    public Task toTask() {
        return Task.builder()
                .description(description)
                .user(user)
                .project(project)
                .status(status)
                .start(start)
                .end(end)
                .build();
    }

}
