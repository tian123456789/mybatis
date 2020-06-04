package com.tgr.springbootmybatis.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tgr.springbootmybatis.entity.Order;
/*import com.tgr.springbootmybatis.mapper.OrderMapper;*/

@Service
public class OrderService {

	/*@Resource
	private OrderMapper orderMapper;*/
	
	public int insertOrderData(Order order) {
		return 0;//orderMapper.insertOrderData(order);
	}
}
