package com.vireosci.sky.common.configure;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

/// 安全相关配置
@Configuration
@EnableWebSecurity
public class SecurityConfiguration
{
    @Resource private ObjectMapper mapper;

    /// 配置密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() { return PasswordEncoderFactories.createDelegatingPasswordEncoder(); }

    /// 配置 [AuthenticationManager]
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity security) throws Exception
    {
        return security
                .getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    private static final String[] allowList = {};

    /// 配置 Spring Security 过滤器链
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception
    {
        return security
                .authenticationManager(authenticationManager(security))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(allowList).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling(conf -> {
                    conf
                            .authenticationEntryPoint(
                                    (req, rsp, e) ->
                                            handle(rsp, e, HttpStatus.UNAUTHORIZED, "认证失败", e.getMessage())
                            )
                            .accessDeniedHandler(
                                    (req, rsp, e) ->
                                            handle(
                                                    rsp, e, HttpStatus.FORBIDDEN, "拒绝访问",
                                                    "抱歉，您没有足够的访问权限"
                                            )
                            );
                })
                .build();
    }

    private void handle(HttpServletResponse rsp, Exception e, HttpStatus status, String title, String detail)
            throws IOException
    {
        rsp.setStatus(status.value());
        rsp.setContentType("application/problem+json");
        var problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle(title);
        mapper.writeValue(rsp.getOutputStream(), problemDetail);
    }
}
