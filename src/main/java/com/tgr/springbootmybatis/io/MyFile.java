package com.tgr.springbootmybatis.io;

import java.io.File;

public class MyFile {

	public static void main(String[] args) {
		method1();
	}

	private static void method1() {
		File file = new File("F:\\myJava\\word.txt");
		System.err.println(file.separator);
		System.err.println(File.separator);
		System.err.println(File.separatorChar);
		System.err.println(File.pathSeparator);
		System.err.println(File.pathSeparatorChar);
	}
}
