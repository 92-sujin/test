package com.example.dkbmcsampleproject1.entity;


import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.TypeMap;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_table")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
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

}
