package com.tgr.springbootmybatis.file.export.enums;

/**
 * 配电运维所有已知的数据表名
 */
public enum UserTableEnum {
    account("account","账户信息"),
    activity("activity","活动"),
    app("app","app"),
    area("area","地区"),
    carousel("carousel","carousel"),
    code("code","编码"),
    contract("contract","合同"),
    counter("counter","counter"),
    coupon("coupon","优惠券"),
    coupon_record("coupon_record","优惠券领用记录"),
    customer("customer","企业信息"),
    customer_addr("customer_addr","企业地址"),
    goods("goods","商品表"),
    goods_recommend("goods_recommend","商品推荐"),
    menu("menu","菜单"),
    message("message","message"),
    operater("operater","operater"),
    orders("orders","订单"),
    persion("persion","账户基本信息"),
    provider("provider","供应商"),
    repair("repair","抢修工单"),
    repair_record("repair_record","抢修记录"),
    role("role","角色"),
    role_menu("role_menu","role_menu"),
    shop("shop","店铺"),
    user("user","小程序用户"),
    withdraw_record("withdraw_record","提现返利"),
    lottery_customer("lottery_customer","参与抽奖企业"),
    lottery_statistics("lottery_statistics" , "大转盘统计"),
    wx_template_param("wx_template_param","消息模板");
    private String name;
    private String comment;

    UserTableEnum(String name,String comment){
        this.name = name;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
