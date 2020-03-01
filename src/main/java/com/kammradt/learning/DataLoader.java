package com.kammradt.learning;

import com.kammradt.learning.project.ProjectService;
import com.kammradt.learning.project.dtos.RequestSaveDTO;
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
    private TaskService taskService;

    @Override
    public void run(ApplicationArguments args) {

        if (databaseIsEmpty())
            insertOneUserAndSomeRequests();
    }

    private void insertOneUserAndSomeRequests() {
        var vini = UserSaveDTO.builder()
                .name("Vinicius Kammradt")
                .email("vinicius.kammradt@email.com")
                .password("12345678")
                .build()
                .toUser();
        var savedVini = userService.save(vini);

        var macbook = RequestSaveDTO.builder()
                .subject("My Macbook PRO")
                .description("I'm buying a new Macbook and I'm really happy")
                .user(savedVini)
                .start(new Date())
                .end(new Date(new Date().getTime() + 1000 * 60 * 60 * 10))
                .build()
                .toRequest();
        var savedMacbook = projectService.save(macbook);

        taskService.save(TaskSaveDTO.builder()
                .description("I'm getting the money to buy")
                .user(savedVini)
                .project(savedMacbook)
                .state(Status.OPEN)
                .start(new Date())
                .end(new Date(new Date().getTime() + 1000 * 60 * 60 * 10))
                .build()
                .toTask()
        );
        taskService.save(TaskSaveDTO.builder()
                .description("I Bought and waiting")
                .user(savedVini)
                .project(savedMacbook)
                .state(Status.IN_PROGRESS)
                .start(new Date())
                .end(new Date(new Date().getTime() + 1000 * 60 * 60 * 10))
                .build()
                .toTask()
        );
        taskService.save(TaskSaveDTO.builder()
                .description("Arrived at my house!")
                .user(savedVini)
                .project(savedMacbook)
                .state(Status.CLOSED)
                .start(new Date())
                .end(new Date(new Date().getTime() + 1000 * 60 * 60 * 10))
                .build()
                .toTask()
        );
    }

    private boolean databaseIsEmpty() {
        return userService.count() == 0L;
    }
}
