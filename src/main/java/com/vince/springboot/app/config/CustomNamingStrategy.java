package com.vince.springboot.app.config;

import org.hibernate.cfg.ImprovedNamingStrategy;

public class CustomNamingStrategy extends ImprovedNamingStrategy {

	@Override
	public String columnName(String columnName) {
		
		String withUnderscores = super.columnName(columnName);
		
		String charToCapitalize = withUnderscores.charAt(withUnderscores.charAt('_') + 1) + "";
		withUnderscores = withUnderscores.replace("_", charToCapitalize.toUpperCase());
		
		
		return withUnderscores;
	}
	
	

}
