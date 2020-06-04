package com.tgr.springbootmybatis.proxy;

public class MyTest implements TestInterface{

	public void say() {
		System.out.println("hello");
	}

	@Override
	public void say2() {
		System.out.println("hello2222");
	}
}
