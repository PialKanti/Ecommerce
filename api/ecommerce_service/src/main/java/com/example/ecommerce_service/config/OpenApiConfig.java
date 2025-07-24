package com.example.ecommerce_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.setInfo(apiInfo());

        return openAPI;
    }

    private Info apiInfo() {
        Info info = new Info();
        info.setTitle("E-commerce Service");
        info.setDescription("E-commerce Service using TDD approach");
        info.setContact(apiContactDetails());
        info.setVersion("1.0");
        info.setLicense(apiLicense());

        return info;
    }

    private Contact apiContactDetails() {
        Contact contact = new Contact();
        contact.setName("Pial Kanti Samadder");
        contact.setEmail("pialkanti2012@gmail.com");

        return contact;
    }

    private License apiLicense() {
        License license = new License();
        license.setName("MIT License");
        license.setUrl("https://opensource.org/license/mit/");

        return license;
    }
}
