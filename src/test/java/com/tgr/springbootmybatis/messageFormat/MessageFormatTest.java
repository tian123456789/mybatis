package com.tgr.springbootmybatis.messageFormat;

import java.text.MessageFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.Test;

public class MessageFormatTest {

	@Test
	public void test1() {
		String msg = MessageFormat.format("0{0}1{1}2{2}", "A",1,"C");
		System.err.println(msg);
	}
	
	@Test
	public void test2() {
		String msg = MessageFormat.format(
				"日期{0,date,long} 货币符{1,number,currency}",new GregorianCalendar(2011, 11, 11).getTime(),100L);
		System.err.println(msg);
	}
	
	@Test
	public void test3() {
		MessageFormat format = new MessageFormat("日期{0,date,long} 货币符{1,number,currency}", Locale.CHINA);
		String msg = format.format(new Object[] {new GregorianCalendar(2011, 11, 11).getTime(),100L});
		System.err.println(msg);
	}
	
	public static void main(String[] args) {
		
	}
	
	
	
}
