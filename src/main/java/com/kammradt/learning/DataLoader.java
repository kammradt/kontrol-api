package com.kammradt.learning;

import com.kammradt.learning.domain.Request;
import com.kammradt.learning.domain.RequestStage;
import com.kammradt.learning.domain.User;
import com.kammradt.learning.domain.enums.RequestState;
import com.kammradt.learning.domain.enums.Role;
import com.kammradt.learning.service.RequestService;
import com.kammradt.learning.service.RequestStageService;
import com.kammradt.learning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private RequestStageService requestStageService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        User vini = userService.save(new User(null, "Vini", "vini@gmail.com", "111", Role.REGULAR, null ,null));
        Request macbook = requestService.save(new Request(null, "Macbook", "The user wants to buy a Macbook", null, vini, RequestState.OPEN, null));
        RequestStage macbook1 = requestStageService.save(new RequestStage(null, "Separting the wanted Macbook", null, vini, macbook, RequestState.IN_PROGRESS));
        RequestStage macbook2 = requestStageService.save(new RequestStage(null, "Sending the Macbook", null, vini, macbook, RequestState.CLOSED));

        Request iphone = requestService.save(new Request(null, "Iphone", "The user wants to buy an Iphone", null, vini, RequestState.OPEN, null));
        RequestStage iphone1 = requestStageService.save(new RequestStage(null, "Searching for an Iphone", null, vini, iphone, RequestState.IN_PROGRESS));
        RequestStage iphone2 = requestStageService.save(new RequestStage(null, "There are no Iphone available", null, vini, iphone, RequestState.CLOSED));

        userService.save(new User(null, "Alves", "alves@gmail.com", "222", Role.REGULAR, null ,null));
    }
}
