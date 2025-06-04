package com.example.mdm_service;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "MDM Service API", version = "v1"))
@Configuration
public class OpenApiConfig {

    
    @Bean
    public GroupedOpenApi mdmApi() {
        return GroupedOpenApi.builder()
                .group("mdm-service")                             
                .packagesToScan("com.example.mdm_service.controller") 
                .build();
    }
}
