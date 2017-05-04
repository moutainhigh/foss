package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

public class RentCarCubcDto {

	/**
	 * 租车编号
	 */
	private String rentCarNo; 
	
	/**
	 * 客户/司机编码
	 */
	private String customerCode;
	
	/**
	 * 客户/司机名称--
	 */
	private String customerName;
	
	/**
	 * 司机联系方式
	 */
	private String customerPhone;
	
	/**
	 * 租车用途
	 */
	private String rentCarUseType;
	/**
	 * 租车金额
	 */
	private BigDecimal amount;
	
	/**
	 * 租车日期
	 */
	private Date useCarDate;
	
	/**
	 * 是否多次标记
	 */
	private String isRepeatemark;
	

	public String getRentCarNo() {
		return rentCarNo;
	}

	public void setRentCarNo(String rentCarNo) {
		this.rentCarNo = rentCarNo;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getUseCarDate() {
		return useCarDate;
	}

	public void setUseCarDate(Date useCarDate) {
		this.useCarDate = useCarDate;
	}

	public String getIsRepeatemark() {
		return isRepeatemark;
	}

	public void setIsRepeatemark(String isRepeatemark) {
		this.isRepeatemark = isRepeatemark;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getRentCarUseType() {
		return rentCarUseType;
	}

	public void setRentCarUseType(String rentCarUseType) {
		this.rentCarUseType = rentCarUseType;
	}
	

}
