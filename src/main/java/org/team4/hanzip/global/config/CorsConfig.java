package org.team4.hanzip.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedOrigin("http://localhost:3000");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addExposedHeader("Authorization");

		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}
}
