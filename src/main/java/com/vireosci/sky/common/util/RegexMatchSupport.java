package com.vireosci.sky.common.util;

/// 正则表达式匹配工具
public final class RegexMatchSupport
{
    /// 手机号验证正则表达式
    public static final String PHONE_REGEX = "^1[3-9]\\d{9}$";

    /// 检测字符串是否为手机号
    public static boolean isPhone(String str) { return str.matches(PHONE_REGEX); }

    /// 邮箱验证正则表达式
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    /// 检测字符串是否为邮箱
    public static boolean isEmail(String str) { return str.matches(EMAIL_REGEX); }

    /// 用户昵称正则表达式
    public static final String SYS_USER_NICKNAME_REGEX = "^[\\p{L}_0-9]+$";

    /// 用户真实姓名正则表达式
    public static final String SYS_USER_NAME_REGEX = "^[\\p{L}]+$";

    /// 用户密码正则表达式
    public static final String SYS_USER_PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d_@$!%*?&]{8,20}$";
}
