package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @ClassName: BushCardDetailEntity
 * @Description: TODO(待刷卡详细实体)
 * @author &268974 wangzhili
 * @date 2016-3-2 上午9:29:00
 * 
 */
public class BushCardDetailEntity implements Serializable {

	/**
	 * @author 268974 wangzhili
	 */
	private static final long serialVersionUID = 1L;
	// 交易流水号
	private String tradeSerialNo;
	// 单据号
	private String invoiceNo;
	// 单据金额(每笔单子刷卡金额)
	private BigDecimal amount;
	// 单据来源
	private String wayBillSource;
	// 发货人名称
	private String sendCustomerName;
	// 待刷卡运单数据条目编号
	private String wscItemId;
	// 发货人编号
	private String sendCustomerCode;
	// 运单总金额
	private double wayBillAmount;
	
	public double getWayBillAmount() {
		return wayBillAmount;
	}
	public void setWayBillAmount(double wayBillAmount) {
		this.wayBillAmount = wayBillAmount;
	}
	public String getTradeSerialNo() {
		return tradeSerialNo;
	}
	public void setTradeSerialNo(String tradeSerialNo) {
		this.tradeSerialNo = tradeSerialNo;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getWayBillSource() {
		return wayBillSource;
	}
	public void setWayBillSource(String wayBillSource) {
		this.wayBillSource = wayBillSource;
	}
	public String getSendCustomerName() {
		return sendCustomerName;
	}
	public void setSendCustomerName(String sendCustomerName) {
		this.sendCustomerName = sendCustomerName;
	}
	public String getWscItemId() {
		return wscItemId;
	}
	public void setWscItemId(String wscItemId) {
		this.wscItemId = wscItemId;
	}
	public String getSendCustomerCode() {
		return sendCustomerCode;
	}
	public void setSendCustomerCode(String sendCustomerCode) {
		this.sendCustomerCode = sendCustomerCode;
	}

	
}
