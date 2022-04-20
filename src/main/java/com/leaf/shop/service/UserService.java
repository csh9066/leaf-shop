package com.leaf.shop.service;

import com.leaf.shop.domain.user.User;
import com.leaf.shop.domain.user.UserRepository;
import com.leaf.shop.module.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findUserById(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("user", "id", userId)
                );
        return user;
    }
}
