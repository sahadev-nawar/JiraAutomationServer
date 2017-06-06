package com.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:global.properties")
public class GlobalProperties {
	
	
	// @Value("${devEnv}")
	private String devEnv;

	public String getDevEnv() {
		return devEnv;
	}

	public void setDevEnv(String devEnv) {
		this.devEnv = devEnv;
	}
	 
	 
	 

}
