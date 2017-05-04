package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 网页端通过 打款编码请求和返回实体
 * 
 * @ClassName: PayInfoRequest
 * @author & 周禹安 | zhouyuan008@deppon.com
 * @date 2016年11月22日 上午10:04:27
 * add by 378375
 */
public class PayInfoRequest implements Serializable {
	private static final long serialVersionUID = -2363454358004766623L;
	/**
	 * 金额
	 */
	private BigDecimal amount;

	/**
	 * 打款方式
	 */
	private String payType;

	/**
	 * 打款编码
	 */
	private String payCode;

	/**
	 * 打款部门名称
	 */
	private String payDeptName;

	/**
	 * 打款部门编码
	 */
	private String payDeptCode;

	/**
	 * 对象名称
	 */
	private String customerName;

	/**
	 * 对象编码
	 */
	private String customerCode;

	/**
	 * 对象类型
	 */
	private String customerType;

	/**
	 * 是否成功
	 */
	private Boolean isSuccess;

	/**
	 * 错误信息
	 */
	private String errorMessage;
	
	
	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getPayDeptName() {
		return payDeptName;
	}

	public void setPayDeptName(String payDeptName) {
		this.payDeptName = payDeptName;
	}

	public String getPayDeptCode() {
		return payDeptCode;
	}

	public void setPayDeptCode(String payDeptCode) {
		this.payDeptCode = payDeptCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	 
}
