package com.interview.project.demoBankingMicroservice.bean.request;

import lombok.Data;

@Data
public class MoneyTransferRequestDTO {

	private String receiverName;
	private String description;
	private String currency;
	private String amount;
	private String executionDate;
	
}
