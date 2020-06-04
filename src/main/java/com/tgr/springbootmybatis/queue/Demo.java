package com.tgr.springbootmybatis.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo {
	public static void main(String[] args) {
		// 建立篮子
		final Lanzi lanzi = new Lanzi();
		//ExecutorService service = Executors.newCachedThreadPool();
		ExecutorService service = Executors.newFixedThreadPool(200);
		for (int i = 0; i < 25; i++) {//125 50 75
			service.submit(new Producer(lanzi));
			service.submit(new Producer(lanzi));
			service.submit(new Producer(lanzi));
			service.submit(new Producer(lanzi));
			service.submit(new Producer(lanzi));
			service.submit(new Consumer(lanzi));
			service.submit(new Consumer(lanzi));
		}
		service.shutdown();
	}

}

// 生产者
class Producer implements Runnable {

	private Lanzi lanzi;

	public Producer(Lanzi lanzi) {
		this.lanzi = lanzi;
	}

	@Override
	public void run() {
		try {
			// System.err.println("准备生产苹果: " + System.currentTimeMillis());
			lanzi.produce();
			System.err.println(Thread.currentThread().getName() + "生产苹果完毕: ");
			System.err.println("生产后篮子苹果总数量: " + lanzi.getAppleNumber() + "个");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}

	}
}

// 消费者
class Consumer implements Runnable {

	private Lanzi lanzi;

	public Consumer(Lanzi lanzi) {
		this.lanzi = lanzi;
	}

	@Override
	public void run() {
			try {
				lanzi.consume();
			} catch (InterruptedException e) {
				System.out.println("消费苹果中断异常");
			}
			System.err.println(Thread.currentThread().getName() + "消费苹果完毕: ");
			System.err.println("消费后篮子苹果总数量: " + lanzi.getAppleNumber() + "个");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

	}
}

class Lanzi {
	// 篮子 容量3个苹果
	//BlockingQueue<String> bq = new ArrayBlockingQueue<>(125);
	ConcurrentLinkedQueue bq = new ConcurrentLinkedQueue<>();
	// 生产苹果，放入篮子
	public void produce() throws InterruptedException {
		//bq.put("苹果");
		bq.offer("苹果");
	}

	// 消费苹果
	public String consume() throws InterruptedException{
		//return bq.take();
		return (String) bq.poll();
	}

	// 篮子里面苹果数量
	public int getAppleNumber() {
		return bq.size();
	}
}
