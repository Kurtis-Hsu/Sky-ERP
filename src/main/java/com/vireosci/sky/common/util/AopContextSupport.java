package com.vireosci.sky.common.util;

import org.springframework.aop.framework.AopContext;

/// AOP 上下文工具
///
/// 使用时实现该接口，将 [T] 类型指定为当前类，即可在类中直接使用 [#currentProxy()] 方法，使获取当前类的代理类的代码可以简洁一些
public interface AopContextSupport<T>
{
    /// 获取当前对象的 AOP 代理类
    @SuppressWarnings("unchecked")
    default T currentProxy()
    {
        return (T) AopContext.currentProxy();
    }
}
