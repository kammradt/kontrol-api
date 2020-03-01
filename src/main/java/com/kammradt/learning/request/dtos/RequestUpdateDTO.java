package com.kammradt.learning.request.dtos;

import com.kammradt.learning.request.entities.Request;
import com.kammradt.learning.file.entities.RequestFile;
import com.kammradt.learning.stage.entities.RequestStage;
import com.kammradt.learning.user.entities.User;
import com.kammradt.learning.stage.entities.RequestState;
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
    private RequestState state;

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
                .state(state)
                .user(user)
                .start(start)
                .end(end)
                .stages(stages)
                .files(files)
                .build();
    }
}
