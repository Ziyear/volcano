package com.ziyear.volcano.service.admin;

import com.ziyear.volcano.annotation.RoleAdminOrRead;
import com.ziyear.volcano.dao.PermissionDao;
import com.ziyear.volcano.domain.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PermissionAdminService {

    private final PermissionDao permissionDao;

    @RoleAdminOrRead
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }
}
