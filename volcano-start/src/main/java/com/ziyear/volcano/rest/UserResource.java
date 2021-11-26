package com.ziyear.volcano.rest;

import com.ziyear.volcano.domain.User;
import com.ziyear.volcano.exception.NotFoundException;
import com.ziyear.volcano.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;

    @GetMapping("/me")
    public User getProfile() {
        return User.builder()
                .name("张三")
                .username("zhangsan")
                .build();
    }

    @GetMapping("/principal")
    public String getCurrentPrincipalName(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/authentication")
    public Authentication getCurrentAuthentication(Authentication authentication) {
        return authentication;
    }

    @GetMapping("/users/{username}")
    public String getCurrentUsername(@PathVariable String username) {
        return username;
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @PostAuthorize("authentication.name.equals(returnObject.name)")
    @GetMapping("/users/email/{email}")
    public User getUserByEmail(@PathVariable String email) throws NotFoundException {
        return userService.findOptionalByEmail(email).orElseThrow(() -> new NotFoundException("根据邮箱为找到用户！"));
    }

    @GetMapping("/users/manager")
    public String manager() throws NotFoundException {
        return "MANAGER";
    }
}
