package com.kammradt.learning;

import com.kammradt.learning.request.entities.Request;
import com.kammradt.learning.stage.entities.RequestStage;
import com.kammradt.learning.user.entities.User;
import com.kammradt.learning.stage.entities.RequestState;
import com.kammradt.learning.user.entities.Role;
import com.kammradt.learning.request.RequestService;
import com.kammradt.learning.stage.RequestStageService;
import com.kammradt.learning.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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
        User vini = userService.save(new User(null, "Vinicius Kammradt", "vinicius.kammradt@email.com", "12345678", Role.REGULAR, null ,null));
        Request macbook = requestService.save(new Request(null, "My Macbook PRO", "I'm buying a new Macbook and I'm really happy", null, vini, null, null, null));
        requestStageService.save(new RequestStage(null, "I'm getting the money to buy", null, vini, macbook, RequestState.OPEN));
        requestStageService.save(new RequestStage(null, "I Bought and waiting", null, vini, macbook, RequestState.IN_PROGRESS));
        requestStageService.save(new RequestStage(null, "Arrived at my house!", null, vini, macbook, RequestState.CLOSED));

        Request iphone = requestService.save(new Request(null, "iPhone 11 MAX", "That's the new Iphone", null, vini, null, null, null));
        requestStageService.save(new RequestStage(null, "I'll sell brownies to get money", null, vini, iphone, RequestState.OPEN));
        requestStageService.save(new RequestStage(null, "Finally Black Friday and I bought", null, vini, iphone, RequestState.IN_PROGRESS));
        requestStageService.save(new RequestStage(null, "Problem with delivery system", null, vini, iphone, RequestState.IN_PROGRESS));

        Request car = requestService.save(new Request(null, "My first CAR!", "I want to buy a gol bolinha", null, vini, null, null, null));
        requestStageService.save(new RequestStage(null, "I'll sell picolés to get money", null, vini, car, RequestState.OPEN));
    }

    private boolean databaseIsEmpty() {
        return userService.count() == 0L;
    }
}
