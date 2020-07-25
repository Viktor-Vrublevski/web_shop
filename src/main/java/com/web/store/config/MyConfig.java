package com.web.store.config;

import com.web.store.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class MyConfig {
    @Bean
   public BCryptPasswordEncoder encoder() {
        return new  BCryptPasswordEncoder();
    }

    @Bean
    public User user(){
        return new User();
    }

}
