package com.ilginbor.LibraryManagementSystem.user.service;

import com.ilginbor.LibraryManagementSystem.exception.ResourceNotFoundException;
import com.ilginbor.LibraryManagementSystem.user.entity.User;
import com.ilginbor.LibraryManagementSystem.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }
}
