package com.vireosci.sky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/// 天际 ERP 系统应用
@SpringBootApplication
public class App
{
    /// 项目运行时生成的密钥对
    ///
    /// **该密钥对会在每次重启项目时重置**
    public static final KeyPair RUNTIME_KEY_PAIR;

    static
    {
        try
        {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            RUNTIME_KEY_PAIR = keyGen.generateKeyPair();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) { SpringApplication.run(App.class, args); }
}
