package com.tgr.springbootmybatis.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

public class Test5 {
	
	@Test
	public void test1() {
		Path path = Paths.get("G:\\javaFile\\");
		FileSystem fileSystem = path.getFileSystem();
		Iterable<FileStore> fileStores = fileSystem.getFileStores();
		
		File file = path.toFile();
		File[] files = file.listFiles();
		Stream.of(files).forEach(x->{
			digui(x);
		});
	}
	
	public void digui(File file) {
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			Stream.of(files).forEach(x->{
				digui(x);
			});
		}else if(file.isFile()) {
			
			if(file.getName().equals("mulu1Rename.txt")) {
				
				//file.renameTo(new File(file.getParentFile().getPath()+"\\"+"mulu1Rename.txt"));
				file.renameTo(Paths.get(file.getPath()).resolveSibling("吼吼吼.txt").toFile());
				System.err.println("getPath: "+ file.getPath());
				System.err.println("getAbsolutePath: "+file.getAbsolutePath());
				System.err.println("parentPath: "+file.getParentFile().getPath());
			}
			
			System.err.println(file.getName());
		}
	}
	
	@Test
	public void test2() throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get("G:\\javaFile\\1.txt"));
		Files.write(Paths.get("G:\\javaFile\\1.txt"), bytes, StandardOpenOption.APPEND);
		byte[] bytes2 = Files.readAllBytes(Paths.get("G:\\javaFile\\1.txt"));
		System.err.println(bytes2.length);
		System.err.println(bytes2.toString());

		String content = new String(bytes, StandardCharsets.UTF_8);
		//System.err.println(content);
		
		List<String> lines = Files.readAllLines(Paths.get("G:\\javaFile\\1.txt"), StandardCharsets.UTF_8);
		lines.forEach(x->{System.err.println(x);});
	} 

	
}
