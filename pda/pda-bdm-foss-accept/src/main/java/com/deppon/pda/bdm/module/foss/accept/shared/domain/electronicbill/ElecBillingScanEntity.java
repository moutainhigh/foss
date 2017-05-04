package com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill;


import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

public class ElecBillingScanEntity  extends ScanMsgEntity {

	/**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;
	
	//快递收入部门编码
	private String revenueCode;
	//快递收入部门名称
	private String revenueName;
	//运输方式
	private String transType;
	//提货方式
	private String takeType;
	//付款方式
	private String payType;
	//重量
	private String weight;
	//体积
	private String volume;
	//保险声明价值
	private String insuredAmount;
	//代收货款类型
	private String reciveLoanType;
	//代收货款金额
	private String reviceMoneyAmount;
	//返单类别
	private String returnBillingType;
	//优惠券
	private String couponNum;
	//运单类型
	private String waybillType;
	//车牌号
	private String truckCode;
		
	//优惠金额
	private String couponMoney;
    //银行交易流水号
    private String bankTradeSerail;
	
	public String getRevenueCode() {
		return revenueCode;
	}
	public void setRevenueCode(String revenueCode) {
		this.revenueCode = revenueCode;
	}
	public String getRevenueName() {
		return revenueName;
	}
	public void setRevenueName(String revenueName) {
		this.revenueName = revenueName;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getTakeType() {
		return takeType;
	}
	public void setTakeType(String takeType) {
		this.takeType = takeType;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getInsuredAmount() {
		return insuredAmount;
	}
	public void setInsuredAmount(String insuredAmount) {
		this.insuredAmount = insuredAmount;
	}
	public String getReciveLoanType() {
		return reciveLoanType;
	}
	public void setReciveLoanType(String reciveLoanType) {
		this.reciveLoanType = reciveLoanType;
	}
	public String getReviceMoneyAmount() {
		return reviceMoneyAmount;
	}
	public void setReviceMoneyAmount(String reviceMoneyAmount) {
		this.reviceMoneyAmount = reviceMoneyAmount;
	}
	public String getReturnBillingType() {
		return returnBillingType;
	}
	public void setReturnBillingType(String returnBillingType) {
		this.returnBillingType = returnBillingType;
	}
	public String getCouponNum() {
		return couponNum;
	}
	public void setCouponNum(String couponNum) {
		this.couponNum = couponNum;
	}
	public String getWaybillType() {
		return waybillType;
	}
	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	public String getCouponMoney() {
		return couponMoney;
	}
	public void setCouponMoney(String couponMoney) {
		this.couponMoney = couponMoney;
	}
	public String getBankTradeSerail() {
		return bankTradeSerail;
	}
	public void setBankTradeSerail(String bankTradeSerail) {
		this.bankTradeSerail = bankTradeSerail;
	}

	
	


}
