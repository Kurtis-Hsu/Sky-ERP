package com.vireosci.sky.repository;

import com.vireosci.sky.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/// 用户数据仓库
public interface SysUserRepository extends JpaRepository<SysUser, String>
{
}
