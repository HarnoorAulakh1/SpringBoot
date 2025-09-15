package com.example.springboot.externalApi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
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
    public List<Data1> getData(){
        String url="https://secrets-api.appbrewery.com/all?page=1";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("harnoor3","123456");
        User user = User.builder()
                .username("harnoor3")
                .password("123456")
                .build();

        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<Data1[]> res = restTemplate.exchange(url, HttpMethod.GET, req, Data1[].class);
        if(res.getStatusCode()== HttpStatus.OK && res.getBody()!=null ) {
            System.out.println(res.getBody()[0].getEmScore());
            return Arrays.asList(res.getBody());
        }
        return new ArrayList<Data1>() ;
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


