package com.ziyear.volcano.service.admin;

import com.querydsl.core.types.Predicate;
import com.ziyear.volcano.annotation.RoleAdminNotSelf;
import com.ziyear.volcano.annotation.RoleAdminOrCreate;
import com.ziyear.volcano.annotation.RoleAdminOrRead;
import com.ziyear.volcano.annotation.RoleAdminOrUpdate;
import com.ziyear.volcano.dao.RoleDao;
import com.ziyear.volcano.dao.UserDao;
import com.ziyear.volcano.domain.Role;
import com.ziyear.volcano.domain.User;
import com.ziyear.volcano.domain.dto.CreateUserDto;
import com.ziyear.volcano.service.EmailService;
import com.ziyear.volcano.util.Constants;
import com.ziyear.volcano.util.CryptoUtil;
import com.ziyear.volcano.util.TotpUtil;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.ziyear.volcano.util.Constants.ROLE_STAFF;


@RequiredArgsConstructor
@Service
public class UserAdminService {

    private final UserDao userDao;

    private final RoleDao roleDao;

    private final TotpUtil totpUtil;

    private final CryptoUtil cryptoUtil;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    /**
     * 取得全部用户列表
     *
     * @return 全部用户列表
     */
    @RoleAdminOrRead
    public Page<User> findAll(Predicate predicate, Pageable pageable) {
        return userDao.findAll(predicate, pageable);
    }

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    @RoleAdminOrRead
    public Optional<User> findByUsername(String username) {
        return userDao.findOptionalByUsername(username);
    }

    /**
     * 管理员直接创建用户
     *
     * @param createUserDto 用户创建必须的字段
     * @return 创建的用户
     */
    @RoleAdminOrCreate
    @Transactional
    public User createUser(final CreateUserDto createUserDto) {
        return roleDao.findOptionalByRoleName(ROLE_STAFF).map(role -> {
            // 生成默认密码
            val password = cryptoUtil.buildDefaultPassword();
            val user = userDao.save(User.builder()
                    .username(createUserDto.getUsername())
                    .email(createUserDto.getEmail())
                    .mobile(createUserDto.getMobile())
                    .name(createUserDto.getName())
                    .usingMfa(createUserDto.isUsingMfa())
                    .mfaKey(totpUtil.encodeKeyToString())
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singleton(role))
                    .build());
            // 通过 email 发送用户密码
            emailService.send(createUserDto.getEmail(), password);
            return user;
        })
                .orElseThrow(Constants.ROLE_NOT_FOUND_ERR);
    }

    /**
     * 给用户设置角色
     *
     * @param username 用户名
     * @param roleIds  要添加的角色 Id 列表
     * @return 用户
     */
    @RoleAdminNotSelf
    @Transactional
    public User updateRoles(final String username, final List<Long> roleIds) {
        return findByUsername(username)
                .map(user -> {
                    val rolesFound = roleDao.findByIdIn(new HashSet<>(roleIds));
                    return userDao.save(user.withRoles(rolesFound));
                })
                .orElseThrow(Constants.USER_NOT_FOUND_ERR);
    }

    /**
     * 切换用户是否激活状态
     *
     * @param username 用户名
     * @return 用户
     */
    @RoleAdminNotSelf
    @Transactional
    public User toggleEnabled(String username) {
        return findByUsername(username)
                .map(user -> userDao.save(user.withEnabled(!user.isEnabled())))
                .orElseThrow(Constants.USER_NOT_FOUND_ERR);
    }

    /**
     * 切换用户账户是否过期的状态
     *
     * @param username 用户名
     * @return 用户
     */
    @RoleAdminNotSelf
    @Transactional
    public User toggleAccountNonExpired(String username) {
        return findByUsername(username)
                .map(user -> userDao.save(user.withAccountNonExpired(!user.isAccountNonExpired())))
                .orElseThrow(Constants.USER_NOT_FOUND_ERR);
    }

    /**
     * 切换用户账户是否锁定的状态
     *
     * @param username 用户名
     * @return 用户
     */
    @RoleAdminNotSelf
    @Transactional
    public User toggleAccountNonLocked(String username) {
        return findByUsername(username)
                .map(user -> userDao.save(user.withAccountNonLocked(!user.isAccountNonLocked())))
                .orElseThrow(Constants.USER_NOT_FOUND_ERR);
    }

    /**
     * 切换用户密码是否过期的状态
     *
     * @param username 用户名
     * @return 用户
     */
    @RoleAdminNotSelf
    @Transactional
    public User toggleCredentialsNonExpired(String username) {
        return findByUsername(username)
                .map(user -> userDao.save(user.withCredentialsNonExpired(!user.isCredentialsNonExpired())))
                .orElseThrow(Constants.USER_NOT_FOUND_ERR);
    }

    /**
     * 重新生成并发送用户密码
     *
     * @param username 用户名
     * @return 用户
     */
    @RoleAdminOrUpdate
    @Transactional
    public User generatePassword(String username) {
        val password = cryptoUtil.buildDefaultPassword();
        return findByUsername(username)
                .map(user -> {
                    val saved = userDao.save(user.withPassword(passwordEncoder.encode(password)).withMfaKey(totpUtil.encodeKeyToString()));
                    emailService.send(saved.getEmail(), password);
                    return saved;
                })
                .orElseThrow(Constants.USER_NOT_FOUND_ERR);
    }

    /**
     * 查询指定用户的可分配角色列表
     *
     * @param username 用户名
     * @return 用户
     */
    @RoleAdminOrRead
    public Set<Role> findAvailableRolesByUserId(String username) {
        return findByUsername(username)
                .map(user -> {
                    val assignedRoles = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet());
                    return roleDao.findAll().stream().filter(role -> !assignedRoles.contains(role.getRoleName())).collect(Collectors.toSet());
                })
                .orElseThrow(Constants.USER_NOT_FOUND_ERR);
    }
}
