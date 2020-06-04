package com.tgr.springbootmybatis.spring_stopwatch;

import org.springframework.util.StopWatch;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		test();
	}
	
	private static void test() throws InterruptedException {
        StopWatch sw = new StopWatch();

        sw.start("起床");
        Thread.sleep(1000);
        sw.stop();

        sw.start("洗漱");
        Thread.sleep(2000);
        sw.stop();

        sw.start("锁门");
        Thread.sleep(500);
        sw.stop();

        System.out.println("prettyPrint`"+sw.prettyPrint());
        System.out.println("getTotalTimeMillis`"+sw.getTotalTimeMillis());
        System.out.println("getLastTaskName`"+sw.getLastTaskName());
        System.out.println("getLastTaskInfo`"+sw.getLastTaskInfo());
        System.out.println("getTaskCount`"+sw.getTaskCount());
    }

}
