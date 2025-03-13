package com.vireosci.sky.common.util;

import org.springframework.aop.framework.AopContext;

/// AOP 上下文工具
public interface AopContextSupport<T>
{
    /// 获取当前对象的 AOP 代理类
    @SuppressWarnings("unchecked")
    default T currentProxy()
    {
        return (T) AopContext.currentProxy();
    }
}
