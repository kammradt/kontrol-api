package com.kammradt.learning.resource;

import com.kammradt.learning.domain.Request;
import com.kammradt.learning.domain.RequestStage;
import com.kammradt.learning.domain.enums.RequestState;
import com.kammradt.learning.dto.RequestStageSaveDTO;
import com.kammradt.learning.dto.RequestUpdateDTO;
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
@RequestMapping(value = "request-stages")
public class RequestStageResource {

    @Autowired RequestStageService requestStageService;
    @Autowired RequestService requestService;

    @PreAuthorize("@resourceAccessManager.isRequestOwner(#requestStageDTO.request.id) and @resourceAccessManager.isOwnUser(#requestStageDTO.user.id)")
    @Secured("ROLE_REGULAR")
    @PostMapping
    public ResponseEntity<RequestStage> save(@RequestBody @Valid RequestStageSaveDTO requestStageDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(requestStageService.save(requestStageDTO.toRequestStage()));
    }

    @PreAuthorize("@resourceAccessManager.isRequestStageOwner(#id)")
    @Secured("ROLE_REGULAR")
    @GetMapping("/{id}")
    public ResponseEntity<RequestStage> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestStageService.findById(id));
    }

    // findAllByRequestId will be in requestResource

    @PreAuthorize("@resourceAccessManager.isRequestStageOwner(#id)")
    @Secured("ROLE_REGULAR")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        RequestStage toBeDeleted = requestStageService.findById(id);
        requestStageService.deleteById(toBeDeleted.getId());

        Request toBeUpdated = toBeDeleted.getRequest();
        List<RequestStage> stages = requestStageService.findAllByRequestId(toBeUpdated.getId());
        RequestState newState = stages.get(stages.size() -1).getState();

        Request updated = requestService.updateState(toBeUpdated.getId(), newState);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updated);
    }
}
