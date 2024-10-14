package com.example.dkbmcsampleproject1.repository;

import com.example.dkbmcsampleproject1.entity.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginRepository extends JpaRepository<LoginEntity,String> {
    LoginEntity findByLoginId(String login_id);
    LoginEntity findByUserEmail(String email);
}
