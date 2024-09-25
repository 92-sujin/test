package com.example.dkbmcsampleproject1.service;

import com.example.dkbmcsampleproject1.common.BasicResponse;
import com.example.dkbmcsampleproject1.common.JwtUtil;
import com.example.dkbmcsampleproject1.dto.UserDto;
import com.example.dkbmcsampleproject1.entity.UserEntity;
import com.example.dkbmcsampleproject1.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class LoginService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // 아이디와 비밀번호가 일치하는지 확인하는 메서드
    public LoginService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public Map<String, Object> isUser(String loginId, String userPassword) {
        // 로그인 ID로 유저 조회
        UserEntity user = userRepository.findByLoginId(loginId);

        // 유저가 존재하지 않거나, 비밀번호가 일치하지 않는 경우
        if (user == null || !userPassword.equals(user.getUserPassword())) {
            // 실패 시 메시지와 코드 반환
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", "404");
            errorResponse.put("message", "유저를 찾을 수 없거나 비밀번호가 일치하지 않습니다.");
            return errorResponse;
        }

        // 로그인 성공 시 JWT 토큰 생성
        Map<String, Object> claims = Map.of("name", user.getUserName(), "email", user.getUserEmail());
        String jwtToken = jwtUtil.generateToken(claims, user.getUserEmail());

        // 성공 시 응답 데이터 구성
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("code", "200");
        successResponse.put("message", "로그인 성공");
        successResponse.put("jwtToken", jwtToken); // JWT 토큰 포함
        successResponse.put("loginId", user.getLoginId());
        successResponse.put("userName", user.getUserName());
        successResponse.put("userEmail", user.getUserEmail());

        return successResponse;
    }

    private UserEntity saveUser(UserEntity userEntity, UserDto dto) {
        userEntity.setLoginId(dto.getLoginId());
        userEntity.setUserName(dto.getUserName());
        userEntity.setUserPassword(dto.getUserPassword());
        userEntity.setProvider("");
        userEntity.setProviderId("");
        userEntity.setUserEmail(dto.getUserEmail());
        return userEntity;
    }
}
