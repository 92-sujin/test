package com.example.dkbmcsampleproject1.controller;


import com.example.dkbmcsampleproject1.common.BasicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.result.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("")
public class HomeController {

    @GetMapping("/home")
    public RedirectView redirectToHome() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();

            String name = "";
            String email = "";

            // OAuth2 사용자 정보 구분
            if (attributes.containsKey("sub")) {
                // 구글 사용자 정보
                name = (String) attributes.get("name");
                email = (String) attributes.get("email");
            } else if (attributes.containsKey("response")) {
                // 네이버 사용자 정보
                Map<String, Object> response = (Map<String, Object>) attributes.get("response");
                name = (String) response.get("name");
                email = (String) response.get("email");
            } else if (attributes.containsKey("kakao_account")) {
                // 카카오 사용자 정보
                Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                name = (String) profile.get("nickname");
                email = (String) kakaoAccount.get("email");
            }

            // 리디렉션 URL 생성
            String redirectUrl = String.format("http://localhost:8081/home?name=%s&email=%s",
                    encodeURIComponent(name), encodeURIComponent(email));

            return new RedirectView(redirectUrl);
        }

        // 인증되지 않은 경우
        return new RedirectView("http://localhost:8081/login");
    }

    private String encodeURIComponent(String value) {
        try {
            return java.net.URLEncoder.encode(value, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
