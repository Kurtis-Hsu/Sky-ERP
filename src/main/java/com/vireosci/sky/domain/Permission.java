package com.vireosci.sky.domain;

import com.vireosci.sky.common.jpa.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;

/// 权限
@Entity
@Comment("权限表")
@Table(
        name = "permissions",
        uniqueConstraints = @UniqueConstraint(name = "uk_name", columnNames = { "name", "deleted_at" })
)
@SuppressWarnings("unused")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE #{#entityName} SET deleted_at = now() WHERE id = ?;")
public class Permission extends BaseEntity implements GrantedAuthority
{
    public static final String PREFIX = "PERMISSION:";

    /// 数据ID
    @Comment("数据ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Integer id;

    /// 权限标记
    @Comment("权限名称")
    @Column(nullable = false, updatable = false, length = 50)
    private String name;

    /// 权限描述
    @Comment("权限描述")
    private String description;

    @Override public String getAuthority() { return PREFIX + name; }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
