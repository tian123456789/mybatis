package com.tgr.springbootmybatis.entity;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String order_id;

    private Integer user_id;

    private String order_status;

    private String goods_id;

    private Date create_date_time;

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return order_id;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.order_id = orderId;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return user_id;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.user_id = userId;
	}

	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return order_status;
	}

	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.order_status = orderStatus;
	}

	/**
	 * @return the goodsId
	 */
	public String getGoodsId() {
		return goods_id;
	}

	/**
	 * @param goodsId the goodsId to set
	 */
	public void setGoodsId(String goodsId) {
		this.goods_id = goodsId;
	}

	/**
	 * @return the createDateTime
	 */
	public Date getCreateDateTime() {
		return create_date_time;
	}

	/**
	 * @param createDateTime the createDateTime to set
	 */
	public void setCreateDateTime(Date createDateTime) {
		this.create_date_time = createDateTime;
	}

	public String toString() {
		StringBuilder strB = new StringBuilder();
		strB.append("[")
			.append(this.order_id).append(", ")
			.append(this.user_id).append(", ")
			.append(this.order_status).append(", ")
			.append(this.goods_id).append(", ")
			.append(this.create_date_time)
			.append("]");
		return strB.toString();
	}

}
