package com.juvenxu.mvnbook.account.captcha;

public class AccountCaptchaException extends Exception {

	    
	private static final long serialVersionUID = 1L;

	public AccountCaptchaException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountCaptchaException(String message) {
		super(message);
	}

	
}
