package com.kammradt.learning.security;

import com.kammradt.learning.domain.Request;
import com.kammradt.learning.domain.RequestStage;
import com.kammradt.learning.domain.User;
import com.kammradt.learning.service.RequestService;
import com.kammradt.learning.service.RequestStageService;
import com.kammradt.learning.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("resourceAccessManager")
public class ResourceAccessManager {

    private UserService userService;
    private RequestService requestService;
    private RequestStageService requestStageService;

    public ResourceAccessManager(@Lazy UserService userService, RequestService requestService, RequestStageService requestStageService) {
        this.userService = userService;
        this.requestService = requestService;
        this.requestStageService = requestStageService;
    }

    public boolean isOwnUser(Long userId) {
        User user = getCurrentUser();

        return user.getId().equals(userId);
    }

    public boolean isRequestOwner(Long resourceId) {
        User user = getCurrentUser();
        Request request = requestService.findById(resourceId);

        return user.getId().equals(request.getUser().getId());
    }

    public boolean isRequestStageOwner(Long resourceId) {
        User user = getCurrentUser();
        RequestStage requestStage = requestStageService.findById(resourceId);

        return user.getId().equals(requestStage.getUser().getId());
    }


    public User getCurrentUser() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByEmail(email);
    }
}
