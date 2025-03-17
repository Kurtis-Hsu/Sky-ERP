package com.vireosci.sky.repository;

import com.vireosci.sky.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>
{
}
