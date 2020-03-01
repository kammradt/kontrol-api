package com.kammradt.learning.project;

import com.kammradt.learning.commom.PageResponse;
import com.kammradt.learning.commom.dtos.ParamsDTO;
import com.kammradt.learning.file.RequestFileService;
import com.kammradt.learning.file.entities.RequestFile;
import com.kammradt.learning.project.dtos.RequestSaveDTO;
import com.kammradt.learning.project.dtos.RequestUpdateDTO;
import com.kammradt.learning.project.entities.Project;
import com.kammradt.learning.task.TaskService;
import com.kammradt.learning.task.entities.Task;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "requests")
public class ProjectResource {

    private ProjectService projectService;
    private TaskService taskService;
    private RequestFileService requestFileService;

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isOwnUser(#requestDTO.user.id)")
    @PostMapping
    public ResponseEntity<Project> save(@RequestBody @Valid RequestSaveDTO requestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectService.save(requestDTO.toRequest()));
    }

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isRequestOwner(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<Project> update(@PathVariable Long id, @RequestBody @Valid RequestUpdateDTO requestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.update(id, requestDTO.toRequest()));
    }

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isRequestOwner(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<Project> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.findById(id));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<List<Project>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.findAll());
    }

    // findAllByUserId will be in userResource

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isRequestOwner(#id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        projectService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isRequestOwner(#id)")
    @GetMapping("/{id}/request-stages")
    public ResponseEntity<List<Task>> findAllRequestStagesByRequestId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.findAllByRequestId(id));
    }

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isRequestOwner(#id)")
    @GetMapping("/{id}/files")
    public ResponseEntity<PageResponse<RequestFile>> findAllFilesByRequestId(
            @PathVariable Long id,
            @RequestParam Map<String, String> params
    ) {
        ParamsDTO paramsDTO = new ParamsDTO(params);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestFileService.findAllByRequestId(id, paramsDTO));
    }

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isRequestOwner(#requestId)")
    @DeleteMapping("/{requestId}/files/{fileId}")
    public ResponseEntity<?> deleteRequestFileById(
            @PathVariable Long requestId,
            @PathVariable Long fileId
    ) {
        requestFileService.deleteRequestFileById(fileId);
        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isRequestOwner(#id)")
    @PostMapping("/{id}/files")
    public ResponseEntity<List<RequestFile>> uploadFilesToRequest(
            @PathVariable Long id,
            @RequestBody List<MultipartFile> files
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(requestFileService.uploadFiles(id, files));
    }
}
