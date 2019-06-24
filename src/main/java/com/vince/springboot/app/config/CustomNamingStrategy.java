package com.vince.springboot.app.config;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomNamingStrategy extends ImprovedNamingStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String columnName(String columnName) {
		
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		logger.warn("COLUMN NAME: " + columnName);
		
		
		
		return columnName;
	}
	
	

}
