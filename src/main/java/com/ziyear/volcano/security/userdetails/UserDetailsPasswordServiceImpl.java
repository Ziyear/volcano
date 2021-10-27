package com.ziyear.volcano.security.userdetails;

import com.ziyear.volcano.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Service
public class UserDetailsPasswordServiceImpl implements UserDetailsPasswordService {

    private final UserDao userDao;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return userDao.findOptionalByUsername(user.getUsername())
                .map(userFromDb -> (UserDetails) userDao.save(userFromDb.withPassword(newPassword)))
                .orElse(user);
    }
}
