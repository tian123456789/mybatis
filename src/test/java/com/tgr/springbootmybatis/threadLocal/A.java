package com.tgr.springbootmybatis.threadLocal;

public class A {

	public void toB(){
		B b = new B();
		b.run();
	}
}
