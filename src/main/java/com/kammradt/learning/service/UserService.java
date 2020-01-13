package com.kammradt.learning.service;

import com.kammradt.learning.domain.User;
import com.kammradt.learning.domain.enums.Role;
import com.kammradt.learning.dto.UserUpdatePasswordDTO;
import com.kammradt.learning.exception.NotFoundException;
import com.kammradt.learning.exception.WrongConfirmationPasswordException;
import com.kammradt.learning.model.PageModel;
import com.kammradt.learning.model.PageFilterDTO;
import com.kammradt.learning.repository.UserRepository;
import com.kammradt.learning.security.ResourceAccessManager;
import com.kammradt.learning.service.util.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.kammradt.learning.service.util.HashUtil.generateHash;

@Service
public class UserService implements UserDetailsService {

    @Autowired private UserRepository userRepository;
    @Autowired private ResourceAccessManager resourceAccessManager;
    @Autowired private ValidationService validationService;

    public User save(User user) {
        String hashedPassword = generateHash(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public User updateProfile(User user) {
        User currentUser = resourceAccessManager.getCurrentUser();

        if (validationService.isNotNullAndNotEmpty(user.getName()))
            currentUser.setName(user.getName());

        if (validationService.isNotNullAndNotEmpty(user.getEmail()))
            currentUser.setEmail(user.getEmail());

        return userRepository.save(currentUser);
    }

    public User updatePassword(UserUpdatePasswordDTO userDTO) {
        String hashedPassword = generateHash(userDTO.getPassword());
        String hashedPasswordConfirmation = generateHash(userDTO.getConfirmationPassword());

        if (!hashedPassword.equals(hashedPasswordConfirmation))
            throw new WrongConfirmationPasswordException("Passwords do not match!");

        User currentUser = resourceAccessManager.getCurrentUser();
        currentUser.setPassword(hashedPassword);
        return userRepository.save(currentUser);
    }

    public User findById(Long id) {
        Optional<User> result = userRepository.findById(id);
        return result.orElseThrow(() -> new NotFoundException("There are no User with this ID"));
    }

    public User findByEmail(String email) {
        Optional<User> result = userRepository.findByEmail(email);
        return result.orElseThrow(() -> new NotFoundException("There are no User with this Email"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public PageModel<User> listAllOnLazyMode(PageFilterDTO pageFilterDTO) {
        Page<User> resultPage = userRepository.findAll(pageFilterDTO.toPageable());

        return new PageModel<>(
                  (int) resultPage.getTotalElements(),
                        resultPage.getSize(),
                        resultPage.getTotalPages(),
                        resultPage.getContent());
    }

    public User login(String email, String password) {
        password = generateHash(password);
        Optional<User> result = userRepository.login(email, password);
        return result.orElseThrow(() -> new NotFoundException("No user found!"));
    }

    public int updateRole(Long userId, Role newRole) {
        User userToBeUpdated = findById(userId);
        return userRepository.updateRole(userToBeUpdated.getId(), newRole);
    }

    public long count() {
        return userRepository.count();
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User for given credentials do not found!"));

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
