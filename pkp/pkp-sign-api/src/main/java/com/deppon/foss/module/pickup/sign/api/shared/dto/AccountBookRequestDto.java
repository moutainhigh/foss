package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 353654
 */
public class AccountBookRequestDto implements Serializable {


	private static final long serialVersionUID = 5759421018854295807L;
	/**
	 * 用户编码
	 */
	private String customerCode;
	
	/**
	 * 用户类型
	 */
	private String customerType;
	
	/**
	 * 用户名称
	 */
	private String customerName;

	/**
	 * 当前余额
	 */
	private BigDecimal balance;
	
	/**
	 * 收款部门编码
	 */
	private String deptCode;
	
	/**
	 * 收款部门名称
	 */
	private String deptName;
	
	/**
	 * 是否成功
	 */
	private String isSuccess;
	
	/**
	 * 失败原因
	 */
	private String errorMessage;
	
	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public AccountBookRequestDto(String customerCode, String customerName,
			String deptCode, String deptName) {
		super();
		this.customerCode = customerCode;
		this.customerName = customerName;
		this.deptCode = deptCode;
		this.deptName = deptName;
	}

	public AccountBookRequestDto(String customerCode, String deptCode) {
		super();
		this.customerCode = customerCode;
		this.deptCode = deptCode;
	}

	public AccountBookRequestDto(String customerCode) {
		super();
		this.customerCode = customerCode;
	}

	public AccountBookRequestDto() {
		super();
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerType() {
		return customerType;
	}
	
}
