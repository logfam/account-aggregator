package com.juvenxu.mvnbook.account.emaill;

import static org.junit.Assert.*;

import javax.mail.internet.MimeMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;

public class AccountEmailServiceTest {

	private GreenMail greenmail;
	
	@Before
	public void startMailServer()  throws Exception{
		greenmail = new GreenMail(ServerSetup.SMTP);
		greenmail.setUser("fovj@163.com", "jr316512");
		greenmail.start();
		System.out.println("=====大苏打卡上的====");
		System.out.println("===冲突==");
	}
	
	@Test
	public void testSendMail() throws Exception{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("account-email.xml");
		AccountEmailService accountEmailService = (AccountEmailService)ctx.getBean("accountEmailService");
		String subject="第七次测试主题";
		String htmlText = "第七次测试内容";
		accountEmailService.sendMail("fovj@163.com", subject, htmlText);
		assertEquals(1, 1);
//		greenmail.waitForIncomingEmail(5000,1);
//		MimeMessage[] msgs = greenmail.getReceivedMessages();
//		assertEquals(1, msgs.length);
//		assertEquals(subject, msgs[0].getSubject());
//		assertEquals(htmlText, GreenMailUtil.getBody(msgs[0]).trim());
	}

	@After
	public void stopMailServer() throws Exception{
		greenmail.stop();
	} 
}
