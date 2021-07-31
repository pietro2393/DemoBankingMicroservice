package com.interview.project.demoBankingMicroservice.service.impl;

import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.interview.project.demoBankingMicroservice.bean.Transaction;
import com.interview.project.demoBankingMicroservice.bean.TransactionsListModel;
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
	
	private RestTemplate restTemplate = new RestTemplate();
	
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
		
//		try {
//			
//			HttpHeaders headers = createHeaders();
//			headers.set("X-Time-Zone", "Europe/Rome");
//			
//			HttpEntity<Object> request = new HttpEntity<>(headers);
//			ResponseEntity<ResponseCallMoneyTransfer> response = restTemplate.exchange(apiBaseUrl + "/api/gbs/banking/v4.0/accounts/" + accountID + "/transactions?fromAccountingDate=" + fromDate + "&toAccountingDate=" + toDate,
//					HttpMethod.GET,
//					request,
//					ResponseCallTranscationList.class);
//			
//		}catch(HttpClientErrorException ex) {
//			//logger.error
//			throw new CreditTransferException(ErrorMessage.GENERIC_ERROR);
//		}
		
		return null;
		
	}
	
//	String responseString = ex.getResponseBodyAsString();
//	ObjectMapper mapper = new ObjectMapper();
//
//		try {
//			Error errorResult = mapper.readValue(responseString, Error.class);
//		} catch (JsonProcessingException e) {
//			throw new DemoBankingException("KO", ErrorMessage.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
//		}

	private HttpHeaders createHeaders() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Api-Key", apiKey);
		headers.set("Auth-Schema", apiAuthSchema);
		headers.set("X-Time-Zone", "Europe/Rome");
		return headers;
		
	}



	
		
}
