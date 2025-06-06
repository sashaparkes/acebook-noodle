package com.makersacademy.acebook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// This class customises how the app serves static resources (e.g. images) from the file system
// Resource handlers map URL paths to filesystem directories so static files (e.g. images) can be served via HTTP
// This is required for image storage outside the classpath (src) on uploading

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Handler for profile images
        registry.addResourceHandler("/images/user_profile/**")
                .addResourceLocations("file:uploads/user_profile/");

        // Handler for post images
        registry.addResourceHandler("/uploads/post_images/**")
                .addResourceLocations("file:uploads/post_images/");
    }
}
