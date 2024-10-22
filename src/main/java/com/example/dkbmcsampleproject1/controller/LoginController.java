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


    /**
     * login : 로그인 요청 후 결과를 반환
     *
     * @param user 로그인 ID와 비밀번호를 포함한 정보
     * @param request  IP 주소를 추출하기 위해 사용
     * @return ResponseEntity<Map<String, Object>> - 로그인 결과를 포함한 응답 (상태 코드와 사용자 정보 또는 오류 메시지)
     */
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

    /**
     * snsLogin : SNS 로그인 요청 후 결과를 반환
     *
     * @param user 로그인 ID와 비밀번호를 포함한 정보
     * @param request  IP 주소를 추출하기 위해 사용
     * @return ResponseEntity<Map<String, Object>> - 로그인 결과를 포함한 응답 (상태 코드와 사용자 정보 또는 오류 메시지)
     */
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

    /**
     * snsLoginCancel : SNS 로그인 요청 후 결과를 반환
     *
     * @param user 로그인 ID와 비밀번호를 포함한 정보
     * @return BasicResponse<userDto> - 로그인 결과를 포함한 응답 (상태 코드와 사용자 정보 또는 오류 메시지)
     */
    @PostMapping("/login/sns/cancel")
    public ResponseEntity<BasicResponse> snsLoginCancel(@RequestBody LoginDto user) {

        // 로그인 서비스 호출 (SNS 사용자 확인 및 로그인 처리)
        BasicResponse response = loginService.snsLoginCancel(user.getLoginId());

        // 응답 반환
        return ResponseEntity.ok(response);
    }


}
