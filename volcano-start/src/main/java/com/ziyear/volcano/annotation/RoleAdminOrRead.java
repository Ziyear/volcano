package com.ziyear.volcano.annotation;

import com.ziyear.volcano.util.Constants;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority('" +
    Constants.ROLE_ADMIN +
    "', '" +
    Constants.AUTHORITY_USER_READ +
    "')")
public @interface RoleAdminOrRead {
}
