package com.tgr.springbootmybatis.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


public class CglibProxyTest {

	public static void main(String[] args) {
		CglibProxy proxy = new CglibProxyTest().new CglibProxy();
		LiuDeHua liudehua = (LiuDeHua)proxy.CreateProxyedObject(LiuDeHua.class);
		liudehua.dance();
	}
	
	class CglibProxy implements MethodInterceptor{
		public Object CreateProxyedObject(Class<?> clazz) {
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(clazz);
			enhancer.setCallback(this);
			return enhancer.create();
		}
		
		@Override
		public Object intercept(Object obj, Method method, Object[] objArr, MethodProxy methodProxy) throws Throwable {
			System.out.println("经纪人收钱");
			return methodProxy.invokeSuper(obj, objArr);
		}
	}
	
	static class LiuDeHua{
		public String sing() {
			System.out.println("给我一杯忘情水");
			return "演唱结束";
		}

		public String dance() {
			System.out.println("开心的马骝");
			return "跳完";
		}
	}
	
}




