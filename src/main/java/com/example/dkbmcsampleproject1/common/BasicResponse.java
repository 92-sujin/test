package com.example.dkbmcsampleproject1.common;

import lombok.*;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Data
@Builder
public class BasicResponse<T> {
    List<T> response;
    private String code;
    private String message;
    private String totalCount;
}

