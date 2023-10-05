package com.test.docs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthToken {

    private String token;

    private String userName;
}
