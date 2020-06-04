package com.tgr.springbootmybatis.threadLocal;

public class Run {
	
	public static void main(String[] args) {//main Thread
		new Thread() {
			public void run() {
				Thread.currentThread().setName("A");
				Connection.threadLocal.set(110);
				A a = new A();
				a.toB();
			};
		}.start();
		
		new Thread() {
			public void run() {
				Thread.currentThread().setName("B");
				Connection.threadLocal.set(220);
				A a = new A();
				a.toB();
			};
		}.start();
	}
}
