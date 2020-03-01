package com.kammradt.learning.task;

import com.kammradt.learning.security.ResourceAccessManager;
import com.kammradt.learning.task.dtos.TaskSaveDTO;
import com.kammradt.learning.task.entities.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AllArgsConstructor
public class TaskMapper {

    private ResourceAccessManager resourceAccessManager;

    public Task toTask(TaskSaveDTO dto) {
        return Task.builder()
                .description(dto.getDescription())
                .user(resourceAccessManager.getCurrentUser())
                .project(dto.getProject())
                .creationDate(new Date())
                .status(dto.getStatus())
                .start(dto.getStart())
                .end(dto.getEnd())
                .build();
    }
}
