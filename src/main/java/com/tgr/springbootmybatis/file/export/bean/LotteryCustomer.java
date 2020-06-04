package com.tgr.springbootmybatis.file.export.bean;


import java.io.Serializable;

/**
 * 可参与抽奖活动的幸运客户列表
 */
/*@Entity
@Table(name = "lottery_customer")*/
public class LotteryCustomer /*extends BaseEntity implements Serializable*/ {

	private static final long serialVersionUID = -6669437639822226614L;

	/**
     * 可参与抽奖的企业
     */
   /* @OneToOne
    @JoinColumn(name = "customer_id")*/
    private Customer customer;

    /**
     * 限定的抽奖次数
     */
    private Integer lotteryLimit;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getLotteryLimit() {
        return lotteryLimit;
    }

    public void setLotteryLimit(Integer lotteryLimit) {
        this.lotteryLimit = lotteryLimit;
    }
}
