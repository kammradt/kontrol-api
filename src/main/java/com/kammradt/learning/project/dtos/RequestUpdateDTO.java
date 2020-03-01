package com.kammradt.learning.project.dtos;

import com.kammradt.learning.file.entities.RequestFile;
import com.kammradt.learning.project.entities.Project;
import com.kammradt.learning.task.entities.Status;
import com.kammradt.learning.task.entities.Task;
import com.kammradt.learning.user.entities.User;
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
public class RequestUpdateDTO {
    @NotBlank(message = "Subject is required")
    private String subject;
    private String description;

    @NotNull(message = "State is required")
    private Status state;

    @NotNull(message = "User is required")
    private User user;

    @Future
    private Date start;

    @Future
    private Date end;

    private List<Task> stages = new ArrayList<>();
    private List<RequestFile> files = new ArrayList<>();

    public Project toRequest() {
        return Project.builder()
                .subject(subject)
                .description(description)
                .state(state)
                .user(user)
                .start(start)
                .end(end)
                .stages(stages)
                .files(files)
                .build();
    }
}
