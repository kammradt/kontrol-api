package com.kammradt.learning.dto;

import com.kammradt.learning.domain.Request;
import com.kammradt.learning.domain.RequestStage;
import com.kammradt.learning.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestSaveDTO {

    @NotBlank(message = "Subject is required")
    private String subject;
    private String description;

    @NotNull(message = "User is required")
    private User user;

    private List<RequestStage> stages = new ArrayList<>();

    public Request toRequest() {
        return new Request(null, this.subject, this.description, null, this.user, null, this.stages);
    }

}
