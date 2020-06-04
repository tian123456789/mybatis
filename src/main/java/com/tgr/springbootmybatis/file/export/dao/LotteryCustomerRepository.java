package com.tgr.springbootmybatis.file.export.dao;

import org.springframework.stereotype.Component;

import com.tgr.springbootmybatis.file.export.bean.Customer;
import com.tgr.springbootmybatis.file.export.bean.LotteryCustomer;


public interface LotteryCustomerRepository {

    /*@Query(value = "from LotteryCustomer a where a.customer = ?1 and a.isDeleted = 0")*/
    LotteryCustomer findByCustomer(Customer customer);
}
