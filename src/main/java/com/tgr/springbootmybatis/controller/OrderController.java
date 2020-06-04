package com.tgr.springbootmybatis.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tgr.springbootmybatis.entity.Order;
import com.tgr.springbootmybatis.service.OrderService;

@RestController
public class OrderController {

	@Resource
	private OrderService orderService;
	
	@RequestMapping("order/insert")
	public int insertOrderData() {
		Order order = new Order();
		order.setGoodsId("123456789");
		order.setOrderStatus("01");
		order.setUserId(1008);
		int count = orderService.insertOrderData(order);
		System.out.println(order.getOrderId());
		return count;
	}
}
