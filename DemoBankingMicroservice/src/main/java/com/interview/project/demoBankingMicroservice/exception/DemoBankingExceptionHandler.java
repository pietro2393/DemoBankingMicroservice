package com.interview.project.demoBankingMicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DemoBankingExceptionHandler {

	@ExceptionHandler(value = {DemoBankingGenericException.class})
	public ResponseEntity<Object> demoBankingGenericException(DemoBankingGenericException exception){
		
		DemoBankingException demobankingException = new DemoBankingException("REQ000", exception.getMessage());
		return new ResponseEntity<>(demobankingException, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = {CreditTransferException.class})
	public ResponseEntity<Object> demoBankingGenericException(CreditTransferException exception){
		
		DemoBankingException demobankingException = new DemoBankingException("API000", exception.getMessage());
		return new ResponseEntity<>(demobankingException, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
