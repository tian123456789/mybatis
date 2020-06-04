package com.tgr.springbootmybatis.file.export.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * 
 * 企业客户
 *
 */
/*@Entity
@Table(name = "customer")*/
public class Customer/* extends BaseEntity implements Serializable */{

	private static final long serialVersionUID = -5738203110459369477L;

	/*@Column(name = "name", nullable = false)*/
	private String name;

	/*@ManyToOne
	@JoinColumn(name = "shop_id")
	private Shop shop;

	@JsonSerialize(nullsUsing = ObjectNullSerializer.class)
	@ManyToOne
	@JoinColumn(name = "type_code_id")
	private Code type;

	@JsonSerialize(nullsUsing = ObjectNullSerializer.class)
	@ManyToOne
	@JoinColumn(name = "kva_id")
	private Code kva;
	@JsonSerialize(nullsUsing = ObjectNullSerializer.class)
	@ManyToOne
	@JoinColumn(name = "distribution_num_code_id")
	private Code distributionNum;

	@Column(name = "has_senior")
	private boolean hasSenior;

	@Column(name = "is_old_custom")
	private boolean isOldCustom;

	@JsonSerialize(nullsUsing = StringNullSerializer.class)
	@Column(name = "license")
	private String license;

	@JsonSerialize(nullsUsing = ObjectNullSerializer.class)
	@ManyToOne
	@JoinColumn(name = "auth_state_code_id")
	private Code authState;

	@JsonSerialize(using = DateFormatSerializer.class, nullsUsing = StringNullSerializer.class)
	@JoinColumn(name = "auth_time")
	private Date authTime;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private List<Repair> repairs;

	*//**
	 ** 推荐人
	 *//*
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "form_user_id")
	private User fromUser;

	*//**
	 ** 推荐企业
	 *//*
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "form_customer_id")
	private Customer fromCustomer;

	@JsonSerialize(nullsUsing = StringNullSerializer.class)
	@Column(name = "reserve")
	private String reserve;

	*//**
	 * 企业管理员用户显示
	 *//*
	@Transient
	private User adminUser;

	*//**
	 * 企业地址
	 *//*
	@OneToMany(targetEntity = CustomerAddr.class, mappedBy = "customer")
	private List<CustomerAddr> customerAddrList = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public Code getKva() {
		return kva;
	}

	public void setKva(Code kva) {
		this.kva = kva;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public List<Repair> getRepairs() {
		return repairs;
	}

	public void setRepairs(List<Repair> repairs) {
		this.repairs = repairs;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Code getDistributionNum() {
		return distributionNum;
	}

	public void setDistributionNum(Code distributionNum) {
		this.distributionNum = distributionNum;
	}

	public boolean isHasSenior() {
		return hasSenior;
	}

	public void setHasSenior(boolean hasSenior) {
		this.hasSenior = hasSenior;
	}

	public boolean isOldCustom() {
		return isOldCustom;
	}

	public void setOldCustom(boolean isOldCustom) {
		this.isOldCustom = isOldCustom;
	}

	public Code getAuthState() {
		return authState;
	}

	public void setAuthState(Code authState) {
		this.authState = authState;
	}

	public Date getAuthTime() {
		return authTime;
	}

	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public Customer getFromCustomer() {
		return fromCustomer;
	}

	public void setFromCustomer(Customer fromCustomer) {
		this.fromCustomer = fromCustomer;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public User getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(User adminUser) {
		this.adminUser = adminUser;
	}

	public List<CustomerAddr> getCustomerAddrList() {
		return customerAddrList;
	}

	public void setCustomerAddrList(List<CustomerAddr> customerAddrList) {
		this.customerAddrList = customerAddrList;
	}*/
}
