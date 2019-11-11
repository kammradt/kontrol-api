package com.kammradt.learning.service;

import com.kammradt.learning.domain.User;
import com.kammradt.learning.repository.UserRepository;
import com.kammradt.learning.service.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.kammradt.learning.service.util.HashUtil.generateHash;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        String hashedPassword = generateHash(user.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    public User update(User user) {
        String hashedPassword = generateHash(user.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    public User findById(Long id) {
        Optional<User> result = userRepository.findById(id);
        return result.orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User login(String email, String password) {
        password = generateHash(password);
        Optional<User> result = userRepository.login(email, password);
        return result.orElse(null);
    }


}
