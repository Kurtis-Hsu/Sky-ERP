package com.vireosci.sky.common.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

/// JWT 过期异常
///
/// 由 JWT 过期引起的错误，而非用户篡改 JWT，需要特殊处理
public class JwtExpiredException extends AuthenticationServiceException
{
    public JwtExpiredException(String msg) { super(msg); }
}
