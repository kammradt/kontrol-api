package com.kammradt.learning.stage.dtos;

import com.kammradt.learning.request.entities.Request;
import com.kammradt.learning.stage.entities.RequestStage;
import com.kammradt.learning.user.entities.User;
import com.kammradt.learning.stage.entities.RequestState;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class RequestStageSaveDTO {

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "User is required")
    private User user;

    @NotNull(message = "Request is required")
    private Request request;

    @NotNull(message = "State is required")
    private RequestState state;

    @NotNull(message = "Start date is required")
    private Date start;

    @NotNull(message = "End date is required")
    private Date end;

    public RequestStage toRequestStage() {
        return RequestStage.builder()
                .description(description)
                .user(user)
                .request(request)
                .state(state)
                .start(start)
                .end(end)
                .build();
    }

}
