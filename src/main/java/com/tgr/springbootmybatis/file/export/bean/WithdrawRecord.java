package com.tgr.springbootmybatis.file.export.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 ** 推荐提现记录表
 */
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class WithdrawRecord /*extends BaseEntity implements Serializable */{

	private static final long serialVersionUID = 4266055502431709678L;

	// 邀请人
	/*@ManyToOne
	@JoinColumn(name = "user_id")*/
	//private User user;

	// 被邀请的人
	/*@OneToOne
	@JoinColumn(name = "invite_user_id")
	private User inviteUser;

	@OneToOne
	@JoinColumn(name = "invite_customer_id")
	private Customer inviteCustomer;

	@JsonSerialize(using = Channel.Serializer.class)
	@Column(name = "channel", length = 64)
	@Enumerated(EnumType.STRING)
	private Channel channel;

	// 申请时间
	@JsonSerialize(using = DateFormatSerializer.class)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "apply_time")
	private Date applyTime;

	// 审核时间
	@JsonSerialize(using = DateFormatSerializer.class)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "audit_time")
	private Date auditTime;

	// 到账时间
	@JsonSerialize(using = DateFormatSerializer.class)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "transfer_time")
	private Date transferTime;

	// 返利金额(元)
	@JsonSerialize(using = YuanSerializer.class)
	@Column(name = "amount", precision = 10, scale = 2)
	private BigDecimal amount;

	*//**
	 * 提现记录号，唯一
	 *//*
	private String recordNo;

	// 交易流水号
	@Column(name = "serial_number")
	private String serialNumber;

	@JsonSerialize(using = WithdrawRecordState.Serializer.class)
	@Column(name = "state", length = 64)
	@Enumerated(EnumType.STRING)
	private WithdrawRecordState state;

	*//**
	 * 拒绝理由
	 *//*
	private String refuseReason;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getInviteUser() {
		return inviteUser;
	}

	public void setInviteUser(User inviteUser) {
		this.inviteUser = inviteUser;
	}

	public Customer getInviteCustomer() {
		return inviteCustomer;
	}

	public void setInviteCustomer(Customer inviteCustomer) {
		this.inviteCustomer = inviteCustomer;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Date getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public WithdrawRecordState getState() {
		return state;
	}

	public void setState(WithdrawRecordState state) {
		this.state = state;
	}

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

	public String getStateName(){
		if(this.state != null){
			return state.name();
		}
		return null;
	}*/
}
