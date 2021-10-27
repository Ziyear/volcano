package com.ziyear.volcano.service;

import com.ziyear.volcano.domain.Auth;
import com.ziyear.volcano.domain.User;
import org.springframework.security.core.AuthenticationException;

/**
 * 功能描述 : TODO
 *
 * @author you_name 2021-10-27 22:16
 */
public interface UserService {

    Auth login(String username, String password) throws AuthenticationException;

    boolean isUserNameExist(String username);
    boolean isEmailExist(String email);
    boolean isMobileExist(String mobile);


    User register(User user);
}
