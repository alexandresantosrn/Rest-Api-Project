package com.restapi.userdpt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf.disable()) // Desabilitando csrf (problemas em requisições post).
				.authorizeHttpRequests(authorizeConfig -> {
			authorizeConfig.requestMatchers("/users-list").permitAll();	
			authorizeConfig.anyRequest().authenticated();
		})
		//.formLogin(Customizer.withDefaults()) // Apresentando form de login do Spring Security.
		.oauth2Login(Customizer.withDefaults())	// Apresentando formulário de login do oauth / Google.
		.oauth2ResourceServer(config -> config.jwt(Customizer.withDefaults())) // Autenticando via Jwt.
		.build();
	}
}
