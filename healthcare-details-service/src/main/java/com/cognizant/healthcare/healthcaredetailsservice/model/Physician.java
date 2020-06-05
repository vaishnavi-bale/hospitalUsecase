package com.cognizant.healthcare.healthcaredetailsservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Physician {
	private int id;
	private String name;
	private String specialization;
}
