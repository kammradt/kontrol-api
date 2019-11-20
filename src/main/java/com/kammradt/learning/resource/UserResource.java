package com.kammradt.learning.resource;

import com.kammradt.learning.domain.Request;
import com.kammradt.learning.domain.User;
import com.kammradt.learning.dto.UserLoginDTO;
import com.kammradt.learning.dto.UserSaveDTO;
import com.kammradt.learning.dto.UserUpdateDTO;
import com.kammradt.learning.dto.UserUpdateRoleDTO;
import com.kammradt.learning.model.PageModel;
import com.kammradt.learning.model.PageRequestModel;
import com.kammradt.learning.service.RequestService;
import com.kammradt.learning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "users")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid UserSaveDTO userDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.save(userDTO.toUser()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO userDTO) {
        User user = userDTO.toUser();
        user.setId(id);
        User updatedUser = userService.update(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findById(id));
    }

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

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginDTO user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.login(user.getEmail(), user.getPassword()));
    }

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


    @PatchMapping("/{id}/role")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody @Valid UserUpdateRoleDTO userDTO) {
        User user = userService.findById(id);
        user.setRole(userDTO.getRole());
        userService.updateRole(user);

        return ResponseEntity.ok().build();
    }


}
