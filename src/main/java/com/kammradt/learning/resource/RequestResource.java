package com.kammradt.learning.resource;

import com.kammradt.learning.domain.Request;
import com.kammradt.learning.domain.RequestStage;
import com.kammradt.learning.dto.RequestSaveDTO;
import com.kammradt.learning.dto.RequestUpdateDTO;
import com.kammradt.learning.security.ResourceAccessManager;
import com.kammradt.learning.service.RequestService;
import com.kammradt.learning.service.RequestStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "requests")
public class RequestResource {

    @Autowired private RequestService requestService;
    @Autowired private RequestStageService requestStageService;

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
                .body(requestService.update(id, requestDTO));
    }

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isRequestOwner(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<Request> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestService.findById(id));
    }

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isRequestOwner(#id)")
    @GetMapping
    public ResponseEntity<List<Request>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestService.findAll());
    }

    // findAllByUserId will be in userResource

    @Secured("ROLE_REGULAR")
    @PreAuthorize("@resourceAccessManager.isRequestOwner(#id)")
    @GetMapping("/{id}/request-stages")
    public ResponseEntity<List<RequestStage>> findAllRequestStagesByRequestId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestStageService.findAllByRequestId(id));
    }



}
