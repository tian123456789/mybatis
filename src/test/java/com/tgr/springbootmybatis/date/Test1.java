package com.tgr.springbootmybatis.date;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import javax.sound.midi.SysexMessage;

import org.junit.Test;

public class Test1 {

	@Test
	public void test() {
		LocalDate localDate1 = LocalDate.now();
		System.err.println(localDate1);
		System.err.println("今天星期几: "+localDate1.getDayOfWeek());//WEDNESDAY

		LocalDate localDate2 = LocalDate.of(2020, 1, 15);
		localDate2 = localDate2.plusDays(3);//+3day
		localDate2 = localDate2.minusDays(2);//-2day
		//localDate2.plus(Duration.ofDays(1));
		
		
		
		Period period = localDate2.until(localDate1);
		System.err.println("年月日算-相差多少月: "+period.getMonths()+"  相差多少天: "+period.getDays());
		
		Long haveDays = localDate2.until(localDate1,ChronoUnit.DAYS);
		System.err.println("按日算总共相差多少天: "+haveDays);
		
		
		System.err.println(localDate2);
		System.err.println("是否为闰年: "+localDate2.isLeapYear());
		System.err.println("是否在date1后: "+localDate2.isAfter(localDate1));
	}
	
	/**
	 * 日期调整器
	 */
	@Test
	public void test2() {
		LocalDate localDate = LocalDate.of(2000, 1, 1).with(
				TemporalAdjusters.lastDayOfMonth()//改到当前月的最后一天
				);
		
		LocalDate localDate2 = LocalDate.of(2000, 1, 15).with(
				TemporalAdjusters.firstDayOfNextYear()//改到下一年 的第一天
				);//2001-01-01
		
		LocalDate localDate3 = LocalDate.of(2000, 1, 15).withYear(1996);//改到1996年
		System.err.println(localDate3);
	}
	
	@Test
	public void test3() {
		
		//@FunctionalInterface
		//TemporalAdjuster
		//with(TemporalAdjuster adjuster) with里是一个函数式接口TemporalAdjuster
		
		LocalDate localdate = LocalDate.of(2020, 4, 13).with(//利用多态
				
				 //相当于TemporalAdjuster的匿名内部类里 Temporal adjustInto(Temporal temporal);方法的实现
				 w ->{
					LocalDate result = (LocalDate)w;
					do {
						result = result.plusDays(1);
						System.err.println("加一天");
					}while(result.getDayOfWeek().getValue() >= 6);
					//如果加到本周倒数第6天  2020-04-14
					return result; 
				}
				
		);
		System.err.println(localdate);
		
		
	}
	
	TemporalAdjuster obj = w ->{
		LocalDate result = (LocalDate)w;
		do {
			result = result.plusDays(1);
			System.err.println("加一天");
		}while(result.getDayOfWeek().getValue() >= 6);
		//如果加到本周倒数第6天  2020-04-14
		return result; 
	};
	
	/*
	@FunctionalInterface
	public interface UnaryOperator<T> extends Function<T, T> {
		static <T> UnaryOperator<T> identity() {
	        return t -> t; //只要返回T类型对象就行
	    }
	}
	@FunctionalInterface
	public interface Function<T, R> {
		
		Applies this function to the given argument.
		R apply(T t);
	}
	*/
	TemporalAdjuster obj2 = TemporalAdjusters.ofDateAdjuster(w->{
		LocalDate result = w;
		do {
			result = result.plusDays(1);
			System.err.println("加一天");
		}while(result.getDayOfWeek().getValue() >= 6);
		//如果加到本周倒数第6天  2020-04-14
		return result; 
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
