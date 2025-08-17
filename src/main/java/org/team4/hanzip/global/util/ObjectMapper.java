package org.team4.hanzip.global.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapper {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}