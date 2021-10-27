package com.ziyear.volcano.service.impl;

import com.google.common.collect.Sets;
import com.ziyear.volcano.dao.RoleDao;
import com.ziyear.volcano.dao.UserDao;
import com.ziyear.volcano.domain.Auth;
import com.ziyear.volcano.domain.User;
import com.ziyear.volcano.service.UserService;
import com.ziyear.volcano.util.Constants;
import com.ziyear.volcano.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 功能描述 : TODO
 *
 * @author you_name 2021-10-27 22:16
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public Auth login(String username, String password) throws AuthenticationException {
        return userDao.findOptionalByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> new Auth(
                        jwtUtil.createAccessToken(user),
                        jwtUtil.createRefreshToken(user)
                ))
                .orElseThrow(() -> new BadCredentialsException("用户名或密码错误"));
    }

    @Override
    public boolean isUserNameExist(String username) {
        return userDao.countByUsername(username) > 0;
    }

    @Override
    public boolean isEmailExist(String email) {
        return userDao.countByEmail(email) > 0;
    }

    @Override
    public boolean isMobileExist(String mobile) {
        return userDao.countByMobile(mobile) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User register(User user) {
        return roleDao.findByRoleCode(Constants.ROLE_USER)
                .map(role -> {
                    User user2Save = user.withAuthorities(Sets.newHashSet(role))
                            .withPassword(passwordEncoder.encode(user.getPassword()));
                    return userDao.save(user2Save);
                })
                .orElseThrow(() -> new RuntimeException("注册失败"));
    }
}
