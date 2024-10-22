package com.example.dkbmcsampleproject1.common;

import jakarta.servlet.http.HttpServletRequest;

public class IpAddressUtill {
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            // X-Forwarded-For 헤더에서 첫 번째 IP를 추출
            return ip.split(",")[0].trim();
        }
        ip = request.getHeader("Proxy-Client-IP");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            // 로컬 환경 일 경우 0:0:0:0:0:0:0:1이 정상 , 실제 IP를 확인하려면 배포된 환경에서 테스트
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
