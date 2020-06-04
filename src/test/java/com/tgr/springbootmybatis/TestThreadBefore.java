package com.tgr.springbootmybatis;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;


public class TestThreadBefore {
	
	@Test
	public void simple2() throws InterruptedException {
		
		ReentrantLock lock = new ReentrantLock();
		Condition conditionProduct = lock.newCondition();
		Condition conditionConsum = lock.newCondition();
		
		Product thread1 = new Product(lock,conditionConsum,conditionProduct);
		Consum thread2 = new Consum(lock,conditionProduct,conditionConsum);
		thread1.start();
		thread2.start();
		
		thread1.join();
		thread2.join();
		//thread3.join();
		
	}
	
	
	public class Product extends Thread {

		private Condition conditionConsum;
		private Condition conditionProduct;
		private ReentrantLock lock;

		public Product(ReentrantLock lock, Condition conditionConsum , Condition conditionProduct) {
			this.conditionConsum = conditionConsum;
			this.conditionProduct = conditionProduct;
			this.lock = lock;
			this.setName("线程1");
		}

		@Override
		public void run() {
			lock.lock();
			try {
				//consum.await(); 自己等待
				System.err.println("生产一个");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conditionConsum.signalAll();//唤醒所有消费者 
				lock.unlock();
			}
		}

	}
	
	public class Consum extends Thread {

		private Condition conditionProduct;
		private Condition conditionConsum;
		private ReentrantLock lock;

		public Consum(ReentrantLock lock, Condition conditionProduct ,Condition conditionConsum) {
			this.conditionProduct = conditionProduct;
			this.conditionConsum = conditionConsum;
			this.lock = lock;
			this.setName("线程2");
		}

		@Override
		public void run() {
			lock.lock();
			try {
				conditionConsum.await();//自己等待
				System.err.println("消费一个");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				//consum2.signal();
				lock.unlock();
			}

		}

	}
	
}
