package com.vireosci.sky.service;

import com.vireosci.sky.common.exception.JwtExpiredException;
import com.vireosci.sky.domain.User;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashSet;

/// JWT 服务
@Service
public class JwtService
{
    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    @Value("${spring.application.name}") private String applicationName;

    public static final String JWT_FLAG_LOGIN = "login";

    private final RsaJsonWebKey loginJwk;
    private final JwtConsumer loginJwtConsumer;

    private final MessageService messageService;

    public JwtService(MessageService messageService) throws JoseException
    {
        this.messageService = messageService;

        loginJwk = RsaJwkGenerator.generateJwk(2048);
        loginJwk.setKeyId(JWT_FLAG_LOGIN);

        loginJwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime() // 必须有一个过期时间
                .setAllowedClockSkewInSeconds(60) // 在解析时允许的过期时长，这里设置为1分钟
                .setRequireSubject() // 必须有一个主题
                .setExpectedIssuer(applicationName) // 指定发布人
                .setVerificationKey(loginJwk.getKey()) // 用公钥验证签名
                .setJwsAlgorithmConstraints(
                        AlgorithmConstraints.ConstraintType.PERMIT,
                        AlgorithmIdentifiers.RSA_USING_SHA256
                )
                .build();
    }

    /// 生成登录用的 JWT
    public String generateLoginJwt(User user) throws JoseException
    {
        log.debug("即将为用户 {} 生成登录认证 JWT", user.getNickname());
        var now = NumericDate.now();
        // 设置令牌有效期为30天
        var expirationTime = NumericDate.fromMilliseconds(now.getValue() + Duration.ofDays(30).toMillis());

        var claims = new JwtClaims();
        claims.setSubject(JWT_FLAG_LOGIN); // JWT 主题
        claims.setIssuer(applicationName); // 发布人，这里使用项目名称
        claims.setAudience(user.getNickname()); // 用户名，这里使用昵称
        claims.setIssuedAt(now); // 令牌生成时间
        claims.setExpirationTime(expirationTime); // 令牌过期时间

        var userAuthorities = new HashSet<String>();
        user.getRoles().forEach(role -> {
            userAuthorities.add(role.getAuthority());
            role.getPermissions().forEach(permission -> userAuthorities.add(permission.getAuthority()));
        });
        claims.setStringListClaim("authorities", userAuthorities.stream().toList());
        claims.setJwtId(user.getId()); // 用户 ID 即是 JWT ID

        var jws = new JsonWebSignature();
        jws.setKey(loginJwk.getPrivateKey());
        jws.setKeyIdHeaderValue(loginJwk.getKeyId());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        jws.setPayload(claims.toJson());

        log.debug("生成登录认证 JWT：{}", jws.getCompactSerialization());
        return jws.getCompactSerialization();
    }

    public JwtClaims parseLoginJwt(String jwt)
    {
        log.debug("解析登录认证 JWT：{}", jwt);
        JwtClaims claims;
        try
        {
            claims = loginJwtConsumer.processToClaims(jwt);
            if (log.isDebugEnabled()) log.debug("登录认证 JWT 解析成功：\n{}", claims.toJson());
            return claims;
        }
        catch (InvalidJwtException e)
        {
            if (log.isDebugEnabled())
                log.debug("登录认证 JWT 解析失败：{}\n{}", e.getMessage(), e.getJwtContext().getJwtClaims().toJson());

            if (e.hasExpired())
            {
                log.debug("登录认证 JWT 过期");
                throw new JwtExpiredException(messageService.getMessage("sky.login.jwt-expired"));
            }

            throw new AuthenticationServiceException(messageService.getMessage("sky.login.failed"));
        }
    }
}
