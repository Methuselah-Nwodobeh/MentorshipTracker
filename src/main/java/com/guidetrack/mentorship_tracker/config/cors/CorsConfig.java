package com.guidetrack.mentorship_tracker.config.cors;

import com.guidetrack.mentorship_tracker.config.ConfigConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    ConfigConstants configConstants = new ConfigConstants();
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins((configConstants.debug ? "*" : "${cors.allowed-origin"))
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
