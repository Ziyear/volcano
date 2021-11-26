package com.ziyear.volcano.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryEntity;
import com.ziyear.volcano.util.Constants;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 角色实体类，实现 GrantedAuthority 接口
 */
//@With
//@Builder
//@QueryEntity
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//@Table(name = "")
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@QueryEntity
@Entity
@Table(name = "volcano_roles")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增长 ID，唯一标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色名称，有唯一约束，不能重复
     */
    @NotNull
    @Pattern(regexp = Constants.PATTERN_ROLE_NAME)
    @Column(unique = true, nullable = false, length = 50)
    private String roleName;

    @Size(max = 50)
    @Column(unique = true, nullable = false, length = 50)
    private String roleCode;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String displayName;

    @NotNull
    @Column(name = "built_in", nullable = false)
    private boolean builtIn;

    @Builder.Default
    @JsonIgnore
    @Fetch(FetchMode.JOIN)
    @ManyToMany
    @JoinTable(
            name = "volcano_roles_permissions",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    @BatchSize(size = 20)
    @ToString.Exclude
    private Set<Permission> permissions = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    private Set<User> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;

        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(roleCode);
    }
}