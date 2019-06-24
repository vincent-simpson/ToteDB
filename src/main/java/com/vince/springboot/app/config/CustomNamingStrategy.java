package com.vince.springboot.app.config;

import org.hibernate.cfg.ImprovedNamingStrategy;

public class CustomNamingStrategy extends ImprovedNamingStrategy {

	@Override
	public String columnName(String columnName) {
		// TODO Auto-generated method stub
		return super.columnName(columnName);
	}
	
	

}
