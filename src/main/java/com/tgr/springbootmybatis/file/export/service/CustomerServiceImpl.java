package com.tgr.springbootmybatis.file.export.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tgr.springbootmybatis.controller.ResponseResult;
import com.tgr.springbootmybatis.file.export.bean.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Override
	public List<Customer> customers(long shop_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseResult save(long shop_id, String name, String kva_code, String distribution_num_code,
			String type_code, boolean has_senior, boolean is_old_custom, List<String> addrs, String admin_name,
			String admin_phone, String job_code, String license) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer put(long id, String name, String kva_code, String distribution_num_code, String type_code,
			boolean has_senior, boolean is_old_custom, String license, List<String> addrs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseResult addCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseResult modifyCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer audit(long id, String reserve, String audit_code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Customer> findByShopIdAndByNameLike(Long shopId, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer findById(Long customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> findByIdsAndLikeUserName(List<Long> customerIds, String userName, int p, int ps) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BigInteger> findByUserNameLike(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseResult<Integer> countNewCustomer(Long shopId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer findByCustomerName(String customerName) {
		// TODO Auto-generated method stub
		return null;
	}

}
