package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

/**
 * 
  * @ClassName ArrPayEntity 
  * @Description 到付 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public class ArrPayEntity {
	/**
	 * 支付类型
	 */
	private String payType;
	/**
	 * 支付金额
	 */
	private double payAmount;
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}
	
}
