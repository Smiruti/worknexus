package com.example.worknexus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WorknexusApplication {

    public static void main(String[] args) {

        SpringApplication.run(WorknexusApplication.class, args);

    }

}
