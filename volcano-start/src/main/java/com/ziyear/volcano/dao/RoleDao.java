package com.ziyear.volcano.dao;

import com.querydsl.core.types.dsl.StringExpression;
import com.ziyear.volcano.domain.QRole;
import com.ziyear.volcano.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 功能描述 : TODO
 *
 * @author you_name 2021-10-27 23:18
 */
@Repository
public interface RoleDao extends JpaRepository<Role, Long>, QuerydslPredicateExecutor<Role>, QuerydslBinderCustomizer<QRole> {


    Optional<Role> findByRoleCode(String roleCode);

    long countByRoleCodeIgnoreCase(String roleCode);

    long countByRoleCodeIgnoreCaseAndIdNot(String roleCode, Long id);

    @Query("select count(r) from Role r inner join r.users ru where r.id = ?1")
    long countByAssigned(Long id);

    Optional<Role> findOptionalByRoleName(String roleStaff);

    Set<Role> findByIdIn(HashSet<Long> longs);

    @Override
    default void customize(QuerydslBindings bindings, QRole root) {
        bindings.bind(root.roleName).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.displayName).first(StringExpression::containsIgnoreCase);
    }
}
