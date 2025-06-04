package com.example.mdm_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.mdm_service.client")
public class MdmServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MdmServiceApplication.class, args);
    }
}
