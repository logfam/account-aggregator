package com.juvenxu.mvnbook.account.service;

public class AccountServiceException extends Exception {

	    
	private static final long serialVersionUID = 1L;

	public AccountServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountServiceException(String message) {
		super(message);
	}

	
}
