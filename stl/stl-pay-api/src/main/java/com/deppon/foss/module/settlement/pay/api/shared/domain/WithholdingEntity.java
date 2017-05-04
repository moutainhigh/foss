/**
 * 
 */
package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 045738
 * @预提实体
 */
public class WithholdingEntity implements Serializable  {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -906654447673340829L;
	
	/**
	 * id
	 */
	private String id;
	/**
	 * 预提单号
	 */
	private String withholdingNo;
	
	/**
	 * 费用承担部门编码
	 */
	private String costDeptCode;
	
	/**
	 * 费用承担部门名称
	 */
	private String costDeptName;
	
	/**
	 * 费用类型
	 */
	private String costType;
	
	/**
	 * 费用所属月份
	 */
	private Date costDate;
	
	/**
	 * 发票抬头
	 */
	private String invoiceCode;
	
	/**
	 * 发票抬头
	 */
	private String invoiceName;
	
	/**
	 * 预提金额
	 */
	private BigDecimal amount;
	
	/**
	 * 申请事由及说明
	 */
	private String notes;
	
	/**
	 * 预提工作流号
	 */
	private String workflowNo;
	
	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * 预提失败原因
	 */
	private String failReasion;
	
	/**
	 * 预提状态
	 */
	private String withholdingStatus;
	
	/**
	 * 创建部门编码
	 */
	private String createOrgCode;
	
	/**
	 * 创建部门名称
	 */
	private String createOrgName;
	
	/**
	 * 创建编码
	 */
	private String createUserCode;
	
	/**
	 * 创建名称
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
	 * 修改人编码
	 */
	private String  modifyUserCode;
	
	/**
	 * 修改人名称
	 */
	private String  modifyUserName;
	
	/**
	 * 失败原因
	 */
	private String failureReason;
	
	/**
	 * 费用承担部门类型
	 */
	private String costDeptType;
	
	


	public String getWithholdingNo() {
		return withholdingNo;
	}

	public void setWithholdingNo(String withholdingNo) {
		this.withholdingNo = withholdingNo;
	}

	public String getCostDeptCode() {
		return costDeptCode;
	}

	public void setCostDeptCode(String costDeptCode) {
		this.costDeptCode = costDeptCode;
	}

	public String getCostDeptName() {
		return costDeptName;
	}

	public void setCostDeptName(String costDeptName) {
		this.costDeptName = costDeptName;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public Date getCostDate() {
		return costDate;
	}

	public void setCostDate(Date costDate) {
		this.costDate = costDate;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getWorkflowNo() {
		return workflowNo;
	}

	public void setWorkflowNo(String workflowNo) {
		this.workflowNo = workflowNo;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getFailReasion() {
		return failReasion;
	}

	public void setFailReasion(String failReasion) {
		this.failReasion = failReasion;
	}

	public String getWithholdingStatus() {
		return withholdingStatus;
	}

	public void setWithholdingStatus(String withholdingStatus) {
		this.withholdingStatus = withholdingStatus;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public String getCostDeptType() {
		return costDeptType;
	}

	public void setCostDeptType(String costDeptType) {
		this.costDeptType = costDeptType;
	}
	
}
