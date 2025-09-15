package com.example.springboot.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("cache")
@Builder
@Data
public class AppCache {

    @Id
    private String id;
    private String key;
    private String value;
}