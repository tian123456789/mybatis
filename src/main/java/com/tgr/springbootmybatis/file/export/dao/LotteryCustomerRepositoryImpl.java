package com.tgr.springbootmybatis.file.export.dao;

import org.springframework.stereotype.Service;

import com.tgr.springbootmybatis.file.export.bean.Customer;
import com.tgr.springbootmybatis.file.export.bean.LotteryCustomer;

@Service
public class LotteryCustomerRepositoryImpl implements LotteryCustomerRepository {

	@Override
	public LotteryCustomer findByCustomer(Customer customer) {
		return null;
	}

}
