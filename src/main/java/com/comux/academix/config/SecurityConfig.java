package com.comux.academix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.comux.academix.security.AppUserDetailsService;

@EnableWebSecurity
@Configuration
public class SecurityConfig {


	@Bean
	public UserDetailsService customUserDetailsService() {
		return new AppUserDetailsService();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.csrf()
		.disable().authorizeHttpRequests(authorize -> authorize
	            .requestMatchers("/webjars/**","/css/**").permitAll()
	            .requestMatchers(HttpMethod.DELETE, "/usuarios/**").permitAll()
	            .anyRequest().authenticated())
	        .formLogin(
	            form -> form
	                .loginPage("/login")
	                .permitAll()
	                .defaultSuccessUrl("/")
	        )
	        .logout(
	            logout -> logout
	                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	                .permitAll()
	        );
	    return http.build();
	}
	

	@SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}


	/*
	
	@Bean
	public UserDetailsService userDetailsService() {
	    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	    manager.createUser(User.builder()
	        .username("user")
	        .password("password")
	        .roles("USER")
	        .build());
	    return manager;
	}
	*/
    
}