package com.vireosci.sky.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vireosci.sky.repository.UserRepository;
import com.vireosci.sky.service.JwtService;
import com.vireosci.sky.service.MessageService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jose4j.jwt.MalformedClaimException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/// JWT 认证过滤器
///
/// 负责组装 [JwtAuthenticationToken]
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public static final String AUTHORIZATION_HEADER = "Authorization", JWT_PREFIX = "Bearer ";

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final MessageService messageService;

    public JwtAuthenticationFilter(
            UserRepository userRepository, JwtService jwtService,
            ObjectMapper objectMapper, MessageService messageService
    )
    {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
        this.messageService = messageService;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse rsp, FilterChain chain)
            throws ServletException, IOException
    {
        var jwtHeader = req.getHeader(AUTHORIZATION_HEADER);
        if (jwtHeader == null || !jwtHeader.startsWith(JWT_PREFIX))
        {
            log.debug("不携带 JWT，放行...");
            chain.doFilter(req, rsp);
            return;
        }

        /// 解析 JWT
        try
        {
            var claims = jwtService.parseLoginJwt(jwtHeader.substring(JWT_PREFIX.length()));
            var user = userRepository.findById(claims.getJwtId());

            if (user.isPresent())
            {
                var authorities = AuthorityUtils.createAuthorityList(claims.getStringListClaimValue("authorities"));
                var jwtAuthenticationToken = JwtAuthenticationToken.authenticated(user.get(), authorities);

                SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
                chain.doFilter(req, rsp);
                return;
            }
        }
        catch (MalformedClaimException e) { throw new RuntimeException(e); }

        /// 用户不存在
        rsp.setStatus(HttpStatus.NOT_FOUND.value());
        rsp.setContentType("application/problem+json");
        var problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                messageService.getMessage("sky.login.failed")
        );
        problemDetail.setTitle(messageService.getMessage("sky.login.failed.title"));
        objectMapper.writeValue(rsp.getOutputStream(), problemDetail);
    }
}
