package com.ziyear.volcano.dao;

import com.ziyear.volcano.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 功能描述 : TODO
 *
 * @author you_name 2021-10-27 23:18
 */
@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleCode(String roleCode);
}
