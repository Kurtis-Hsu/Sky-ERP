package com.vireosci.sky.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Locale;

/// I18n 消息服务
///
/// 该服务类是对 [MessageSource] 的扩展，提供更简单的方法，`args` 使用可变参数，默认从 [HttpServletRequest] 中提取 [Locale]
@Service
@SuppressWarnings("NullableProblems")
public class MessageService implements MessageSource
{
    private final MessageSource messageSource;
    private final HttpServletRequest request;

    public MessageService(@Qualifier("messageSource") MessageSource messageSource, HttpServletRequest request)
    {
        this.messageSource = messageSource;
        this.request = request;
    }

    public String getMessage(String code, Object... args) { return getMessage(code, args, request.getLocale()); }

    @Override public String getMessage(String code, Object[] args, String defaultMessage, Locale locale)
    {
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

    @Override public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException
    {
        return messageSource.getMessage(code, args, locale);
    }

    @Override public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException
    {
        return messageSource.getMessage(resolvable, locale);
    }
}
