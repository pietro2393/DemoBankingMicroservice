package com.interview.project.demoBankingMicroservice.bean.external;

import lombok.Data;

@Data
public class PayloadBalance {

	private String date;
    private double balance;
    private double availableBalance;
    private String currency;
	
}
