package com.kammradt.learning.security;

import com.kammradt.learning.project.ProjectService;
import com.kammradt.learning.project.entities.Project;
import com.kammradt.learning.task.TaskService;
import com.kammradt.learning.task.entities.Task;
import com.kammradt.learning.user.UserService;
import com.kammradt.learning.user.entities.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("resourceAccessManager")
public class ResourceAccessManager {

    private UserService userService;
    private ProjectService projectService;
    private TaskService taskService;

    public ResourceAccessManager(@Lazy UserService userService, @Lazy ProjectService projectService, @Lazy TaskService taskService) {
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    public boolean isOwnUser(Long userId) {
        User user = getCurrentUser();

        return user.getId().equals(userId);
    }

    public boolean isProjectOwner(Long resourceId) {
        User user = getCurrentUser();
        Project project = projectService.findById(resourceId);

        return user.getId().equals(project.getUser().getId());
    }

    public boolean isTaskOwner(Long resourceId) {
        User user = getCurrentUser();
        Task task = taskService.findById(resourceId);

        return user.getId().equals(task.getUser().getId());
    }


    public User getCurrentUser() {
        String email = null; // (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (email == null)
            email = "vinicius.kammradt@email.com";
        return userService.findByEmail(email);
    }
}
