package com.interview.project.demoBankingMicroservice.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import com.interview.project.demoBankingMicroservice.bean.TransactionsListModel;
import com.interview.project.demoBankingMicroservice.bean.request.MoneyTransferRequestDTO;
import com.interview.project.demoBankingMicroservice.controllers.DemoBankingMicroserviceController;
import com.interview.project.demoBankingMicroservice.exception.CreditTransferException;
import com.interview.project.demoBankingMicroservice.exception.DemoBankingGenericException;
import com.interview.project.demoBankingMicroservice.service.DemoBankingMicroserviceService;

@SpringBootTest
public class DemoBankingMicroserviceControllerTest {

	@InjectMocks
	private DemoBankingMicroserviceController demoBankingMicroserviceController;
	
	@MockBean
	DemoBankingMicroserviceService demoBankingMicroserviceService;
	
	@Test
	public void getBalanceTest() throws DemoBankingGenericException {
				
		ReflectionTestUtils.setField(demoBankingMicroserviceController, "demoBankingMicroserviceService", demoBankingMicroserviceService);
		
		Double balance = 123D;
		given(demoBankingMicroserviceService.getBalance()).willReturn(balance);
		assertNotNull(demoBankingMicroserviceController.getBalance());
	}
	
	@Test
	public void getTransactionListTest() throws DemoBankingGenericException {
				
		ReflectionTestUtils.setField(demoBankingMicroserviceController, "demoBankingMicroserviceService", demoBankingMicroserviceService);
		
		TransactionsListModel model = new TransactionsListModel();
		given(demoBankingMicroserviceService.getTransactionList(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).willReturn(model);
		assertNotNull(demoBankingMicroserviceController.getTransactionList(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()));
	}
	
	@Test
	public void createMoneyTransfer_expectedCreditMoneyTransferException() {
		
		ReflectionTestUtils.setField(demoBankingMicroserviceController, "demoBankingMicroserviceService", demoBankingMicroserviceService);
		MoneyTransferRequestDTO dto = new MoneyTransferRequestDTO();
		
		given(demoBankingMicroserviceService.createMoneyTransfer(dto)).willThrow(CreditTransferException.class);
		
		Assertions.assertThrows(CreditTransferException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {
				demoBankingMicroserviceController.createMoneyTransfer(dto);
			}
		});
								
	}
	
}
