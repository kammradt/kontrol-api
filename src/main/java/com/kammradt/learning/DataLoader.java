package com.kammradt.learning;

import com.kammradt.learning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired private UserService userService;

    @Override
    public void run(ApplicationArguments args) {

        if (databaseIsEmpty())
            insertOneUserAndSomeRequests();
    }

    private void insertOneUserAndSomeRequests() {
    }

    private boolean databaseIsEmpty() {
        return userService.count() == 0L;
    }
}
