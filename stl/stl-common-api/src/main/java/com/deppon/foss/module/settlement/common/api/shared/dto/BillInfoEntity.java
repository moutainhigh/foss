package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.List;
/**
 * FOSS向VTS回传字段实体（VTS提供）
 * @author 395982
 *
 */
public class BillInfoEntity implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 运单集合
	 */
	private List<String> billIds;
	//付款状态
	private String paymentStatus;
	//付款工作流号
	private String paymentNumber;
	
	public List<String> getBillIds() {
		return billIds;
	}
	public void setBillIds(List<String> billIds) {
		this.billIds = billIds;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getPaymentNumber() {
		return paymentNumber;
	}
	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}
}
