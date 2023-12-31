package com.example.usercenterbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.usercenterbackend.Mapper")
public class UsercenterbackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsercenterbackendApplication.class, args);
    }

}
