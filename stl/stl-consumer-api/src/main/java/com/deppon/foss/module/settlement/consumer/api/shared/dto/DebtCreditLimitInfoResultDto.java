package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 获得最早欠款客户已用额度信息Dto
 * 
 * @author foss-zhangxiaohui
 * @date Jan 15, 2013 3:01:19 PM
 */
public class DebtCreditLimitInfoResultDto implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2556420722353209598L;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 客户编码
	 */
	private String customerCode;
	
	/**
	 * 客户编码
	 */
	private BigDecimal usedAmount;
	
	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * @return customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param  customerName  
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param  customerCode  
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return usedAmount
	 */
	public BigDecimal getUsedAmount() {
		return usedAmount;
	}

	/**
	 * @param  usedAmount  
	 */
	public void setUsedAmount(BigDecimal usedAmount) {
		this.usedAmount = usedAmount;
	}

	/**
	 * @return businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param  businessDate  
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}
}
