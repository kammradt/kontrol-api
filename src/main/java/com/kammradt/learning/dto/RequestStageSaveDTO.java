package com.kammradt.learning.dto;

import com.kammradt.learning.domain.Request;
import com.kammradt.learning.domain.RequestStage;
import com.kammradt.learning.domain.User;
import com.kammradt.learning.domain.enums.RequestState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
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
        return new RequestStage(null, this.description, null, this.user, this.request, this.state);
    }

}
