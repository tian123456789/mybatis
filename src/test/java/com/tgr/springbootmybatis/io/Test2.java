package com.tgr.springbootmybatis.io;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.util.stream.Stream;

import org.junit.Test;

import com.fasterxml.jackson.databind.util.ArrayBuilders.ByteBuilder;

public class Test2 {

	@Test
	public void test1() throws IOException {
		
		File file = new File("G:\\javaFile\\AccessFileR.txt");
		long capcity = file.length();//字节
		
		
		
		//应该没有缓冲区
		RandomAccessFile in = new RandomAccessFile(file , "r");
		RandomAccessFile inOut = new RandomAccessFile("G:\\\\javaFile\\\\AccessFileW.txt", "rw");
		
		long seek = 0;
		long len = in.length();
		System.err.println(len);
		in.seek(seek);
		
		while(true) {
			try {
				inOut.writeByte(in.readByte());
				long position = in.getFilePointer();
				
				BigDecimal a = new BigDecimal(position);
				BigDecimal b = new BigDecimal(len);
				System.err.println("------  "+a.divide(b, 3,RoundingMode.HALF_UP).multiply(new BigDecimal(100))+"%  ------");
			} catch (EOFException e) {
				
				System.err.println("已经到文件末尾");
				in.close();
				inOut.close();
				break;
			}finally {
			}
		}
		
	}
	
	@Test
	public void test2() throws IOException {
		File file = new File("G:\\javaFile\\AccessFileR.txt");
		
		//应该没有缓冲区
		RandomAccessFile in = new RandomAccessFile(file , "r");
		RandomAccessFile inOut = new RandomAccessFile("G:\\\\javaFile\\\\AccessFileW.txt", "rw");
		
		long seek = 0;
		long len = in.length();
		System.err.println(len);
		
		byte[] bytes = new byte[13];
		int flg = 0 ;
		while((flg = in.read(bytes)) != -1) {
			inOut.write(bytes,0,flg);
			long position = in.getFilePointer();
			
			BigDecimal a = new BigDecimal(position);
			BigDecimal b = new BigDecimal(len);
			System.err.println("------  "+a.divide(b, 3,RoundingMode.HALF_UP).multiply(new BigDecimal(100))+"%  ------");
		}
		
		in.close();
		inOut.close();
		
	}
	
	@Test
	public void test2_2() throws IOException {
		File file = new File("G:\\javaFile\\AccessFileR.txt");
		
		//应该没有缓冲区
		RandomAccessFile in = new RandomAccessFile(file , "r");
		RandomAccessFile inOut = new RandomAccessFile("G:\\\\javaFile\\\\AccessFileW.txt", "rw");
		
		long seek = 0;
		long len = in.length();
		int count = 0;
		System.err.println(len);
		
		byte[] bytes = new byte[13];
		//ByteBuffer.allocate(capacity)//堆内存 提现不出来
		//ByteBuffer.allocateDirect(capacity)//直接内存 提现不出来
		
		int flg = 0 ;
		while((flg = in.read(bytes)) != -1) {
			inOut.write(bytes,0,flg);
			long position = in.getFilePointer();
			
			BigDecimal a = new BigDecimal(position);
			BigDecimal b = new BigDecimal(len);
			count++;
			System.err.println("------  "+a.divide(b, 3,RoundingMode.HALF_UP).multiply(new BigDecimal(100))+"%  ------");
		}
		System.err.println("总计: "+count+"次");
		in.close();
		inOut.close();
		
	}
	
	/**
	 * 展示下载进度
	 */
	public void showSpeedOfProgress() {
		System.err.println();
	}
	
	@Test
	public void test() {
		Stream.iterate(1, x->x+1).limit(100).forEach(x->{
			System.err.println(x);
		});
	}
}
