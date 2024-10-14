package com.example.dkbmcsampleproject1.controller;

import com.example.dkbmcsampleproject1.common.BasicResponse;
import com.example.dkbmcsampleproject1.common.IpAddressUtill;
import com.example.dkbmcsampleproject1.common.JwtUtil;
import com.example.dkbmcsampleproject1.dto.LoginDto;
import com.example.dkbmcsampleproject1.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {
    private final LoginService loginService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDto user, HttpServletRequest request) {
        // 클라이언트 IP 가져오기
        String clientIp = IpAddressUtill.getClientIp(request);
        System.out.println("Login request from IP: " + clientIp);
        // 로그인 서비스 호출
        Map<String, Object> response = loginService.isUser(user.getLoginId(), user.getUserPassword(), clientIp);

        // 응답 반환
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login/sns")
    public ResponseEntity<Map<String, Object>> snsLogin(@RequestBody LoginDto user, HttpServletRequest request) {
        // 클라이언트 IP 가져오기
        String clientIp = IpAddressUtill.getClientIp(request);
        System.out.println("Login request from IP: " + clientIp);

        // 로그인 서비스 호출 (SNS 사용자 확인 및 로그인 처리)
        Map<String, Object> response = loginService.isSnSUser(user.getUserEmail(), clientIp);

        // 응답 반환
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login/sns/cancel")
    public ResponseEntity<BasicResponse> snsLoginCancel(@RequestBody LoginDto user) {

        // 로그인 서비스 호출 (SNS 사용자 확인 및 로그인 처리)
        BasicResponse response = loginService.snsLoginCancel(user.getLoginId());

        // 응답 반환
        return ResponseEntity.ok(response);
    }


}
