package com.finalProject.questionAndAnswer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileUploadConfig implements WebMvcConfigurer {
    /**
     * server-path get value from application.properties
     */
    @Value("${server-path}")
    private String serverPath;

    /**
     * Override method addResourceHandlers
     * @param registry object ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/upload/**")
                .addResourceLocations("file:"+serverPath,"classpath:/static/images/");

        // Serve images from a custom directory in your project
//        registry.addResourceHandler("/api/images/**")
//                .addResourceLocations("classpath:/static/images/");
    }
}
