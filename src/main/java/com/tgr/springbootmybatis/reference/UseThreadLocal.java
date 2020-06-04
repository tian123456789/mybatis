package com.tgr.springbootmybatis.reference;


public class UseThreadLocal {

	public static void main(String[] args) {
		Service b1 = new UseThreadLocal().new Service();
		Service b2 = new UseThreadLocal().new Service();
		
		T t1 = new UseThreadLocal().new T(b1);
		T t2 = new UseThreadLocal().new T(b2);
		t1.setName("t1");
		t2.setName("t2");
		t1.start();
		t2.start();
	}
	
	class T extends Thread{
		Service service;
		public T(Service b) {
 			this.service = b;
		}
		public void run() {
			service.print();
		}
	}
	
	class Service{
		void print() {
			new Dao().print(TestFactory.getCurrentTestSession());
		}
	}
	
	class Dao{
		void print(TestSession session) {
			System.out.println(Thread.currentThread().getName()+"::"+session+"::"+TestFactory.threadLocal.getName());
			TestFactory.threadLocal.remove();
		}
	}
	
	static class TestFactory{
		static MyThreadLocal<TestSession> threadLocal = 
				new UseThreadLocal().new MyThreadLocal<TestSession>(Thread.currentThread().getName());
		
		public static TestSession getCurrentTestSession() {
			TestSession session = threadLocal.get();
			if(session == null) {
				session = new TestSession(Thread.currentThread().getName());
			}
			threadLocal.set(session);
			return session;
		}
		
	}

	static class TestSession{
		String name;
		
		public TestSession(String name) {
			super();
			this.name = name;
		}
		public String getName() {return name;}
		public void setName(String name) {this.name = name;}
		@Override
		public String toString() {
			return this.name;
		}
	}
	
	class MyThreadLocal<T> extends ThreadLocal<TestSession>{
		
		String name;

		public MyThreadLocal(String name) {
			super();
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		
	}

}



