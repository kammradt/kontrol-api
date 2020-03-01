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

    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;
    private ProjectService projectService;


    public Task save(Task task) {
        Long projectBeingUpdated = task.getProject().getId();
        projectService.verifyIfProjectCanBeUpdated(projectBeingUpdated);

        task.setCreationDate(new Date());
        Task savedTask = taskRepository.save(task);

        projectRepository.updateProjectStatus(projectBeingUpdated, task.getStatus());

        return savedTask;
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new NotFoundException("There are no Task with this ID"));
    }

    public List<Task> findAllByProjectId(Long requestId) {
        return taskRepository.findAllByProjectId(requestId);
    }

    private Project updateStatus(Long id, Status newStatus) {
        Project project = projectService.findById(id);
        project.setStatus(newStatus);
        return projectRepository.save(project);
    }

    public void deleteById(Long id) {
        Project toBeUpdated = findById(id).getProject();
        taskRepository.deleteById(id);

        Status newState = getNewStateAfterDeletion(toBeUpdated.getId());

        updateStatus(toBeUpdated.getId(), newState);
    }

    private Status getNewStateAfterDeletion(Long requestId) {
        List<Task> tasks = findAllByProjectId(requestId);

        Status newStatus;
        if (tasks.isEmpty())
            newStatus = Status.STARTED;
        else if (tasks.size() == 1)
            newStatus = tasks.get(0).getStatus();
        else if (tasks.stream().anyMatch(stage -> stage.getStatus() == Status.DONE))
            newStatus = Status.DONE;
        else
            newStatus = Status.IN_PROGRESS;

        return newStatus;
    }


}
