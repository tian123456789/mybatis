package com.tgr.springbootmybatis.proxy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import sun.misc.ProxyGenerator;

/**
 * @author tgr
 *整个JDK动态代理的秘密也就这些，简单一句话，动态代理就是要生成一个包装类对象，
 *由于代理的对象是动态的，所以叫动态代理。
 *由于我们需要增强，这个增强是需要留给开发人员开发代码的，因此代理类不能直接包含被代理对象，
 *而是一个InvocationHandler，
 *该InvocationHandler包含被代理对象，并负责分发请求给被代理对象，
 *分发前后均可以做增强。从原理可以看出，JDK动态代理是“对象”的代理。
 *
 *jdk动态代理要求对象必须实现接口（三个参数的第二个参数）
 *	https://blog.csdn.net/flyfeifei66/article/details/81481222#%E4%BB%A3%E7%90%86%E6%A8%A1%E5%BC%8F
 */
public class JDKDynamicProxy {
	
	public static void main(String[] args) {
		Star liudehua = /*new JDKDynamicProxy().*/new LiuDeHua();
		StarProxy proxy = new JDKDynamicProxy().new StarProxy();
		proxy.setTarget(liudehua);
		
		Object obj = proxy.createProxyObj();
		Star star = (Star)obj;
		star.sing();
		star.dance();
		
		// 将生成的字节码保存到本地，
       createProxyClassFile();
	}
	//https://blog.csdn.net/jiankunking/article/details/52143504
	 private static void createProxyClassFile(){//生成.class字节码文件
		 //最终生成的类
	        String name = "LiuDeHua";
	        byte[] data = ProxyGenerator.generateProxyClass(name,new Class[]{Star.class});
	        FileOutputStream out =null;
	        try {
	            out = new FileOutputStream(name+".class");
	            //System.out.println((new File("hello")).getAbsolutePath());
	            out.write(data);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally {
	            if(null!=out) try {
	                out.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	public class StarProxy implements InvocationHandler{

		private Object target;//目标类，被代理对象
		
		public void setTarget(Object target) {this.target = target;}
		
		public Object createProxyObj() {//生成代理类
			return Proxy.newProxyInstance(target.getClass().getClassLoader(),
					target.getClass().getInterfaces(), this);
		}
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			System.out.println("演出前经纪人收钱");//增强 被代理人(歌星)
			Object result = method.invoke(target, args);
			return result;
		}
		
	}
	
}


