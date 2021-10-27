package com.ziyear.volcano.rest;

import com.ziyear.volcano.domain.Auth;
import com.ziyear.volcano.domain.User;
import com.ziyear.volcano.domain.dto.LoginDTO;
import com.ziyear.volcano.domain.dto.UserDto;
import com.ziyear.volcano.exception.DuplicateProblem;
import com.ziyear.volcano.service.UserService;
import com.ziyear.volcano.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/authorize")
@RequiredArgsConstructor
public class AuthorizeResource {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping(value = "greeting")
    public String sayHello() {
        return "hello world";
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody UserDto userDto) {
        // 1 检查username，email，mobile 唯一
        if (userService.isUserNameExist(userDto.getUsername())) {
            throw new DuplicateProblem("用户名重复");
        }
        if (userService.isEmailExist(userDto.getEmail())) {
            throw new DuplicateProblem("邮箱重复");
        }
        if (userService.isMobileExist(userDto.getMobile())) {
            throw new DuplicateProblem("手机号重复");
        }
        User user = User.builder()
                .username(userDto.getUsername())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .mobile(userDto.getMobile())
                .password(userDto.getPassword())
                .build();
        // 2 userDTO转换成 user 保存 设置默认角色 （ROLE_USER）
        userService.register(user);
    }

    @PostMapping("/token")
    public Auth login(@Valid @RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO.getUsername(), loginDTO.getPassword());
    }

    @PostMapping("/token/refresh")
    public Auth refreshToken(@RequestHeader("Authorization") String authorization,
                             @RequestParam String refreshToken) throws AccessDeniedException {
        String prefix = "Bearer ";
        String accessToken = authorization.replace(prefix, "");
        if (jwtUtil.validateRefreshToken(refreshToken) &&
                jwtUtil.validateAccessTokenWithoutExpiration(accessToken)) {
            return new Auth(jwtUtil.createAccessTokenWithRefreshToken(refreshToken), refreshToken);
        }
        throw new AccessDeniedException("访问被拒绝");
    }
}
