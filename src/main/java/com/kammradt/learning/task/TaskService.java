package com.kammradt.learning.task;

import com.kammradt.learning.exception.exceptions.NotFoundException;
import com.kammradt.learning.project.ProjectRepository;
import com.kammradt.learning.project.ProjectService;
import com.kammradt.learning.project.entities.Project;
import com.kammradt.learning.task.entities.Status;
import com.kammradt.learning.task.entities.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository requestStageRepository;
    private ProjectRepository projectRepository;
    private ProjectService projectService;


    public Task save(Task task) {
        Long requestBeingUpdated = task.getProject().getId();
        projectService.verifyIfRequestCanBeUpdated(requestBeingUpdated);

        task.setRealizationDate(new Date());
        Task savedTask = requestStageRepository.save(task);

        projectRepository.updateRequestState(requestBeingUpdated, task.getState());

        return savedTask;
    }

    public Task findById(Long id) {
        return requestStageRepository.findById(id).orElseThrow(() -> new NotFoundException("There are no RequestStage with this ID"));
    }

    public List<Task> findAllByRequestId(Long requestId) {
        return requestStageRepository.findAllByProjectId(requestId);
    }

    private Project updateState(Long id, Status newState) {
        Project project = projectService.findById(id);
        project.setState(newState);
        return projectRepository.save(project);
    }

    public void deleteById(Long id) {
        Project toBeUpdated = findById(id).getProject();
        requestStageRepository.deleteById(id);

        Status newState = getNewStateAfterDeletion(toBeUpdated.getId());

        updateState(toBeUpdated.getId(), newState);
    }

    private Status getNewStateAfterDeletion(Long requestId) {
        List<Task> stages = findAllByRequestId(requestId);

        Status newState;
        if (stages.isEmpty())
            newState = Status.OPEN;
        else if (stages.size() == 1)
            newState = stages.get(0).getState();
        else if (stages.stream().anyMatch(stage -> stage.getState() == Status.CLOSED))
            newState = Status.CLOSED;
        else
            newState = Status.IN_PROGRESS;

        return newState;
    }


}
