package com.example.proj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ProjApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjApplication.class, args);
    }

}
