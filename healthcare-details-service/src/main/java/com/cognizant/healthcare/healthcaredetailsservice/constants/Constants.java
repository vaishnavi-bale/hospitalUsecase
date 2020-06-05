package com.cognizant.healthcare.healthcaredetailsservice.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class Constants {

	@Value("${patient-service.url}")
	private String patientServiceUrl;
	
	@Value("${physician-service.url}")
	private String physicianServiceUrl;
	
	@Bean
	public String physicianServiceUrl() {
		return physicianServiceUrl;
	}
	
	@Bean
	public String patientServiceUrl() {
		return patientServiceUrl;
	}
}
