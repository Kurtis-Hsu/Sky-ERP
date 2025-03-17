package com.vireosci.sky.common.util;

import jakarta.annotation.Nullable;

import java.util.function.Supplier;

/// 通用工具
public final class Utils
{
    /// 提供一个原参数和一个默认参数，原参数不为 `null` 时返回原参数，否则返回默认参数
    ///
    /// @param <T>          参数类型
    /// @param origin       原参数
    /// @param defaultValue 默认参数
    public static <T> T defaultIfNull(@Nullable T origin, T defaultValue)
    {
        return origin == null ? defaultValue : origin;
    }

    /// 提供一个原参数和一个默认参数获取方法，原参数不为 `null` 时返回原参数，否则返回执行默认参数获取方法并返回结果
    ///
    /// @param <T>          参数类型
    /// @param origin       原参数
    /// @param defaultValue 默认参数获取方法
    public static <T> T defaultIfNull(@Nullable T origin, Supplier<T> defaultValue)
    {
        return origin == null ? defaultValue.get() : origin;
    }

    /// 断言 `condition` 为真，否则抛出异常 [AssertException]
    public static void asserts(boolean condition) { if (!condition) throw new AssertException(); }

    /// 断言 `condition` 为真，否则抛出异常 [AssertException]
    public static void asserts(boolean condition, String message, Object... args)
    {
        if (!condition) throw new AssertException(message, args);
    }
}
