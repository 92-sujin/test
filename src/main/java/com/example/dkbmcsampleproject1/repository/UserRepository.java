package com.example.dkbmcsampleproject1.repository;


import com.example.dkbmcsampleproject1.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByLoginId(String loginId);
    UserEntity findByUserEmail(String email);
    Boolean existsByLoginIdInAndPhoneNumberIsNotNull(List<String> loginId);
}
