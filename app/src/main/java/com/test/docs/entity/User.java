package com.test.docs.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "user")
@Data
public class User {

    @Id
    private String userId;

    private String userName;

    private String password;

    private String email;
}
