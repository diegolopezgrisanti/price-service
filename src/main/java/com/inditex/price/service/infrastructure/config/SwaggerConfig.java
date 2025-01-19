package com.inditex.price.service.infrastructure.config;

import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public Info apiInfo() {
        return new Info()
                .title("Pricing API")
                .version("1.0")
                .description("Documentation of the API to retrieve product prices");
    }
}