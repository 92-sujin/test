package com.example.dkbmcsampleproject1.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String loginId;
    private String userPassword;
    private String userName;
    private String userEmail;
    private String provider;
    private String providerId;
}
