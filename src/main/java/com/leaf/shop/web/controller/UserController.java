package com.leaf.shop.web.controller;

import com.leaf.shop.domain.user.User;
import com.leaf.shop.service.UserService;
import com.leaf.shop.dto.UserInfoDto;
import com.leaf.shop.security.AuthUser;
import com.leaf.shop.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/check")
    public boolean checkUser() {
        return true;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public UserInfoDto findUser(@AuthUser UserPrincipal user) {
        User findUser = userService.findUserById(user.getUserId());
        return new UserInfoDto(findUser.getId(), findUser.getNickname(), findUser.getEmail());
    }

}
    