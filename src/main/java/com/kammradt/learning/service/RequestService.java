package com.kammradt.learning.service;

import com.kammradt.learning.domain.Request;
import com.kammradt.learning.domain.enums.RequestState;
import com.kammradt.learning.dto.RequestUpdateDTO;
import com.kammradt.learning.exception.NotFoundException;
import com.kammradt.learning.model.PageModel;
import com.kammradt.learning.model.PageFilterDTO;
import com.kammradt.learning.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public Request save(Request request) {
        request.setState(RequestState.OPEN);
        request.setCreationDate(new Date());

        return requestRepository.save(request);
    }

    public Request update(Long id, RequestUpdateDTO requestDTO) {
        Request request = findById(id);
        Request newRequest = requestDTO.toRequest();
        newRequest.setId(request.getId());
        newRequest.setCreationDate(request.getCreationDate());
        return requestRepository.save(newRequest);
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

    public Request updateState(Long id, RequestState newState) {
        Request request = findById(id);
        request.setState(newState);
        return requestRepository.save(request);
    }

    public PageModel<Request> findAllByUserIdOnLazyMode(Long id, PageFilterDTO pageFilterDTO) {
        Page<Request> resultPage = requestRepository.findAllByUserId(id, pageFilterDTO.toPageable());

        return new PageModel<>(
                (int) resultPage.getTotalElements(),
                      resultPage.getSize(),
                      resultPage.getTotalPages(),
                      resultPage.getContent());
    }










}
