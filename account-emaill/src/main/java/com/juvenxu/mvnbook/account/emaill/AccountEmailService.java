package com.juvenxu.mvnbook.account.emaill;

public interface AccountEmailService {

	void sendMail(String to,String subject,String htmlText) throws AccountEmailException;
}
