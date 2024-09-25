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

    @GetMapping("/mypage/getUser")
    public ResponseEntity<BasicResponse> getUser(@RequestParam String email) {
        BasicResponse response = mypageService.getUserInfo(email);
        return ResponseEntity.ok(response);
    }

}
