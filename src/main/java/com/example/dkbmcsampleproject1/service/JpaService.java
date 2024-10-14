package com.example.dkbmcsampleproject1.service;

import com.example.dkbmcsampleproject1.common.BasicResponse;
import com.example.dkbmcsampleproject1.dto.UserDto;

import java.util.List;

public interface JpaService<T> {
    BasicResponse findAll();
    BasicResponse create(List<T> req);
    BasicResponse update(String pk, T dto);
}
