package com.vireosci.sky.common.auth;

import com.vireosci.sky.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/// JWT 认证过滤器
///
/// 负责组装 [JwtAuthenticationToken]
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public static final String AUTHORIZATION_HEADER = "Authorization", JWT_PREFIX = "Bearer ";

    private final UserService userService;

    public JwtAuthenticationFilter(UserService userService) { this.userService = userService; }

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        var jwtHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (jwtHeader == null || !jwtHeader.startsWith(JWT_PREFIX))
        {
            log.debug("不携带 JWT，放行...");
            filterChain.doFilter(request, response);
            return;
        }

        var jwt = jwtHeader.substring(JWT_PREFIX.length());
    }
}
