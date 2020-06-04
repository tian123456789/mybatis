package com.tgr.springbootmybatis.threadLocal;

public class Connection {
	
	public static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

	private int flg;
	
	public Connection(int flg) {
		super();
		this.flg = flg;
	}
	

	public Connection() {
		super();
	}

	public int getFlg() {
		return flg;
	}

	public void setFlg(int flg) {
		this.flg = flg;
	}
	
}
