package com.interview.project.demoBankingMicroservice.service;

import com.interview.project.demoBankingMicroservice.bean.TransactionsListModel;
import com.interview.project.demoBankingMicroservice.bean.request.MoneyTransferRequestDTO;
import com.interview.project.demoBankingMicroservice.exception.CreditTransferException;
import com.interview.project.demoBankingMicroservice.exception.DemoBankingGenericException;

public interface DemoBankingMicroserviceService {
	
	public Double getBalance() throws DemoBankingGenericException;
	
	public TransactionsListModel getTransactionList(String fromDate, String toDate) throws DemoBankingGenericException;
	
	public String createMoneyTransfer(MoneyTransferRequestDTO moneyTransferRequestDTO) throws CreditTransferException;

}
