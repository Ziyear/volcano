package com.ziyear.volcano.rest;

import com.ziyear.volcano.domain.Auth;
import com.ziyear.volcano.domain.User;
import com.ziyear.volcano.domain.dto.LoginDTO;
import com.ziyear.volcano.domain.dto.SendTotpDto;
import com.ziyear.volcano.domain.dto.TotpVerificationDto;
import com.ziyear.volcano.domain.dto.UserDto;
import com.ziyear.volcano.enums.MfaType;
import com.ziyear.volcano.exception.*;
import com.ziyear.volcano.service.EmailService;
import com.ziyear.volcano.service.SmsService;
import com.ziyear.volcano.service.UserCacheService;
import com.ziyear.volcano.service.UserService;
import com.ziyear.volcano.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/authorize")
@RequiredArgsConstructor
public class AuthorizeResource {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserCacheService userCacheService;
    private final SmsService smsService;
    private final EmailService emailService;

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
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        return userService.findOptionalByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword())
                .map(user -> {
                    userService.upgradePasswordEncodingIfNeeded(user, loginDTO.getPassword());
                    if (!user.isEnabled()) {
                        throw new UserNotEnabledProblem();
                    }
                    if (!user.isAccountNonLocked()) {
                        throw new UserAccountLockedProblem();
                    }
                    if (!user.isAccountNonExpired()) {
                        throw new UserAccountExpiredProblem();
                    }
                    // 不使用多因子认证
                    if (!user.isUsingMfa()) {
                        return ResponseEntity.ok().body(userService.login(user));
                    }
                    // 使用多因子认证
                    val mfaId = userCacheService.cacheUser(user);
                    return ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .header("X-Authenticate", "mfa", "realm=" + mfaId)
                            .build();
                })
                .orElseThrow(BadCredentialProblem::new);

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

    @PostMapping("/totp")
    public void sendTotp(@Valid @RequestBody SendTotpDto sendTotpDto) {
        Optional<Pair<User, String>> userStringPair = userCacheService.retrieveUser(sendTotpDto.getMfaId())
                .flatMap(user -> userService.createTotp(user.getMfaKey()).map(totp -> Pair.of(user, totp)));
        if (userStringPair.isPresent()) {
            Pair<User, String> userTotpPair = userStringPair.get();
            if (MfaType.SMS.equals(sendTotpDto.getMfaType())) {
                smsService.send(userTotpPair.getFirst().getMobile(), userTotpPair.getSecond());
            } else {
                emailService.send(userTotpPair.getFirst().getEmail(), userTotpPair.getSecond());
            }
        } else {
            throw new InvalidTotpProblem();
        }
    }

    @PostMapping("/verifyTotp")
    public Auth verifyTotp(@Valid @RequestBody TotpVerificationDto verificationDto) {
        return userCacheService.verifyTotp(verificationDto.getMfaId(), verificationDto.getCode())
                .map(userService::login)
                .orElseThrow(InvalidTotpProblem::new);
    }
}
