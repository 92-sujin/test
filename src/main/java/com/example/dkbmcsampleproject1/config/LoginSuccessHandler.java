package com.example.dkbmcsampleproject1.config;

import com.example.dkbmcsampleproject1.common.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    public LoginSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String name = "";
        String email = "";

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // OAuth2 사용자 정보 구분
        if (attributes.containsKey("sub")) {
            // 구글 사용자 정보
            name = (String) attributes.get("name");
            email = (String) attributes.get("email");
        } else if (attributes.containsKey("response")) {
            // 네이버 사용자 정보
            Map<String, Object> responseMap = (Map<String, Object>) attributes.get("response");
            name = (String) responseMap.get("name");
            email = (String) responseMap.get("email");
        } else if (attributes.containsKey("kakao_account")) {
            // 카카오 사용자 정보
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            name = (String) profile.get("nickname");
            email = (String) kakaoAccount.get("email");
        }

        // JWT 생성
        Map<String, Object> claims = Map.of("name", name, "email", email);
        String jwt = jwtUtil.generateToken(claims, email);

        // 프론트엔드 URL로 리디렉션
        String redirectUrl = String.format("http://localhost:8081/login?token=%s", jwt);
        System.out.println("Redirecting to: " + redirectUrl);

        // 리디렉션 처리
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

}
