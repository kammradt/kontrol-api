package com.kammradt.learning.project.dtos;

import com.kammradt.learning.file.entities.File;
import com.kammradt.learning.project.entities.Project;
import com.kammradt.learning.task.entities.Status;
import com.kammradt.learning.task.entities.Task;
import com.kammradt.learning.user.entities.User;
import lombok.*;

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
@Builder
public class ProjectSaveDTO {

    @NotBlank(message = "Subject is required")
    private String title;
    private String description;

    @NotNull(message = "User is required")
    private User user;

    @Future
    private Date start;

    @Future
    private Date end;

    private List<Task> tasks = new ArrayList<>();
    private List<File> files = new ArrayList<>();

    public Project toProject() {
        return Project.builder()
                .title(title)
                .description(description)
                .user(user)
                .tasks(tasks)
                .files(files)
                .start(start)
                .end(end)
                .status(Status.OPEN)
                .creationDate(new Date())
                .build();
    }

}
