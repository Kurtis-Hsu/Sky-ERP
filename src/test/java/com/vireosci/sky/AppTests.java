package com.vireosci.sky;

import com.vireosci.sky.repository.UserRepository;
import com.vireosci.sky.service.JwtService;
import com.vireosci.sky.service.UserService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.jose4j.lang.JoseException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest
class AppTests
{
    @Resource private UserService userService;
    @Resource private UserRepository userRepository;
    @Resource private JwtService jwtService;

    @Test
    @Commit
    @Transactional
    void test() throws JoseException
    {
        var user = userService.register("13344445555", "testPWD123");
        userService.register("233QWQ@test.com", "testPWD123");
        userRepository.flush();

        var jwt = jwtService.generateLoginJwt(user);
        System.out.println("生成 JWT：" + jwt);
        System.out.println("解析 JWT：" + jwtService.parseLoginJwt(jwt).toJson());
    }
}
