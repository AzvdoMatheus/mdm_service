package com.example.mdm_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MdmServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MdmServiceApplication.class, args);
	}

}
