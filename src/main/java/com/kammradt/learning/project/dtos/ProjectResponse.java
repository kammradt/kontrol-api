package com.kammradt.learning.project.dtos;

import com.kammradt.learning.file.entities.File;
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
public class ProjectResponse implements Serializable {

    private Long id;
    private String title;
    private String description;
    private Date creationDate;
    private User user;
    private Status status;
    private Date start;
    private Date end;
    private List<Task> tasks;
    private List<File> files;
    private boolean willBeDelayed;
    private Float completionPercentage;

    public static ProjectResponse build(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .creationDate(project.getCreationDate())
                .user(project.getUser())
                .status(project.getStatus())
                .start(project.getStart())
                .end(project.getEnd())
                .tasks(project.getTasks())
                .files(project.getFiles())
                .willBeDelayed(willBeDelayed(project))
                .completionPercentage(formatPercentage(project))
                .build();
    }

    private static boolean willBeDelayed(Project project) {
        return project.getTasks().stream().anyMatch(r -> r.getEnd().after(project.getEnd()));
    }

    private static Float formatPercentage(Project project) {
        DecimalFormat df2 = new DecimalFormat("#.##");
        Float numberOfDone = (float) Math.toIntExact(project.getTasks().stream().filter(r -> r.getStatus().equals(Status.DONE)).count());
        Float numberOfStages = (float) project.getTasks().size();
        Float percent = ((numberOfDone / numberOfStages) * 100);
        df2.format(percent);
        return percent;
    }

}
