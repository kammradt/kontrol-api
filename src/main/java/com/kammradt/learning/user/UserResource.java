package com.kammradt.learning.user;

import com.kammradt.learning.request.RequestResource;
import com.kammradt.learning.request.dtos.RequestResponse;
import com.kammradt.learning.request.entities.Request;
import com.kammradt.learning.commom.dtos.ParamsDTO;
import com.kammradt.learning.commom.PageResponse;
import com.kammradt.learning.request.RequestService;
import com.kammradt.learning.security.ResourceAccessManager;
import com.kammradt.learning.security.SecurityService;
import com.kammradt.learning.user.dtos.*;
import com.kammradt.learning.user.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "users")
public class UserResource {

    private UserService userService;
    private RequestService requestService;
    private SecurityService securityService;
    private ResourceAccessManager resourceAccessManager;


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
        User updatedUser = userService.updateProfile(userDTO.toUser());
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
    public ResponseEntity<PageResponse<User>> findAll(@RequestParam Map<String, String> params) {
        ParamsDTO paramsDTO = new ParamsDTO(params);
        PageResponse pageResponse = userService.listAllOnLazyMode(paramsDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pageResponse);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<HashMap<String, String>> login(@RequestBody @Valid UserLoginDTO userDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(securityService.generateJWTToken(userDTO));
    }

    @PreAuthorize("@resourceAccessManager.isOwnUser(#id)")
    @Secured("ROLE_REGULAR")
    @GetMapping("/{id}/requests")
    public ResponseEntity<PageResponse<RequestResponse>> findAllRequestsByUserId(
            @PathVariable Long id,
            @RequestParam Map<String, String> params
    ) {
        ParamsDTO paramsDTO = new ParamsDTO(params);
        PageResponse<RequestResponse> pageResponse = requestService.findAllByUserIdOnLazyMode(id, paramsDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pageResponse);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("/{id}/role")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody @Valid UserUpdateRoleDTO userDTO) {
        userService.updateRole(id, userDTO.toUser().getRole());
        return ResponseEntity.ok().build();
    }


}
