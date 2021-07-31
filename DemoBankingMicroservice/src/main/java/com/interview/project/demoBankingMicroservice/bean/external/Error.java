package com.interview.project.demoBankingMicroservice.bean.external;

import lombok.Data;

@Data
public class Error {

	private String code;
    private String description;
    private String params;
	
}
