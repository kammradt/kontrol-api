package com.kammradt.learning.repository;

import com.kammradt.learning.domain.Request;
import com.kammradt.learning.domain.enums.RequestState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByUserId(Long id);

    Page<Request> findAllByUserId(Long id, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Request SET state = ?2 WHERE id = ?1")
    Integer updateRequestState(Long requestId, RequestState state);

}
