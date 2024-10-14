package com.example.dkbmcsampleproject1.service;

import com.example.dkbmcsampleproject1.common.JwtUtil;
import com.example.dkbmcsampleproject1.dto.oauth.GoogleUserInfo;
import com.example.dkbmcsampleproject1.dto.oauth.KakaoUserInfo;
import com.example.dkbmcsampleproject1.dto.oauth.NaverUserInfo;
import com.example.dkbmcsampleproject1.dto.oauth.OAuthUserInfo;
import com.example.dkbmcsampleproject1.entity.LoginEntity;
import com.example.dkbmcsampleproject1.entity.UserEntity;
import com.example.dkbmcsampleproject1.repository.LoginRepository;
import com.example.dkbmcsampleproject1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OAuthService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 기본적으로 제공되는 사용자 정보를 가져옴
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 공급자 정보 (구글, 네이버, 카카오 등)
        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuthUserInfo oAuthUserInfo = null;

        // 사용자 정보 가져오기 (구글은 "sub", 네이버는 "id", 카카오는 "id")
        if (provider.equals("google")) {
            oAuthUserInfo = new GoogleUserInfo((oAuth2User.getAttributes()));
        } else if (provider.equals("naver")) {
            oAuthUserInfo = new NaverUserInfo(oAuth2User.getAttributes());
        } else if (provider.equals("kakao")) {
            oAuthUserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }

       String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        // 로그인 ID는 이메일로 처리 (필요시 변경 가능)
        String providerId = oAuthUserInfo.getProviderId();
        String email = oAuthUserInfo.getEmail();
        String loginId = provider + "_" + providerId;
        String name = oAuthUserInfo.getName();

        // 유저가 이미 존재하는지 확인
        UserEntity user = userRepository.findByLoginId(loginId); // 이메일로 사용자 조회

        if (user == null) {
            // 유저가 없으면 회원가입 처리
            user = UserEntity.builder()
                    .loginId(loginId)
                    .userName(name)
                    .userEmail(email)
                    .signUpDate(LocalDateTime.now())
                    .build();
            userRepository.save(user);
        }
        
        // 유저 정보로 OAuth2User 반환 (권한 설정 등 추가 가능)
        return new DefaultOAuth2User(
                Collections.emptyList(),  // 권한 리스트를 빈 리스트로 설정
                oAuth2User.getAttributes(), // 사용자 정보 (Google, Naver, Kakao에서 받은 사용자 정보)
                userNameAttributeName
        );
    }


}


