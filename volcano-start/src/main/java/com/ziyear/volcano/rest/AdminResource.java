package com.ziyear.volcano.rest;

import com.querydsl.core.types.Predicate;
import com.ziyear.volcano.dao.UserDao;
import com.ziyear.volcano.domain.Permission;
import com.ziyear.volcano.domain.Role;
import com.ziyear.volcano.domain.User;
import com.ziyear.volcano.domain.dto.*;
import com.ziyear.volcano.service.UserService;
import com.ziyear.volcano.service.admin.PermissionAdminService;
import com.ziyear.volcano.service.admin.RoleAdminService;
import com.ziyear.volcano.service.admin.UserAdminService;
import com.ziyear.volcano.service.validation.RoleValidationService;
import com.ziyear.volcano.service.validation.UserValidationService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminResource {

    private final UserService userService;
    private final UserAdminService userAdminService;
    private final RoleAdminService roleAdminService;
    private final PermissionAdminService permissionAdminService;
    private final UserValidationService userValidationService;
    private final RoleValidationService roleValidationService;

    @GetMapping("/users")
    public Page<UserDto> findAllUsers(@QuerydslPredicate(root = User.class,bindings = UserDao.class) Predicate predicate, Pageable pageable) {
        return userAdminService.findAll(predicate, pageable)
                .map(user -> UserDto.fromUser.apply(user));
    }

    @GetMapping("/users/{username}")
    public UserDto findByUsername(@PathVariable String username) {
        return userAdminService.findByUsername(username)
                .map(user -> UserDto.fromUser.apply(user))
                .orElseThrow(RuntimeException::new);
    }

    @PostMapping("/users")
    public UserDto createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        userValidationService.validateUserUniqueFields(createUserDto.getUsername(), createUserDto.getEmail(), createUserDto.getMobile());
        val user = userAdminService.createUser(createUserDto);
        return UserDto.fromUser.apply(user);
    }

    @PutMapping("/users/{username}")
    public UserDto updateUser(@PathVariable String username, @Valid @RequestBody UserProfileDto userProfileDto) {
        return userService.findOptionalByUsername(username)
                .map(user -> {
                    val toSave = user
                            .withName(userProfileDto.getName())
                            .withEmail(userProfileDto.getEmail())
                            .withMobile(userProfileDto.getMobile());
                    val saved = userService.saveUser(toSave);
                    return UserDto.fromUser.apply(saved);
                })
                .orElseThrow(RuntimeException::new);
    }

    @PutMapping("/users/{username}/enabled")
    public UserDto toggleUserEnabled(@PathVariable String username) {
        val user = userAdminService.toggleEnabled(username);
        return UserDto.fromUser.apply(user);
    }

    @GetMapping("/users/{username}/roles/available")
    public Set<Role> getUserAvailableRoles(@PathVariable String username) {
        return userAdminService.findAvailableRolesByUserId(username);
    }

    @PutMapping("/users/{username}/roles")
    public UserDto updateUserRoles(@PathVariable String username, @RequestBody List<Long> roleIds) {
        val user = userAdminService.updateRoles(username, roleIds);
        return UserDto.fromUser.apply(user);
    }

    @GetMapping("/roles")
    public Page<RoleDto> findAllRoles(@QuerydslPredicate(root = Role.class) Predicate predicate, Pageable pageable) {
        return roleAdminService.findAll(predicate, pageable).map(role -> RoleDto.fromRole.apply(role));
    }

    @PostMapping("/roles")
    public RoleDto addRole(@Valid @RequestBody CreateOrUpdateRoleDto createOrUpdateRoleDto) {
        val role = roleAdminService.createRole(createOrUpdateRoleDto);
        return RoleDto.fromRole.apply(role);
    }

    @GetMapping("/roles/{roleId}")
    public RoleDto getRole(@PathVariable Long roleId) {
        return roleAdminService.findById(roleId)
                .map(role -> RoleDto.fromRole.apply(role))
                .orElseThrow(RuntimeException::new);
    }

    @PutMapping("/roles/{roleId}")
    public RoleDto updateRole(@PathVariable Long roleId, @Valid @RequestBody CreateOrUpdateRoleDto createOrUpdateRoleDto) {
        val role = roleAdminService.updateRole(roleId, createOrUpdateRoleDto);
        return RoleDto.fromRole.apply(role);
    }

    @DeleteMapping("/roles/{roleId}")
    public void deleteRole(@PathVariable Long roleId) {
        roleAdminService.deleteRole(roleId);
    }

    @GetMapping("/roles/{roleId}/permissions/available")
    public Set<Permission> findAvailablePermissions(@PathVariable Long roleId) {
        return roleAdminService.findAvailablePermissions(roleId);
    }

    @PutMapping("/roles/{roleId}/permissions")
    public RoleDto removeRolePermissions(@PathVariable Long roleId, @RequestBody List<Long> permissionIds) {
        val role = roleAdminService.updatePermissions(roleId, permissionIds);
        return RoleDto.fromRole.apply(role);
    }

    @GetMapping("/permissions")
    public List<Permission> findAllPermissions() {
        return permissionAdminService.findAll();
    }

    @GetMapping("/validation/email")
    public boolean validateEmail(@RequestParam String email, @RequestParam String username) {
        return userValidationService.isEmailExistedAndUsernameIsNot(email, username);
    }

    @GetMapping("/validation/mobile")
    public boolean validateMobile(@RequestParam String mobile, @RequestParam String username) {
        return userValidationService.isMobileExistedAndUsernameIsNot(mobile, username);
    }

    @GetMapping("/validation/role-name")
    public boolean validateRoleName(@RequestParam String roleName) {
        return roleValidationService.isRoleNameExisted(roleName);
    }

    @GetMapping("/validation/roles/{id}/role-name")
    public boolean validateRoleNameNotSelf(@PathVariable Long id, @RequestParam String roleName) {
        return roleValidationService.isRoleNameExistedAndIdIsNot(roleName, id);
    }
}
