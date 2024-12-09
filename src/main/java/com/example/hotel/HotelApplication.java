package com.example.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class HotelApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(HotelApplication.class, args);
    }
}
