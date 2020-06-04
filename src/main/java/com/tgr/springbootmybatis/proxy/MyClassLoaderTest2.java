package com.tgr.springbootmybatis.proxy;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class MyClassLoaderTest2 {

	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		String rootDir = "file:E:\\workspace\\";
		URLClassLoader loader = new URLClassLoader(new URL[] {new URL(rootDir)});
		Class<MyTest> clazz = (Class<MyTest>) loader
				.loadClass("com.tgr.springbootmybatis.proxy.MyTest");
		clazz.newInstance().say2();
	}
}
