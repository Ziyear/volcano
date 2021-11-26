package com.ziyear.volcano.dao;

import com.ziyear.volcano.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

/**
 * 功能描述 : TODO
 *
 * @author you_name 2021-10-30 14:22
 */
@Repository
public interface PermissionDao extends JpaRepository<Permission, Long> {
    Set<Permission> findByIdIn(HashSet<Long> longs);
}
