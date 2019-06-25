package com.vince.springboot.app.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		http
		.authorizeRequests()
			.antMatchers("/css/**", "/js/**", "/img/**", "/scss/**", "/vendor/**").permitAll()
			.anyRequest().authenticated()
		.and()
		.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/authenticateUser")
			.permitAll()
			.successHandler(new AuthenticationSuccessHandler() {
				
				@Override
				public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
						Authentication authentication) throws IOException, ServletException {
					response.sendRedirect("/");
				}
			})
			.failureHandler(new AuthenticationFailureHandler() {
				
				@Override
				public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
						AuthenticationException exception) throws IOException, ServletException {
					exception.printStackTrace();
				}
			})
			.permitAll()
			.and()
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/login")
			.permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/access-denied");

	}
	
	
	
}
