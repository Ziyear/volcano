package com.ziyear.volcano.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@With
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@QueryEntity
@Entity
@Table(name = "volcano_permissions")
public class Permission implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String permissionCode;

    @NotNull
    @Size(max = 50)
    @Column(unique = true, nullable = false, length = 50)
    private String displayName;


    @JsonIgnore
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Permission)) {
            return false;
        }
        return id != null && id.equals(((Permission) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(permissionCode);
    }

    @Override
    public String getAuthority() {
        return permissionCode;
    }
}
