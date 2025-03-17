package com.vireosci.sky;

import com.vireosci.sky.repository.UserRepository;
import com.vireosci.sky.service.UserService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppTests
{
    @Resource private UserService userService;
    @Resource private UserRepository userRepository;

    @Test
    @Transactional
    void test()
    {
        userService.register("13344445555", "testPWD123");
        userService.register("233QWQ@test.com", "testPWD123");
        userRepository.flush();
    }
}
