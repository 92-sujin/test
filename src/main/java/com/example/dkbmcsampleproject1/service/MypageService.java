package com.example.dkbmcsampleproject1.service;

import com.example.dkbmcsampleproject1.common.BasicResponse;
import java.util.Collections;

import com.example.dkbmcsampleproject1.dto.UserDto;
import com.example.dkbmcsampleproject1.entity.UserEntity;
import com.example.dkbmcsampleproject1.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MypageService {
    @Autowired
    private UserRepository userRepository;

    public BasicResponse getUserInfo(String email) {
        // 유저가 이미 존재하는지 확인
        List<UserEntity> user = userRepository.findByUserEmail(email);

        return BasicResponse.<UserEntity>builder()
                .response(user)
                .totalCount(String.valueOf(user.size()))
                .code("200")
                .message("성공")
                .build();
    }
}
