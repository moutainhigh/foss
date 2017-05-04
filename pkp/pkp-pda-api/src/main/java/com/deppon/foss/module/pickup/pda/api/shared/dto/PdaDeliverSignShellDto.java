package com.deppon.foss.module.pickup.pda.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
/**
 * 子母件派送签收接口 参数
 * @author foss-sunyanfei
 * @date 2015-9-11 上午17:09:29
 * @since
 * @version
 */
public class PdaDeliverSignShellDto implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3296867869735207739L;
	
	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmount;
	
	/**
	 * 付款方式 -- 快递  到付金额
	 * 	（现金、临欠、月结、银行卡、支票、电汇）
	 */
	private String paymentType;
	
	/**
	 * 银行交易流水号--到付流水号
	 */
	private String bankTradeSerail;
	
	/**
	 * 代收货款
	 */
	private BigDecimal codAmount;
	
	/**
	 * 代收货款--付款方式 
	 * 	（现金、临欠、月结、银行卡、支票、电汇）
	 */
	private String codPaymentType;
	
	/**
	 * 银行交易流水号--代收货款流水号
	 */
	private String codBankTradeSerail;
	
	/**
	 * 合并金额
	 */
	private BigDecimal totalAmount;
	
	/**
	 * 合并--付款方式 
	 * 	（现金、临欠、月结、银行卡、支票、电汇）
	 */
	private String totalPaymentType;
	
	/**
	 * 派送签收接口 参数 集合
	 */
	private List<PdaDeliverSignDto> pdaDeliverSignDtos;

	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	public BigDecimal getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	public String getCodPaymentType() {
		return codPaymentType;
	}

	public void setCodPaymentType(String codPaymentType) {
		this.codPaymentType = codPaymentType;
	}

	public String getCodBankTradeSerail() {
		return codBankTradeSerail;
	}

	public void setCodBankTradeSerail(String codBankTradeSerail) {
		this.codBankTradeSerail = codBankTradeSerail;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTotalPaymentType() {
		return totalPaymentType;
	}

	public void setTotalPaymentType(String totalPaymentType) {
		this.totalPaymentType = totalPaymentType;
	}

	public List<PdaDeliverSignDto> getPdaDeliverSignDtos() {
		return pdaDeliverSignDtos;
	}

	public void setPdaDeliverSignDtos(List<PdaDeliverSignDto> pdaDeliverSignDtos) {
		this.pdaDeliverSignDtos = pdaDeliverSignDtos;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getBankTradeSerail() {
		return bankTradeSerail;
	}

	public void setBankTradeSerail(String bankTradeSerail) {
		this.bankTradeSerail = bankTradeSerail;
	}
	
}