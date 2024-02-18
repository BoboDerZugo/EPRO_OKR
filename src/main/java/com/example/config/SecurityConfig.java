package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This class represents the security configuration for the application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig{

    /**
     * This method creates a security filter chain bean.
     * @param http
     * @return The security filter chain.
     * @throws Exception
     */
    @SuppressWarnings("removal")
    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers(HttpMethod.GET, "/company/**").hasAnyRole("CO_ADMIN", "BU_ADMIN", "NORMAL")
                .requestMatchers(HttpMethod.POST, "/company/**").hasAnyRole("CO_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/company/**").hasAnyRole("CO_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/company/**").hasAnyRole("CO_ADMIN")
                .requestMatchers(HttpMethod.POST, "/company/**/businessunit/**").hasAnyRole("CO_ADMIN", "BU_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/company/**/businessunit/**").hasAnyRole("CO_ADMIN", "BU_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/company/**/businessunit/**").hasAnyRole("CO_ADMIN", "BU_ADMIN")
                .requestMatchers("/unit/**").hasAnyRole("CO_ADMIN")
                .requestMatchers("/user/**").hasAnyRole("CO_ADMIN")
                .anyRequest().hasAnyRole("CO_ADMIN")
			)
			.httpBasic(Customizer.withDefaults());

		return http.build();
	}

    /**
     * This method creates an authentication manager bean.
     * @param userDetailsService
     * @param passwordEncoder
     * @return The authentication manager.
     */
    @Bean
	public AuthenticationManager authenticationManager(
			UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authenticationProvider);
	}

    /**
     * This method creates a password encoder bean.
     * @return The password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return charSequence.toString().equals(s);
            }
        };
    }



}