package com.kammradt.learning.repository;

import com.kammradt.learning.domain.RequestFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestFileRepository extends JpaRepository<RequestFile, Long> {

    Page<RequestFile> findAllByRequestId(Long requestId, Pageable pageable);
}
