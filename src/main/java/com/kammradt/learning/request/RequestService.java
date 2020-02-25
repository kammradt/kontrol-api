package com.kammradt.learning.request;

import com.kammradt.learning.stage.entities.RequestState;
import com.kammradt.learning.exception.exceptions.NotFoundException;
import com.kammradt.learning.exception.exceptions.RequestClosedCannotBeUpdatedException;
import com.kammradt.learning.commom.dtos.ParamsDTO;
import com.kammradt.learning.commom.PageResponse;
import com.kammradt.learning.request.entities.Request;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class RequestService {

    private RequestRepository requestRepository;

    public Request save(Request request) {
        request.setState(RequestState.OPEN);
        request.setCreationDate(new Date());

        return requestRepository.save(request);
    }

    public Request update(Long id, Request updatedRequest) {
        verifyIfRequestCanBeUpdated(id);
        Request request = findById(id);
        updatedRequest.setId(id);
        updatedRequest.setCreationDate(request.getCreationDate());
        return requestRepository.save(updatedRequest);
    }

    public Request findById(Long id) {
        return requestRepository.findById(id).orElseThrow(() -> new NotFoundException("There are no Request with this ID"));
    }

    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    public List<Request> findAllByUserId(Long id) {
        return requestRepository.findAllByUserId(id);
    }

    public void deleteById(Long id) {
        requestRepository.deleteById(id);
    }

    public PageResponse<Request> findAllByUserIdOnLazyMode(Long id, ParamsDTO paramsDTO) {
        Page<Request> resultPage = requestRepository.findAllByUserId(id, paramsDTO.toPageable());

        return new PageResponse<>(
                (int) resultPage.getTotalElements(),
                resultPage.getSize(),
                resultPage.getTotalPages(),
                resultPage.getContent());
    }

    public void verifyIfRequestCanBeUpdated(Long requstId) {
        Request request = findById(requstId);
        if (request.getState().equals(RequestState.CLOSED))
            throw new RequestClosedCannotBeUpdatedException("Request is already closed and cannot be updated!");
    }
}
