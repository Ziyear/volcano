package com.ziyear.volcano.security.auth.ldap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.naming.Name;
import java.util.Collection;
import java.util.Collections;

/**
 * 功能描述 : TODO
 *
 * @author zhaorui 2021-10-27 18:11
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entry(objectClasses = {"inetOrgPerson", "organizationalPerson", "person", "top"})
public final class LdapUser implements UserDetails {
    @Id
    @JsonIgnore
    private Name id;

    @Attribute(name = "uid")
    private String username;

    @Attribute(name = "userPassword")
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
