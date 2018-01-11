package com.juvenxu.mvnbook.account.service;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import com.juvenxu.mvnbook.account.captcha.AccountCaptchaService;

public class AccountServiceTest {

	private GreenMail greenMail;

	private AccountService accountService;

	@Before
	public void prepare() throws Exception {
		String[] springConfigFiles = { "account-email.xml", "account-persist.xml", "account-captcha.xml",
				"account-service.xml" };

		ApplicationContext ctx = new ClassPathXmlApplicationContext(springConfigFiles);

		AccountCaptchaService accountCaptchaService = (AccountCaptchaService) ctx.getBean("accountCaptchaService");

		List<String> preDefinedTexts = new ArrayList<String>();
		preDefinedTexts.add("12345");
		preDefinedTexts.add("abcde");
		accountCaptchaService.setPreDefinedTexts(preDefinedTexts);

		accountService = (AccountService) ctx.getBean("accountService");

		greenMail = new GreenMail(ServerSetup.SMTP);
		greenMail.setUser("fovj@163.com", "jr316512");
		greenMail.start();

		File persistDataFile = new File("target/test-classes/persist-data.xml");
		if (persistDataFile.exists()) {
			persistDataFile.delete();
		}
	}

	@Ignore@Test
	public void testAccountService() throws Exception {
		// 1. Get captcha
		String captchaKey = accountService.generateCaptchaKey();
		accountService.generateCaptchaImage(captchaKey);
		String captchaValue = "12345";

		// 2. Submit sign up Request
		SignUpRequest signUpRequest = new SignUpRequest();
		signUpRequest.setCaptchaKey(captchaKey);
		signUpRequest.setCaptchaValue(captchaValue);
		signUpRequest.setId("fovj");
		signUpRequest.setEmail("fovj@163.com");
		signUpRequest.setName("fovj");
		signUpRequest.setPassword("mima2");
		signUpRequest.setConfirmPassword("mima2");
		signUpRequest.setActivateServiceUrl("http://localhost:8080/account/activate");
		String activationLink = accountService.signUp(signUpRequest);
		System.out.println("[activationLink========]"+activationLink);

		// 3. Read activation link
		greenMail.waitForIncomingEmail(2000, 1);
//		Message[] msgs = greenMail.getReceivedMessages();
//		assertEquals(1, msgs.length);
//		assertEquals("Please Activate Your Account", msgs[0].getSubject());
//		String activationLink = GreenMailUtil.getBody(msgs[0]).trim();
		
		// 3a. Try login but not activated
		try {
			accountService.login("fovj", "mima2");
			fail("Disabled account shouldn't be able to log in.");
		} catch (AccountServiceException e) {
		}

		// 4. Activate account
		String activationCode = activationLink.substring(activationLink.lastIndexOf("=") + 1);
		accountService.activate(activationCode);

		// 5. Login with correct id and password
		accountService.login("fovj", "mima2");

		// 5a. Login with incorrect password
		try {
			accountService.login("fovj", "mima2no");
			fail("Password is incorrect, shouldn't be able to login.");
		} catch (AccountServiceException e) {
		}

	}

	@After
	public void stopMailServer() throws Exception {
		greenMail.stop();
	}

}
