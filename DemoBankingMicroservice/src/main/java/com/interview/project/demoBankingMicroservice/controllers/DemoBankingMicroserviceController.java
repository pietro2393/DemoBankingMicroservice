package com.interview.project.demoBankingMicroservice.controllers;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.project.demoBankingMicroservice.bean.TransactionsListModel;
import com.interview.project.demoBankingMicroservice.bean.request.MoneyTransferRequestDTO;
import com.interview.project.demoBankingMicroservice.bean.response.BalanceResource;
import com.interview.project.demoBankingMicroservice.bean.response.TransactionsResource;
import com.interview.project.demoBankingMicroservice.exception.CreditTransferException;
import com.interview.project.demoBankingMicroservice.exception.DemoBankingException;
import com.interview.project.demoBankingMicroservice.exception.DemoBankingGenericException;
import com.interview.project.demoBankingMicroservice.service.DemoBankingMicroserviceService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/demoBanking")
public class DemoBankingMicroserviceController {
	
	private Logger logger = LoggerFactory.getLogger(DemoBankingMicroserviceController.class);
		
	@Autowired
	public DemoBankingMicroserviceService demoBankingMicroserviceService;

	@GetMapping("/getBalance")
	@ApiOperation(value = "Find Balance", notes = "Retrieving balance")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "OK"),
	        @ApiResponse(code = 401, message = "Unauthorized"),
	        @ApiResponse(code = 500, message = "An unexpected error has occurred.", response = DemoBankingException.class) })
	public ResponseEntity<BalanceResource> getBalance() throws DemoBankingGenericException {
	    
		logger.info("Start DemoBankingMicroserviceService getBalance");
		
		try {
			
			BalanceResource balanceResource = new BalanceResource();
			Double balance = demoBankingMicroserviceService.getBalance();
			
			balanceResource.setBalance(balance);
			
			logger.info("Result getBalance: {}", balance);
			return ResponseEntity.ok(balanceResource);
			
		}catch(DemoBankingGenericException ex) {
			logger.error("Error in DemoBankingMicroserviceService getBalance : " + ex);
			throw ex;
		}
	}
	
	@GetMapping("/getTransactionList")
	@ApiOperation(value = "Find Transactions List", notes = "Retrieving transactions list")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "OK"),
	        @ApiResponse(code = 400, message = "Bad Request"),
	        @ApiResponse(code = 500, message = "An unexpected error has occurred.", response = DemoBankingException.class) })
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fromDate", dataType = "String", paramType = "query", defaultValue = "YYYY-MM-DD"),
		@ApiImplicitParam(name = "toDate", dataType = "String", paramType = "query", defaultValue = "YYYY-MM-DD")
	})
	public ResponseEntity<TransactionsResource> getTransactionList(@PathParam(value = "fromDate") String fromDate, @PathParam(value = "toDate") String toDate) throws DemoBankingGenericException {
		
		logger.info("Start DemoBankingMicroserviceService getTransactionList");
		logger.info("DemoBankingMicroserviceService getTransactionList fromDate : {} and toDate : {}", fromDate, toDate);
		
		try {
			
			TransactionsResource transactionsResource = new TransactionsResource();
			TransactionsListModel transactionList = demoBankingMicroserviceService.getTransactionList(fromDate, toDate);
			
			transactionsResource.setListTransaction(transactionList.getList());
			
			logger.info("Result getTranscationList: {}", transactionsResource);
			return ResponseEntity.ok(transactionsResource);
			
		}catch(DemoBankingGenericException ex) {
			logger.error("Error in DemoBankingMicroserviceService getTransactionList : " + ex);
			throw ex;
		}
		
	}
	
	@PostMapping("/createMoneyTransfer")
	@ApiOperation(value = "Create money Transfer", notes = "Create Money Transfer")
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Created"),
	        @ApiResponse(code = 400, message = "Bad Request"),
	        @ApiResponse(code = 403, message = "Forbidden"),
	        @ApiResponse(code = 500, message = "An unexpected error has occurred.") })
	public ResponseEntity<String> createMoneyTransfer(MoneyTransferRequestDTO moneyTransferRequestDTO) throws CreditTransferException {
		
		logger.info("Start DemoBankingMicroserviceService createMoneyTransfer");
		logger.info("DemoBankingMicroserviceService createMoneyTransfer moneyTransferRequestDTO : {}", moneyTransferRequestDTO);
		
		try {
			
			String result = demoBankingMicroserviceService.createMoneyTransfer(moneyTransferRequestDTO);
			
			logger.info("Result createMoneyTransfer: {}", result);
			return ResponseEntity.ok(result);
			
		}catch(CreditTransferException ex) {
			logger.error("Error in DemoBankingMicroserviceService createMoneyTransfer : " + ex);
			throw ex;
		}
		
	}
	
}
