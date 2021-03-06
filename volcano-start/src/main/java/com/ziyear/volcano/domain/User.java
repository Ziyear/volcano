package com.ziyear.volcano.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryEntity;
import com.ziyear.volcano.validation.annotation.ValidEmail;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用户实体类，实现了 UserDetails 接口
 */
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@QueryEntity
@Entity
@Table(name = "volcano_users")
public class User implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增长 ID，唯一标识
     */
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String username;

    /**
     * 手机号
     */
    @Getter
    @Setter
    @NotNull
    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$")
    @Size(min = 11, max = 11)
    @Column(length = 11, unique = true, nullable = false)
    private String mobile;

    /**
     * 姓名
     */
    @Getter
    @Setter
    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    private String name;

    /**
     * 是否激活，默认激活
     */
    @Builder.Default
    @Setter
    @NotNull
    @Column(nullable = false)
    private Boolean enabled = true;

    /**
     * 账户是否未过期，默认未过期
     */
    @Builder.Default
    @Setter
    @NotNull
    @Column
    private Boolean accountNonExpired = true;

    /**
     * 账户是否未锁定，默认未锁定
     */
    @Builder.Default
    @Setter
    @NotNull
    @Column
    private Boolean accountNonLocked = true;

    /**
     * 密码是否未过期，默认未过期
     */
    @Builder.Default
    @Setter
    @NotNull
    private Boolean credentialsNonExpired = true;

    /**
     * 密码哈希
     */
    @Getter
    @Setter
    @JsonIgnore
    @NotNull
    @Size(min = 40, max = 80)
    @Column(name = "password_hash", length = 100, nullable = false)
    private String password;

    /**
     * 电邮地址
     */
    @Getter
    @Setter
    @ValidEmail
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true, nullable = false)
    private String email;


    /**
     * 是否启用两步验证
     */
    @Getter
    @Setter
    @Builder.Default
    @NotNull
    @Column(name = "using_mfa", nullable = false)
    private boolean usingMfa = false;

    /**
     * 两步验证的key
     */
    @JsonIgnore
    @Getter
    @Setter
    @NotNull
    @Column(name = "mfa_key", nullable = false)
    private String mfaKey;

    /**
     * 角色列表，使用 Set 确保不重复
     */
    @Getter
    @Setter
    @JsonIgnore
    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "volcano_users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().flatMap(role -> Stream.concat(Stream.of(new SimpleGrantedAuthority(role.getRoleCode())), role.getPermissions().stream())).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}