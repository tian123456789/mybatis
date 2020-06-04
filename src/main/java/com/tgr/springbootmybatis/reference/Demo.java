package com.tgr.springbootmybatis.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class Demo {

	public static void main(String[] args) throws InterruptedException {
		
		Demo.User u = new Demo().new User("坚强的小强");//强引用
		
		Demo.User u1 = new Demo().new User("lisi");
		SoftReference<Demo.User> sr = new SoftReference<Demo.User>(u1);//软引用
		u1 = null;
		Demo.User u1_1 = sr.get();
		
		Demo.User u2 = new Demo().new User("zs");
		WeakReference<Demo.User> wr = new WeakReference<Demo.User>(u2);//弱引用
		u2 = null;
		
		Demo.User u3 = new Demo().new User("王二麻子");//虚引用
		PhantomReference<Demo.User> pr = new PhantomReference<Demo.User>(u3, new ReferenceQueue<>());
		u3 = null;
		System.out.println("虚引用"+pr.get());//直接空指针 对象消亡前做清理工作
		
		System.gc();
		
		System.out.println("强引用:"+u);//宁愿内存溢出也不回收
		System.out.println("软引用:"+sr.get());//一次gc后依旧打印名字 只有内存要溢出时回收
		System.out.println("弱引用:"+ wr.get());//一次gc后null
		
		
		
		
		
	}
	
	class User{
		String name;

		public User(String name) {
			super();
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
		
		
	}
}
