package com.kammradt.learning.security;

import com.kammradt.learning.project.ProjectService;
import com.kammradt.learning.project.entities.Project;
import com.kammradt.learning.task.TaskService;
import com.kammradt.learning.task.entities.Task;
import com.kammradt.learning.user.UserService;
import com.kammradt.learning.user.entities.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("resourceAccessManager")
public class ResourceAccessManager {

    private UserService userService;
    private ProjectService projectService;
    private TaskService taskService;

    public ResourceAccessManager(@Lazy UserService userService, ProjectService projectService, TaskService taskService) {
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    public boolean isOwnUser(Long userId) {
        User user = getCurrentUser();

        return user.getId().equals(userId);
    }

    public boolean isRequestOwner(Long resourceId) {
        User user = getCurrentUser();
        Project project = projectService.findById(resourceId);

        return user.getId().equals(project.getUser().getId());
    }

    public boolean isRequestStageOwner(Long resourceId) {
        User user = getCurrentUser();
        Task task = taskService.findById(resourceId);

        return user.getId().equals(task.getUser().getId());
    }


    public User getCurrentUser() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByEmail(email);
    }
}
