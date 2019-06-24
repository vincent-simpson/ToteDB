package com.vince.springboot.app.config;

import org.hibernate.cfg.ImprovedNamingStrategy;

public class CustomNamingStrategy extends ImprovedNamingStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String columnName(String columnName) {
		
		
		
		return columnName;
	}
	
	

}
