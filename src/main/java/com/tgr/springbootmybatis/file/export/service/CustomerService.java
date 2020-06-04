package com.tgr.springbootmybatis.file.export.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import com.tgr.springbootmybatis.controller.ResponseResult;
import com.tgr.springbootmybatis.file.export.bean.Customer;

import java.math.BigInteger;
import java.util.List;

public interface CustomerService {

	public List<Customer> customers(long shop_id);

	public ResponseResult save(long shop_id, String name, String kva_code, String distribution_num_code, String type_code, //
                               boolean has_senior, boolean is_old_custom,
                               @RequestParam(name = "addrs", required = false) List<String> addrs, //
                               String admin_name, String admin_phone, String job_code, //
                               String license);
			//@RequestParam(name = "license", required = false) MultipartFile multipartFile
	
	/*public User admin(Customer customer);*/

	/**
	 * 繁琐，不建议使用
	 * @param id
	 * @param name
	 * @param kva_code
	 * @param distribution_num_code
	 * @param type_code
	 * @param has_senior
	 * @param is_old_custom
	 * @param license
	 * @param addrs
	 * @return
	 */
	@Deprecated
	public Customer put(long id, String name, String kva_code, String distribution_num_code, String type_code,
			boolean has_senior, boolean is_old_custom, String license,List<String> addrs);

	/**
	 * 新增企业信息
	 * @param customer
	 * @return
	 */
	ResponseResult addCustomer(Customer customer);

	/**
	 * 修改企业信息
	 * @param customer
	 * @return
	 */
	ResponseResult modifyCustomer(Customer customer);

	public Customer audit(long id, String reserve ,String audit_code);

	public void delete(Long id);

	public List<Customer> findByShopIdAndByNameLike(Long shopId, String name);

	public Customer findById(Long customerId);

	/*public Page<Customer> findAll(Specification<Customer> specification, Pageable pageRequest);*/

	public List<Customer> findByIdsAndLikeUserName(List<Long> customerIds, String userName, int p, int ps);

	public List<BigInteger> findByUserNameLike(String string);


    ResponseResult<Integer> countNewCustomer(Long shopId);

    Customer findByCustomerName(String customerName);
}
