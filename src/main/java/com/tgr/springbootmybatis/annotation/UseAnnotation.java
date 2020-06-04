package com.tgr.springbootmybatis.annotation;

public class UseAnnotation {

	@MyAnnotation(value = "111")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
