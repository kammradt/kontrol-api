package com.kammradt.learning.project.dtos;

import com.kammradt.learning.file.entities.RequestFile;
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
public class RequestSaveDTO {

    @NotBlank(message = "Subject is required")
    private String subject;
    private String description;

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
                .user(user)
                .stages(stages)
                .files(files)
                .start(start)
                .end(end)
                .state(Status.OPEN)
                .creationDate(new Date())
                .build();
    }

}
