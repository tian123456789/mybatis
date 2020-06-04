package com.tgr.springbootmybatis.threadpool_socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.Thread.State;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class CustomPool {
	
	@SuppressWarnings("unused")
	private int captiy = 10;
	
	private volatile boolean demoRunFlg = true;
	
	private static volatile BlockingQueue<Thread> threadQueue = new ArrayBlockingQueue<Thread>(100);
	
	public volatile ThreadListEleFromQueue<Thread> takedThreads = new ThreadListEleFromQueue<Thread>();
	
	public static volatile BlockingQueue<Socket> socketQueue = new ArrayBlockingQueue<>(10000);
	
	public void doing() {
		
		System.err.println("doing");
		
		new Thread() {
			public void run() {
				startDeamon();//开启守护线程 保障线程队列数量   此时没有socket将阻塞于此
			};
		}.start();
		
		new DaemonThreadList(takedThreads).start();
		
		
		/*
		 *  队列传过来
		 *	最多取10个利用线程运行
		 *	如果 10个一直在跑 那么阻塞获取
		 *	直到有空闲线程
		 */
		new Thread() {
			public void run() {
				takedThreads.takeForQueue();
			};
		}.start();
		
		
		while(true) {
			
			if(threadQueue.size() > 0) {
				System.err.println("线程对列数: "+threadQueue.size());
			}
			if(socketQueue.size() > 0) {
				System.err.println("socket队列数: "+socketQueue.size());
			}
			
			if(takedThreads.size() > 0) {
				
				for(int i = 0 ; i < takedThreads.size() ; i++) {
					try {
						Thread x=takedThreads.get(i);
						
						if(x != null && x.getState() == State.NEW) {//不要没等删进来了 刚运行 删了
							x.start(); 
						}
					} catch (Exception e) {}
				}
			}
		}
		
	}
	
	public void startDeamon() {
		DaemonThreadQueue daemonThread = new DaemonThreadQueue(threadQueue);
		daemonThread.start();
	}
	
	/**
	 * @author tgr
	 * 努力维持线程数量 是为了更好节约线程资源
	 * 如何突出这个问题
	 * 用哪种编程模型来体现
	 * 
	 * socket的接待者模式 记得是不是这个原理呢
	 */
	public class DaemonThreadQueue extends Thread{
		private BlockingQueue<Thread> threadQueue;
		
		public DaemonThreadQueue(BlockingQueue<Thread> queue) {
			this.threadQueue = queue;
		}
		
		@Override
		public void run() {
			while(demoRunFlg) {
				try {
					while(threadQueue.size() < 20) {
						System.err.println("重新加入线程size = "+threadQueue.size());
						threadQueue.put(new TimeServerHandler(socketQueue.take()));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally {}
			}
		}
	}
	
	public class DaemonThreadList extends Thread{
		private ThreadListEleFromQueue<Thread> takedThreads;
		
		public DaemonThreadList(ThreadListEleFromQueue<Thread> takedThreads) {
			this.takedThreads = takedThreads;
		}
		
		@Override
		public void run() {
			while(demoRunFlg) {
				for(int i = 0 ; i < takedThreads.size() ; i++) {
					try {
						if(takedThreads.get(i) ==null || takedThreads.get(i).getState() == State.TERMINATED) {
							System.out.println("线程终止  删除-------");
							takedThreads.remove(i);
						}
					} catch (Exception e) {}
				}
			}
		}
	}
	
	@SuppressWarnings("hiding")
	public class ThreadListEleFromQueue<T> extends ArrayList<T>{
		
		private static final long serialVersionUID = 1L;

		@Override
		public boolean add(T e) {
			return super.add(e);
		}
		
		@SuppressWarnings("unchecked")
		public void takeForQueue() {
			while(this.size() < 10) {
				try {
					this.add((T)threadQueue.take());
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}

	public static BlockingQueue<Thread> getThreadQueue() {
		return threadQueue;
	}

	public static void setThreadQueue(BlockingQueue<Thread> threadQueue) {
		CustomPool.threadQueue = threadQueue;
	}

	public static BlockingQueue<Socket> getSocketQueue() {
		return socketQueue;
	}

	public static void setSocketQueue(BlockingQueue<Socket> socketQueue) {
		socketQueue = socketQueue;
	}

	
}
