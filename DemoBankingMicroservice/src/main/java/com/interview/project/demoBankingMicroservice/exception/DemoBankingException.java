package com.interview.project.demoBankingMicroservice.exception;

import lombok.Data;

@Data
public class DemoBankingException {

	private final String code;
	private final String description;
	
}
