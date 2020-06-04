package com.tgr.springbootmybatis.lambda;

import java.util.stream.Stream;

public class Demo2 {

	public static void main(String[] args) {
		//默认的串行流
		int max = Stream.iterate(1, x -> x+1)
				.limit(200)
				.peek(x ->{
					System.out.println(Thread.currentThread().getName());
				})
				.max(Integer::compare)
				.get();
		System.out.println("max="+max);
		
		//并行流
		int max2 = Stream.iterate(1, x->x+1)
				.limit(200).parallel()
				.peek(x->{
					System.out.println(Thread.currentThread().getName());
				})
				.max(Integer::compare)
				.get();
		System.out.println("max2="+max2);
		
		//并行改为串行
		int max3 = Stream.iterate(1, x->x+1)
				.limit(200)
				.parallel()
				.sequential()
				.peek(x->{
					System.out.println(Thread.currentThread().getName());
				})
				.max(Integer::compare)
				.get();
		System.out.println("max3="+max3);
			
	}
}
