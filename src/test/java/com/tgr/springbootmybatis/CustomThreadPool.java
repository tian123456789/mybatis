package com.tgr.springbootmybatis;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class CustomThreadPool {

	private volatile AtomicInteger calculate = new AtomicInteger(0);
	
	private volatile boolean demoRunFlg = true;
	
	private static volatile boolean stopFlg = false; //停止标志
	
	private boolean isFullStop = false;
	
	@Test
	public void simpleArr() throws InterruptedException {
		
		int captiy = 10;
		BlockingQueue<Thread> queue = new ArrayBlockingQueue<Thread>(10);
		ThreadList<Thread> takedThreads = new ThreadList<Thread>();
		
		for(int i = 0 ; i < captiy ; i++) {
			queue.add(new SimpleThread(String.valueOf(i)));
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
		
		takedThreads.isSkip();
		returnResult();
		clearResource(queue,demoRunFlg);
	}
	
	
	/**
	 * 停止运行线程池
	 */
	public static void stopThreadPool() {
		stopFlg = true;
	}
	
	public Integer returnResult() {
		System.out.println("FULLSTOP FULLSTOP FULLSTOP FULLSTOP FULLSTOP FULLSTOP FULLSTOP FULLSTOP FULLSTOP FULLSTOP");
		System.err.println("返回计算结果calculate = "+calculate.get());
		return calculate.get();
	}
	
	public void clearResource(BlockingQueue<Thread> queue , boolean demoRunFlg) {
		queue.clear();
		queue = null;
		demoRunFlg = false;
	}
	
	@SuppressWarnings("all")
	public class SimpleThread extends Thread{
		
		private String index;
		
		public SimpleThread(String string) {
			this.index = string;
			this.setName("MyThread-"+string);
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
						queue.put(new SimpleThread("two-"+calculate.get()));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
				}
			}
		}
	}
	
	public class ThreadList<Object> extends ArrayList<Object>{
		
		private static final long serialVersionUID = 1L;

		@Override
		public boolean add(Object e) {
			return super.add(e);
		}
		
		public void isSkip() {
			while(this.size() != 0) {
				try {
					this.stream().map(x->{return (Thread)x;}).forEach(x->{
						if(x.getState() == State.TERMINATED) {
							System.err.println("线程: "+x.getName()+"  已经终止!剩余数量为: "+(this.size()-1));
							this.remove(x);
						}
					});
				} catch (Exception e) {}
			}
		}
		
	}
	
}
