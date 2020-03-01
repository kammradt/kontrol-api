package com.kammradt.learning;

import com.kammradt.learning.request.RequestService;
import com.kammradt.learning.request.dtos.RequestSaveDTO;
import com.kammradt.learning.request.entities.Request;
import com.kammradt.learning.stage.RequestStageService;
import com.kammradt.learning.stage.dtos.RequestStageSaveDTO;
import com.kammradt.learning.stage.entities.RequestState;
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
    private RequestService requestService;
    private RequestStageService requestStageService;

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
        var savedMacbook = requestService.save(macbook);

        requestStageService.save(RequestStageSaveDTO.builder()
                .description("I'm getting the money to buy")
                .user(savedVini)
                .request(savedMacbook)
                .state(RequestState.OPEN)
                .build()
                .toRequestStage()
        );
        requestStageService.save(RequestStageSaveDTO.builder()
                .description("I Bought and waiting")
                .user(savedVini)
                .request(savedMacbook)
                .state(RequestState.IN_PROGRESS)
                .build()
                .toRequestStage()
        );
        requestStageService.save(RequestStageSaveDTO.builder()
                .description("Arrived at my house!")
                .user(savedVini)
                .request(savedMacbook)
                .state(RequestState.CLOSED)
                .build()
                .toRequestStage()
        );
    }

    private boolean databaseIsEmpty() {
        return userService.count() == 0L;
    }
}
