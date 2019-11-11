package com.kammradt.learning.service;

import com.kammradt.learning.domain.RequestStage;
import com.kammradt.learning.exception.NotFoundException;
import com.kammradt.learning.repository.RequestRepository;
import com.kammradt.learning.repository.RequestStageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RequestStageService {

    @Autowired
    private RequestStageRepository requestStageRepository;

    @Autowired
    private RequestRepository requestRepository;


    public RequestStage save(RequestStage requestStage) {
        requestStage.setRealizationDate(new Date());
        RequestStage savedRequestStage = requestStageRepository.save(requestStage);

        Long requestBeingUpdated = requestStage.getRequest().getId();
        requestRepository.updateRequestState(requestBeingUpdated, requestStage.getState());

        return savedRequestStage;
    }

    public RequestStage findById(Long id) {
        return requestStageRepository.findById(id).orElseThrow(() -> new NotFoundException("There are no RequestStage with this ID"));

    }

    public List<RequestStage> findAllByRequestId(Long requestId) {
        return requestStageRepository.findAllByRequestId(requestId);
    }





}
