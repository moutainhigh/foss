/*
 * PROJECT NAME: stl-pay-api
 * PACKAGE NAME: com.deppon.foss.module.
 *               settlement.pay.api.shared.domain
 * FILE    NAME: CashCollectionIncomeEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 现金收入报表缴款记录
 * 
 * 用于保存现金缴款记录
 * 
 * @author 095793-foss-LiQin
 * @date 2012-12-18 下午7:39:14
 */
public class CashCollectionIncomeEntity extends BaseEntity {

	/**
	 * 现金收入报表缴款记录序列号
	 */
	private static final long serialVersionUID = -1835373139711650930L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 网点编码（非标杆编码）
	 */
	private String orgCode;

	/**
	 * 缴款金额
	 */
	private BigDecimal amount;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 流水号
	 */
	private String serialno;

	/**
	 * 财务自助传递的标杆编码
	 */
	private String unifiedCode;

	/**
	 * 备注
	 */
	private String notes;

	/**
	 * 已缴款金额
	 */
	private BigDecimal paidAmount;

	/**
	 * 未缴款金额
	 */
	private BigDecimal overdueAmount;

	/**
	 * 报表编码
	 */
	private String reportNo;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 修改日期
	 */
	private Date modifyTime;

	/**
	 * 版本号
	 */
	private Short versionNo;
	
	/**
	 * 付款方式 add20131112
	 */
	private String paymentType;
	
	
	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * Get
	 * 
	 * @return amount
	 */

	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Set
	 * 
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * Get
	 * 
	 * @return serialno
	 */

	public String getSerialno() {
		return serialno;
	}

	/**
	 * Set
	 * 
	 * @param serialno
	 */
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	/**
	 * Get
	 * 
	 * @return unifiedCode
	 */

	public String getUnifiedCode() {
		return unifiedCode;
	}

	/**
	 * Set
	 * 
	 * @param unifiedCode
	 */
	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}

	/**
	 * Get
	 * 
	 * @return notes
	 */

	public String getNotes() {
		return notes;
	}

	/**
	 * Set
	 * 
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Get
	 * 
	 * @return paidAmount
	 */

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	/**
	 * Set
	 * 
	 * @param paidAmount
	 */
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	/**
	 * Get
	 * 
	 * @return overdueAmount
	 */

	public BigDecimal getOverdueAmount() {
		return overdueAmount;
	}

	/**
	 * Set
	 * 
	 * @param overdueAmount
	 */
	public void setOverdueAmount(BigDecimal overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	/**
	 * Get
	 * 
	 * @return reportNo
	 */

	public String getReportNo() {
		return reportNo;
	}

	/**
	 * Set
	 * 
	 * @param reportNo
	 */
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	/**
	 * Get
	 * 
	 * @return businessDate
	 */

	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * Set
	 * 
	 * @param businessDate
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * Get
	 * 
	 * @return modifyTime
	 */

	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * Set
	 * 
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * Get
	 * 
	 * @return versionNo
	 */

	public Short getVersionNo() {
		return versionNo;
	}

	/**
	 * Set
	 * 
	 * @param versionNo
	 */
	public void setVersionNo(Short versionNo) {
		this.versionNo = versionNo;
	}
}
