package com.tgr.springbootmybatis.threadLocal;

public class B {

	public void run() {
		System.err.println("Thread-"+Thread.currentThread().getName()+" ::: "+Connection.threadLocal.get());
	}
}
