package com.vireosci.sky.common.configure;

import com.vireosci.sky.common.util.IpUtils;
import com.vireosci.sky.common.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        var environment = context.getEnvironment();

        var projectName = Utils.defaultIfNull(environment.getProperty("spring.application.name"), "project");
        var projectVersion = Utils.defaultIfNull(environment.getProperty("spring.application.version"), "unknown");
        var startTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        var protocol = Utils.defaultIfNull(environment.getProperty("server.protocol"), "http");
        var port = Utils.defaultIfNull(environment.getProperty("server.port"), "8080");

        logger.info(
                """
                        
                        ---------------------------------------------------------------------------
                            (♥◠‿◠)ﾉﾞ [ %s ] runs successfully!  ლ(´ڡ`ლ)ﾞ
                                 Version: %s
                            Started time: %s
                                     Url: %s://%s:%s
                             System info: %s %s
                               Java info: %s v%s
                        ---------------------------------------------------------------------------
                        """
                        .formatted(
                                projectName, projectVersion, startTime, protocol, IpUtils.localIp(), port,
                                System.getProperty("os.name"), System.getProperty("os.arch"),
                                System.getProperty("java.vm.name"), System.getProperty("java.version")
                        )
        );
    }
}
