package com.kammradt.learning.resource;

import com.kammradt.learning.domain.Request;
import com.kammradt.learning.domain.User;
import com.kammradt.learning.dto.*;
import com.kammradt.learning.model.PageModel;
import com.kammradt.learning.model.PageRequestModel;
import com.kammradt.learning.security.ResourceAccessManager;
import com.kammradt.learning.service.RequestService;
import com.kammradt.learning.service.SecurityService;
import com.kammradt.learning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping(value = "users")
public class UserResource {

    @Autowired private UserService userService;
    @Autowired private RequestService requestService;
    @Autowired private SecurityService securityService;
    @Autowired private ResourceAccessManager resourceAccessManager;


    // Any
    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid UserSaveDTO userDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.save(userDTO.toUser()));
    }

    @PreAuthorize("@resourceAccessManager.isOwnUser(#id)")
    @Secured("ROLE_REGULAR")
    @PatchMapping("/{id}/profile")
    public ResponseEntity<User> updateProfile(@PathVariable Long id, @RequestBody @Valid UserUpdateProfileDTO userDTO) {
        User updatedUser = userService.updateProfile(userDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedUser);

    }

    @PreAuthorize("@resourceAccessManager.isOwnUser(#id)")
    @Secured("ROLE_REGULAR")
    @PatchMapping("/{id}/password")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody @Valid UserUpdatePasswordDTO userDTO) {
        userService.updatePassword(userDTO);
        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<User> me() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resourceAccessManager.getCurrentUser());
    }


    @Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<PageModel<User>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequestModel pageRequestModel = new PageRequestModel(page, size);
        PageModel pageModel = userService.listAllOnLazyMode(pageRequestModel);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pageModel);
    }

    // Any
    @PostMapping("/login")
    public ResponseEntity<HashMap> login(@RequestBody @Valid UserLoginDTO user) {
        String jwt = securityService.generateJWTToken(user);
        HashMap<String, String> response = new HashMap<>();
        response.put("token", jwt);
        
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PreAuthorize("@resourceAccessManager.isOwnUser(#id)")
    @Secured("ROLE_REGULAR")
    @GetMapping("/{id}/requests")
    public ResponseEntity<PageModel<Request>> findAllRequestsByUserId(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequestModel pageRequestModel = new PageRequestModel(page, size);
        PageModel<Request> pageModel = requestService.findAllByUserIdOnLazyMode(id, pageRequestModel);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pageModel);
    }


    @Secured("ROLE_ADMIN")
    @PatchMapping("/{id}/role")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody @Valid UserUpdateRoleDTO userDTO) {
        User user = userService.findById(id);
        user.setRole(userDTO.getRole());
        userService.updateRole(user);

        return ResponseEntity.ok().build();
    }


}
