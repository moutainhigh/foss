package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.math.BigDecimal;

/**
 * 始发空运往来.
 * 
 * @author ibm-zhuwei
 * @date 2013-3-5 下午5:58:03
 */
public class MvrAfiEntity {

	/** id. */
	private String id;

	/** 期间. */
	private String period;

	/** 客户编码. */
	private String customerCode;

	/** 客户名称. */
	private String customerName;

	/** 始发/空运. */
	private String orgType;

	/** 部门编码. */
	private String orgCode;

	/** 部门名称. */
	private String orgName;

	// ------空运应付冲应收------
	/**
	 * 应付到达代理成本冲应收到付运费已签收
	 */
	private BigDecimal airPrAgencyWoDestRcvPod;

	/**
	 * 应付到达代理成本冲应收到付运费未签收
	 */
	private BigDecimal airPrAgencyWoDestRcvNpod;

	/**
	 * 其他应付冲应收到付运费已签收
	 */
	private BigDecimal airPrOtWoDestRcvPod;

	/**
	 * 其他应付冲应收到付运费未签收
	 */
	private BigDecimal airPrOthWoDestRcvNPod;

	// --------------空运代收货款------------------
	/**
	 * 空运签收时未核销代收货款
	 */
	private BigDecimal airCodPodNwoCod;

	/**
	 * 空运反签收时未核销代收货款
	 */
	private BigDecimal airCodNpodNwoCod;

	/**
	 * 空运还款代收货款现金已签收
	 */
	private BigDecimal airCodChPod;

	/**
	 * 空运还款代收货款银行已签收
	 */
	private BigDecimal airCodCdPod;

	/**
	 * 空运签收时已核销代收货款
	 */
	private BigDecimal airCodPodWoCod;

	/**
	 * 空运反签收时已核销代收货款
	 */
	private BigDecimal airCodNpodWoCod;

	/**
	 * 空运到达代理应付冲应收代收货款已签收
	 */
	private BigDecimal airCodWoAgencyPayPod;

	/**
	 * 空运其他应付冲应收代收货款已签收
	 */
	private BigDecimal airCodWoOthPayCod;

	// ------------预收空运代理------
	/**
	 * 预收空运代理冲应收到付运费已签收
	 */
	private BigDecimal airDrDestRcvPod;

	/**
	 * 预收空运代理冲应收到付运费未签收
	 */
	private BigDecimal airDrDestRcvNpod;

	/**
	 * 预收空运代理冲应收到付运费已签收
	 */
	private BigDecimal airDrWoCodRcvPod;

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

	/**
	 * @get
	 * @return airPrAgencyWoDestRcvPod
	 */
	public BigDecimal getAirPrAgencyWoDestRcvPod() {
		/*
		 * @get
		 * 
		 * @return airPrAgencyWoDestRcvPod
		 */
		return airPrAgencyWoDestRcvPod;
	}

	/**
	 * @set
	 * @param airPrAgencyWoDestRcvPod
	 */
	public void setAirPrAgencyWoDestRcvPod(BigDecimal airPrAgencyWoDestRcvPod) {
		/*
		 * @set
		 * 
		 * @this.airPrAgencyWoDestRcvPod = airPrAgencyWoDestRcvPod
		 */
		this.airPrAgencyWoDestRcvPod = airPrAgencyWoDestRcvPod;
	}

	/**
	 * @get
	 * @return airPrAgencyWoDestRcvNpod
	 */
	public BigDecimal getAirPrAgencyWoDestRcvNpod() {
		/*
		 * @get
		 * 
		 * @return airPrAgencyWoDestRcvNpod
		 */
		return airPrAgencyWoDestRcvNpod;
	}

	/**
	 * @set
	 * @param airPrAgencyWoDestRcvNpod
	 */
	public void setAirPrAgencyWoDestRcvNpod(BigDecimal airPrAgencyWoDestRcvNpod) {
		/*
		 * @set
		 * 
		 * @this.airPrAgencyWoDestRcvNpod = airPrAgencyWoDestRcvNpod
		 */
		this.airPrAgencyWoDestRcvNpod = airPrAgencyWoDestRcvNpod;
	}

	/**
	 * @get
	 * @return airPrOtWoDestRcvPod
	 */
	public BigDecimal getAirPrOtWoDestRcvPod() {
		/*
		 * @get
		 * 
		 * @return airPrOtWoDestRcvPod
		 */
		return airPrOtWoDestRcvPod;
	}

	/**
	 * @set
	 * @param airPrOtWoDestRcvPod
	 */
	public void setAirPrOtWoDestRcvPod(BigDecimal airPrOtWoDestRcvPod) {
		/*
		 * @set
		 * 
		 * @this.airPrOtWoDestRcvPod = airPrOtWoDestRcvPod
		 */
		this.airPrOtWoDestRcvPod = airPrOtWoDestRcvPod;
	}



	
	/**
	 * @get
	 * @return airPrOthWoDestRcvNPod
	 */
	public BigDecimal getAirPrOthWoDestRcvNPod() {
		/*
		 * @get
		 * @return airPrOthWoDestRcvNPod
		 */
		return airPrOthWoDestRcvNPod;
	}

	
	/**
	 * @set
	 * @param airPrOthWoDestRcvNPod
	 */
	public void setAirPrOthWoDestRcvNPod(BigDecimal airPrOthWoDestRcvNPod) {
		/*
		 *@set
		 *@this.airPrOthWoDestRcvNPod = airPrOthWoDestRcvNPod
		 */
		this.airPrOthWoDestRcvNPod = airPrOthWoDestRcvNPod;
	}

	/**
	 * @get
	 * @return airCodPodNwoCod
	 */
	public BigDecimal getAirCodPodNwoCod() {
		/*
		 * @get
		 * 
		 * @return airCodPodNwoCod
		 */
		return airCodPodNwoCod;
	}

	/**
	 * @set
	 * @param airCodPodNwoCod
	 */
	public void setAirCodPodNwoCod(BigDecimal airCodPodNwoCod) {
		/*
		 * @set
		 * 
		 * @this.airCodPodNwoCod = airCodPodNwoCod
		 */
		this.airCodPodNwoCod = airCodPodNwoCod;
	}

	/**
	 * @get
	 * @return airCodNpodNwoCod
	 */
	public BigDecimal getAirCodNpodNwoCod() {
		/*
		 * @get
		 * 
		 * @return airCodNpodNwoCod
		 */
		return airCodNpodNwoCod;
	}

	/**
	 * @set
	 * @param airCodNpodNwoCod
	 */
	public void setAirCodNpodNwoCod(BigDecimal airCodNpodNwoCod) {
		/*
		 * @set
		 * 
		 * @this.airCodNpodNwoCod = airCodNpodNwoCod
		 */
		this.airCodNpodNwoCod = airCodNpodNwoCod;
	}

	/**
	 * @get
	 * @return airCodChPod
	 */
	public BigDecimal getAirCodChPod() {
		/*
		 * @get
		 * 
		 * @return airCodChPod
		 */
		return airCodChPod;
	}

	/**
	 * @set
	 * @param airCodChPod
	 */
	public void setAirCodChPod(BigDecimal airCodChPod) {
		/*
		 * @set
		 * 
		 * @this.airCodChPod = airCodChPod
		 */
		this.airCodChPod = airCodChPod;
	}

	/**
	 * @get
	 * @return airCodCdPod
	 */
	public BigDecimal getAirCodCdPod() {
		/*
		 * @get
		 * 
		 * @return airCodCdPod
		 */
		return airCodCdPod;
	}

	/**
	 * @set
	 * @param airCodCdPod
	 */
	public void setAirCodCdPod(BigDecimal airCodCdPod) {
		/*
		 * @set
		 * 
		 * @this.airCodCdPod = airCodCdPod
		 */
		this.airCodCdPod = airCodCdPod;
	}

	/**
	 * @get
	 * @return airCodPodWoCod
	 */
	public BigDecimal getAirCodPodWoCod() {
		/*
		 * @get
		 * 
		 * @return airCodPodWoCod
		 */
		return airCodPodWoCod;
	}

	/**
	 * @set
	 * @param airCodPodWoCod
	 */
	public void setAirCodPodWoCod(BigDecimal airCodPodWoCod) {
		/*
		 * @set
		 * 
		 * @this.airCodPodWoCod = airCodPodWoCod
		 */
		this.airCodPodWoCod = airCodPodWoCod;
	}

	/**
	 * @get
	 * @return airCodNpodWoCod
	 */
	public BigDecimal getAirCodNpodWoCod() {
		/*
		 * @get
		 * 
		 * @return airCodNpodWoCod
		 */
		return airCodNpodWoCod;
	}

	/**
	 * @set
	 * @param airCodNpodWoCod
	 */
	public void setAirCodNpodWoCod(BigDecimal airCodNpodWoCod) {
		/*
		 * @set
		 * 
		 * @this.airCodNpodWoCod = airCodNpodWoCod
		 */
		this.airCodNpodWoCod = airCodNpodWoCod;
	}

	/**
	 * @get
	 * @return airCodWoAgencyPayPod
	 */
	public BigDecimal getAirCodWoAgencyPayPod() {
		/*
		 * @get
		 * 
		 * @return airCodWoAgencyPayPod
		 */
		return airCodWoAgencyPayPod;
	}

	/**
	 * @set
	 * @param airCodWoAgencyPayPod
	 */
	public void setAirCodWoAgencyPayPod(BigDecimal airCodWoAgencyPayPod) {
		/*
		 * @set
		 * 
		 * @this.airCodWoAgencyPayPod = airCodWoAgencyPayPod
		 */
		this.airCodWoAgencyPayPod = airCodWoAgencyPayPod;
	}

	/**
	 * @get
	 * @return airCodWoOthPayCod
	 */
	public BigDecimal getAirCodWoOthPayCod() {
		/*
		 * @get
		 * 
		 * @return airCodWoOthPayCod
		 */
		return airCodWoOthPayCod;
	}

	/**
	 * @set
	 * @param airCodWoOthPayCod
	 */
	public void setAirCodWoOthPayCod(BigDecimal airCodWoOthPayCod) {
		/*
		 * @set
		 * 
		 * @this.airCodWoOthPayCod = airCodWoOthPayCod
		 */
		this.airCodWoOthPayCod = airCodWoOthPayCod;
	}

	/**
	 * @get
	 * @return airDrDestRcvPod
	 */
	public BigDecimal getAirDrDestRcvPod() {
		/*
		 * @get
		 * 
		 * @return airDrDestRcvPod
		 */
		return airDrDestRcvPod;
	}

	/**
	 * @set
	 * @param airDrDestRcvPod
	 */
	public void setAirDrDestRcvPod(BigDecimal airDrDestRcvPod) {
		/*
		 * @set
		 * 
		 * @this.airDrDestRcvPod = airDrDestRcvPod
		 */
		this.airDrDestRcvPod = airDrDestRcvPod;
	}

	/**
	 * @get
	 * @return airDrDestRcvNpod
	 */
	public BigDecimal getAirDrDestRcvNpod() {
		/*
		 * @get
		 * 
		 * @return airDrDestRcvNpod
		 */
		return airDrDestRcvNpod;
	}

	/**
	 * @set
	 * @param airDrDestRcvNpod
	 */
	public void setAirDrDestRcvNpod(BigDecimal airDrDestRcvNpod) {
		/*
		 * @set
		 * 
		 * @this.airDrDestRcvNpod = airDrDestRcvNpod
		 */
		this.airDrDestRcvNpod = airDrDestRcvNpod;
	}

	/**
	 * @get
	 * @return airDrWoCodRcvPod
	 */
	public BigDecimal getAirDrWoCodRcvPod() {
		/*
		 * @get
		 * 
		 * @return airDrWoCodRcvPod
		 */
		return airDrWoCodRcvPod;
	}

	/**
	 * @set
	 * @param airDrWoCodRcvPod
	 */
	public void setAirDrWoCodRcvPod(BigDecimal airDrWoCodRcvPod) {
		/*
		 * @set
		 * 
		 * @this.airDrWoCodRcvPod = airDrWoCodRcvPod
		 */
		this.airDrWoCodRcvPod = airDrWoCodRcvPod;
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

}
