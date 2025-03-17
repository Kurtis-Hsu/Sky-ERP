package com.vireosci.sky.common.util;

public class AssertException extends RuntimeException
{
    /// 构造方法
    ///
    /// 默认异常信息为 `Assertion failed`
    public AssertException() { super("Assertion failed"); }

    /// 构造方法
    ///
    /// @param message 异常信息
    /// @param args    异常信息格式化参数
    public AssertException(String message, Object... args) { super(String.format(message, args)); }
}
