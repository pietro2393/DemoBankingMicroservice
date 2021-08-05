package com.interview.project.demoBankingMicroservice.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.interview.project.demoBankingMicroservice.bean.Creditor;
import com.interview.project.demoBankingMicroservice.bean.Transaction;
import com.interview.project.demoBankingMicroservice.bean.TransactionsListModel;
import com.interview.project.demoBankingMicroservice.bean.external.MoneyTransferRequest;
import com.interview.project.demoBankingMicroservice.bean.external.PayloadBalance;
import com.interview.project.demoBankingMicroservice.bean.external.PayloadTransactionList;
import com.interview.project.demoBankingMicroservice.bean.external.ResponseCallBalance;
import com.interview.project.demoBankingMicroservice.bean.external.ResponseCallTranscationList;
import com.interview.project.demoBankingMicroservice.bean.request.MoneyTransferRequestDTO;
import com.interview.project.demoBankingMicroservice.exception.CreditTransferException;
import com.interview.project.demoBankingMicroservice.exception.DemoBankingGenericException;
import com.interview.project.demoBankingMicroservice.exception.ErrorMessage;
import com.interview.project.demoBankingMicroservice.service.DemoBankingMicroserviceService;

@Service
public class DemoBankingMicroserviceServiceImpl implements DemoBankingMicroserviceService {

	@Value("${api.key}")
	private String apiKey;
	
	@Value("${api.baseUrl}")
	private String apiBaseUrl;
	
	@Value("${api.authSchema}")
	private String apiAuthSchema;
	
	@Value("${api.accountID}")
	private String accountID;
	
	private Logger logger = LoggerFactory.getLogger(DemoBankingMicroserviceServiceImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public Double getBalance() throws DemoBankingGenericException {
		
		logger.info("Start DemoBankingMicroserviceServiceImpl getBalance");
		
		try {

			HttpHeaders headers = createHeaders();
			
			HttpEntity<Object> request = new HttpEntity<>(headers);
			ResponseEntity<ResponseCallBalance> response = restTemplate.exchange(apiBaseUrl + "/api/gbs/banking/v4.0/accounts/" + accountID + "/balance",
					HttpMethod.GET,
					request,
					ResponseCallBalance.class);
			
			ResponseCallBalance responseCallExternalApiFabrick = response.getBody();
			PayloadBalance payloadBalance = responseCallExternalApiFabrick.getPayload();
			
			logger.info("DemoBankingMicroserviceServiceImpl getBalance result : {}", payloadBalance.getAvailableBalance());
			return payloadBalance.getAvailableBalance();
			
		}catch(HttpClientErrorException ex) {
			logger.error("Error in DemoBankingMicroserviceServiceImpl getBalance : " + ex);
			throw new DemoBankingGenericException(ErrorMessage.GENERIC_ERROR);
		}
		
	}

	@Override
	public TransactionsListModel getTransactionList(String fromDate, String toDate) throws DemoBankingGenericException {
		
		logger.info("Start DemoBankingMicroserviceServiceImpl getTransactionList fromDate {} and toDate {}", fromDate, toDate);
		
		try {
			
			HttpHeaders headers = createHeaders();
			
			HttpEntity<Object> request = new HttpEntity<>(headers);
			ResponseEntity<ResponseCallTranscationList> response = restTemplate.exchange(apiBaseUrl + "/api/gbs/banking/v4.0/accounts/" + accountID + "/transactions?fromAccountingDate=" + fromDate + "&toAccountingDate=" + toDate,
					HttpMethod.GET,
					request,
					ResponseCallTranscationList.class);
			
			ResponseCallTranscationList responseTransactionsList = response.getBody();
			PayloadTransactionList payload = responseTransactionsList.getPayload();
			List<Transaction> transactionList = payload.getList();
			
			TransactionsListModel transactionListModel = new TransactionsListModel();
			transactionListModel.setList(transactionList);
			
			logger.info("DemoBankingMicroserviceServiceImpl getTransactionList result : {}", transactionListModel);
			return transactionListModel;
			
		}catch(HttpClientErrorException ex) {
			logger.error("Error in DemoBankingMicroserviceServiceImpl getTransactionList : " + ex);
			throw new DemoBankingGenericException(ErrorMessage.GENERIC_ERROR);
		}
		
	}
	
	@Override
	public String createMoneyTransfer(MoneyTransferRequestDTO moneyTransferRequestDTO) throws CreditTransferException {
		
		logger.info("Start DemoBankingMicroserviceServiceImpl createMoneyTransfer moneyTransferRequestDTO {}", moneyTransferRequestDTO);
		
		try {
			
			HttpEntity<Object> request = createRequestMoneyTransfer(moneyTransferRequestDTO);
			
			ResponseEntity<String> response = restTemplate.exchange(apiBaseUrl + "/api/gbs/banking/v4.0/accounts/" + accountID + "/payments/money-transfers",
					HttpMethod.POST,
					request,
					String.class);
			
			return response.getBody();
			
		}catch(Exception ex) {
			logger.error("Error in DemoBankingMicroserviceServiceImpl createMoneyTransfer : " + ex);
			throw new CreditTransferException(ErrorMessage.ERROR_MONEY_TRANSFER);
		}
		
	}
	
	private HttpHeaders createHeaders() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Api-Key", apiKey);
		headers.set("Auth-Schema", apiAuthSchema);
		headers.set("X-Time-Zone", "Europe/Rome");
		return headers;
		
	}

	private HttpEntity<Object> createRequestMoneyTransfer(MoneyTransferRequestDTO moneyTransferRequestDTO){
		
		HttpHeaders headers = createHeaders();
		MoneyTransferRequest moneyTransferRequest = new MoneyTransferRequest();
		moneyTransferRequest.setAmount(moneyTransferRequestDTO.getAmount());
		moneyTransferRequest.setDescription(moneyTransferRequestDTO.getDescription());
		moneyTransferRequest.setCurrency(moneyTransferRequestDTO.getCurrency());
		moneyTransferRequest.setExecutionDate(moneyTransferRequestDTO.getExecutionDate());
		Creditor creditor = new Creditor();
		creditor.setName(moneyTransferRequestDTO.getReceiverName());
		moneyTransferRequest.setCreditor(creditor);
		moneyTransferRequest.setFeeAccountId(accountID);
		
		HttpEntity<Object> request = new HttpEntity<>(moneyTransferRequest.toString(), headers);	
		
		return request;
	}
	
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
		
}
