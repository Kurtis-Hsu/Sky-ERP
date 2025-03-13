package com.vireosci.sky.module;

import com.vireosci.sky.repository.SysUserRepository;
import com.vireosci.sky.service.SysUserService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest
public class SysUserTests
{
    @Resource private SysUserService sysUserService;
    @Resource private SysUserRepository sysUserRepository;

    @Test
    @Commit
    @Transactional
    void user()
    {
        sysUserService.register("13344445555", "testPWD123");
        sysUserService.register("233QWQ@test.com", "testPWD123");

        var users = sysUserRepository.findAll();
        var updatedUser = users.getFirst();
        users.forEach(System.out::println);

        updatedUser.setNickname("test");
        updatedUser.setFirstName("测试");
        updatedUser.setSecondName("人员");
        sysUserRepository.saveAndFlush(updatedUser);

        sysUserRepository.deleteById(updatedUser.getId());

        sysUserRepository.findAll().forEach(user -> { assert user.getDeletedAt() == null; });
    }
}
