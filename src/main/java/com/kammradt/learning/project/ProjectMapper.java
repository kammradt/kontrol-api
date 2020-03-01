package com.kammradt.learning.project;

import com.kammradt.learning.project.dtos.ProjectResponse;
import com.kammradt.learning.project.dtos.ProjectSaveDTO;
import com.kammradt.learning.project.dtos.ProjectUpdateDTO;
import com.kammradt.learning.project.entities.Project;
import com.kammradt.learning.security.ResourceAccessManager;
import com.kammradt.learning.task.entities.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProjectMapper {

    private ResourceAccessManager resourceAccessManager;

    public Project toProject(ProjectSaveDTO dto) {
        return Project.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .user(resourceAccessManager.getCurrentUser())
                .tasks(dto.getTasks())
                .files(dto.getFiles())
                .start(dto.getStart())
                .end(dto.getEnd())
                .status(Status.STARTED)
                .creationDate(new Date())
                .build();
    }

    public Project toProject(ProjectUpdateDTO dto) {
        return Project.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .user(resourceAccessManager.getCurrentUser())
                .creationDate(new Date())
                .start(dto.getStart())
                .end(dto.getEnd())
                .tasks(dto.getTasks())
                .files(dto.getFiles())
                .build();
    }

    public List<ProjectResponse> toResponseList(List<Project> projects) {
        return projects.stream()
                .map(this::build)
                .collect(Collectors.toList());
    }

    public ProjectResponse build(Project project) {
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

    private boolean willBeDelayed(Project project) {
        return project.getTasks().stream().anyMatch(r -> r.getEnd().after(project.getEnd()));
    }

    private Float formatPercentage(Project project) {
        DecimalFormat df2 = new DecimalFormat("#.##");
        Float numberOfDone = (float) Math.toIntExact(project.getTasks().stream().filter(r -> r.getStatus().equals(Status.DONE)).count());
        Float numberOfStages = (float) project.getTasks().size();
        Float percent = ((numberOfDone / numberOfStages) * 100);
        return Float.valueOf(df2.format(percent));
    }
}
