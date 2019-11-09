package com.kammradt.learning.service;

import com.kammradt.learning.domain.Request;
import com.kammradt.learning.domain.enums.RequestState;
import com.kammradt.learning.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Request update(Request request) {
        return requestRepository.save(request);
    }

    public Request findById(Long id) {
        return requestRepository.findById(id).orElse(null);
    }

    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    public List<Request> findAllByUserId(Long id) {
        return requestRepository.findAllByUserId(id);
    }










}
