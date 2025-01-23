package com.zogg.zoggservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.zogg.zoggservice.repository")
@EnableMongoAuditing
@EnableJpaAuditing
public class ZoggServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZoggServiceApplication.class, args);
    }
}
