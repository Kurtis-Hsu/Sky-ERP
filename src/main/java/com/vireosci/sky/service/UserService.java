package com.vireosci.sky.service;

import com.vireosci.sky.common.util.AopContextSupport;
import com.vireosci.sky.domain.User;
import com.vireosci.sky.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/// 用户服务
@Service
public class UserService implements AopContextSupport<UserService>
{
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository)
    {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /// 注册用户
    ///
    /// @param principal   身份（手机号/邮箱）
    /// @param credentials 凭证（密码）
    public User register(String principal, String credentials)
    {
        var user = new User();
        user.setPrincipal(principal);
        user.setEncodePassword(credentials, passwordEncoder);

        log.debug("注册用户: {}", user);
        userRepository.save(user);
        return user;
    }
}
