package com.interview.project.demoBankingMicroservice.bean.external;

import java.util.List;

import lombok.Data;

@Data
public class ResponseCallBalance {

	private String status;
	private List<Error> errors;
    private PayloadBalance payload; 

}