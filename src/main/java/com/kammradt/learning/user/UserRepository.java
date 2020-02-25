package com.kammradt.learning.user;

import com.kammradt.learning.user.entities.Role;
import com.kammradt.learning.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT U FROM User U WHERE U.email = ?1 AND U.password = ?2")
    Optional<User> login(String email, String password);

    @Transactional
    @Modifying
    @Query("UPDATE User SET role = ?2 WHERE id = ?1")
    int updateRole(Long id, Role role);

    Optional<User> findByEmail(String email);
}
