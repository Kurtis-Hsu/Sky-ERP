package com.vireosci.sky.repository;

import com.vireosci.sky.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/// 角色数据仓库
public interface RoleRepository extends JpaRepository<Role, Integer> { }
