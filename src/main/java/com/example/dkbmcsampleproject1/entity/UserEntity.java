package com.example.dkbmcsampleproject1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_info") // 회원 정보를 저장하는 테이블
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private String id; // 기본 회원 ID

    @Column(name = "login_id",nullable = false, unique = true)
    private String loginId; // 로그인 ID

    @Column(name = "user_name",nullable = false)
    private String userName; // 회원 이름

    @Column(name = "user_email",nullable = false, unique = true)
    private String userEmail; // 회원 이메일

    @Column(name = "user_password")
    private String userPassword; // 비밀번호

    @Column(name = "phone_number")
    private String phoneNumber; // 핸드폰 번호

    @Column(name = "sign_up_date")
    private LocalDateTime signUpDate; // 가입일

}
