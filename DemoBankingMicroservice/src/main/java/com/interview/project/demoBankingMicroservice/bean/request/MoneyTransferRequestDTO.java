package com.interview.project.demoBankingMicroservice.bean.request;

import lombok.Data;

@Data
public class MoneyTransferRequestDTO {

	private String receiverName;
	private String description;
	private String currency;
	private Double amount;
	private String executionDate;
	
}
