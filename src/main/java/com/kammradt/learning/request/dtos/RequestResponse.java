package com.kammradt.learning.request.dtos;

import com.kammradt.learning.file.entities.RequestFile;
import com.kammradt.learning.request.entities.Request;
import com.kammradt.learning.stage.entities.RequestStage;
import com.kammradt.learning.stage.entities.RequestState;
import com.kammradt.learning.user.entities.User;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@Builder
@Data
public class RequestResponse implements Serializable {

    private Long id;
    private String subject;
    private String description;
    private Date creationDate;
    private User user;
    private RequestState state;
    private Date start;
    private Date end;
    private List<RequestStage> stages;
    private List<RequestFile> files;
    private boolean willBeDelayed;
    private Float completionPercentage;

    public static RequestResponse build(Request request) {
        return RequestResponse.builder()
                .id(request.getId())
                .subject(request.getSubject())
                .description(request.getDescription())
                .creationDate(request.getCreationDate())
                .user(request.getUser())
                .state(request.getState())
                .start(request.getStart())
                .end(request.getEnd())
                .stages(request.getStages())
                .files(request.getFiles())
                .willBeDelayed(willBeDelayed(request))
                .completionPercentage(formatPercentage(request))
                .build();
    }

    private static boolean willBeDelayed(Request request) {
        return request.getStages().stream().anyMatch(r -> r.getEnd().after(request.getEnd()));
    }

    private static Float formatPercentage(Request request) {
        DecimalFormat df2 = new DecimalFormat("#.##");
        Float numberOfDone = (float) Math.toIntExact(request.getStages().stream().filter(r -> r.getState().equals(RequestState.CLOSED)).count());
        Float numberOfStages = (float) request.getStages().size();
        Float percent = ((numberOfDone / numberOfStages) * 100);
        df2.format(percent);
        return percent;
    }

}
