package com.kammradt.learning.task;

import com.kammradt.learning.project.ProjectService;
import com.kammradt.learning.task.dtos.TaskSaveDTO;
import com.kammradt.learning.task.entities.Task;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "request-stages")
public class TaskResource {

    TaskService taskService;
    ProjectService projectService;

    @PreAuthorize("@resourceAccessManager.isRequestOwner(#requestStageDTO.project.id) and @resourceAccessManager.isOwnUser(#requestStageDTO.user.id)")
    @Secured("ROLE_REGULAR")
    @PostMapping
    public ResponseEntity<Task> save(@RequestBody @Valid TaskSaveDTO requestStageDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskService.save(requestStageDTO.toTask()));
    }

    @PreAuthorize("@resourceAccessManager.isRequestStageOwner(#id)")
    @Secured("ROLE_REGULAR")
    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.findById(id));
    }

    // findAllByRequestId will be in requestResource

    @PreAuthorize("@resourceAccessManager.isRequestStageOwner(#id)")
    @Secured("ROLE_REGULAR")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
