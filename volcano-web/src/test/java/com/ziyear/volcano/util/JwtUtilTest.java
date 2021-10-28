package com.ziyear.volcano.util;

import com.ziyear.volcano.config.AppProperties;
import com.ziyear.volcano.domain.Role;
import com.ziyear.volcano.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

/**
 * 功能描述 : TODO
 *
 * @author you_name 2021-10-27 21:48
 */
@ExtendWith(SpringExtension.class)
public class JwtUtilTest {
    private JwtUtil jwtUtil;

    @BeforeEach
    public void before() {
        jwtUtil = new JwtUtil(new AppProperties());
    }

    @Test
    public void test() {
        User user = new User();
        user.setUsername("zhang san");
        Role role1 = new Role();
        role1.setRoleCode("ROLE_ADMIN");
        Role role2 = new Role();
        role2.setRoleCode("ROLE_USER");
        Set<Role> list = new HashSet<>();
        list.add(role1);
        list.add(role2);
        user.setAuthorities(list);
        String jwtToken = jwtUtil.createAccessToken(user);
        System.out.println(jwtToken);
    }
}
