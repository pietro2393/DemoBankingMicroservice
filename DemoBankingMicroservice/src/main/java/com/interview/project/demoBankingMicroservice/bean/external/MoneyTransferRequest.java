package com.interview.project.demoBankingMicroservice.bean.external;

import com.interview.project.demoBankingMicroservice.bean.Creditor;
import com.interview.project.demoBankingMicroservice.bean.TaxRelief;

import lombok.Data;

@Data
public class MoneyTransferRequest {

	private Creditor creditor;
	private String executionDate;
	private String uri;
	private String description;
	private Double amount;
	private String currency;
	private boolean isUrgent;
	private boolean isInstant;
	private String feeType;
	private String feeAccountId;
	private TaxRelief taxRelief;
}


