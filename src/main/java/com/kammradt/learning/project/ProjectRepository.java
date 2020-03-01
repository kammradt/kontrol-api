package com.kammradt.learning.project;

import com.kammradt.learning.project.entities.Project;
import com.kammradt.learning.task.entities.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByUserId(Long id);

    Page<Project> findAllByUserId(Long id, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Project SET state = ?2 WHERE id = ?1")
    Integer updateRequestState(Long requestId, Status state);

}
