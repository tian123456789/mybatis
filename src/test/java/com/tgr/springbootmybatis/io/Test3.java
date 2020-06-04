package com.tgr.springbootmybatis.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.junit.Test;

public class Test3 {

	@Test
	public void test1() throws IOException {
		
		File srcFile = new File("G:\\\\javaFile\\\\1.txt");
		
		File outFile = new File("G:\\\\javaFile\\\\1.zip");
		if(srcFile.exists()) {
		}else{
			srcFile.createNewFile();
		}
		
		FileInputStream in = new FileInputStream(srcFile);
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(outFile));
		
		
		
		zipOut.putNextEntry(new ZipEntry(srcFile.getName()));
		
		int len;
		byte[] bytes = new byte[1024];
		while((len = in.read(bytes)) != -1) {
			zipOut.write(bytes, 0, len);
		}
		
		zipOut.closeEntry();
		
		in.close();
		zipOut.close();
		
	}
	
	@Test
	public void test2() throws IOException {
		ZipInputStream in = new ZipInputStream(new FileInputStream("G:\\\\\\\\javaFile\\\\\\\\1.zip"));
		
		File outFile = new File("G:\\\\\\\\\\\\\\\\javaFile\\\\\\\\\\\\\\\\2.zip");
		if(!outFile.exists()) {
			outFile.createNewFile();
		}
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFile));
		ZipEntry zipEntry;
		while((zipEntry = in.getNextEntry()) != null) {
			out.putNextEntry(zipEntry);
			out.closeEntry();
		}
		
		in.close();
		out.close();
	}
}
