package com.luv2code.springboot.thymeleafdemo.config;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public BasicDataSource dataSource() throws URISyntaxException {
        URI dbUri;
        
        try {
        	 dbUri = new URI(System.getenv("DATABASE_URL"));
		} catch (URISyntaxException e) {
			logger.error("Invalid DATABASE_URL");
			return null;
		}

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.addConnectionProperty(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

        return basicDataSource;
    }
}
