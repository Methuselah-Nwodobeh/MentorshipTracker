package com.guidetrack.mentorship_tracker.config.cors;

import com.guidetrack.mentorship_tracker.config.ConfigConstants;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    ConfigConstants configConstants = new ConfigConstants();
    Dotenv dotenv = Dotenv.load();
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins((Boolean.TRUE.equals(configConstants.debug) ? "*" : dotenv.get("ALLOWED_ORIGINS")))
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
