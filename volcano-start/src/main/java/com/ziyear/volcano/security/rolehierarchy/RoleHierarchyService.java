package com.ziyear.volcano.security.rolehierarchy;

import com.ziyear.volcano.dao.RoleDao;
import com.ziyear.volcano.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ziyear.volcano.util.Constants.ROLE_ADMIN;
import static com.ziyear.volcano.util.Constants.ROLE_STAFF;

@RequiredArgsConstructor
@Service
public class RoleHierarchyService {

    private final RoleDao roleDao;

    public String getRoleHierarchyExpr() {
        List<Role> roles = roleDao.findAll();
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream()
                        .map(permission -> role.getRoleName() + " > " + permission.getAuthority() + "\n"))
                .collect(Collectors.joining("", ROLE_ADMIN + " > " + ROLE_STAFF, "\n"));
    }
}
