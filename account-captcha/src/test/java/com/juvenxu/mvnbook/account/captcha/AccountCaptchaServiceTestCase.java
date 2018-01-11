package com.juvenxu.mvnbook.account.captcha;


import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class AccountCaptchaServiceTestCase {

	private AccountCaptchaService accountCaptchaService;
	
	@BeforeMethod
	public void prepare() throws Exception{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("account-captcha.xml");
		accountCaptchaService = (AccountCaptchaService)ctx.getBean("accountCaptchaService");
	}
	
	@Test
	public void testGenerateCaptcha() throws Exception {
		String captchaKey = accountCaptchaService.generateCaptchaKey();
		assertNotNull(captchaKey);
		byte[] captchaImage = accountCaptchaService.generateCaptchaImage(captchaKey);
		assertTrue(captchaImage.length>0);
		File image = new File("target/"+captchaKey+".jpg");
		OutputStream output = null;
		try {
			output = new FileOutputStream(image);
			output.write(captchaImage);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if (output!=null) {
				output.close();
			}
		}
		assertTrue(image.exists()&&image.length()>0);
	}

	@Test
	public void testValidateCaptchaCorrect() throws Exception{
		List<String> preDefinedTexts = new ArrayList<String>();
		preDefinedTexts.add("12345");
		preDefinedTexts.add("abcde");
		accountCaptchaService.setPreDefinedTexts(preDefinedTexts);
		String captchaKey = accountCaptchaService.generateCaptchaKey();
		accountCaptchaService.generateCaptchaImage(captchaKey);
		assertTrue(accountCaptchaService.validateCaptcha(captchaKey, "12345"));
		captchaKey = accountCaptchaService.generateCaptchaKey();
		accountCaptchaService.generateCaptchaImage(captchaKey);
		assertTrue(accountCaptchaService.validateCaptcha(captchaKey, "abcde"));
	} 

}
