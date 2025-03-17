package com.vireosci.sky.domain;

import com.vireosci.sky.common.jpa.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(
        name = "roles",
        uniqueConstraints = @UniqueConstraint(name = "uk_name", columnNames = { "name", "deleted_at" })
)
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE roles SET deleted_at = now() WHERE id = ?;")
public class Role extends BaseEntity implements GrantedAuthority
{
    public static final String PREFIX = "ROLE_";

    /// 数据ID
    @Comment("数据ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Integer id;

    /// 角色标记
    @Comment("角色名称")
    @Column(nullable = false, updatable = false, length = 50)
    private String name;

    /// 角色描述
    @Comment("角色描述")
    private String description;

    /// 角色权限集合
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Role> permissions;

    @Override public String getAuthority() { return PREFIX + name; }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Set<Role> getPermissions() { return permissions; }

    public void setPermissions(Set<Role> permissions) { this.permissions = permissions; }
}
