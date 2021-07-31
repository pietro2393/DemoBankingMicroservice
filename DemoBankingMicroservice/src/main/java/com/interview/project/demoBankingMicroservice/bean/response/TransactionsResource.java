package com.interview.project.demoBankingMicroservice.bean.response;

import java.util.List;

import com.interview.project.demoBankingMicroservice.bean.Transaction;

import lombok.Data;

@Data
public class TransactionsResource {

	private List<Transaction> listTransaction;
	
}
