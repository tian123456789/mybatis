package com.tgr.springbootmybatis.threadpool_socket;

public class PoolThread extends Thread{

	private CustomPool customPool;
	
	public PoolThread(CustomPool customPool) {
		this.customPool = customPool;
	}
	
	@Override
	public void run() {
		customPool.doing(); 
	}
}
