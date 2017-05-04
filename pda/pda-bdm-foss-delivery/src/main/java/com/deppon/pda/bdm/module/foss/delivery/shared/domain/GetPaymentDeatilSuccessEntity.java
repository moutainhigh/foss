package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 结清货款单条运单明细信息
 * @ClassName: GetPaymentDeatilSuccessEntity 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author 245955
 * @date 2016-3-13  
 *
 */
public class GetPaymentDeatilSuccessEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	// 交易流水号
	private String tradeSerialNo;
	// 单据号
	private String invoiceNo;
	// 总金额
	private BigDecimal amount;
	// 收货人
	private String awayCustomerName;
	
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getTradeSerialNo() {
		return tradeSerialNo;
	}
	public void setTradeSerialNo(String tradeSerialNo) {
		this.tradeSerialNo = tradeSerialNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getAwayCustomerName() {
		return awayCustomerName;
	}
	public void setAwayCustomerName(String awayCustomerName) {
		this.awayCustomerName = awayCustomerName;
	}
}
