package com.interview.project.demoBankingMicroservice.bean.external;

import java.util.List;

import com.interview.project.demoBankingMicroservice.bean.Transaction;

import lombok.Data;

@Data
public class PayloadTransactionList {

	private List<Transaction> list;
	
}
