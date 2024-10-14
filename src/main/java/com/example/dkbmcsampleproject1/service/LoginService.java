package com.example.dkbmcsampleproject1.service;

import com.example.dkbmcsampleproject1.common.BasicResponse;
import com.example.dkbmcsampleproject1.common.JwtUtil;
import com.example.dkbmcsampleproject1.dto.LoginDto;
import com.example.dkbmcsampleproject1.dto.UserDto;
import com.example.dkbmcsampleproject1.entity.LoginEntity;
import com.example.dkbmcsampleproject1.entity.UserEntity;
import com.example.dkbmcsampleproject1.repository.LoginRepository;
import com.example.dkbmcsampleproject1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {
    private final LoginRepository loginRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;

    public Map<String, Object> isUser(String loginId, String userPassword, String userIp) {

        // 로그인 ID로 유저 조회
        LoginEntity loginUser = loginRepository.findByLoginId(loginId);

        // 로그인 유저가 존재하지 않는 경우
        if (loginUser == null) {
            // 회원 정보가 있는 지 확인
            UserEntity user = userRepository.findByLoginId(loginId);
            if (user != null) {
                // 비밀번호가 일치하지 않는 경우
                if (!userPassword.equals(user.getUserPassword())) {
                    Map<String, Object> errorResponse = new HashMap<>();
                    errorResponse.put("code", "401");
                    errorResponse.put("message", "비밀번호가 일치하지 않습니다.");
                    return errorResponse;
                }

                // 비밀번호가 일치하는 경우 로그인 정보에 담기
                loginUser = LoginEntity.builder() // 빌더 객체 생성
                        .loginId(loginId)             // loginId 설정
                        .userName(user.getUserName())  // UserEntity에서 가져온 userName 설정
                        .userEmail(user.getUserEmail()) // UserEntity에서 가져온 userEmail 설정
                        .userPassword(userPassword)
                        .provider("")                  // 필요에 따라 설정
                        .providerId("")                // 필요에 따라 설정
                        .loginIp(userIp)               // IP 주소 설정 (필요한 경우)
                        .firstLoginDate(LocalDateTime.now())
                        .lastLoginDate(LocalDateTime.now()) // 현재 시간 설정
                        .build();

                // 로그인 유저 정보 저장
                loginRepository.save(loginUser);

            } else {
                // 회원 정보가 없을 경우
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("code", "404");
                errorResponse.put("message", "유저를 찾을 수 없습니다.");
                return errorResponse;
            }
        } else {
            // 로그인 유저가 있지만 비밀번호가 다른경우
            if (!userPassword.equals(loginUser.getUserPassword())) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("code", "401");
                errorResponse.put("message", "비밀번호가 일치하지 않습니다.");
                return errorResponse;
            }
        }

        // 로그인 ip, 최근 로그인 한 날짜 수정
        loginUser.setLoginIp(userIp);
        loginUser.setLastLoginDate(LocalDateTime.now()); // 현재 시간으로 설정

        loginRepository.save(loginUser);

        // 로그인 성공 시 JWT 토큰 생성
        Map<String, Object> claims = Map.of("name", loginUser.getUserName(), "email", loginUser.getUserEmail());
        String jwtToken = jwtUtil.generateToken(claims, loginUser.getUserEmail());

        // 성공 시 응답 데이터 구성
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("code", "200");
        successResponse.put("message", "로그인 성공");
        successResponse.put("jwtToken", jwtToken); // JWT 토큰 포함
        successResponse.put("loginId", loginUser.getLoginId());
        successResponse.put("userName", loginUser.getUserName());
        successResponse.put("userEmail", loginUser.getUserEmail());

        return successResponse;
    }

    public Map<String, Object> isSnSUser(String email, String userIp) {
        Map<String, Object> successResponse = new HashMap<>();

        // 1. 이메일로 로그인 유저 조회
        LoginEntity loginUser = loginRepository.findByUserEmail(email);

        // 2. 로그인 유저가 없으면, 유저 테이블에서 조회하여 로그인 테이블에 추가
        if (loginUser == null) {
            // 이메일로 유저 정보를 유저 테이블에서 조회
            UserEntity user = userRepository.findByUserEmail(email);

            String[] loginIdParts = user.getLoginId().split("_", 2);
            String provider = loginIdParts[0]; // 앞부분을 provider로 설정
            String providerId = loginIdParts.length > 1 ? loginIdParts[1] : ""; // 뒷부분을 providerId로 설정 (없을 경우 빈 문자열)

            if (user != null) {
                // 회원 정보가 있으면 로그인 유저에 데이터 추가
                loginUser = LoginEntity.builder()
                        .loginId(user.getLoginId())
                        .userName(user.getUserName())
                        .userEmail(user.getUserEmail())
                        .provider(provider)   // SNS 제공자 정보 필요시 설정 (ex. Google, Facebook)
                        .providerId(providerId) // SNS 제공자의 ID 필요시 설정
                        .loginIp(userIp) // 로그인한 IP
                        .lastLoginDate(LocalDateTime.now()) // 로그인 시간
                        .build();

                // 로그인 테이블에 새 사용자 기록 저장
                loginRepository.save(loginUser);
            } else {
                // 유저 테이블에도 없으면 오류 응답 반환
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("code", "404");
                errorResponse.put("message", "유저를 찾을 수 없습니다.");
                return errorResponse;
            }
        }

        // 3. 로그인 기록이 있으면 IP와 최근 로그인 시간을 업데이트
        loginUser.setLoginIp(userIp);
        loginUser.setLastLoginDate(LocalDateTime.now());
        loginRepository.save(loginUser); // 변경사항 저장

        // 핸드폰 여부
        Boolean isPhoneNumber = userRepository.existsByLoginIdInAndPhoneNumberIsNotNull(Collections.singletonList(loginUser.getLoginId()));

        // 4. 성공 응답 반환
        successResponse.put("code", "200");
        successResponse.put("message", "로그인 성공");
        successResponse.put("userInfo", loginUser);
        successResponse.put("userEmail", loginUser.getUserEmail());
        successResponse.put("isPhoneNumber", isPhoneNumber);
        successResponse.put("lastLoginDate", loginUser.getLastLoginDate().toString());
        return successResponse;
    }

    public BasicResponse<LoginDto> snsLoginCancel(String loginId) {

        BasicResponse<LoginDto> response;

        try {
            // 삭제할 사용자 정보를 미리 조회

            LoginEntity loginEntity = loginRepository.findByLoginId(loginId);

            // UserEntity를 UserDto로 변환하고 리스트에 담기
            LoginDto loginDto = modelMapper.map(loginEntity, LoginDto.class);
            List<LoginDto> loginDtoList = Collections.singletonList(loginDto);

            // 삭제 시도
            loginRepository.delete(loginEntity);

            // 성공적으로 삭제되었을 때의 응답
            response = BasicResponse.<LoginDto>builder()
                    .code("200")  // 성공 코드
                    .message("Login ID " + loginId + " has been successfully deleted.")
                    .response(loginDtoList) // 삭제된 LoginDto 객체를 리스트에 추가
                    .build();
        } catch (EmptyResultDataAccessException e) {
            // 존재하지 않는 ID를 삭제하려 했을 때의 처리
            response = BasicResponse.<LoginDto>builder()
                    .code("404")  // 실패 코드 (Not Found)
                    .message("Login ID " + loginId + " not found.")
                    .response(Collections.emptyList()) // 빈 리스트 반환
                    .build();
        } catch (Exception e) {
            // 그 외 다른 예외 처리
            response = BasicResponse.<LoginDto>builder()
                    .code("500")  // 실패 코드 (Internal Server Error)
                    .message("An error occurred during deletion: " + e.getMessage())
                    .response(Collections.emptyList()) // 빈 리스트 반환
                    .build();
        }

        return response; // 결과 반환
    }
}
