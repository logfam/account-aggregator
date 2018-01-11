package com.juvenxu.mvnbook.account.persist;

import static org.junit.Assert.*;
import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AccountPersistServiceTest {

	private AccountPersistService service;
	
	@Before
	public void prepare() throws Exception{
		File persistDataFile = new File("target/test-classes/persist-data.xml");
		if (persistDataFile.exists()) {
			persistDataFile.delete();
			System.out.println("test");
			System.out.println("test2");
		}
		ApplicationContext ctx = new ClassPathXmlApplicationContext("account-persist.xml");
		service = (AccountPersistService)ctx.getBean("accountPersistService");
		Account account = new Account();
		account.setId("fovj");
		account.setName("fovj");
		account.setEmail("fovj@163.com");
		account.setPassword("mima");
		account.setActivated(true);
		service.createAccount(account);
	}
	
	@Ignore@Test
	public void testReadAccount() throws Exception{
		Account account = service.readAccount("fovj");
		assertNotNull(account);
		assertEquals("fovj", account.getId());
		assertEquals("fovj", account.getName());
		assertEquals("fovj@163.com", account.getEmail());
		assertEquals("mima2", account.getPassword());
		assertTrue(account.isActivated());
	}

}
