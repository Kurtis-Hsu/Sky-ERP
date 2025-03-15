package com.vireosci.sky.common;

import com.vireosci.sky.common.util.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.vireosci.sky.common.util.Utils.defaultIfNull;

/// 应用启动监听器
public class BootListener implements SpringApplicationRunListener
{
    private final Logger logger;

    public BootListener(SpringApplication app, String[] ignoredArgs)
    {
        logger = LoggerFactory.getLogger(app.getMainApplicationClass());
    }

    @Override public void ready(ConfigurableApplicationContext context, Duration timeTaken)
    {
        if (!logger.isInfoEnabled()) return;

        var environment = context.getEnvironment();

        var osInfo = ManagementFactory.getOperatingSystemMXBean();
        var jvmInfo = ManagementFactory.getRuntimeMXBean();

        logger.info(
                """
                        
                        ---------------------------------------------------------------------------
                            (♥◠‿◠)ﾉﾞ [ {} ] runs successfully!  ლ(´ڡ`ლ)ﾞ
                                 Version: {}
                            Started time: {}
                                     Url: {}://{}:{}
                             System info: {} {}
                               Java info: {} v{}
                        ---------------------------------------------------------------------------
                        """,
                defaultIfNull(environment.getProperty("spring.application.name"), "project"),
                defaultIfNull(environment.getProperty("spring.application.version"), "unknown"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()),
                defaultIfNull(environment.getProperty("server.protocol"), "http"),
                IpUtils.localIp(),
                defaultIfNull(environment.getProperty("server.port"), "8080"),
                osInfo.getName(), osInfo.getArch(),
                jvmInfo.getVmName(), jvmInfo.getVmVersion()
        );
    }
}
