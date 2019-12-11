package com.kammradt.learning.service;

import com.kammradt.learning.domain.RequestFile;
import com.kammradt.learning.model.PageModel;
import com.kammradt.learning.model.PageRequestModel;
import com.kammradt.learning.repository.RequestFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RequestFileService {

    @Autowired private RequestFileRepository requestFileRepository;

    // Upload all to S3

    public PageModel<RequestFile> findAllByRequestId(Long requestId, PageRequestModel pageRequestModel) {
        Pageable pageable = PageRequest.of(pageRequestModel.getPageNumber(), pageRequestModel.getPageSize());
        Page<RequestFile> page = requestFileRepository.findAllByRequestId(requestId, pageable);

        return new PageModel<>(
                (int) page.getTotalElements(),
                page.getSize(),
                page.getTotalPages(),
                page.getContent());
    }


}
