package com.interview.project.demoBankingMicroservice.bean;

import lombok.Data;

@Data
public class Creditor {

	private String name;
	private Account account;
	private Address address;
	
}

