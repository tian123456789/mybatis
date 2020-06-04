package com.tgr.springbootmybatis.threadpool_socket;

import java.util.ArrayList;
import java.util.List;

public class Test {
	
	public static void main(String[] args) {
		
		List<Thread> list = new ArrayList<>();
		
		for(int i = 0 ; i < 10 ; i++) {
			list.add(new Test().new T());
		}
		
		list.stream().forEach(x->{
			x.start();
		});
		
		list.stream().forEach(x->{
			try {
				x.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		System.err.println("size="+list.size());
	}

	class T extends Thread{
		@Override
		public void run() {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.err.println(1);
		}
	}
}
