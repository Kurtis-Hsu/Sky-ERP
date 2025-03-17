package com.vireosci.sky.common.util;

/// 正则表达式匹配工具
public final class RegexMatchSupport
{
    // @formatter:off
    public static final String
            /// 手机号验证正则表达式
            PHONE_REGEX = "^1[3-9]\\d{9}$",
            /// 邮箱验证正则表达式
            EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",
            /// 用户真实姓名正则表达式
            SYS_USER_NAME_REGEX = "^[\\p{L}]+$",
            /// 用户昵称正则表达式
            SYS_USER_NICKNAME_REGEX = "^[\\p{L}_0-9]+$",
            /// 用户密码正则表达式
            SYS_USER_PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d_@$!%*?&]{8,20}$";
    // @formatter:on

    /// 检测字符串是否为手机号
    public static boolean isPhone(String str) { return str.matches(PHONE_REGEX); }

    /// 检测字符串是否为邮箱
    public static boolean isEmail(String str) { return str.matches(EMAIL_REGEX); }
}
