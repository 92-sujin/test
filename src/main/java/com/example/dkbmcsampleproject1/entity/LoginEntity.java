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
@Table(name = "login_info")
public class LoginEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "login_id", nullable = false)
    private String loginId;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "user_name", nullable = false)
    private String userName;
    @Column(name = "user_email", nullable = false)
    private String userEmail;
    @Column(name = "provider")
    private String provider;
    @Column(name = "provider_id")
    private String providerId;
    // 로그인한 IP
    @Column(name = "login_ip")
    private String loginIp;
    // 최초 로그인 날짜
    @Column(name = "first_login_date")
    private LocalDateTime firstLoginDate;
    // 최근 로그인 날짜
    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;
}
