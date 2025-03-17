package com.vireosci.sky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/// 天际 ERP 系统应用
@SpringBootApplication
public class App
{
    public static final String RUNNING_PROFILE = "running.profile";

    public static void main(String[] args) { SpringApplication.run(App.class, args); }
}
