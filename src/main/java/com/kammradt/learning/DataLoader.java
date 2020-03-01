package com.kammradt.learning;

import com.kammradt.learning.project.ProjectMapper;
import com.kammradt.learning.project.ProjectService;
import com.kammradt.learning.project.dtos.ProjectSaveDTO;
import com.kammradt.learning.task.TaskMapper;
import com.kammradt.learning.task.TaskService;
import com.kammradt.learning.task.dtos.TaskSaveDTO;
import com.kammradt.learning.task.entities.Status;
import com.kammradt.learning.user.UserService;
import com.kammradt.learning.user.dtos.UserSaveDTO;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AllArgsConstructor
public class DataLoader implements ApplicationRunner {

    private UserService userService;
    private ProjectService projectService;
    private ProjectMapper projectMapper;
    private TaskService taskService;
    private TaskMapper taskMapper;

    @Override
    public void run(ApplicationArguments args) {

        if (databaseIsEmpty())
            insertOneUserAndSomeProjects();
    }

    private void insertOneUserAndSomeProjects() {
        var vini = UserSaveDTO.builder()
                .name("Vinicius Kammradt")
                .email("vinicius.kammradt@email.com")
                .password("12345678")
                .build()
                .toUser();
        userService.save(vini);

        var macbook = ProjectSaveDTO.builder()
                .title("My Macbook PRO")
                .description("I'm buying a new Macbook and I'm really happy")
                .start(new Date())
                .end(new Date(new Date().getTime() + 1000 * 60 * 60 * 10))
                .build();
        var savedMacbook = projectService.save(projectMapper.toProject(macbook));

        taskService.save(taskMapper.toTask(TaskSaveDTO.builder()
                .description("I'm getting the money to buy")
                .project(savedMacbook)
                .status(Status.STARTED)
                .start(new Date())
                .end(new Date(new Date().getTime() + 1000 * 60 * 60 * 10))
                .build()));
        taskService.save(taskMapper.toTask(TaskSaveDTO.builder()
                .description("I Bought and waiting")
                .project(savedMacbook)
                .status(Status.IN_PROGRESS)
                .start(new Date())
                .end(new Date(new Date().getTime() + 1000 * 60 * 60 * 10))
                .build()));
        taskService.save(taskMapper.toTask(TaskSaveDTO.builder()
                .description("Arrived at my house!")
                .project(savedMacbook)
                .status(Status.DONE)
                .start(new Date())
                .end(new Date(new Date().getTime() + 1000 * 60 * 60 * 10))
                .build()));
    }

    private boolean databaseIsEmpty() {
        return userService.count() == 0L;
    }
}
