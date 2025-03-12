package com.vireosci.sky.repository;

import com.vireosci.sky.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/// 用户数据仓库
@Repository
public interface SysUserRepository extends JpaRepository<SysUser, String>
{
}
