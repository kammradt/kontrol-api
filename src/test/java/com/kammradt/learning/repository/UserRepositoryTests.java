package com.kammradt.learning.repository;

import com.kammradt.learning.domain.User;
import com.kammradt.learning.domain.enums.Role;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class UserRepositoryTests {


    @Autowired
    private UserRepository userRepository;

    @Test
    public void a_saveTest() {
        User user = new User(null, "creatingUser", "creatingUser@mail.com", "123", Role.ADMIN, null, null);
        User createdUser = userRepository.save(user);

        assertEquals("creatingUser", createdUser.getName());
    }

    @Test
    public void updateTest() {
        User user = new User(1L, "updatingUser", "creatingUser@mail.com", "123", Role.ADMIN, null, null);
        User updatedUser = userRepository.save(user);

        assertEquals("updatingUser", updatedUser.getName());
    }

    @Test
    public void findByIdTest() {
        Optional<User> result = userRepository.findById(1L);
        result.ifPresent(user -> assertEquals(Long.valueOf(1), user.getId()));
    }

    @Test
    public void findAll() {
        List<User> users = userRepository.findAll();
        assertEquals(1, users.size());
    }

    @Test
    public void loginTest() {
        Optional<User> result = userRepository.login("creatingUser@mail.com", "123");
        result.ifPresent(user -> assertEquals(Long.valueOf(1), user.getId()));
    }

}
