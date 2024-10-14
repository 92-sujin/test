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
    private final LoginService loginService;

    @PostMapping("/user/saveUser")
    public ResponseEntity<BasicResponse> createUser(@RequestBody List<UserDto> userDto) {
        BasicResponse response = userService.saveOrUpdateUser(userDto);
        return ResponseEntity.ok(response);
    }


}
