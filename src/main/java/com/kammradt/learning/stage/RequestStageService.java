package com.kammradt.learning.stage;

import com.kammradt.learning.request.entities.Request;
import com.kammradt.learning.exception.exceptions.NotFoundException;
import com.kammradt.learning.request.RequestRepository;
import com.kammradt.learning.request.RequestService;
import com.kammradt.learning.stage.entities.RequestStage;
import com.kammradt.learning.stage.entities.RequestState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class RequestStageService {

    private RequestStageRepository requestStageRepository;
    private RequestRepository requestRepository;
    private RequestService requestService;


    public RequestStage save(RequestStage requestStage) {
        Long requestBeingUpdated = requestStage.getRequest().getId();
        requestService.verifyIfRequestCanBeUpdated(requestBeingUpdated);

        requestStage.setRealizationDate(new Date());
        RequestStage savedRequestStage = requestStageRepository.save(requestStage);

        requestRepository.updateRequestState(requestBeingUpdated, requestStage.getState());

        return savedRequestStage;
    }

    public RequestStage findById(Long id) {
        return requestStageRepository.findById(id).orElseThrow(() -> new NotFoundException("There are no RequestStage with this ID"));
    }

    public List<RequestStage> findAllByRequestId(Long requestId) {
        return requestStageRepository.findAllByRequestId(requestId);
    }

    private Request updateState(Long id, RequestState newState) {
        Request request = requestService.findById(id);
        request.setState(newState);
        return requestRepository.save(request);
    }

    public void deleteById(Long id) {
        Request toBeUpdated = findById(id).getRequest();
        requestStageRepository.deleteById(id);

        RequestState newState = getNewStateAfterDeletion(toBeUpdated.getId());

        updateState(toBeUpdated.getId(), newState);
    }

    private RequestState getNewStateAfterDeletion(Long requestId) {
        List<RequestStage> stages = findAllByRequestId(requestId);

        RequestState newState;
        if (stages.isEmpty())
            newState = RequestState.OPEN;
        else if (stages.size() == 1)
            newState = stages.get(0).getState();
        else if (stages.stream().anyMatch(stage -> stage.getState() == RequestState.CLOSED))
            newState = RequestState.CLOSED;
        else
            newState = RequestState.IN_PROGRESS;

        return newState;
    }


}
