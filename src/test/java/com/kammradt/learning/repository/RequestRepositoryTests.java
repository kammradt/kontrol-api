package com.kammradt.learning.repository;

import com.kammradt.learning.domain.Request;
import com.kammradt.learning.domain.User;
import com.kammradt.learning.domain.enums.RequestState;
import com.kammradt.learning.domain.enums.Role;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RequestRepositoryTests {

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    UserRepository userRepository;


    @Test
    public void a_saveTest() {
        User user = new User(null, "UserName", "UserEmail", "123", Role.REGULAR, null, null);
        User savedUser = userRepository.save(user);

        Request request = new Request(null,"Macbook","Buying Macbook", new Date(), savedUser, RequestState.OPEN, null);
        Request savedRequest = requestRepository.save(request);

        assertEquals(Long.valueOf(1), savedRequest.getId());
    }

    @Test
    public void b_updateTest() {
        Optional<User> user = userRepository.findById(1L);
        if (!user.isPresent())
            fail();

        Request request = new Request(1L,"Macbook PRO","Buying Macbook PRO", null, user.get(), RequestState.OPEN, null);
        Request updatedRequest = requestRepository.save(request);

        assertEquals("Macbook PRO", updatedRequest.getSubject());
        assertEquals("Buying Macbook PRO", updatedRequest.getDescription());

    }

    @Test
    public void findById() {
        Optional<Request> result = requestRepository.findById(1L);
        result.ifPresent(request -> assertEquals("Buying Macbook PRO", request.getDescription()));

    }

    @Test
    public void findAll() {
        List<Request> requests = requestRepository.findAll();
        assertEquals(1, requests.size());
    }

    @Test
    public void findAllByUserId() {
        List<Request> requests = requestRepository.findAllByUserId(1L);
        assertEquals(1, requests.size());
    }

    @Test
    public void updateRequestStateTest() {
        Integer rowsAffected = requestRepository.updateRequestState(1L, RequestState.IN_PROGRESS);
        assertEquals(Integer.valueOf(1), rowsAffected);
    }




}
