package com.kammradt.learning.request.dtos;

import com.kammradt.learning.request.entities.Request;
import com.kammradt.learning.file.entities.RequestFile;
import com.kammradt.learning.stage.entities.RequestStage;
import com.kammradt.learning.stage.entities.RequestState;
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

    private List<RequestStage> stages = new ArrayList<>();
    private List<RequestFile> files = new ArrayList<>();

    public Request toRequest() {
        return Request.builder()
                .subject(subject)
                .description(description)
                .user(user)
                .stages(stages)
                .files(files)
                .start(start)
                .end(end)
                .state(RequestState.OPEN)
                .creationDate(new Date())
                .build();
    }

}
