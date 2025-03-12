package com.vireosci.sky.module;

import com.vireosci.sky.domain.SysUser;
import com.vireosci.sky.repository.SysUserRepository;
import com.vireosci.sky.service.SysUserService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@Rollback(false)
@Transactional
@SpringBootTest
public class SysUserTests
{
    @Resource private SysUserService sysUserService;
    @Resource private SysUserRepository sysUserRepository;

    @Test
    void user()
    {
        sysUserService.register("13344445555", "testPWD123");
        sysUserService.register("233QWQ@test.com", "testPWD123");

        var users = sysUserRepository.findAll();
        var userId = users.getFirst().getId();
        users.forEach(System.out::println);

        var updatedUser = new SysUser();
        updatedUser.setId(userId);
        updatedUser.setNickname("test");
        updatedUser.setFirstName("测试");
        updatedUser.setSecondName("人员");
        sysUserRepository.save(updatedUser);
        sysUserRepository.deleteById(userId);
    }
}
