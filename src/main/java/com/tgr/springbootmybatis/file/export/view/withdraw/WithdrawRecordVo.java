package com.tgr.springbootmybatis.file.export.view.withdraw;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.tgr.springbootmybatis.file.export.bean.WithdrawRecord;

import java.math.BigDecimal;

public class WithdrawRecordVo {

    /**
     * 推广人姓名
     */
    private String userName;

    /**
     * 推广人手机号
     */
    private String mobile;

    /**
     * 所属企业
     */
    private String customerName;

    /**
     * 邀请人姓名
     */
    private String inviteUserName;

    /**
     * 邀请企业
     */
    private String inviteCustomerName;

    /**
     * 推广渠道
     */
    private String channel;

    private String applyTime;

    private String auditTime;

    private String transferTime;

    private String state;

    private BigDecimal amount;

    private String failReason;

    public WithdrawRecordVo(WithdrawRecord withdrawRecord) {
       /* this.amount = withdrawRecord.getAmount();
        if (withdrawRecord.getApplyTime() != null) {
            this.applyTime = DateFormatUtils.format(withdrawRecord.getApplyTime(),"yyyy-MM-dd HH:mm:ss");
        }

        if (withdrawRecord.getAuditTime() != null) {
            this.auditTime = DateFormatUtils.format(withdrawRecord.getAuditTime(),"yyyy-MM-dd HH:mm:ss");
        }
        if (withdrawRecord.getChannel() != null) {
            this.channel = withdrawRecord.getChannel().getLabel();
        }

        if (withdrawRecord.getUser() != null) {
            this.userName = withdrawRecord.getUser().getName();
            this.mobile = withdrawRecord.getUser().getPhone();
            if (withdrawRecord.getUser().getCustomer() != null)
                this.customerName = withdrawRecord.getUser().getCustomer().getName();
        }

        if (withdrawRecord.getInviteUser() != null) {
            this.inviteUserName = withdrawRecord.getInviteUser().getName();
            if (withdrawRecord.getInviteUser().getCustomer() != null)
                this.inviteCustomerName = withdrawRecord.getInviteUser().getCustomer().getName();
        }

        if (withdrawRecord.getState() != null) {
            this.state = withdrawRecord.getState().getLabel();
        }

        if (withdrawRecord.getTransferTime() != null) {
            this.transferTime = DateFormatUtils.format(withdrawRecord.getTransferTime(),"yyyy-MM-dd HH:mm:ss");
        }

        this.failReason = withdrawRecord.getRefuseReason();*/

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getInviteUserName() {
        return inviteUserName;
    }

    public void setInviteUserName(String inviteUserName) {
        this.inviteUserName = inviteUserName;
    }

    public String getInviteCustomerName() {
        return inviteCustomerName;
    }

    public void setInviteCustomerName(String inviteCustomerName) {
        this.inviteCustomerName = inviteCustomerName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(String transferTime) {
        this.transferTime = transferTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}
