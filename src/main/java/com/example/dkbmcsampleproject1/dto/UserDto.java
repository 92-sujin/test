package com.example.dkbmcsampleproject1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {
    private String id;
    private String loginId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String phoneNumber;
    private LocalDateTime signUpDate;
}
