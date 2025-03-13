package com.vireosci.sky.common.util;

import jakarta.annotation.Nullable;

/// 字符串相关工具
public final class StringUtils
{
    /// 若字符串为 `null`，或长度等于 0，或所有字符都为 `whitespace` 字符，返回 `true`，否则返回 `false`
    ///
    /// @see Character#isWhitespace(char)
    public static boolean isBlank(@Nullable String str) { return str == null || str.isBlank(); }

    /// 若字符串不为 `null`，长度大于 0，且至少包含一个非 `whitespace` 字符，返回 `true`，否则返回 `false`
    ///
    /// @see Character#isWhitespace(char)
    public static boolean notBlank(@Nullable String str) { return str != null && !str.isBlank(); }
}
