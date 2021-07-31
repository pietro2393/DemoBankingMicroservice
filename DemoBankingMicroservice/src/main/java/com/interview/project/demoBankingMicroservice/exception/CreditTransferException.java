package com.interview.project.demoBankingMicroservice.exception;

@SuppressWarnings("serial")
public class CreditTransferException extends RuntimeException{
	
	public CreditTransferException(String message) {
		super(message);
	}
}
