package com.tgr.springbootmybatis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;
import org.springframework.util.ResourceUtils;

public class TestFile {
	
	@Test
	public void test() throws FileNotFoundException {
		File file = ResourceUtils.getFile("classpath:springboot-mybatis.log");
		new FileInputStream(file);
		
		this.getClass().getResourceAsStream("springboot-mybatis.log");
		
		System.err.println(file.exists());
	}

	
}
