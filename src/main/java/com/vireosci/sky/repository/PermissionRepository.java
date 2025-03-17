package com.vireosci.sky.repository;

import com.vireosci.sky.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/// 权限数据仓库
public interface PermissionRepository extends JpaRepository<Permission, Integer> { }
