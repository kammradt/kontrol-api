package com.kammradt.learning.project;

import com.kammradt.learning.commom.PageResponse;
import com.kammradt.learning.commom.dtos.ParamsDTO;
import com.kammradt.learning.file.FileService;
import com.kammradt.learning.file.entities.File;
import com.kammradt.learning.project.dtos.ProjectSaveDTO;
import com.kammradt.learning.project.dtos.ProjectUpdateDTO;
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
@RequestMapping(value = "projects")
public class ProjectResource {

    private ProjectService projectService;
    private TaskService taskService;
    private FileService fileService;

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isOwnUser(#requestDTO.user.id)")
    @PostMapping
    public ResponseEntity<Project> save(@RequestBody @Valid ProjectSaveDTO requestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectService.save(requestDTO.toProject()));
    }

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isProjectOwner(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<Project> update(@PathVariable Long id, @RequestBody @Valid ProjectUpdateDTO requestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.update(id, requestDTO.toProject()));
    }

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isProjectOwner(#id)")
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

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isProjectOwner(#id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        projectService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isProjectOwner(#id)")
    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<Task>> findAllTasksByProjectId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.findAllByProjectId(id));
    }

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isProjectOwner(#id)")
    @GetMapping("/{id}/files")
    public ResponseEntity<PageResponse<File>> findAllFilesByProjectId(
            @PathVariable Long id,
            @RequestParam Map<String, String> params
    ) {
        ParamsDTO paramsDTO = new ParamsDTO(params);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fileService.findAllByProjectId(id, paramsDTO));
    }

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isProjectOwner(#projectId)")
    @DeleteMapping("/{projectId}/files/{fileId}")
    public ResponseEntity<?> deleteFileById(
            @PathVariable Long projectId,
            @PathVariable Long fileId
    ) {
        fileService.deleteFileById(fileId);
        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isProjectOwner(#id)")
    @PostMapping("/{id}/files")
    public ResponseEntity<List<File>> uploadFilesToProject(
            @PathVariable Long id,
            @RequestBody List<MultipartFile> files
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(fileService.uploadFiles(id, files));
    }
}
