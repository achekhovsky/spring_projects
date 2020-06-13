package com.custom.spring.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.Controller;

import com.custom.spring.mvc.controller.ImplController;

@Configuration
public class SimpleUrlHandlerMappingConfig {
	
    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
        SimpleUrlHandlerMapping simpleUrlHandlerMapping
          = new SimpleUrlHandlerMapping();

        Map<String, Object> urlMap = new HashMap<>();
        urlMap.put("/simpleUrlHandler", provide());
        simpleUrlHandlerMapping.setUrlMap(urlMap);

        return simpleUrlHandlerMapping;
    }

    @Bean
    public Controller provide() {
        return new ImplController();
    }
}