package com.ziyear.volcano.service;

import com.ziyear.volcano.domain.Auth;
import com.ziyear.volcano.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * 功能描述 : TODO
 *
 * @author you_name 2021-10-27 22:16
 */
public interface UserService {

    Auth login(UserDetails userDetails) throws AuthenticationException;

    boolean isUserNameExist(String username);
    boolean isEmailExist(String email);
    boolean isMobileExist(String mobile);


    User saveUser(User user);

    Optional<User> findOptionalByUsernameAndPassword(String username, String password);

    void upgradePasswordEncodingIfNeeded(User user, String password);

    Optional<String> createTotp(String key);

    boolean isValidUser(Authentication authentication,String username);

    Optional<User> findOptionalByEmail(String email);
}
