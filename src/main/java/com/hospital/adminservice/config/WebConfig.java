package com.hospital.adminservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

// admin-service/src/.../config/WebConfig.java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")          // everything under /api
                .allowedOrigins(                // ① list every front-end
                        "http://localhost:3000",  // doctor portal
                        "http://localhost:3001",  // auth portal  ←  add / keep
                        "http://localhost:3002",  // patient portal
                        "http://localhost:3003"   // admin portal
                )
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(false);       // keep false unless you send cookies
    }
}
