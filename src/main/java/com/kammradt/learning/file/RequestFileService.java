package com.kammradt.learning.file;

import com.kammradt.learning.file.dtos.RequestFileDTO;
import com.kammradt.learning.request.entities.Request;
import com.kammradt.learning.file.entities.RequestFile;
import com.kammradt.learning.exception.exceptions.NotFoundException;
import com.kammradt.learning.commom.dtos.ParamsDTO;
import com.kammradt.learning.commom.PageResponse;
import com.kammradt.learning.request.RequestService;
import com.kammradt.learning.s3.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RequestFileService {

    private RequestFileRepository requestFileRepository;
    private S3Service s3Service;
    private RequestService requestService;


    public List<RequestFile> uploadFiles(Long requestId, List<MultipartFile> files) {
        requestService.verifyIfRequestCanBeUpdated(requestId);
        List<RequestFile> requestFiles = s3Service
                .uploadMultipleFiles(files)
                .stream().map(uploadedFileDTO -> uploadedFilesToEntities(requestId, uploadedFileDTO))
                .collect(Collectors.toList());
        return requestFileRepository.saveAll(requestFiles);
    }

    private RequestFile uploadedFilesToEntities(Long requestId, RequestFileDTO uploadedFileDTO) {
        RequestFile requestFile = uploadedFileDTO.toRequestFile();
        Request request = requestService.findById(requestId);
        requestFile.setRequest(request);
        return requestFile;
    }

    public PageResponse<RequestFile> findAllByRequestId(Long requestId, ParamsDTO paramsDTO) {
        Page<RequestFile> page = requestFileRepository.findAllByRequestId(requestId, paramsDTO.toPageable());

        return new PageResponse<>(
                (int) page.getTotalElements(),
                page.getSize(),
                page.getTotalPages(),
                page.getContent());
    }

    public void deleteRequestFileById(Long id) {
        RequestFile requestFile = requestFileRepository.findById(id).orElseThrow(() -> new NotFoundException("There are no RequestFile with this ID"));
        requestFileRepository.deleteById(id);
        s3Service.delete(requestFile.getS3Name());
    }


}
