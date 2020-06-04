package com.tgr.springbootmybatis.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.assertj.core.util.Arrays;

public class Test {

	public static void main(String[] args) {
		String s = "abcdefg";
		/*Stream
			.of(s.toCharArray())
			.forEach(x->{
				System.out.println(s);
			});*/
		/*char[] c =s.toCharArray();
		System.out.println(c);*/
		
		/*for(int i = 2; i>=0 ; i--)
		{
			if(i==1) {
				break;
			}
			System.out.println(i);
		}*/
	/*	System.out.println(Math.abs(-3-7));*/
		/*List<Data> list = new LinkedList<Test.Data>();
		Data u1 = new Test().new Data("a",1);
		Data u2 = new Test().new Data("b",2);
		list.add(u1);
		list.add(u2);
		list.add(u1);
		list.add(u2);
		list.stream().forEach(x->System.out.println(x.toString()));
		System.out.println(list.get(3).toString());*/
		
		List<Data> list = new ArrayList<Test.Data>();
		Data u1 = new Test().new Data("a",1);
		Data u2 = new Test().new Data("b",2);
		list.add(u1);
		list.add(u2);
		list.add(u1);
		list.add(u2);
		list.stream().forEach(x->System.out.println(x.toString()));
		System.out.println(list.get(3).toString());
	}
	
	class Data{
		String name;
		int age;
		
		public Data(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		
		@Override
		public String toString() {
			return "name="+name+";age="+age;
		}
		
	}
}
