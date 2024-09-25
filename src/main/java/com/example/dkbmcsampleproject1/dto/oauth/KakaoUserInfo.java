package com.example.dkbmcsampleproject1.dto.oauth;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class KakaoUserInfo implements OAuthUserInfo {
    private Map<String, Object> attributes;

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return (String) ((Map) attributes.get("kakao_account")).get("email");
    }

    @Override
    public String getName() {
        return(String) ((Map) attributes.get("properties")).get("nickname");
    }

    @Override
    public String getProfileImage() {
        return null;
    }
}
