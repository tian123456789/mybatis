package com.tgr.springbootmybatis;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class CustomPool {
	
	@SuppressWarnings("unused")
	private int captiy = 10;

	private volatile AtomicInteger calculate = new AtomicInteger(0);
	
	private volatile boolean demoRunFlg = true;
	
	private static volatile boolean stopFlg = false; //停止标志
	
	private boolean isNeedReturnResult = true;
	
	private BlockingQueue<Thread> queue = new ArrayBlockingQueue<Thread>(10);
	
	private ThreadList<Thread> takedThreads = new ThreadList<Thread>();
	
	@Test
	public void test() {
		for(int i = 0 ; i < 10 ; i++) {
			Thread thread = new SimpleThread(String.valueOf(i));
			submit(thread);//提交的每个线程都是新进来的请求
		}
		start();
	}
	
	public void initPool(Thread thread) {
		/*for(int i = 0 ; i < 10 ; i++) {*/
			queue.add(thread);
		/*}*/
	}
	
	public void submit(Thread thread) {
		initPool(thread);
	}
	
	public void start() {
		
		for(int i = 0 ; i < 10 ; i++) {
			Thread thread;
			try {
				thread = queue.take();
				thread.start();
				takedThreads.add(thread);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		
		startDeamon();//开启守护线程 保障线程池内数量

		if (isNeedReturnResult) {
			takedThreads.isSkip();
			waitStopAfterReturn();
		}
	}
	
	public Integer waitStopAfterReturn(){
		return returnResult();
	}
	
	public void startDeamon() {
		DaemonThread daemonThread = new DaemonThread(queue);
		daemonThread.start();
	}
	
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
	
	/**
	 * @author tgr
	 * 努力维持线程数量 是为了更好节约线程资源
	 * 如何突出这个问题
	 * 用哪种编程模型来体现
	 * 
	 * socket的接待者模式 记得是不是这个原理呢
	 */
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
				}finally {}
			}
		}
	}
	
	@SuppressWarnings("hiding")
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
							System.err.println("线程: "+x.getName()+"  已经终止!  剩余运行数量为: "+(this.size()-1));
							this.remove(x);
						}
					});
				} catch (Exception e) {}
			}
		}
		
	}
}
