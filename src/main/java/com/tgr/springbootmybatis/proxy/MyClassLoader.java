package com.tgr.springbootmybatis.proxy;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;

public class MyClassLoader {

	public static void main(String[] args) throws IOException {
		CustomClassLoader loader = new MyClassLoader().new CustomClassLoader();
		Class<?> clazz = loader.findClass("LiuDeHua");//com.tgr.springbootmybatis.proxy.
		try {
            Object obj = clazz.newInstance();
            Method method = clazz.getMethod("dance");
            method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public class CustomClassLoader extends ClassLoader {
	    @Override
	    protected Class<?> findClass(String name) {
	        String myPath = "file:///E://workspace/tgr/springboot-mybatis/LiuDeHua"+ ".class";
	        System.out.println(myPath);
	        byte[] cLassBytes = null;
	        Path path = null;
	        try {
	            path = Paths.get(new URI(myPath));
	            //FileSystemProvider fileSystemProvider = path.getFileSystem().provider();
	            cLassBytes = Files.readAllBytes(path);
	        } catch (IOException  | URISyntaxException e) {
	            e.printStackTrace();
	        }
	        @SuppressWarnings("rawtypes")
			Class clazz = defineClass(name, cLassBytes, 0, cLassBytes.length);
	        return clazz;
	    }
	}
	
	public static void testGetPath() throws IOException {
		System.out.println("path4:" + System.getProperty("user.dir"));
		// E:\workspace\tgr\springboot-mybatis
		
		System.out.println(MyClassLoader.class.getResource("/").getPath());
		// /E:/workspace/tgr/springboot-mybatis/target/classes/
		
		System.out.println(MyClassLoader.class.getResource("").getPath());
		// /E:/workspace/tgr/springboot-mybatis/target/classes/com/tgr/springbootmybatis/proxy/
		
		File directory = new File("");// 参数为空
		String courseFile = directory.getCanonicalPath();
		System.out.println("path2: " + courseFile);
		// E:\workspace\tgr\springboot-mybatis
		
		URL xmlpath = MyClassLoader.class.getClassLoader().getResource("");
		System.out.println("path3: " + xmlpath);
		// file:/E:/workspace/tgr/springboot-mybatis/target/classes/
		
		System.out.println("path5: " + System.getProperty("java.class.path").split(";")[0]);
		// E:\workspace\tgr\springboot-mybatis\target\classes
	}
	

	/*public class CustomClassLoader extends ClassLoader {
	    @Override
	    protected Class<?> findClass(String name) {
	        File file = new File("C:\\Users\\tgr\\Desktop\\"+name.replace(".","\\") + ".class");
	        byte[] cLassBytes = null;
	        Path path = null;
	        try {
	        	path = file.toPath();
	            cLassBytes = Files.readAllBytes(path);
	        } catch (IOException e | URISyntaxException e) {
	            e.printStackTrace();
	        }
	        Class clazz = defineClass(name, cLassBytes, 0, cLassBytes.length);
	        return clazz;
	    }
	}*/
	
}
