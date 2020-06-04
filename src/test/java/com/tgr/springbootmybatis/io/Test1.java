package com.tgr.springbootmybatis.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import org.codehaus.groovy.runtime.StringBufferWriter;
import org.junit.Test;

import io.netty.util.CharsetUtil;

public class Test1 {

	@Test
	public void test() throws IOException {
		InputStream in = new FileInputStream("G://javaFile/1.txt");
		
		if(in.available() > 0) {
			byte[] data = new byte[in.available()];
			int i = in.read(data);
			
			System.err.println(data.toString());
			
			Stream.of(data).forEach(x->{
				System.err.println(x);
			});
		}
		
		in.close();
		
	}
	
	@Test
	public void test2_mark() throws IOException {
		
		InputStream in = new FileInputStream("G://javaFile/1.txt");
		OutputStream out = new FileOutputStream("G://javaFile/2.txt");
		
		BufferedInputStream reader = new BufferedInputStream(in);
		BufferedOutputStream writer = new BufferedOutputStream(out);
		
		
		int flg = -1;
		byte[] data = new byte[1024*1024];
		while((flg = reader.read(data)) != -1) {
			writer.write(data, 0, flg);
			writer.flush();//刷进文件
		}
		
		
		/*
		 *  mark/reset not supported
			int flg = 0;
			byte[] data = new byte[5];
			while(flg != -1) {
				in.reset();
				flg = in.read(data,0, 4);
				in.mark(flg);
		}*/
		
		in.close();
		out.close();
		reader.close();
		writer.close();
		
	}
	
	
	@Test
	public void test2() throws IOException {
		
		InputStream in = new FileInputStream("G://javaFile/1.txt");
		OutputStream out = new FileOutputStream("G://javaFile/2.txt");
		
		BufferedInputStream reader = new BufferedInputStream(in);
		BufferedOutputStream writer = new BufferedOutputStream(out);
		
		
		int flg = -1;
		byte[] data = new byte[2];
		while((flg = reader.read(data)) != -1) {
			writer.write(data, 0, flg);//
		}
		
		writer.flush();//如果最后还没满 没导致自动flushBuff 那么手动刷进out
		
		in.close();
		out.close();
		reader.close();
		writer.close();
		
	}
	
	@Test
	public void test3() throws IOException {
		
		InputStreamReader in = new InputStreamReader(new FileInputStream("G://javaFile/1.txt") , CharsetUtil.UTF_8);
		BufferedReader reader = new BufferedReader(in);
				
		int flg = -1;
		int i = 0;
		
		String read;
		while((read = reader.readLine()) != null) {//(flg = reader.read()) != -1
			
			if(++i == 2) {
				reader.skip(3L);//跳过3个字符
			}
			
			System.err.println(read);
			
		}
		in.close();
		
	}
	
	
	
	@Test
	public void test6() throws IOException {//文本格式 还不用写/n /t 直接回车 tab就行
		Reader reader = new InputStreamReader(System.in,StandardCharsets.UTF_8);
		PrintWriter writer = new PrintWriter(new FileOutputStream("G:\\javaFile\\writer.txt"),true);//自动刷新 默认自带缓冲区
		while(true) {
			int i = -1;
			char[] chars = new char[1024];
			if((i=reader.read(chars)) != -1) {
				/*writer.write(chars, 0, i);
				writer.flush();*/
				//writer.print(chars);
				char[] toChars = Arrays.copyOf(chars, i);
				writer.println(toChars);
				//writer.flush(); 文本打印流 自带刷新 默认情况下
			}
		}
	}
	
	@Test
	public void test7() throws IOException {//文本格式 还不用写/n /t 直接回车 tab就行
		
		Reader in = new InputStreamReader(System.in);
		PrintStream out = new PrintStream(new FileOutputStream("G:\\javaFile\\writer.txt"));
		
		while(true) {
			int i = -1;
			char[] chars = new char[1024];
			if((i=in.read(chars)) != -1) {
				/*writer.write(chars, 0, i);
				writer.flush();*/
				//writer.print(chars);
				char[] toChars = Arrays.copyOf(chars, i);
				out.println(toChars);
				//writer.flush(); 文本打印流 自带刷新 默认情况下
				System.out.println(); //利用了PrintStream
				System.err.println();
			}
		}
	}
	
	@Test
	public void test8() throws IOException {//nio Files 代替简化了 BufferedReader读取文件到字符串
		String content = new String(
				Files.readAllBytes(new File("G:\\javaFile\\1.txt").toPath())
				,
				StandardCharsets.UTF_8);
		System.err.println(content);
		
		List<String> lines = Files.readAllLines(new File("G:\\javaFile\\1.txt").toPath());
	}
	
	@Test
	public void test9() {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			if(scanner.hasNext()) {
				System.err.println(scanner.next());
			}
		}
	}
	
	@Test
	public void test10() throws IOException {
		FileInputStream in = new FileInputStream("G:\\javaFile\\1.dat");
		DataInputStream dataIn = new DataInputStream(in);
		
		
		Integer i = -1;
		while(i != null) {
			try {
				i = dataIn.readInt();
			} finally {
				System.err.println(i.intValue());
			}
		}
		
		in.close();
		dataIn.close();
	}
	
	@Test
	public void test11() throws IOException {
		System.err.println(Integer.toBinaryString(7));//0111 2^(n-1) 1+2+4
	}
	
	
}
