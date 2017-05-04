package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 快递业务 查询财务单据 返回的单条DTO
 * @author 243921-zhangtingting
 * @date 2016-04-14 下午03:09:51
 *
 */
public class EcsBillReceivableDto implements Serializable {
	
	//序列号
	private static final long serialVersionUID = 1L;
	
	//运单号
	private String waybillNo;
	
	//总金额
	private BigDecimal amount;
	
	//已核销金额
	private BigDecimal verifyAmount;
	
	//未核销金额
	private BigDecimal unverifyAmount;
	
	//单据子类型
	private String billType;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getVerifyAmount() {
		return verifyAmount;
	}

	public void setVerifyAmount(BigDecimal verifyAmount) {
		this.verifyAmount = verifyAmount;
	}

	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}

	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}
	
}
