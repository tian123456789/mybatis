package com.tgr.springbootmybatis;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tgr.springbootmybatis.mapper.AreaMapper;


@SpringBootTest(classes = {SpringbootMybatisApplication.class})
@RunWith(value = SpringRunner.class)
public class TestThread {

	@Autowired
	private AreaMapper areaMapper;
	
	private volatile AtomicInteger count = new AtomicInteger(0);
	
	@Test
	public void simple() throws InterruptedException {
		
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();
		
		for(int i = 1 ; i <= 44203 ; i++) {
			if(i%2 == 0) {
				list1.add(i);
			}else {
				list2.add(i);
			}
		}
		
		Thread_1 thread1 = new Thread_1(list1);
		Thread_2 thread2 = new Thread_2(list2);
		thread1.start();
		thread2.start();
		
		thread1.join();
		thread2.join();
		
		System.err.println("处理总数为: "+count.get());
		
	}
	
	
	public class Thread_1 extends Thread{
		
		private List<Integer> list;
		
		public Thread_1(List<Integer> list) {
			this.list = list;
			this.setName("线程1");
		}
		
		@Override
		public void run() {
			if(list.size() > 0) {
				for(int i = 0 ; i < list.size() ; i++) {
					System.err.println(Thread.currentThread().getName() + " :: " + areaMapper.selectByPrimaryKey(list.get(i)).getId());
					count.addAndGet(1);
				}
			}
		}
		
	}
	
	public class Thread_2 extends Thread {

		private List<Integer> list;

		public Thread_2(List<Integer> list) {
			this.list = list;
			this.setName("线程2");
		}

		@Override
		public void run() {
			if(list.size() > 0) {
				for(int i = 0 ; i < list.size() ; i++) {
					System.err.println(Thread.currentThread().getName() + " :: " + areaMapper.selectByPrimaryKey(list.get(i)).getId());
					count.addAndGet(1);
				}
			}
		}

	}
	
	/********************************************消费通知 ----并行变串行***********************************************************/
	
	private volatile boolean flag = false;
	
	@Test
	public void simple2() throws InterruptedException {
		
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();
		
		ReentrantLock lock = new ReentrantLock();
		
		Condition condition = lock.newCondition();
		
		for(int i = 1 ; i <= 44205 ; i++) {
			if(i%2 == 0) {
				list1.add(i);
			}else {
				list2.add(i);
			}
		}
		
		Product thread1 = new Product(list1,lock,condition);
		Consum thread2 = new Consum(list2,lock,condition);
		thread1.start();
		thread2.start();
		
		thread1.join();
		thread2.join();
		
		System.err.println("处理总数为: "+count.get());
	}
	
	
	public class Product extends Thread{
		
		private List<Integer> list;
		private Condition condition;
		private ReentrantLock lock;
		
		public Product(List<Integer> list,ReentrantLock lock ,Condition condition) {
			this.list = list;
			this.condition = condition;
			this.lock = lock;
			this.setName("线程1");
		}
		
		@Override
		public void run() {
			if(list.size() > 0) {
				for(int i = 0 ; i <= list.size() ; i++) {
					lock.lock();
					try {
						if(i == list.size()) {
							lock.unlock();;
						}
						if(flag) {
							condition.await();
						}
						System.err.println(Thread.currentThread().getName() + " :: " + areaMapper.selectByPrimaryKey(list.get(i)).getId());
						count.addAndGet(1);
						flag = true;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally {
						condition.signalAll();
						lock.unlock();
					}
				}
			}
		}
		
	}
	
	public class Consum extends Thread {

		private List<Integer> list;
		private Condition condition;
		private ReentrantLock lock;

		public Consum(List<Integer> list,ReentrantLock lock ,Condition condition) {
			this.list = list;
			this.condition = condition;
			this.lock = lock;
			this.setName("线程2");
		}

		@Override
		public void run() {
			if(list.size() > 0) {
				for(int i = 0 ; i <= list.size() ; i++) {
					lock.lock();
					try {
						if(i == list.size()-1) {
							lock.unlock();
						}
						if(!flag) {
							condition.await();
						}
						System.err.println(Thread.currentThread().getName() + " :: " + areaMapper.selectByPrimaryKey(list.get(i)).getId());
						count.addAndGet(1);
						flag = false;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally {
						condition.signalAll();
						lock.unlock();
					}
				}
			}
		}

	}



/******************************************** --简易池演化FILO池  ***********************************************************/
	//线程池结束 返回计算值
	//线程间传递 参数 
	private volatile AtomicInteger calculate = new AtomicInteger(0);
	private volatile boolean demoRunFlg = true;
	private static volatile boolean stopFlg = false; //停止标志
	private boolean isFullStop = false;
	
	@Test
	public void simpleArr() throws InterruptedException {
		int captiy = 10;
		
		BlockingQueue<Thread> queue = new ArrayBlockingQueue<Thread>(10);
		List<Thread> takedThreads = new ArrayList<Thread>();
		
		for(int i = 0 ; i < captiy ; i++) {
			queue.add(new SimpleThread(i));
		}
		
		DaemonThread daemonThread = new DaemonThread(queue);
		daemonThread.start();
		
		for(int i = 0 ; i  < 10 ; i++) {
			Thread thread = queue.take();
			takedThreads.add(thread);
			thread.start();
		}
		
		for(int i = 0 ; i  < 10 ; i++) {
			System.err.println("for:2");
			Thread thread = queue.take();
			takedThreads.add(thread);//add加入动作应该触发 foreach join   那么应用哪种设计模式呢? 观察者模式
			thread.start();
		}
		
		
		takedThreads.stream().forEach(x->{//这应该是一个应该被触发的功能 触发的位置 还要再计算结果之前 那么又要加入哪种设计模式呢 
			try {x.join();takedThreads.remove(x);} catch (InterruptedException e) {e.printStackTrace();}
		});
		
		System.out.println("FULLSTOP FULLSTOP FULLSTOP FULLSTOP FULLSTOP FULLSTOP FULLSTOP FULLSTOP FULLSTOP FULLSTOP");
		System.err.println("返回计算结果calculate = "+calculate.get());
		
		queue.clear();
		queue = null;
		demoRunFlg = false;
	}
	
	public void caculte(Thread thread) {
		System.err.println(thread.getName()+"线程状态: "+thread.getState().name());
		if(thread.getState() != State.TERMINATED) {
			isFullStop = false;
		}else {
			isFullStop = true;
		}
	}
	
	/**
	 * 停止运行线程池
	 */
	public static void stopThreadPool() {
		stopFlg = true;
	}
	
	@SuppressWarnings("all")
	public class SimpleThread extends Thread{
		
		private int index;
		
		public SimpleThread(int index) {
			this.index = index;
			this.setName("MyThread-"+index);
		}
		
		@Override
		public void run() {
			if(stopFlg) {
				int temp = 1/0;
			}
			try {
				System.err.println(this.currentThread().getName()+"--RUN");
				calculate.addAndGet(1);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {}
			
		}
	}
	
	public class DaemonThread extends Thread{
		private BlockingQueue<Thread> queue;
		
		public DaemonThread(BlockingQueue<Thread> queue) {
			this.queue = queue;
		}
		
		@Override
		public void run() {
			while(demoRunFlg) {
				try {
					while(queue.size() < 10) {
						System.err.println("重新加入线程size = "+queue.size());
						queue.put(new SimpleThread(9999));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
				}
			}
		}
	}
	
/******************************************** --简易池演化FILO池  ***********************************************************/

}

