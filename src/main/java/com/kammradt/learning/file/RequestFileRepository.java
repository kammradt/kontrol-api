package com.kammradt.learning.file;

import com.kammradt.learning.file.entities.RequestFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestFileRepository extends JpaRepository<RequestFile, Long> {

    Page<RequestFile> findAllByProjectId(Long requestId, Pageable pageable);
}
