package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 子母件签收实体
 * @author 245955
 *
 */
public class ExpSignParentEntity extends ScanMsgEntity {
	  
	private static final long serialVersionUID = 1L;
	/**
	  * 子母件签收明细信息
	  */
	 private List<MotherSonSignList> motherSonSignList;
	// 合并金额
	private double totalAmount; 
	// 合并金额类型
	private String totalType;
	// 代收货款金额
	private double payAmount; 
	// 代收货款支付类型
	private String payType; 
	// 代收货款银行流水号	
	private String codBankTradeSerail; 
	// 到付金额
	private double arriveAmount; 
	// 到付金额支付类型
	private String arriveType; 
	// 到付银行交易流水号
    private String bankTradeSerail;
	public List<MotherSonSignList> getMotherSonSignList() {
		return motherSonSignList;
	}
	public void setMotherSonSignList(List<MotherSonSignList> motherSonSignList) {
		this.motherSonSignList = motherSonSignList;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getTotalType() {
		return totalType;
	}
	public void setTotalType(String totalType) {
		this.totalType = totalType;
	}
	public double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getCodBankTradeSerail() {
		return codBankTradeSerail;
	}
	public void setCodBankTradeSerail(String codBankTradeSerail) {
		this.codBankTradeSerail = codBankTradeSerail;
	}
	public double getArriveAmount() {
		return arriveAmount;
	}
	public void setArriveAmount(double arriveAmount) {
		this.arriveAmount = arriveAmount;
	}
	public String getArriveType() {
		return arriveType;
	}
	public void setArriveType(String arriveType) {
		this.arriveType = arriveType;
	}
	public String getBankTradeSerail() {
		return bankTradeSerail;
	}
	public void setBankTradeSerail(String bankTradeSerail) {
		this.bankTradeSerail = bankTradeSerail;
	} 
    
}
