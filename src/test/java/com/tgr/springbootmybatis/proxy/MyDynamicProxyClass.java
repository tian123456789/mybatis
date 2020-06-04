package com.tgr.springbootmybatis.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyDynamicProxyClass implements InvocationHandler{
	
	//https://www.cnblogs.com/aspirant/p/9036805.html
	
	//我们要代理的真实对象
	private Object subject;
	
	//构造方法,给我们要代理的真实对象赋予初始值
	public MyDynamicProxyClass(Object subject) {
		this.subject = subject;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//代理真实对象前添加一些自己的操作
		System.out.println("before invoke");
		
		System.out.println("Method:"+method);
		
		//当代理对象那个调用真实对象的方法时,其会自动的跳转到代理对象关联的handler对象的invoke方法来调用
		method.invoke(subject, args);
		
		System.out.println("after invoke");
		return null;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
