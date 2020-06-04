package com.tgr.springbootmybatis.proxy;

public class RealSubject implements Subject {

	@Override
	public void rent() {
		System.out.println("rent方法");
	}

	@Override
	public void hello(String str) {
		System.out.println("hello方法+str"+str);
	}

}
