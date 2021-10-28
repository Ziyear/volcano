package com.ziyear.volcano.security.auth.ldap;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.ldap.DataLdapTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@DataLdapTest
public class LDAPUserRepoIntTests {

    @Autowired
    private LDAPUserDao ldapUserDao;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void givenUsername_ThenFindUserSuccess() {
        val user = ldapUserDao.findByUsername("zhaoliu");
        assertTrue(user.isPresent());
    }

    @Test
    public void givenUsernameAndPassword_ThenFindUserSuccess() {
        val user = ldapUserDao.findByUsernameAndPassword("zhaoliu", "123");
        assertTrue(user.isPresent());
    }

    @Test
    public void givenUsernameAndWrongPassword_ThenFindUserFail() {
        Optional<LdapUser> ldapUser = ldapUserDao.findByUsernameAndPassword("zhaoliu", "bad_password");
        assertTrue(ldapUser.isPresent());
    }
}
