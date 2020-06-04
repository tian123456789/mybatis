package com.tgr.springbootmybatis.shell_compile_annotation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.junit.Test;

public class TestJavaCompile {

	@Test
	public void test() throws FileNotFoundException {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		//OutputStream classOutStream = new FileOutputStream("C:\\\\Users\\\\tgr\\\\Desktop\\\\test\\\\");
		OutputStream outStream = System.out;
		OutputStream errOutStream = System.err;
		InputStream inputStream = new FileInputStream("C:\\Users\\tgr\\Desktop\\桌面\\test\\A.java");
		int result = compiler.run(null,outStream, errOutStream, "-sourcepath","C:\\\\Users\\\\tgr\\\\Desktop\\\\桌面\\\\test\\","C:\\\\Users\\\\tgr\\\\Desktop\\\\桌面\\\\test\\\\A.java");
		System.err.println(result);
	}
}
