package com.example.dkbmcsampleproject1.dto;

import com.example.dkbmcsampleproject1.entity.LoginEntity;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoginDto {
    private String id;
    private String loginId;
    private String userPassword;
    private String userName;
    private String userEmail;
    private String provider;
    private String providerId;
    private String loginIp;
    private LocalDateTime firstLoginDate;
    private LocalDateTime lastLoginDate;

    // 매개변수가 있는 생성자 추가
    public LoginDto(String id, String loginId, String userPassword, String userName,
                    String userEmail, String provider, String providerId,
                    String loginIp, LocalDateTime firstLoginDate,
                    LocalDateTime lastLoginDate) {
        this.id = id;
        this.loginId = loginId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userEmail = userEmail;
        this.provider = provider;
        this.providerId = providerId;
        this.loginIp = loginIp;
        this.firstLoginDate = firstLoginDate;
        this.lastLoginDate = lastLoginDate;
    }
}
