package com.example.dkbmcsampleproject1.service;

import com.example.dkbmcsampleproject1.common.BasicResponse;

import com.example.dkbmcsampleproject1.dto.LoginDto;
import com.example.dkbmcsampleproject1.entity.LoginEntity;
import com.example.dkbmcsampleproject1.repository.LoginRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class MypageService {
    @Autowired
    private LoginRepository loginRepository;

    /**
     * getUserInfo : email로 조회하여 유저 정보를 가져옴
     *
     * @param email - email 문자열
     * @return BasicResponse(userDto)
     */
    public BasicResponse getUserInfo(String email) {
        // 유저가 이미 존재하는지 확인
        LoginEntity user = loginRepository.findByUserEmail(email);

        // LoginEntity를 DTO로 변환
        LoginDto userDto = Stream.of(user)  // Stream으로 변환
                .map(u -> new LoginDto(
                        u.getId(),
                        u.getLoginId(),
                        u.getUserPassword(),
                        u.getUserName(),
                        u.getUserEmail(),
                        u.getProvider(),
                        u.getProviderId(),
                        u.getLoginIp(),
                        u.getFirstLoginDate(),
                        u.getLastLoginDate()
                ))
                .findFirst()  // 첫 번째 요소를 찾음
                .orElse(null); // 사용자 존재하지 않을 경우 null
        // Stream을 사용해 DTO를 리스트로 변환
        List<LoginDto> responseList = Stream.of(userDto)
                .collect(Collectors.toList());

        // BasicResponse의 제네릭 타입을 LoginDto로 설정
        return BasicResponse.<LoginDto>builder()
                .response(responseList)  // response는 List<T>
                .totalCount(String.valueOf(responseList.size()))
                .code("200")
                .message("성공")
                .build();
    }

}
