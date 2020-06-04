package com.tgr.springbootmybatis.threadpool_socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TimeServerHandler extends Thread{

	private Socket socket;
	
	private boolean responseFlg = false;
	
	public TimeServerHandler(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		System.err.println("Handler ::: run");
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(),true);
			String body = null;
			while((body = in.readLine()) != null && body.length() != 0 && !responseFlg) {
				out.println("TimeHandler处理请求 返回数据:" +new Date().toString());
				out.close();
				in.close();
				responseFlg = true;
			}
			System.err.println("RUN--StOP  RUN--StOP  RUN--StOP   RUN--StOP  RUN--StOP");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(in != null) {
				try {
					in.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			
			if(out != null) {
				try {
					out.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
}
