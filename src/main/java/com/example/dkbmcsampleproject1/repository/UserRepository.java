package com.example.dkbmcsampleproject1.repository;

import com.example.dkbmcsampleproject1.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity,String> {
    UserEntity findByLoginId(String login_id);
    List<UserEntity> findByUserEmail(String email);
}
