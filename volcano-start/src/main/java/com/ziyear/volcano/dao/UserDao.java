package com.ziyear.volcano.dao;

import com.querydsl.core.types.dsl.StringExpression;
import com.ziyear.volcano.domain.QUser;
import com.ziyear.volcano.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 功能描述 : TODO
 *
 * @author zhaorui 2021-10-27 15:25
 */
@Repository
public interface UserDao extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {
    Optional<User> findOptionalByUsername(String username);

    long countByUsername(String username);

    long countByEmail(String email);

    long countByMobile(String mobile);

    Optional<User> findOptionalByEmail(String email);

    long countByEmailAndUsernameIsNot(String email, String username);

    long countByMobileAndUsernameIsNot(String mobile, String username);

    @Override
    default void customize(QuerydslBindings bindings, QUser root) {
        bindings.bind(root.username).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.name).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.mobile).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.email).first(StringExpression::containsIgnoreCase);
    }
}

