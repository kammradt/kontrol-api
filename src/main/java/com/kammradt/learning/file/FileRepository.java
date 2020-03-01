package com.kammradt.learning.file;

import com.kammradt.learning.file.entities.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    Page<File> findAllByProjectId(Long requestId, Pageable pageable);
}
