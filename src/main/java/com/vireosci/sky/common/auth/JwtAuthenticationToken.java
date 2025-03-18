package com.vireosci.sky.common.auth;

import com.vireosci.sky.domain.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static com.vireosci.sky.common.util.Utils.asserts;

public class JwtAuthenticationToken extends AbstractAuthenticationToken
{
    /// 认证之前存 Json Web Token
    ///
    /// 认证之后存用户 [ID][com.vireosci.sky.domain.User#getId()]
    private final User principal;

    /// 构造函数
    ///
    /// 传入一个 userId 和一个角色和权限集合，认证状态为 `true`，表示认证成功
    ///
    /// @param user      用户，存入 [#principal]
    /// @param authorities 用户角色和权限集合
    private JwtAuthenticationToken(User user, Collection<? extends GrantedAuthority> authorities)
    {
        super(authorities);
        principal = user;
        super.setAuthenticated(true);
    }

    public static JwtAuthenticationToken authenticated(User user, Collection<? extends GrantedAuthority> authorities)
    {
        return new JwtAuthenticationToken(user, authorities);
    }

    @Override public User getPrincipal() { return principal; }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException
    {
        asserts(
                !isAuthenticated,
                "无法将此令牌设置为已认证 - 需要使用带 authenticated(String, Collection<GrantedAuthority>) 静态工厂方法"
        );
        super.setAuthenticated(false);
    }

    /// JWT 认证不需要凭证
    @Override public Object getCredentials() { return null; }
}
