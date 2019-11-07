package com.kammradt.learning.repository;

import com.kammradt.learning.domain.Request;
import com.kammradt.learning.domain.enums.RequestState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByUserId(Long id);

    @Query("UPDATE Request SET state = ?2 WHERE id = ?1")
    Request updateRequestState(Long id, RequestState state);

}
