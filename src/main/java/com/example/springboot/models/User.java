package com.example.springboot.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="user")
@Data
public class User {

    private String username;
    private String password;
    private String role;
}
