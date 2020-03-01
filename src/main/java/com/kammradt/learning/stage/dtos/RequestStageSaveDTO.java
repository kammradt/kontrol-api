package com.kammradt.learning.stage.dtos;

import com.kammradt.learning.request.entities.Request;
import com.kammradt.learning.stage.entities.RequestStage;
import com.kammradt.learning.user.entities.User;
import com.kammradt.learning.stage.entities.RequestState;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    public RequestStage toRequestStage() {
        return RequestStage.builder()
                .description(description)
                .user(user)
                .request(request)
                .state(state)
                .build();
    }

}
