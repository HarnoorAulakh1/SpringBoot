package com.example.springboot.externalApi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Api {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String registerUser() {
        String url = "https://secrets-api.appbrewery.com/get-auth-token";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        User user = User.builder()
                .username("harnoor3")
                .password("123456")
                .build();

        HttpEntity<User> req = new HttpEntity<>(user, headers);

        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, req, String.class);

        System.out.println(res);
        return res.getBody();
    }

    @GetMapping("/random")
    public Data1 getData(){
        String url="https://secrets-api.appbrewery.com/random";
        HttpHeaders headers = new HttpHeaders();
        User user = User.builder()
                .username("harnoor3")
                .password("123456")
                .build();

        HttpEntity<User> req = new HttpEntity<>(user, headers);

        ResponseEntity<Data1> res = restTemplate.exchange(url, HttpMethod.GET, req, Data1.class);
        System.out.println(res.getBody().getEmScore());
        return res.getBody();
    }
}

@Data
@Builder
class Data2 {
    private List<Data1> array;
}
@Data
@Builder
class Data1 {
    private String id;
    private String score;
    private String username;
    private String emScore;
}
@Data
@Builder
class User {

    private String username;
    private String password;
}


