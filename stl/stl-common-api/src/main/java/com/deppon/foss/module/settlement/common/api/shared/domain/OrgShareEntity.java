package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 理赔成本划分实体
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-27 上午10:46:13
 */
public class OrgShareEntity extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4597667505602602213L;

	/**
	 * 应付单号
	 */
	private String sourceBillNo;

	/**
	 * 来源单据类型
	 */
	private String sourceBillType;

	/**
	 * 部门编码
	 */
	private String orgCode;

	/**
	 * 部门名称
	 */
	private String orgName;

	/**
	 * 承担金额
	 */
	private BigDecimal amount;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 快递代理点部编码
	 */
	private String expressOrgCode;
	
	/**
	 * 快递代理点部名称
	 */
	private String expressOrgName;
	
	
	/**
	 * @return sourceBillNo
	 */
	public String getSourceBillNo() {
		return sourceBillNo;
	}

	/**
	 * @param sourceBillNo
	 */
	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

	/**
	 * @return sourceBillType
	 */
	public String getSourceBillType() {
		return sourceBillType;
	}

	/**
	 * @param sourceBillType
	 */
	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}

	/**
	 * @return orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @GET
	 * @return expressOrgCode
	 */
	public String getExpressOrgCode() {
		/*
		 *@get
		 *@ return expressOrgCode
		 */
		return expressOrgCode;
	}

	/**
	 * @SET
	 * @param expressOrgCode
	 */
	public void setExpressOrgCode(String expressOrgCode) {
		/*
		 *@set
		 *@this.expressOrgCode = expressOrgCode
		 */
		this.expressOrgCode = expressOrgCode;
	}

	/**
	 * @GET
	 * @return expressOrgName
	 */
	public String getExpressOrgName() {
		/*
		 *@get
		 *@ return expressOrgName
		 */
		return expressOrgName;
	}

	/**
	 * @SET
	 * @param expressOrgName
	 */
	public void setExpressOrgName(String expressOrgName) {
		/*
		 *@set
		 *@this.expressOrgName = expressOrgName
		 */
		this.expressOrgName = expressOrgName;
	}

	
}
