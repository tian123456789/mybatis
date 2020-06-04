package com.tgr.springbootmybatis.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;


public class TestDownLoadVideo {

	public static void main(String[] args) {
		String s = "http://v.xiaohongshu.com/7f12fa54fa6e6950f556488358c56594b339dc7f?sign=58e2320363005318c40d6dd0aa3f139e&t=5d221700";
		String tx = "https://v.qq.com/b1f15df0-3d21-412c-9d10-8ad92675cc26";
		doDown(tx);
	}
	
	public static void doDown(String urlString) {
		
	    final int MAX_BUFFER_SIZE = 1000000;
	    HttpResponse response;
		HttpEntity entity; 
		RandomAccessFile  randomAccessFile = null;
		InputStream in = null;
		
		try {
			//1创建build
			HttpClient client = HttpClientBuilder.create().build();
			//2创建httpGet
			HttpGet get = new HttpGet(urlString);
			//构造代理主机
			//HttpHost proxyHost = new HttpHost("113.108.242.36", 47713);
			//设置请求配置的代理地址
			//RequestConfig reqConfig = RequestConfig.custom().setProxy(proxyHost).build();
			get.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
		    get.setHeader("Content-Type","application/json; charset=UTF-8");
			get.setHeader("Origin", "https://www.xiaohongshu.com");
			get.setHeader("Referer", "https://www.xiaohongshu.com/discovery/item/5d1f5ef300000000270135c9?xhsshare=CopyLink&appuid=5cca142e0000000017027925&apptime=1562380478");
			//get.setConfig(reqConfig);
			
			//3执行请求并获得相应
			response = client.execute(get);
			if(response.getStatusLine().getStatusCode() == 200) {
				//4获得响应实体
				entity = response.getEntity();
				int fileSize = (int) entity.getContentLength();
				in= entity.getContent();
				
				randomAccessFile = new RandomAccessFile("F:/video/7f12fa54fa6e6950f556488358c56594b339dc7f", "rw");
				int downloaded = 0;
				
				while(downloaded < fileSize) {
					byte[] buffer = null;
					if(fileSize - downloaded > MAX_BUFFER_SIZE) {
						buffer = new byte[MAX_BUFFER_SIZE];
					}else {
						buffer = new byte[(fileSize - downloaded)];
					}
					
					int read = -1;
					int currentDownLoad = 0;
					
					long startTime = System.currentTimeMillis();
					while(currentDownLoad < buffer.length) {
						read = in.read(buffer);
						buffer[currentDownLoad++] = (byte) read;
					}
					long endTime = System.currentTimeMillis();
					
					double speed = 0.0;
					if(endTime - startTime > 0) {
	                    speed = currentDownLoad / 1024.0 / ((double)(endTime - startTime)/1000);
	                }
					randomAccessFile.write(buffer);
					downloaded += currentDownLoad;
					randomAccessFile.seek(downloaded);
				    System.out.printf(
				    		"下载了进度:%.2f%%,下载速度：%.1fkb/s(%.1fM/s)%n",
				    		downloaded * 1.0 / fileSize * 10000 / 100,
				    		speed,speed/1000
				    		);
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				randomAccessFile.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
			
	}
	
public static void doDown2(String urlString) {
		
	    final int MAX_BUFFER_SIZE = 1000000;
	    HttpURLConnection connection = null;
        InputStream in = null;
        RandomAccessFile randomAccessFile = null;
		try {
			URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Range", "bytes=0-");
            connection.connect();
            if(connection.getResponseCode() / 100 != 2) {
                System.out.println("连接失败...");
                return;
            }
			
			if(connection.getResponseCode() == 200) {
				in = connection.getInputStream();
				int fileSize = connection.getContentLength();
				randomAccessFile = new RandomAccessFile("7f12fa54fa6e6950f556488358c56594b339dc7f", "rw");//"F:\\video"+fileName
				int downloaded = 0;
				while(downloaded < fileSize) {
					byte[] buffer = null;
					if(fileSize - downloaded > MAX_BUFFER_SIZE) {
						buffer = new byte[MAX_BUFFER_SIZE];
					}else {
						buffer = new byte[(fileSize - downloaded)];
					}
					
					int read = -1;
					int currentDownLoad = 0;
					long startTime = System.currentTimeMillis();
					while(currentDownLoad < buffer.length) {
						read = in.read(buffer);
						buffer[currentDownLoad++] = (byte) read;
					}
					long endTime = System.currentTimeMillis();
					double speed = 0.0;
					if(endTime - startTime > 0) {
	                    speed = currentDownLoad / 1024.0 / ((double)(endTime - startTime)/1000);
	                }
					randomAccessFile.write(buffer);
					downloaded += currentDownLoad;
					randomAccessFile.seek(downloaded);
				    System.out.printf(
				    		"下载了进度:%.2f%%,下载速度：%.1fkb/s(%.1fM/s)%n",
				    		downloaded * 1.0 / fileSize * 10000 / 100,
				    		speed,speed/1000
				    		);
	            
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
	}

	public static void testlinejie(String urlString) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Range", "bytes=0-");
			connection.connect();
			if(connection.getResponseCode() / 100 != 2) {
			    System.out.println("连接失败...");
			    return;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("all")
	public static void doDown3(String urlString) {
		
	    final int MAX_BUFFER_SIZE = 1000000;
		try {
			HttpResponse response;
			HttpEntity entity; 
			//1创建build
			HttpClient client = HttpClientBuilder.create().build();
			//2创建httpGet
			HttpGet get = new HttpGet(urlString);
			//构造代理主机
			//HttpHost proxyHost = new HttpHost("113.108.242.36", 47713);
			//设置请求配置的代理地址
			//RequestConfig reqConfig = RequestConfig.custom().setProxy(proxyHost).build();
			get.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
		    get.setHeader("Content-Type","application/json; charset=UTF-8");
			get.setHeader("Origin", "https://www.xiaohongshu.com");
			get.setHeader("Referer", "https://www.xiaohongshu.com/discovery/item/5d1f5ef300000000270135c9?xhsshare=CopyLink&appuid=5cca142e0000000017027925&apptime=1562380478");
			
			//3执行请求并获得相应
			response = client.execute(get);
			if(response.getStatusLine().getStatusCode() == 200) {
				
				entity = response.getEntity();
				byte[] bytes = new byte[1024];
				File file = new File("F:/video/7f12fa54fa6e6950f556488358c56594b339dc7f");
				InputStream in=  entity.getContent();
				OutputStream out = new FileOutputStream(file);
				
				int len = -1;
				while((len=in.read(bytes)) != -1) {
					out.write(bytes);
				}
				in.close();
				out.close();
				
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void go(String videoUrl ) {
		
		final int MAX_BUFFER_SIZE = 1000000; 
		HttpURLConnection connection = null;
        InputStream inputStream = null;
        RandomAccessFile randomAccessFile = null;
        try {
            // 1.获取连接对象
            URL url = new URL(videoUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Range", "bytes=0-");
            connection.connect();
            if(connection.getResponseCode() / 100 != 2) {
                System.out.println("连接失败...");
                return;
            }
            // 2.获取连接对象的流
            inputStream = connection.getInputStream();
            //已下载的大小
            int downloaded = 0;
            //总文件的大小
            int fileSize = connection.getContentLength();
            String fileName = url.getFile();
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
            // 3.把资源写入文件
            randomAccessFile = new RandomAccessFile(fileName,"rw");
            while(downloaded < fileSize) {
                // 3.1设置缓存流的大小
                byte[] buffer = null;
                if(fileSize - downloaded >= MAX_BUFFER_SIZE) {
                    buffer = new byte[MAX_BUFFER_SIZE];
                }else {
                    buffer = new byte[fileSize - downloaded];
                }
                // 3.2把每一次缓存的数据写入文件
                int read = -1;
                int currentDownload = 0;
                long startTime = System.currentTimeMillis();
                while(currentDownload < buffer.length) {
                    read = inputStream.read();
                    buffer[currentDownload ++] = (byte) read;
                }
                long endTime = System.currentTimeMillis();
                double speed = 0.0;
                if(endTime - startTime > 0) {
                    speed = currentDownload / 1024.0 / ((double)(endTime - startTime)/1000);
                }
                randomAccessFile.write(buffer);
                downloaded += currentDownload;
                randomAccessFile.seek(downloaded);
                System.out.printf("下载了进度:%.2f%%,下载速度：%.1fkb/s(%.1fM/s)%n",downloaded * 1.0 / fileSize * 10000 / 100,speed,speed/1000);
            }
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.disconnect();
                inputStream.close();
                randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
}
