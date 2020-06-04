package com.tgr.springbootmybatis.proxy;

public class LiuDeHua implements Star{
	
	/*public LiuDeHua() {
	}*/
	
	@Override
	public String sing() {
		System.out.println("给我一杯忘情水");
		return "演唱结束";
	}

	@Override
	public String dance() {
		System.out.println("开心的马骝");
		return "跳完";
	}
}
