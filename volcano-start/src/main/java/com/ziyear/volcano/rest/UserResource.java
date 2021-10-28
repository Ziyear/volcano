package com.ziyear.volcano.rest;

import com.ziyear.volcano.domain.User;
import com.ziyear.volcano.util.SecurityUtil;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;

@RestController
@RequestMapping("/api")
public class UserResource {
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
}
