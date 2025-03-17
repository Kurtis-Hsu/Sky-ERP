package com.vireosci.sky.common.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken
{
    /// 认证之前存 Json Web Token
    ///
    /// 认证之后存用户 [ID][com.vireosci.sky.domain.User#getId()]
    private final String principal;

    /// 构造函数
    ///
    /// 传入一个 JWT，会被存入 [#principal]，认证状态为 `false`，且不可更改
    public JwtAuthenticationToken(String jwt)
    {
        super(null);
        principal = jwt;
    }

    /// 构造函数
    ///
    /// 传入一个 userId 和一个角色和权限集合，认证状态为 `true`，表示认证成功
    ///
    /// @param userId      用户 ID，存入 [#principal]
    /// @param authorities 用户角色和权限集合
    public JwtAuthenticationToken(String userId, Collection<? extends GrantedAuthority> authorities)
    {
        super(authorities);
        principal = userId;
        super.setAuthenticated(true);
    }

    public static JwtAuthenticationToken unauthenticated(String jwt) { return new JwtAuthenticationToken(jwt); }

    public static JwtAuthenticationToken authenticated(String jwt, Collection<? extends GrantedAuthority> authorities)
    {
        return new JwtAuthenticationToken(jwt, authorities);
    }

    @Override public String getPrincipal() { return principal; }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException
    {
        Assert.isTrue(!isAuthenticated, "无法将此令牌设置为已认证 - 需要使用带 GrantedAuthority 列表的构造函数");
        super.setAuthenticated(false);
    }

    /// JWT 认证不需要凭证
    @Override public Object getCredentials() { return null; }
}
