package com.example.springboot;

import com.example.springboot.config.AppEnv;
import com.example.springboot.models.Mail;
import com.example.springboot.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@EnableScheduling
public class Application {

    @Autowired
    private AppEnv ob1;

    @Autowired
    private EmailService emailService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping
    public String Start() {
        System.out.println(ob1.get("api"));
        return "Hello World!!!";
    }

    @PostMapping("/send-mail")
    public ResponseEntity<String> sendMail(@RequestBody Mail data){
        String res="";
        try{
            emailService.sendMail(data.getTo(),data.getSubject(), data.getBody());
            res="Mail successfully sent";
        }
        catch (Exception e){
            System.out.println("Error Occurred "+e);
            res="Error Occurred "+e;
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
