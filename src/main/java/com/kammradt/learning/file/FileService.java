package com.kammradt.learning.file;

import com.kammradt.learning.commom.PageResponse;
import com.kammradt.learning.commom.dtos.ParamsDTO;
import com.kammradt.learning.exception.exceptions.NotFoundException;
import com.kammradt.learning.file.dtos.FileDto;
import com.kammradt.learning.file.entities.File;
import com.kammradt.learning.project.ProjectService;
import com.kammradt.learning.project.entities.Project;
import com.kammradt.learning.s3.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileService {

    private FileRepository fileRepository;
    private S3Service s3Service;
    private ProjectService projectService;


    public List<File> uploadFiles(Long requestId, List<MultipartFile> files) {
        projectService.verifyIfProjectCanBeUpdated(requestId);
        List<File> requestFiles = s3Service
                .uploadMultipleFiles(files)
                .stream().map(uploadedFileDTO -> uploadedFilesToEntities(requestId, uploadedFileDTO))
                .collect(Collectors.toList());
        return fileRepository.saveAll(requestFiles);
    }

    private File uploadedFilesToEntities(Long requestId, FileDto uploadedFileDTO) {
        File file = uploadedFileDTO.toFile();
        Project project = projectService.findById(requestId);
        file.setProject(project);
        return file;
    }

    public PageResponse<File> findAllByProjectId(Long requestId, ParamsDTO paramsDTO) {
        Page<File> page = fileRepository.findAllByProjectId(requestId, paramsDTO.toPageable());

        return new PageResponse<>(
                (int) page.getTotalElements(),
                page.getSize(),
                page.getTotalPages(),
                page.getContent());
    }

    public void deleteFileById(Long id) {
        File file = fileRepository.findById(id).orElseThrow(() -> new NotFoundException("There are no File with this ID"));
        fileRepository.deleteById(id);
        s3Service.delete(file.getS3Name());
    }


}
