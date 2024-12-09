package com.example.hotel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/guests").setViewName("guests");
        registry.addViewController("/rooms").setViewName("rooms");
        registry.addViewController("/new_guest").setViewName("new_guest");
        registry.addViewController("/edit_guest").setViewName("edit_guest");
        registry.addViewController("/new_room").setViewName("new_room");
        registry.addViewController("/edit_room").setViewName("edit_room");
        registry.addViewController("/statistics").setViewName("statistics");
        registry.addViewController("/about_author").setViewName("about_author");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
    }
}
