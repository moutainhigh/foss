/**
 * 
 */
package com.deppon.foss.module.pickup.sign.api.shared.vo;

import java.math.BigDecimal;


/**
 * @author 239284
 * @descript 合伙人签收VO
 */
public class PtpSignVo {

	private String waybillNo; // 运单号
	private String paymentType; //付款方式
	private String consigneeCode;//客户代码
	private String consigneeName; //客户名称
	private BigDecimal codAmount; //代收货款
	private BigDecimal toPayFee;  //实收到付运费
	
	///传前台///
	private String receiveMethod; //提货方式

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getConsigneeCode() {
		return consigneeCode;
	}

	public void setConsigneeCode(String consigneeCode) {
		this.consigneeCode = consigneeCode;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public BigDecimal getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	public BigDecimal getToPayFee() {
		return toPayFee;
	}

	public void setToPayFee(BigDecimal toPayFee) {
		this.toPayFee = toPayFee;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

}
