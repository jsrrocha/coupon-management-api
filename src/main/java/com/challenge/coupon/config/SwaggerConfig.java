package com.challenge.coupon.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI couponApiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Coupon Management API")
                        .description("API para desafio de gerenciamento de cupons com Domínio Rico.")
                        .version("v1.0.0"));
    }
}