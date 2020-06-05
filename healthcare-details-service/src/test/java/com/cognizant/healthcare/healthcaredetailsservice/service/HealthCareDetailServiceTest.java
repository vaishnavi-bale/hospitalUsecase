package com.cognizant.healthcare.healthcaredetailsservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cognizant.healthcare.healthcaredetailsservice.HealthcareDetailsServiceApplication;
import com.cognizant.healthcare.healthcaredetailsservice.constants.Constants;
import com.cognizant.healthcare.healthcaredetailsservice.exception.PatientRecordNotFoundException;
import com.cognizant.healthcare.healthcaredetailsservice.model.Patient;
import com.cognizant.healthcare.healthcaredetailsservice.model.PatientRecord;
import com.cognizant.healthcare.healthcaredetailsservice.model.Physician;
import com.cognizant.healthcare.healthcaredetailsservice.serviceimpl.HealthCareDetailServiceImpl;

@SpringBootTest(classes = {HealthcareDetailsServiceApplication.class})
class HealthCareDetailServiceTest {

	@InjectMocks
	private HealthCareDetailServiceImpl service;

	@Mock
	private RestTemplate restTemplate;

	String patientServiceUrl;

	String physicianServiceUrl;

	@Test
	void getPatientRecordTest() {

		Patient patient = new Patient(1, "ABC", 22, "male", "xyz", 4);
		Physician physician = new Physician(4, "LMNO", "mlo");

		Map<String, String> patientParams = new HashMap<String, String>();
		patientParams.put("id", Integer.toString(patient.getId()));
		Map<String, String> physicianParams = new HashMap<String, String>();
		physicianParams.put("id", Integer.toString(physician.getId()));

		when(restTemplate.getForEntity(patientServiceUrl  + "get/" + "{id}", Patient.class, patientParams))
				.thenReturn(new ResponseEntity<Patient>(patient, HttpStatus.OK));
		when(restTemplate.getForEntity(physicianServiceUrl  + "get/" + "{id}", Physician.class, physicianParams))
				.thenReturn(new ResponseEntity<Physician>(physician, HttpStatus.OK));

		assertTrue(service.getPatientRecord(1).equals(new PatientRecord(patient, physician)));

		verify(restTemplate, times(1)).getForEntity(patientServiceUrl + "get/" + "{id}", Patient.class, patientParams);
		verify(restTemplate, times(1)).getForEntity(physicianServiceUrl + "get/" + "{id}", Physician.class,
				physicianParams);
	}

	@Test
	void getPatientRecordTestForException() {
		Map<String, String> patientParams = new HashMap<String, String>();
		patientParams.put("id", Integer.toString(1));

		Exception exception = assertThrows(PatientRecordNotFoundException.class, () -> {
			when(restTemplate.getForEntity(patientServiceUrl + "get/" + "{id}", Patient.class, patientParams))
					.thenThrow(RestClientException.class);
			service.getPatientRecord(1);

		});
		assertNotNull(exception);

		verify(restTemplate, times(1)).getForEntity(patientServiceUrl + "get/" + "{id}", Patient.class, patientParams);
	}

}
