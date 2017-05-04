package com.deppon.foss.dubbo.uip.api.define;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReceivableEntity implements Serializable {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1528763406460526166L;
	/**
	 * 应收单号
	 */
	private String receivableNo;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 客户编码
	 */
	private String customerCode;
	/**
	 * 应收部门名称
	 */
	private String receivableOrgName;
	/**
	 * 标杆编码
	 */
	private String unifiedCode;
	/**
	 * 未核销金额
	 */
	private BigDecimal unverifyAmount;
	/**
	 * 在线支付编号
	 */
	private String onlineNo;
	/**
	 * 是否错误
	 */
	private String isError;
	/**
	 * 错误信息
	 */
	private String errorMsg;
	
	public String getReceivableNo() {
		return receivableNo;
	}

	public void setReceivableNo(String receivableNo) {
		this.receivableNo = receivableNo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}
	
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getReceivableOrgName() {
		return receivableOrgName;
	}
	
	public void setReceivableOrgName(String receivableOrgName) {
		this.receivableOrgName = receivableOrgName;
	}
	
	public String getUnifiedCode() {
		return unifiedCode;
	}
	
	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}
	
	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}
	
	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
	}
	
	public String getOnlineNo() {
		return onlineNo;
	}

	public void setOnlineNo(String onlineNo) {
		this.onlineNo = onlineNo;
	}

	public String getIsError() {
		return isError;
	}

	public void setIsError(String isError) {
		this.isError = isError;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
