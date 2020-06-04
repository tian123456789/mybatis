package com.tgr.springbootmybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mongodb.MongoClient;
import com.tgr.async.service.AsyncSendSMS;


@SpringBootTest(classes = {SpringbootMybatisApplication.class})
@RunWith(value = SpringRunner.class)
public class TestMongo {

	@Autowired
	private MongoClient mongoClient;
	
	/*@Autowired
	private AsyncSendSMS asyncSendSMS;*/
	
	@Test
	private void test() {
		System.out.println(mongoClient.getAddress().getHost());
	}
	
	@Test
	private void testSMS() {
		/*try {
			asyncSendSMS.sendAuthSMS();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}

}
