package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 未完全核销单据查询结果dto
 * @author foss-qiaolifeng
 * @date 2013-5-14 下午2:10:43
 */
public class BillUnverifyComletedResultDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -537492147580041428L;
	
	/**
	 * 单号
	 */
	private String billNo;
	
	/**
	 * 单据父类型
	 */
	private String billParentType;
	
	/**
	 * 单据子类型
	 */
	private String billType;
	
	/**
	 * 客户编码
	 */
	private String customerCode;
	
	/**
	 * 客户便名称
	 */
	private String custoemerName;
	
	/**
	 * 未核销金额
	 */
	private BigDecimal unverifyAmount;

	
	/**
	 * @get
	 * @return billNo
	 */
	public String getBillNo() {
		/*
		 * @get
		 * @return billNo
		 */
		return billNo;
	}

	
	/**
	 * @set
	 * @param billNo
	 */
	public void setBillNo(String billNo) {
		/*
		 *@set
		 *@this.billNo = billNo
		 */
		this.billNo = billNo;
	}

	
	/**
	 * @get
	 * @return billParentType
	 */
	public String getBillParentType() {
		/*
		 * @get
		 * @return billParentType
		 */
		return billParentType;
	}

	
	/**
	 * @set
	 * @param billParentType
	 */
	public void setBillParentType(String billParentType) {
		/*
		 *@set
		 *@this.billParentType = billParentType
		 */
		this.billParentType = billParentType;
	}

	
	/**
	 * @get
	 * @return billType
	 */
	public String getBillType() {
		/*
		 * @get
		 * @return billType
		 */
		return billType;
	}

	
	/**
	 * @set
	 * @param billType
	 */
	public void setBillType(String billType) {
		/*
		 *@set
		 *@this.billType = billType
		 */
		this.billType = billType;
	}

	
	/**
	 * @get
	 * @return customerCode
	 */
	public String getCustomerCode() {
		/*
		 * @get
		 * @return customerCode
		 */
		return customerCode;
	}

	
	/**
	 * @set
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		/*
		 *@set
		 *@this.customerCode = customerCode
		 */
		this.customerCode = customerCode;
	}

	
	/**
	 * @get
	 * @return custoemerName
	 */
	public String getCustoemerName() {
		/*
		 * @get
		 * @return custoemerName
		 */
		return custoemerName;
	}

	
	/**
	 * @set
	 * @param custoemerName
	 */
	public void setCustoemerName(String custoemerName) {
		/*
		 *@set
		 *@this.custoemerName = custoemerName
		 */
		this.custoemerName = custoemerName;
	}

	
	/**
	 * @get
	 * @return unverifyAmount
	 */
	public BigDecimal getUnverifyAmount() {
		/*
		 * @get
		 * @return unverifyAmount
		 */
		return unverifyAmount;
	}

	
	/**
	 * @set
	 * @param unverifyAmount
	 */
	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		/*
		 *@set
		 *@this.unverifyAmount = unverifyAmount
		 */
		this.unverifyAmount = unverifyAmount;
	}
	
	
}
