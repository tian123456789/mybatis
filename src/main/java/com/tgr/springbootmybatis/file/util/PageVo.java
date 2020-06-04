package com.tgr.springbootmybatis.file.util;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import java.util.ArrayList;
import java.util.List;

public class PageVo {
	public static final String ORDER_DIRECTION_ASC = "ASC";
	public static final String ORDER_DIRECTION_DESC = "DESC";
	public static final int DEFAULT_PAGE_SIZE = 10;

	protected int page = 0;
	protected int size = 10;

	private List<Order> orderFields = new ArrayList<Order>();

	private int totalPage = 1;

	private int prePage = 1;

	private int nextPage = 1;

	protected long totalCount = 0L;

	public PageVo(){}

	public PageVo(int page,int size){
		this.page = page;
		this.size = size;
	}

	public int getPage() {
		return this.page;
	}

	public void setPage(int pageNum) {
		this.page = pageNum;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int numPerPage) {
		this.size = ((numPerPage > 0) ? numPerPage : 10);
	}

	public List<Order> getOrderFields() {
		return orderFields;
	}

	public void setOrderFields(List<Order> orderFields) {
		this.orderFields = orderFields;
	}
	
	/**
	 * 正向排序，先加入的优先
	 * @param property
	 */
	public void orderByAsc(String property){
		orderFields.add(new Order(Direction.ASC, property));
	}
	
	/**
	 * 反向排序，先加入的优先
	 * @param property
	 */
	public void orderByDesc(String property){
		orderFields.add(new Order(Direction.DESC, property));
	}

	public int getTotalPage() {
		return this.totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;

	}

	public int getPrePage() {
		this.prePage = (this.page - 1);
		if (this.prePage < 1) {
			this.prePage = 1;
		}
		return this.prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		this.nextPage = (this.page + 1);
		if (this.nextPage > this.totalPage) {
			this.nextPage = this.totalPage;
		}

		return this.nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public long getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
		this.totalPage = ((int) (totalCount - 1L) / this.size + 1);
	}

}
