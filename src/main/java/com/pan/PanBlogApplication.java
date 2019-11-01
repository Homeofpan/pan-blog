package com.pan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.pan.dao")
@SpringBootApplication
public class PanBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(PanBlogApplication.class, args);
    }

}
