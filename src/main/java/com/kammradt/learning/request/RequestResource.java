package com.kammradt.learning.request;

import com.kammradt.learning.file.entities.RequestFile;
import com.kammradt.learning.stage.entities.RequestStage;
import com.kammradt.learning.request.dtos.RequestSaveDTO;
import com.kammradt.learning.request.dtos.RequestUpdateDTO;
import com.kammradt.learning.commom.PageResponse;
import com.kammradt.learning.commom.dtos.ParamsDTO;
import com.kammradt.learning.request.entities.Request;
import com.kammradt.learning.file.RequestFileService;
import com.kammradt.learning.stage.RequestStageService;
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
public class RequestResource {

    private RequestService requestService;
    private RequestStageService requestStageService;
    private RequestFileService requestFileService;

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isOwnUser(#requestDTO.user.id)")
    @PostMapping
    public ResponseEntity<Request> save(@RequestBody @Valid RequestSaveDTO requestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(requestService.save(requestDTO.toRequest()));
    }

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isRequestOwner(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<Request> update(@PathVariable Long id, @RequestBody @Valid RequestUpdateDTO requestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestService.update(id, requestDTO.toRequest()));
    }

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isRequestOwner(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<Request> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestService.findById(id));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<List<Request>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestService.findAll());
    }

    // findAllByUserId will be in userResource

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isRequestOwner(#id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        requestService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isRequestOwner(#id)")
    @GetMapping("/{id}/request-stages")
    public ResponseEntity<List<RequestStage>> findAllRequestStagesByRequestId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestStageService.findAllByRequestId(id));
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
