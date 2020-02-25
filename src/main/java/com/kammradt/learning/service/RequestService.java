package com.kammradt.learning.service;

import com.kammradt.learning.domain.Request;
import com.kammradt.learning.domain.enums.RequestState;
import com.kammradt.learning.exception.NotFoundException;
import com.kammradt.learning.exception.RequestClosedCannotBeUpdatedException;
import com.kammradt.learning.model.PageFilterDTO;
import com.kammradt.learning.model.PageModel;
import com.kammradt.learning.repository.RequestRepository;
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

    public PageModel<Request> findAllByUserIdOnLazyMode(Long id, PageFilterDTO pageFilterDTO) {
        Page<Request> resultPage = requestRepository.findAllByUserId(id, pageFilterDTO.toPageable());

        return new PageModel<>(
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
