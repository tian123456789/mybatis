package com.tgr.springbootmybatis.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import org.junit.Test;

import com.tgr.springbootmybatis.entity.Video;

public class Test4 {

	@Test
	public void test1() throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("G:\\javaFile\\object.txt"));
		
		Video video = new Video();
		video.setActor("张三");
		video.setArea("地球");
		video.setId(1);
		out.writeObject(video);
		out.close();
	}
	
	@Test
	public void test2() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream objOut = new ObjectOutputStream(out);
		
		Video video = new Video();
		video.setActor("张三2");
		video.setArea("地球2");
		video.setId(12);
		
		objOut.writeObject(video);
		
		ObjectInputStream objIn = 
				new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));
		Video cloneVideo = (Video) objIn.readObject();
		
		System.err.println("cloneObject: "+cloneVideo.toString());
		
	}
}
