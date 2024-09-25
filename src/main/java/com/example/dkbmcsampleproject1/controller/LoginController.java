package com.example.dkbmcsampleproject1.controller;

import com.example.dkbmcsampleproject1.common.BasicResponse;
import com.example.dkbmcsampleproject1.common.JwtUtil;
import com.example.dkbmcsampleproject1.dto.UserDto;
import com.example.dkbmcsampleproject1.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {
    private final LoginService loginService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDto user) {
        // 로그인 서비스 호출
        Map<String, Object> response = loginService.isUser(user.getLoginId(), user.getUserPassword());

        // 응답 반환
        return ResponseEntity.ok(response);
    }

}
