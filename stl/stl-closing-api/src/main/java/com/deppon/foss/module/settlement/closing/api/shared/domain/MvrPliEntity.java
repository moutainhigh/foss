package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.math.BigDecimal;

/**
 * 始发偏线往来.
 * 
 * @author ibm-zhuwei
 * @date 2013-3-5 下午5:57:40
 */
public class MvrPliEntity {

	/** id. */
	private String id;

	/** 期间. */
	private String period;

	/** 客户编码. */
	private String customerCode;

	/** 客户名称. */
	private String customerName;

	/** 始发/偏线. */
	private String orgType;

	/** 部门编码. */
	private String orgCode;

	/** 部门名称. */
	private String orgName;

	// -----------偏线代理成本------------
	/** 应付偏线代理成本冲应收到付运费已签收 */
	private BigDecimal plCostWoDestRcvPod;

	/** 应付偏线代理成本冲应收到付运费未签收 */
	private BigDecimal plCostWoDestRcvNpod;

	// -------------还款运单总运费（到付）-------------------
	/**
	 * 还款现金未签收
	 */
	private BigDecimal urDestChNpod;

	/**
	 * 还款银行未签收
	 */
	private BigDecimal urDestCdNpod;

	/**
	 * 还款现金已签收
	 */
	private BigDecimal urDestChPod;

	/**
	 * 还款银行已签收
	 */
	private BigDecimal urDestCdPod;

	// -----------预收偏线代理------------
	/**
	 * 预收偏线代理冲应收到付运费已签收
	 */
	private BigDecimal plDrWoDestRcvPod;

	/**
	 * 预收偏线代理冲应收到付运费未签收
	 */
	private BigDecimal plDrWoDestRcvNpod;

	/**
	 * @get
	 * @return plCostWoDestRcvPod
	 */
	public BigDecimal getPlCostWoDestRcvPod() {
		/*
		 * @get
		 * 
		 * @return plCostWoDestRcvPod
		 */
		return plCostWoDestRcvPod;
	}

	/**
	 * @set
	 * @param plCostWoDestRcvPod
	 */
	public void setPlCostWoDestRcvPod(BigDecimal plCostWoDestRcvPod) {
		/*
		 * @set
		 * 
		 * @this.plCostWoDestRcvPod = plCostWoDestRcvPod
		 */
		this.plCostWoDestRcvPod = plCostWoDestRcvPod;
	}

	/**
	 * @get
	 * @return plCostWoDestRcvNpod
	 */
	public BigDecimal getPlCostWoDestRcvNpod() {
		/*
		 * @get
		 * 
		 * @return plCostWoDestRcvNpod
		 */
		return plCostWoDestRcvNpod;
	}

	/**
	 * @set
	 * @param plCostWoDestRcvNpod
	 */
	public void setPlCostWoDestRcvNpod(BigDecimal plCostWoDestRcvNpod) {
		/*
		 * @set
		 * 
		 * @this.plCostWoDestRcvNpod = plCostWoDestRcvNpod
		 */
		this.plCostWoDestRcvNpod = plCostWoDestRcvNpod;
	}

	/**
	 * @get
	 * @return urDestChNpod
	 */
	public BigDecimal getUrDestChNpod() {
		/*
		 * @get
		 * 
		 * @return urDestChNpod
		 */
		return urDestChNpod;
	}

	/**
	 * @set
	 * @param urDestChNpod
	 */
	public void setUrDestChNpod(BigDecimal urDestChNpod) {
		/*
		 * @set
		 * 
		 * @this.urDestChNpod = urDestChNpod
		 */
		this.urDestChNpod = urDestChNpod;
	}

	/**
	 * @get
	 * @return urDestCdNpod
	 */
	public BigDecimal getUrDestCdNpod() {
		/*
		 * @get
		 * 
		 * @return urDestCdNpod
		 */
		return urDestCdNpod;
	}

	/**
	 * @set
	 * @param urDestCdNpod
	 */
	public void setUrDestCdNpod(BigDecimal urDestCdNpod) {
		/*
		 * @set
		 * 
		 * @this.urDestCdNpod = urDestCdNpod
		 */
		this.urDestCdNpod = urDestCdNpod;
	}

	/**
	 * @get
	 * @return urDestChPod
	 */
	public BigDecimal getUrDestChPod() {
		/*
		 * @get
		 * 
		 * @return urDestChPod
		 */
		return urDestChPod;
	}

	/**
	 * @set
	 * @param urDestChPod
	 */
	public void setUrDestChPod(BigDecimal urDestChPod) {
		/*
		 * @set
		 * 
		 * @this.urDestChPod = urDestChPod
		 */
		this.urDestChPod = urDestChPod;
	}

	/**
	 * @get
	 * @return urDestCdPod
	 */
	public BigDecimal getUrDestCdPod() {
		/*
		 * @get
		 * 
		 * @return urDestCdPod
		 */
		return urDestCdPod;
	}

	/**
	 * @set
	 * @param urDestCdPod
	 */
	public void setUrDestCdPod(BigDecimal urDestCdPod) {
		/*
		 * @set
		 * 
		 * @this.urDestCdPod = urDestCdPod
		 */
		this.urDestCdPod = urDestCdPod;
	}

	/**
	 * @get
	 * @return plDrWoDestRcvPod
	 */
	public BigDecimal getPlDrWoDestRcvPod() {
		/*
		 * @get
		 * 
		 * @return plDrWoDestRcvPod
		 */
		return plDrWoDestRcvPod;
	}

	/**
	 * @set
	 * @param plDrWoDestRcvPod
	 */
	public void setPlDrWoDestRcvPod(BigDecimal plDrWoDestRcvPod) {
		/*
		 * @set
		 * 
		 * @this.plDrWoDestRcvPod = plDrWoDestRcvPod
		 */
		this.plDrWoDestRcvPod = plDrWoDestRcvPod;
	}

	/**
	 * @get
	 * @return plDrWoDestRcvNpod
	 */
	public BigDecimal getPlDrWoDestRcvNpod() {
		/*
		 * @get
		 * 
		 * @return plDrWoDestRcvNpod
		 */
		return plDrWoDestRcvNpod;
	}

	/**
	 * @set
	 * @param plDrWoDestRcvNpod
	 */
	public void setPlDrWoDestRcvNpod(BigDecimal plDrWoDestRcvNpod) {
		/*
		 * @set
		 * 
		 * @this.plDrWoDestRcvNpod = plDrWoDestRcvNpod
		 */
		this.plDrWoDestRcvNpod = plDrWoDestRcvNpod;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the period.
	 * 
	 * @return the period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * Sets the period.
	 * 
	 * @param period
	 *            the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * Gets the customer code.
	 * 
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * Sets the customer code.
	 * 
	 * @param customerCode
	 *            the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * Gets the customer name.
	 * 
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * Sets the customer name.
	 * 
	 * @param customerName
	 *            the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * Gets the org type.
	 * 
	 * @return the orgType
	 */
	public String getOrgType() {
		return orgType;
	}

	/**
	 * Sets the org type.
	 * 
	 * @param orgType
	 *            the orgType to set
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	/**
	 * Gets the org code.
	 * 
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 * 
	 * @param orgCode
	 *            the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the org name.
	 * 
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * Sets the org name.
	 * 
	 * @param orgName
	 *            the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

}
