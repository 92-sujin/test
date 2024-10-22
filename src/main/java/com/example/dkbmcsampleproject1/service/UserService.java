package com.example.dkbmcsampleproject1.service;

import com.example.dkbmcsampleproject1.common.BasicResponse;
import com.example.dkbmcsampleproject1.dto.UserDto;
import com.example.dkbmcsampleproject1.entity.LoginEntity;
import com.example.dkbmcsampleproject1.entity.UserEntity;
import com.example.dkbmcsampleproject1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements JpaService<UserDto>{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    /**
     * findAll : 유저 전체 조회
     *
     * @return BasicResponse(userDto)
     */
    @Override
    public BasicResponse findAll() {

        List<UserEntity> userEntities = userRepository.findAll();

        List<UserDto> userDtos = userEntities.stream()
                .map(entity -> modelMapper.map(entity, UserDto.class))
                .collect(Collectors.toList());

        return BasicResponse.<UserDto>builder()
                .response(userDtos)
                .totalCount(String.valueOf(userDtos.size()))
                .code("200")
                .message("성공")
                .build();
    }

    /**
     * findByEmail : 이메일 주소로 유저 조회
     *
     * @param email : 이메일 주소
     * @return BasicResponse(userDto)
     */
    public BasicResponse findByEmail(String email) {
        // 요청한 이메일로 사용자 조회
        UserEntity userEntity = userRepository.findByUserEmail(email);

        // 사용자가 존재하지 않을 경우 처리
        if (userEntity == null) {
            return BasicResponse.<UserDto>builder()
                    .response(Collections.emptyList()) // 빈 리스트 반환
                    .totalCount("0") // 유저 정보가 존재하지 않으므로 0
                    .code("404") // 사용자 없음 코드
                    .message("사용자를 찾을 수 없습니다.")
                    .build();
        }

        // isUser 대로 로그인 정보 insert

        // UserEntity를 UserDto로 변환하고 리스트에 담기
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        List<UserDto> userDtoList = Collections.singletonList(userDto);

        return BasicResponse.<UserDto>builder()
                .response(userDtoList) // 변환된 사용자 정보를 리스트로 반환
                .totalCount(String.valueOf(userDtoList.size())) // 리스트 크기를 totalCount로 설정
                .code("200") // 성공 코드
                .message("성공")
                .build();
    }

    /**
     * saveOrUpdateUser : 유저 정보를 저장 하거나 수정
     *
     * @param userDtos : 유저 정보
     * @return BasicResponse(userDto)
     */
    public BasicResponse saveOrUpdateUser(List<UserDto> userDtos) {
        // 각 UserDto를 순회하며 저장/수정
        for (UserDto userDto : userDtos) {
            // loginId로 기존 사용자를 찾음
            UserEntity userEntity = userRepository.findByLoginId(userDto.getLoginId());

            if (userEntity != null) {
                // 존재하는 사용자가 있으면 업데이트
                update(userEntity.getLoginId(), userDto);
            } else {
                // 존재하지 않으면 새로 저장
                create(userDtos);
            }
        }

        // 성공적으로 처리된 결과 반환
        return BasicResponse.<UserDto>builder()
                .response(userDtos)
                .totalCount(String.valueOf(userDtos.size()))
                .code("200")
                .message("성공")
                .build();
    }

    /**
     * create : 유저 정보를 생성
     *
     * @param req : 유저 정보
     * @return BasicResponse(userDto)
     */
    @Override
    public BasicResponse create(List<UserDto> req) {

        // DTO를 Entity로 변환하고, 가입일을 설정
        List<UserEntity> userEntities = req.stream()
                .map(dto -> modelMapper.map(dto, UserEntity.class))
                .peek(userEntity -> userEntity.setSignUpDate(LocalDateTime.now()))
                .collect(Collectors.toList());

        userRepository.saveAll(userEntities);

        return BasicResponse.<UserDto>builder()
                .response(req)
                .totalCount(String.valueOf(req.size()))
                .code("200")
                .message("성공")
                .build();
    }

    /**
     * create : 로그인 아이디로 유저 조회 후 업데이트 
     *
     * @param loginId : 로그인 아이디
     * @param userDto : 유저 정보
     * @return BasicResponse(userDto)
     */
    @Override
    public BasicResponse update(String loginId, UserDto userDto) {

        UserEntity userEntity = userRepository.findByLoginId(loginId);
        if (userEntity != null) {
            // 기존 사용자 업데이트
            userEntity.setUserName(userDto.getUserName());
            userEntity.setUserEmail(userDto.getUserEmail());
            userEntity.setPhoneNumber(userDto.getPhoneNumber());
            userRepository.save(userEntity);  // 업데이트
            return BasicResponse.<UserDto>builder()
                    .response(Collections.singletonList(userDto))
                    .code("200")
                    .message("사용자 업데이트 성공")
                    .build();
        } else {
            return BasicResponse.<UserDto>builder()
                    .code("404")
                    .message("사용자를 찾을 수 없음")
                    .build();
        }
    }
}
