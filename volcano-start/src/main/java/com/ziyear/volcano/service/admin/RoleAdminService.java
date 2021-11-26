package com.ziyear.volcano.service.admin;

import com.querydsl.core.types.Predicate;
import com.ziyear.volcano.annotation.ReloadRoleHierarchy;
import com.ziyear.volcano.annotation.RoleAdmin;
import com.ziyear.volcano.annotation.RoleAdminOrRead;
import com.ziyear.volcano.dao.PermissionDao;
import com.ziyear.volcano.dao.RoleDao;
import com.ziyear.volcano.domain.Permission;
import com.ziyear.volcano.domain.Role;
import com.ziyear.volcano.domain.dto.CreateOrUpdateRoleDto;
import com.ziyear.volcano.exception.DataConflictProblem;
import com.ziyear.volcano.exception.DuplicateProblem;
import com.ziyear.volcano.service.validation.RoleValidationService;
import com.ziyear.volcano.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoleAdminService {

    private final RoleDao roleDao;
    private final PermissionDao permissionDao;
    private final RoleValidationService roleValidationService;

    public Page<Role> findAll(Predicate predicate, Pageable pageable) {
        return roleDao.findAll(predicate, pageable);
    }

    @ReloadRoleHierarchy
    @RoleAdmin
    @Transactional
    public Role createRole(final CreateOrUpdateRoleDto createOrUpdateRoleDto) {
        if (roleValidationService.isRoleNameExisted(createOrUpdateRoleDto.getRoleName())) {
            throw new DuplicateProblem("角色名称已存在，请修改后再试");
        }
        return roleDao.save(Role.builder()
                .roleName(createOrUpdateRoleDto.getRoleName().toUpperCase())
                .displayName(createOrUpdateRoleDto.getDisplayName())
                .build());
    }

    @ReloadRoleHierarchy
    @RoleAdmin
    @Transactional
    public Role updateRole(Long id, CreateOrUpdateRoleDto createOrUpdateRoleDto) {
        if (roleValidationService.isRoleNameExistedAndIdIsNot(createOrUpdateRoleDto.getRoleName(), id)) {
            throw new DuplicateProblem("角色名称已存在，请修改后再试");
        }
        return roleDao.findById(id)
                .map(role -> roleDao.save(role
                        .withRoleName(createOrUpdateRoleDto.getRoleName().toUpperCase())
                        .withDisplayName(createOrUpdateRoleDto.getDisplayName())
                        .withPermissions(new HashSet<>())
                ))
                .orElseThrow(Constants.ROLE_NOT_FOUND_ERR);
    }

    @ReloadRoleHierarchy
    @RoleAdmin
    @Transactional
    public void deleteRole(Long id) {
        if (roleValidationService.isRoleAssigned(id)) {
            throw new DataConflictProblem("该角色已经分配，请先从所有用户移除该角色");
        }
        roleDao.findById(id).ifPresent(role -> {
            if (role.isBuiltIn()) {
                throw new DataConflictProblem("该角色为内置角色，不能删除！");
            }
            roleDao.deleteById(id);
        });
    }

    @ReloadRoleHierarchy
    @RoleAdmin
    @Transactional
    public Role updatePermissions(Long id, List<Long> permissionIds) {
        return roleDao.findById(id)
                .map(role -> {
                    val permissionsFiltered = permissionDao.findByIdIn(new HashSet<>(permissionIds));
                    return roleDao.save(role.withPermissions(permissionsFiltered));
                })
                .orElseThrow((Constants.ROLE_NOT_FOUND_ERR));
    }

    @RoleAdminOrRead
    public Optional<Role> findById(final Long roleId) {
        return roleDao.findById(roleId);
    }

    @RoleAdminOrRead
    public Set<Permission> findAvailablePermissions(final Long roleId) {
        return roleDao.findById(roleId)
                .map(role -> {
                    val assignedPermissions = role.getPermissions().stream().map(Permission::getAuthority).collect(Collectors.toSet());
                    return permissionDao.findAll().stream().filter(permission -> !assignedPermissions.contains(permission.getAuthority())).collect(Collectors.toSet());
                })
                .orElseThrow(Constants.ROLE_NOT_FOUND_ERR);
    }
}
