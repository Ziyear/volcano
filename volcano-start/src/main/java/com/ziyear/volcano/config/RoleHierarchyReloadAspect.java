package com.ziyear.volcano.config;

import com.ziyear.volcano.security.rolehierarchy.RoleHierarchyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.stereotype.Component;

/**
 * 功能描述 : 角色重新加载切面
 *
 * @author you_name 2021-11-28 14:32
 */
@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class RoleHierarchyReloadAspect {
    private final RoleHierarchyImpl roleHierarchyImpl;
    private final RoleHierarchyService roleHierarchyService;

    @Pointcut("execution(* com.ziyear.volcano.service.admin.*.*(..))")
    public void pointcut() {

    }

    @AfterReturning("pointcut() && @annotation(com.ziyear.volcano.annotation.ReloadRoleHierarchy)")
    public void reloadRoleHierarchy() {
        String hierarchyExpr = roleHierarchyService.getRoleHierarchyExpr();
        roleHierarchyImpl.setHierarchy(hierarchyExpr);
        log.debug("RoleHierarchy Reload.");
    }
}
