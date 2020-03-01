package com.kammradt.learning.project.dtos;

import com.kammradt.learning.file.entities.RequestFile;
import com.kammradt.learning.project.entities.Project;
import com.kammradt.learning.task.entities.Status;
import com.kammradt.learning.task.entities.Task;
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
    private Status state;
    private Date start;
    private Date end;
    private List<Task> stages;
    private List<RequestFile> files;
    private boolean willBeDelayed;
    private Float completionPercentage;

    public static RequestResponse build(Project project) {
        return RequestResponse.builder()
                .id(project.getId())
                .subject(project.getSubject())
                .description(project.getDescription())
                .creationDate(project.getCreationDate())
                .user(project.getUser())
                .state(project.getState())
                .start(project.getStart())
                .end(project.getEnd())
                .stages(project.getStages())
                .files(project.getFiles())
                .willBeDelayed(willBeDelayed(project))
                .completionPercentage(formatPercentage(project))
                .build();
    }

    private static boolean willBeDelayed(Project project) {
        return project.getStages().stream().anyMatch(r -> r.getEnd().after(project.getEnd()));
    }

    private static Float formatPercentage(Project project) {
        DecimalFormat df2 = new DecimalFormat("#.##");
        Float numberOfDone = (float) Math.toIntExact(project.getStages().stream().filter(r -> r.getState().equals(Status.CLOSED)).count());
        Float numberOfStages = (float) project.getStages().size();
        Float percent = ((numberOfDone / numberOfStages) * 100);
        df2.format(percent);
        return percent;
    }

}
