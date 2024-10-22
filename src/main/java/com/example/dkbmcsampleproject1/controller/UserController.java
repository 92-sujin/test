package com.example.dkbmcsampleproject1.controller;
import com.example.dkbmcsampleproject1.common.BasicResponse;
import com.example.dkbmcsampleproject1.dto.UserDto;
import com.example.dkbmcsampleproject1.service.LoginService;
import com.example.dkbmcsampleproject1.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    /**
     * saveUser : 유저 정보 저장
     *
     * @param userDto 유저 정보
     * @return BasicResponse<userDto> - 유저 저장 결과를 포함한 응답 (상태 코드와 사용자 정보 또는 오류 메시지)
     */
    @PostMapping("/user/saveUser")
    public ResponseEntity<BasicResponse> saveUser(@RequestBody List<UserDto> userDto) {
        BasicResponse response = userService.saveOrUpdateUser(userDto);
        return ResponseEntity.ok(response);
    }


}
