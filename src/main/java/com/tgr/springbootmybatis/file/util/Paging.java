package com.tgr.springbootmybatis.file.util;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Pageable;

public class Paging<T> implements Serializable{

	private static final long serialVersionUID = 4524105252838390133L;

	private List<T> content;
	
	private String code;
	
	private Pageable pageable;

	private int totalPages;
	
	private long totalElements;
	
	private int size;
	
	private int numberOfElements;

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}
	
	
}
