package com.tgr.springbootmybatis.threadpool_socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BioServer {
	
	private static final int PORT = 17001;
	
	private static CustomPool customPool = new CustomPool();
	
	public static void main(String[] args) throws InterruptedException {
		
		/**
		 * 异步启动 pool后
		 * socketQueue依旧不在一个线程 那么socketQueue只能是线程间可见啦
		 */
		PoolThread poolThread = new PoolThread(customPool);
		poolThread.start();
		
		Thread.sleep(100);
		
		ServerSocket server = null;
		try {
			server = new ServerSocket(PORT);
			Socket socket = null;
			while(true) {
				socket = server.accept();
				/*  将接收的请求 放入一个队列 
					另一定容量的线程池 从队列中获取请求体 如果队列阻塞 等待接收请求
					分发到不同线程 以pipelind的形式 向下流转
					如果 池内线程全部在忙 那么阻塞与此 知道池内线程出现空闲
					阻塞条件: 以前判断停止的集合 为满容量==10
				*/
				//new Thread(new TimeServerHandler(socket)).start();
				CustomPool.socketQueue.add(socket);
				//pool.submit(thread);
				System.out.println("socket已经传入socket队列");
				
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}finally {
			if(server != null) {
				try {
					server.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
}
