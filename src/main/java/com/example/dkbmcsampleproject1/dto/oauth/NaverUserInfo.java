package com.example.dkbmcsampleproject1.dto.oauth;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class NaverUserInfo implements OAuthUserInfo{
    private Map<String, Object> attributes;

    @Override
    public String getProviderId() {
        return (String) ((Map) attributes.get("response")).get("id");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        return (String) ((Map) attributes.get("response")).get("email");
    }

    @Override
    public String getName() {
        return (String) ((Map) attributes.get("response")).get("name");
    }

    @Override
    public String getProfileImage() {
        return (String) ((Map) attributes.get("response")).get("profile_image");
    }
}
