package com.kammradt.learning.resource;

import com.kammradt.learning.domain.Request;
import com.kammradt.learning.domain.RequestStage;
import com.kammradt.learning.service.RequestService;
import com.kammradt.learning.service.RequestStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "requests")
public class RequestResource {

    @Autowired
    private RequestService requestService;

    @Autowired
    private RequestStageService requestStageService;

    @PostMapping
    public ResponseEntity<Request> save(@RequestBody Request request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(requestService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Request> update(@PathVariable Long id, @RequestBody Request request) {
        request.setId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestService.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Request>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestService.findAll());
    }

    // findAllByUserId will be in userResource

    @GetMapping("/{id}/request-stages")
    public ResponseEntity<List<RequestStage>> findAllRequestStagesByRequestId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestStageService.findAllByRequestId(id));
    }



}
