package com.tgr.springbootmybatis.lambda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Demo1 {

	public static void main(String[] args) throws IOException {
		//过滤去重 filter distinct
		Arrays.asList(1,2,3,4,5,5,6,6).stream().distinct().filter(x ->{
			return x > 1;
		}).forEach((x)->{System.out.print(x);});
		
		Arrays.asList(1,2,3,4,5,5,6,6).stream().distinct().filter(x->{
			return x>1;
		}).forEach(System.out::println);
		
		//排序sorted
		System.out.println("22222222222222222222222222222222222222222222222");
		Arrays.asList(1,3,2,5,4,6).stream()
		.sorted((a,b) -> b-a)
		.forEach(System.out::print);
		
		System.out.println();
		
		List<Integer> list = Arrays.asList(1,3,2,5,4,6);
		list.sort(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o1-o2;
			}
		});
		Iterator<Integer> itr = list.iterator();
		while(itr.hasNext()) {
			System.out.print(itr.next());
		}
		
		
		//分页(截取limit shik)
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("3333333333333333333333333333333333333333333");
		//第二页
		List<Integer> list1 = Stream.iterate(1, x -> x+1)//迭代生成
				.limit(50)
				.sorted((a,b) -> b-a)
				.skip(10)//跳过前10个 第二页
				.limit(10)//只要十个
				.collect(Collectors.toList());//终止
		
		list1.stream().forEach((x)->{System.out.print(x+" ");});
		//第三页
		System.out.println();
		List<Integer> list2 = Stream.iterate(1, x -> x+1)
				.limit(50)
				.sorted((a,b) -> b-a)
				.skip(20)
				.limit(10)
				.collect(Collectors.toList());
		
		list2.stream().forEach((x)->{System.out.print(x+" ");});
		
		//转换   map/flatMap
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("4444444444444444444444444444444444444444444444");
		
		String str = "11,22,33,44,55";
		int sum1 = Stream.of(str.split(","))
				.map(x -> Integer.valueOf(x)) //里元素转换
				.mapToInt(x -> x)//转换成 int Stream   IntStream mapToInt()
				.sum();
		System.out.println(sum1);
		
		//也可以直接转换成 int Stream
		int sum2 = Stream.of(str.split(","))
				.mapToInt(x -> Integer.valueOf(x))
				.sum();
		System.out.println(sum2);
		
		//字符串转user对象
		String u = "老李,老王";
		Stream.of(u.split(","))
			.map(User::new).forEach(System.out::print);
		
		
		//peek 遍历的时候每个元素会调用consumer去消费
		System.out.println();
		System.out.println("5555555555555555555555555555555555555555555555555");
		String str3 = "2,3,4";
		int sum3 = Stream.of(str3.split(","))
				.peek((x) -> System.out.println("将要转换内部元素"+x) )
				.map(x -> Integer.valueOf(x))
				.peek((x) -> System.out.println("正在将stream 转换为 int stream "))
				.mapToInt(x -> x)
				.sum();
		System.out.println(sum3);
		
		//终止操作——————————终止操作——————————终止操作——————————终止操作——————————终止操作——————————终止操作——————————
		//sum min max count average sum
		System.out.println("66666666666666666666666666666666666666666666");
		double sum = Arrays.asList(1,2,3,4,5)
				.stream()
				.mapToDouble(x -> Integer.valueOf(x))
				.sum();
		System.out.println("sum="+sum);
		
		int min = Arrays.asList(1,2,3,4,5)
				.stream()
				.mapToInt(x -> x)
				.min().getAsInt();
		int min2 = Arrays.asList(1,2,3,4,5)
				.stream()
				.min((a,b)->a-b).get();
		System.out.println("min="+min);
		
		int max = Arrays.asList(1,2,3,4,5)
				.stream()
				.mapToInt(x->x)
				.max().getAsInt();
		int max2 = Arrays.asList(1,2,3,4,5)
				.stream()
				.max((a,b)->a-b).get();
		System.out.println("max="+max);
		
		int count = (int) Arrays.asList(1,2,3,4,5)
				.stream()
				.mapToInt(x -> x)
				.count();
		int count2 = (int) Arrays.asList(1,2,3,4,5)
				.stream()
				.count();
		System.out.println("count="+count);
		
		double average = Arrays.asList(1,2,3,4,5)
				.stream()
				.mapToInt(x->x)
				.average().getAsDouble();
		System.out.println("aversge="+average);
		
		//匹配: anyMatch allMatch noneMatch findFirst findAny
		boolean ba = Arrays.asList(1,2,3,4,5)
			.stream()
			.anyMatch(x -> x>3);
		System.out.println("ba="+ba);
		
		boolean bb = Arrays.asList(1,2,3,4,5)
				.stream()
				.allMatch(x -> x>1);
		System.out.println("bb="+bb);
		
		boolean bc = Arrays.asList(1,2,3,4,5)
				.stream()
				.noneMatch(x -> x>5);
		System.out.println("bc="+bc);
		
		int first = Arrays.asList(1,2,3,4,5)
				.stream()
				.findFirst()
				.get();
		System.out.println("first="+first);
		
		int any = Arrays.asList(1,2,3,4,5)
				.stream()
				.findAny()
				.get();
		System.out.println("any="+any);
		
		
		//汇聚reduce
		int reduce = Arrays.asList(1,2,3,4,9)//(((1+2)+3)+4)+9
			.stream()
			.reduce((a,b)->a+b).get();
		System.out.println("reduce="+reduce);
		Arrays.asList(1,2,3,4,5)
			.stream()
			.reduce((a,b) -> a+b).get();
			
		//收集器 Collector
		//找出1-50所有偶数存入list
		List<Integer> ll = Stream.iterate(1, x->x+1)
				.limit(50)
				.filter(x -> x%2==0)
				.collect(Collectors.toList());
		System.out.println(ll);
		
		//Stream 的创建
		//通过数组
		String[] arr = {"a","b","c"};
		Stream<String> stream = Stream.of(arr);
		//通过集合创建
		List<String> ll2 = Arrays.asList("1","2","3");
		Stream<Integer> stream2 = list.stream();
		
		//通过generate方法创建
		/*返回无限顺序无序流，其中每个元素

		*由提供的{@code supplier}生成。这很适合

		*生成恒定流、随机元素流等。*/
		Stream<Integer> stream3 = Stream.generate(()->1);
		//通过iterate 创建 无止境流 stream.limit来限制
		Stream<Integer> stream4 = Stream.iterate(1, x->x+1).limit(50);
		
		//其他api创建
		Stream fileStream = Files.lines(Paths.get("d:/1.txt"));
		fileStream.forEach(System.out::println);
	}
	
}
class User{
	String name;
	
	public User(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name+";";
	}
}
