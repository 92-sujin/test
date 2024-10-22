package com.example.dkbmcsampleproject1.controller;

import com.example.dkbmcsampleproject1.common.BasicResponse;
import com.example.dkbmcsampleproject1.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MypageController {

    private final MypageService mypageService;

    /**
     * getUser : 유저 정보 결과를 반환
     *
     * @param email 이메일 주소
     * @return BasicResponse<userDto> - 유저 정보 결과를 포함한 응답 (상태 코드와 사용자 정보 또는 오류 메시지)
     */
    @GetMapping("/mypage/getUser")
    public ResponseEntity<BasicResponse> getUser(@RequestParam String email) {
        BasicResponse response = mypageService.getUserInfo(email);
        return ResponseEntity.ok(response);
    }

}
