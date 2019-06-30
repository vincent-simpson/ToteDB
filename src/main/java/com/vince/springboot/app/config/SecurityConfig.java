package com.vince.springboot.app.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/*
This is the configuration file for Spring Security.
 */
@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties") //The application.properties file that holds values to be used
													//for configuration.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final Environment env;

	public SecurityConfig(Environment env) {
		this.env = env;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(securityDataSource());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		http
		.authorizeRequests()
				//antMatchers for resource files.
			.antMatchers("/css/**", "/js/**", "/img/**", "/scss/**", "/vendor/**").permitAll()
			.anyRequest().authenticated()
		.and()
		.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/authenticateUser")
			.permitAll()
			.successHandler(new AuthenticationSuccessHandler() {
				// if authentication succeeds, send request to controller for main dashboard
				@Override
				public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
						Authentication authentication) throws IOException, ServletException {
					response.sendRedirect("/"); // "/" is the request that maps to the main dashboard.
				}
			})
			.failureHandler(new AuthenticationFailureHandler() {
				//if authentication fails, print the exception stack trace.
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

	/**
	 * Datasource that defines the properties of the database source.
	 * @return a {@link DataSource} object containing the database connection information.
	 */
	@Bean
	public DataSource securityDataSource() {
		
		BasicDataSource dataSource = new BasicDataSource();
		
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		
		return dataSource;
	}
	
	
	
}
