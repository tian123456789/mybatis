package com.tgr.async.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncSendSMS {

	@Async
	public void sendAuthSMS() throws InterruptedException {
		System.err.println("发送认证SMS");
		System.err.println("currentThreadName: "+Thread.currentThread().getName());
		Thread.sleep(200000);
	}
}
