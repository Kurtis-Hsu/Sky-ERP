package com.vireosci.sky;

import com.vireosci.sky.repository.UserRepository;
import com.vireosci.sky.service.UserService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest
class AppTests
{
    @Resource private UserService userService;
    @Resource private UserRepository userRepository;

    @Test
    @Commit
    @Transactional
    void test()
    {
        var user = userService.register("13344445555", "testPWD123");
        userService.register("233QWQ@test.com", "testPWD123");
        userRepository.flush();

        // System.out.println(userRepository.findNicknameById(user.getId()));
        userRepository.delete(user);
    }
}
