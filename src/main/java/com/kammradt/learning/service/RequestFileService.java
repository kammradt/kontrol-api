package com.kammradt.learning.service;

import com.kammradt.learning.domain.Request;
import com.kammradt.learning.domain.RequestFile;
import com.kammradt.learning.model.PageModel;
import com.kammradt.learning.model.PageFilterDTO;
import com.kammradt.learning.model.RequestFileDTO;
import com.kammradt.learning.repository.RequestFileRepository;
import com.kammradt.learning.service.s3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestFileService {

    @Autowired private RequestFileRepository requestFileRepository;
    @Autowired private S3Service s3Service;
    @Autowired private RequestService requestService;


    public List<RequestFile> uploadFiles(Long requestId, List<MultipartFile> files) {
        List<RequestFile> requestFiles = s3Service
                .uploadMultipleFiles(files)
                .stream().map(uploadedFileDTO -> {
                    RequestFile requestFile = uploadedFileDTO.toRequestFile();
                    Request request = requestService.findById(requestId);
                    requestFile.setRequest(request);
                    return requestFile;
                })
                .collect(Collectors.toList());
        return requestFileRepository.saveAll(requestFiles);
    }

    public PageModel<RequestFile> findAllByRequestId(Long requestId, PageFilterDTO pageFilterDTO) {
        Page<RequestFile> page = requestFileRepository.findAllByRequestId(requestId, pageFilterDTO.toPageable());

        return new PageModel<>(
                (int) page.getTotalElements(),
                page.getSize(),
                page.getTotalPages(),
                page.getContent());
    }


}
