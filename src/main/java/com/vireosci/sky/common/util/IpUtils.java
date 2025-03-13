package com.vireosci.sky.common.util;

import jakarta.servlet.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;

/// IP 相关工具
public final class IpUtils
{
    /// 获取本地 IP，获取不到时默认返回 127.0.0.1
    public static String localIp()
    {
        try
        {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException ignored)
        {
            return "127.0.0.1";
        }
    }

    private static final String[] ipHeaders = {
            "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "X-Real-IP"
    };

    /// 获取 [HttpServletRequest] 中包含的远程请求的 IP
    public static String remoteIp(HttpServletRequest req)
    {
        String ip = req.getHeader("X-Forwarded-For");
        if (isValidIp(ip))
        {
            if (ip.contains(",")) return ip.split(",")[0];
            else return ip;
        }

        for (var header : ipHeaders)
        {
            ip = req.getHeader(header);
            if (isValidIp(ip)) return ip;
        }

        ip = req.getRemoteAddr();
        if (isValidIp(ip)) return ip;

        return "127.0.0.1";
    }

    private static boolean isValidIp(String ip) { return StringUtils.notBlank(ip) && !"unknown".equalsIgnoreCase(ip); }
}
