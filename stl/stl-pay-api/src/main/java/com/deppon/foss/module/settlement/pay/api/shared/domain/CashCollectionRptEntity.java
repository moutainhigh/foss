package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * （现金缴款报表）报表 Entity
 * 
 * 
 *  部门编码  、部门名称  、报表编码  、
 *   报表日期  、应缴款    、已缴款    、未缴款 
 *  、创建时间  、创建人 、营业款金额、预收款金额
 *  
 *   
 * @author ibm-pengzhen
 * @date 2012-10-10 下午12:34:42
 * @since
 * @version
 */
public class CashCollectionRptEntity extends BaseEntity {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = -81377200002986994L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 报表编码
	 */
	private String reportNo;

	/**
	 * 部门编码
	 */
	private String orgCode;

	/**
	 * 部门名称
	 */
	private String orgName;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 应缴款 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal duesAmount;

	/**
	 * 已缴款 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal paidAmount;

	/**
	 * 未缴款 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal overdueAmount;

	/**
	 * 营业款金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal clerksAmount;

	/**
	 * 非营业款金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal unclerksAmount;

	/**
	 * 预收款金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal precollectedAmount;

	/**
	 * 创建人工号
	 */
	private String createUserCode;

	/**
	 * 创建人名称
	 */
	private String createUserName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 备注
	 */
	private String notes;

	/**
	 * 版本号
	 */
	private Short versionNo;

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
	 * @return reportNo
	 */
	public String getReportNo() {
		return reportNo;
	}

	/**
	 * @param reportNo
	 */
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
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
	 * @return customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return duesAmount
	 */
	public BigDecimal getDuesAmount() {
		return duesAmount;
	}

	/**
	 * @param duesAmount
	 */
	public void setDuesAmount(BigDecimal duesAmount) {
		this.duesAmount = duesAmount;
	}

	/**
	 * @return paidAmount
	 */
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	/**
	 * @param paidAmount
	 */
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	/**
	 * @return overdueAmount
	 */
	public BigDecimal getOverdueAmount() {
		return overdueAmount;
	}

	/**
	 * @param overdueAmount
	 */
	public void setOverdueAmount(BigDecimal overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	/**
	 * @return clerksAmount
	 */
	public BigDecimal getClerksAmount() {
		return clerksAmount;
	}

	/**
	 * @param clerksAmount
	 */
	public void setClerksAmount(BigDecimal clerksAmount) {
		this.clerksAmount = clerksAmount;
	}

	/**
	 * @return unclerksAmount
	 */
	public BigDecimal getUnclerksAmount() {
		return unclerksAmount;
	}

	/**
	 * @param unclerksAmount
	 */
	public void setUnclerksAmount(BigDecimal unclerksAmount) {
		this.unclerksAmount = unclerksAmount;
	}

	/**
	 * @return precollectedAmount
	 */
	public BigDecimal getPrecollectedAmount() {
		return precollectedAmount;
	}

	/**
	 * @param precollectedAmount
	 */
	public void setPrecollectedAmount(BigDecimal precollectedAmount) {
		this.precollectedAmount = precollectedAmount;
	}

	/**
	 * @return createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param createUserCode
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return versionNo
	 */
	public Short getVersionNo() {
		return versionNo;
	}

	/**
	 * @param versionNo
	 */
	public void setVersionNo(Short versionNo) {
		this.versionNo = versionNo;
	}

}