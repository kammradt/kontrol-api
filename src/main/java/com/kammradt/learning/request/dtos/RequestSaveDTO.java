package com.kammradt.learning.request.dtos;

import com.kammradt.learning.request.entities.Request;
import com.kammradt.learning.file.entities.RequestFile;
import com.kammradt.learning.stage.entities.RequestStage;
import com.kammradt.learning.user.entities.User;
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
    private List<RequestFile> files = new ArrayList<>();

    public Request toRequest() {
        return new Request(null, this.subject, this.description, null, this.user, null, this.stages, this.files);
    }

}
