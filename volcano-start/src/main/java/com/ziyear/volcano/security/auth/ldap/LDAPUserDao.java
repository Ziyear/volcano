package com.ziyear.volcano.security.auth.ldap;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LDAPUserDao extends LdapRepository<LdapUser> {
    Optional<LdapUser> findByUsername(String username);

    Optional<LdapUser> findByUsernameAndPassword(String username, String password);

    List<LdapUser> findByUsernameLikeIgnoreCase(String username);
}

