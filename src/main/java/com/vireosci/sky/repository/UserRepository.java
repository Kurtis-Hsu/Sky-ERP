package com.vireosci.sky.repository;

import com.vireosci.sky.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/// 用户数据仓库
public interface UserRepository extends JpaRepository<User, String> { }
