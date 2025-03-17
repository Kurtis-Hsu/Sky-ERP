package com.vireosci.sky.repository;

import com.vireosci.sky.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission  , Integer> { }
