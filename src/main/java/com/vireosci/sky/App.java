package com.vireosci.sky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App
{
    public static final String RUNNING_PROFILE = "running.profile";

    public static void main(String[] args) { SpringApplication.run(App.class, args); }
}
