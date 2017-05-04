package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.math.BigDecimal;

/**
 * 对账单明细（用于对账单制作、修改时前后台传值使用）
 * @author foss-wangxuemin
 * @date Apr 17, 2013 5:10:33 PM
 */
public class StatementOfAccountDetailDto {

	/**
	 * 来源单号
	 */
	private String sourceBillNo;

	/**
	 * 单据父类型
	 */
	private String billParentType;

	/**
	 * 未核销金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal unverifyAmount;

	/**
	 * 审核状态
	 */
	private String auditStatus;
	
	/**
	 * 版本号
	 */
	private Short versionNo;

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
	 * @return unverifyAmount
	 */
	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}

	/**
	 * @param unverifyAmount
	 */
	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
	}

	/**
	 * @return auditStatus
	 */
	public String getAuditStatus() {
		return auditStatus;
	}

	/**
	 * @param auditStatus
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * @return billParentType
	 */
	public String getBillParentType() {
		return billParentType;
	}

	/**
	 * @param billParentType
	 */
	public void setBillParentType(String billParentType) {
		this.billParentType = billParentType;
	}

	/**
	 * @GET
	 * @return versionNo
	 */
	public Short getVersionNo() {
		/*
		 *@get
		 *@ return versionNo
		 */
		return versionNo;
	}

	/**
	 * @SET
	 * @param versionNo
	 */
	public void setVersionNo(Short versionNo) {
		/*
		 *@set
		 *@this.versionNo = versionNo
		 */
		this.versionNo = versionNo;
	}

}
