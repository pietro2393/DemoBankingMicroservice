package com.interview.project.demoBankingMicroservice.bean;

import lombok.Data;

@Data
public class Transaction {

	private String operationId;
	private String accountingDate;
	private String valueDate;
	private Double amount;
	private String currency;
	private String description;
	
}