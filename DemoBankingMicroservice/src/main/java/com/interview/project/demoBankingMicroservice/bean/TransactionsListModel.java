package com.interview.project.demoBankingMicroservice.bean;

import java.util.List;

import lombok.Data;

@Data
public class TransactionsListModel {

	private List<Transaction> list;
}
