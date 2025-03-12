package com.vireosci.sky.service;

import com.vireosci.sky.domain.SysUser;
import com.vireosci.sky.repository.SysUserRepository;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/// 用户服务
@Service
public class SysUserService
{
    private static final Logger log = LoggerFactory.getLogger(SysUserService.class);

    @Resource private PasswordEncoder passwordEncoder;
    @Resource private SysUserRepository sysUserRepository;

    /// 注册用户
    ///
    /// @param principal   身份（例如手机号）
    /// @param credentials 凭证（密码）
    public void register(String principal, String credentials)
    {
        var user = new SysUser();
        user.setPrincipal(principal);
        user.encodePassword(credentials, passwordEncoder);

        log.debug("注册用户: {}", user);
        sysUserRepository.save(user);
    }
}
