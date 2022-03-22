package com.example.tesodevchallenge;

import com.example.tesodevchallenge.model.entity.Customer;
import com.example.tesodevchallenge.service.CustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TesodevChallengeApplication {



    public static void main(String[] args) {
        SpringApplication.run(TesodevChallengeApplication.class, args);
    }



}
