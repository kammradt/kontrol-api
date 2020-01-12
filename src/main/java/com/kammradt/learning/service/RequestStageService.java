package com.kammradt.learning.service;

import com.kammradt.learning.domain.Request;
import com.kammradt.learning.domain.RequestStage;
import com.kammradt.learning.domain.enums.RequestState;
import com.kammradt.learning.exception.NotFoundException;
import com.kammradt.learning.repository.RequestRepository;
import com.kammradt.learning.repository.RequestStageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RequestStageService {

    @Autowired private RequestStageRepository requestStageRepository;
    @Autowired private RequestRepository requestRepository;
    @Autowired private RequestService requestService;


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


    public void deleteById(Long id) {
        Request toBeUpdated = findById(id).getRequest();
        requestStageRepository.deleteById(id);

        RequestState newState = getNewStateAfterDeletion(toBeUpdated.getId());

        requestService.updateState(toBeUpdated.getId(), newState);
    }

    private RequestState getNewStateAfterDeletion(Long requestId) {
        List<RequestStage> stages = findAllByRequestId(requestId);

        RequestState newState;
        if (stages.isEmpty())
            newState = RequestState.OPEN;
        else if (stages.size() == 1)
            newState = stages.get(0).getState();
        else
            if (stages.stream().anyMatch(stage -> stage.getState() == RequestState.CLOSED))
                newState = RequestState.CLOSED;
            else
                newState = RequestState.IN_PROGRESS;

        return newState;
    }


}
