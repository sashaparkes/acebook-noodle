package com.makersacademy.acebook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Existing handler for user profile images
        registry.addResourceHandler("/images/user_profile/**")
                .addResourceLocations("file:uploads/user_profile/");

        // New handler for post images
        registry.addResourceHandler("/uploads/post_images/**")
                .addResourceLocations("file:uploads/post_images/");
    }
}
