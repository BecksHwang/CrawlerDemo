package com.becks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("sinaconfig")
public class SinaConfig {
	
	@Value("${sinaUrl}")
	private String sinaUrl;

	public String getSinaUrl() {
		return sinaUrl;
	}

}
