package com.kammradt.learning.project;

import com.kammradt.learning.commom.PageResponse;
import com.kammradt.learning.commom.dtos.ParamsDTO;
import com.kammradt.learning.exception.exceptions.NotFoundException;
import com.kammradt.learning.exception.exceptions.ProjectClosedCannotBeUpdatedException;
import com.kammradt.learning.project.dtos.ProjectResponse;
import com.kammradt.learning.project.entities.Project;
import com.kammradt.learning.task.entities.Status;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectService {

    private ProjectRepository projectRepository;

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public Project update(Long id, Project updatedProject) {
        verifyIfProjectCanBeUpdated(id);
        Project project = findById(id);
        updatedProject.setId(id);
        updatedProject.setCreationDate(project.getCreationDate());
        return projectRepository.save(updatedProject);
    }

    public Project findById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new NotFoundException("There are no Project with this ID"));
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    public PageResponse<ProjectResponse> findAllByUserIdOnLazyMode(Long id, ParamsDTO paramsDTO) {
        Page<Project> resultPage = projectRepository.findAllByUserId(id, paramsDTO.toPageable());
        var responseList = resultPage.getContent().stream().map(ProjectResponse::build).collect(Collectors.toList());

        return new PageResponse<>(
                (int) resultPage.getTotalElements(),
                resultPage.getSize(),
                resultPage.getTotalPages(),
                responseList);
    }

    public void verifyIfProjectCanBeUpdated(Long requstId) {
        Project project = findById(requstId);
        if (project.getStatus().equals(Status.DONE))
            throw new ProjectClosedCannotBeUpdatedException("Project is already closed and cannot be updated!");
    }
}
