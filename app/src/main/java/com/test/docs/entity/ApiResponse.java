package com.test.docs.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponse<T> {
    private int status;
    private String message;
    private String sessionId;
}