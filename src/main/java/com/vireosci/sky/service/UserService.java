package com.vireosci.sky.service;

import com.vireosci.sky.common.property.SecurityProperties;
import com.vireosci.sky.common.util.AopContextSupport;
import com.vireosci.sky.domain.User;
import com.vireosci.sky.repository.UserRepository;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/// 用户服务
@Service
public class UserService implements AopContextSupport<UserService>
{
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Resource private PasswordEncoder passwordEncoder;
    @Resource private UserRepository userRepository;
    @Resource private SecurityProperties securityProperties;
    @Resource private org.springframework.context.MessageSource messageSource;

    /// 注册用户
    ///
    /// @param principal   身份（手机号/邮箱）
    /// @param credentials 凭证（密码）
    public void register(String principal, String credentials)
    {
        var user = new User();
        user.setPrincipal(principal);
        user.setEncodePassword(credentials, passwordEncoder);

        log.debug("注册用户: {}", user);
        userRepository.save(user);
    }
}
