package com.ziyear.volcano.service.validation;

import com.ziyear.volcano.dao.RoleDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleValidationService {

    private final RoleDao roleDao;

    public boolean isRoleNameExisted(String roleName) {
        return roleDao.countByRoleCodeIgnoreCase(roleName) > 0;
    }

    public boolean isRoleNameExistedAndIdIsNot(String roleName, Long id) {
        return roleDao.countByRoleCodeIgnoreCaseAndIdNot(roleName, id) > 0;
    }

    public boolean isRoleAssigned(Long id) {
        return roleDao.countByAssigned(id) > 0;
    }
}
