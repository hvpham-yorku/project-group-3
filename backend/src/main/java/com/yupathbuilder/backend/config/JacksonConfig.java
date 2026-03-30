package com.yupathbuilder.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provides shared Jackson configuration for JSON serialization and
 * deserialization.
 *
 * <p>The application currently uses the default {@link ObjectMapper}
 * configuration, but defining it here centralizes future customization.</p>
 */
@Configuration
public class JacksonConfig {

    /**
     * Exposes the application's primary Jackson object mapper.
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
