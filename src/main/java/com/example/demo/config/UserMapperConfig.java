package com.example.demo.config;

import com.example.demo.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserMapperConfig {

    @Bean
    public UserMapper userMapper(){
        return UserMapper.INSTANCE;
    }
}
