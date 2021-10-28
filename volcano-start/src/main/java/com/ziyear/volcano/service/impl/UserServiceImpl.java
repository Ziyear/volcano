package com.ziyear.volcano.service.impl;

import com.google.common.collect.Sets;
import com.ziyear.volcano.dao.RoleDao;
import com.ziyear.volcano.dao.UserDao;
import com.ziyear.volcano.domain.Auth;
import com.ziyear.volcano.domain.User;
import com.ziyear.volcano.service.UserService;
import com.ziyear.volcano.util.Constants;
import com.ziyear.volcano.util.JwtUtil;
import com.ziyear.volcano.util.TotpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    private final TotpUtil totpUtil;

    @Override
    public Auth login(UserDetails userDetails) {
        return new Auth(jwtUtil.createAccessToken(userDetails), jwtUtil.createRefreshToken(userDetails));
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
                            .withPassword(passwordEncoder.encode(user.getPassword()))
                            .withMfaKey(totpUtil.encodeKeyToString());
                    return userDao.save(user2Save);
                })
                .orElseThrow(() -> new RuntimeException("注册失败"));
    }

    @Override
    public Optional<User> findOptionalByUsernameAndPassword(String username, String password) {
        return userDao.findOptionalByUsername(username)
                .filter(user -> passwordEncoder.matches(password,user.getPassword()));
    }

    @Override
    public void upgradePasswordEncodingIfNeeded(User user, String password) {
        if (passwordEncoder.upgradeEncoding(user.getPassword())) {
            userDao.save(user.withPassword(passwordEncoder.encode(password)));
        }
    }

    @Override
    public Optional<String> createTotp(String key) {
        return totpUtil.createTotp(key);
    }
}
