package com.tgr.springbootmybatis.threadpool_socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.stream.Stream;

public class BioClient {

	private static final int PORT = 17001;
	private static final String HOST = "127.0.0.1";
	
	public static void send() {
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			socket = new Socket(HOST, PORT);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(),true);
			out.println("I am Client");
			String resp = null;
			while((resp = in.readLine()) != null && resp.length() != 0) {
				System.out.println("client接收server返回的数据: "+resp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
		}
	}
	
	public static void main(String[] args) {
		
		for(int i = 0 ; i < 50 ; i++) {
			new Thread() {
				public void run() {
					send();
				};
			}.start();
		}
		
	}
}
