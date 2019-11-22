package com.kammradt.learning.resource;

import com.kammradt.learning.domain.RequestStage;
import com.kammradt.learning.dto.RequestStageSaveDTO;
import com.kammradt.learning.service.RequestStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "request-stages")
public class RequestStageResource {

    @Autowired
    RequestStageService requestStageService;

    @PostMapping
    public ResponseEntity<RequestStage> save(@RequestBody @Valid RequestStageSaveDTO requestStageDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(requestStageService.save(requestStageDTO.toRequestStage()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestStage> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestStageService.findById(id));
    }

    // findAllByRequestId will be in requestResource


}
