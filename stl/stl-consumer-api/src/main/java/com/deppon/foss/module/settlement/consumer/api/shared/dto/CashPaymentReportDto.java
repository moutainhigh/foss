package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 现金支出报表Dto
 * 
 * @author foss-zhangxiaohui
 * @date Dec 11, 2012 2:34:19 PM
 */
public class CashPaymentReportDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2344474786161368L;

	/**
	 * 开始日期
	 */
	private Date startDate;

	/**
	 * 结束日期
	 */
	private Date endDate;

	/**
	 * 单据子类型
	 */
	private String sourceBillType;

	/**
	 * 付款部门编码
	 */
	private String paymentOrgCode;

	/**
	 * 付款单部门
	 */
	private String paymentOrgName;

	/**
	 * 数据库符合条件的数据总条数
	 */
	private int totalRecordsInDB;

	/**
	 * 合计总金额
	 */
	private BigDecimal totalAmount;

	/**
	 * 审核状态
	 */
	private String auditStatus;

	/**
	 * 付款方式
	 */
	private String paymentType;
	
	/**
	 * 员工工号
	 */
	private String empCode;

	/**
	 * @return startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	 * @return paymentOrgCode
	 */
	public String getPaymentOrgCode() {
		return paymentOrgCode;
	}

	/**
	 * @param paymentOrgCode
	 */
	public void setPaymentOrgCode(String paymentOrgCode) {
		this.paymentOrgCode = paymentOrgCode;
	}

	/**
	 * @return paymentOrgName
	 */
	public String getPaymentOrgName() {
		return paymentOrgName;
	}

	/**
	 * @param paymentOrgName
	 */
	public void setPaymentOrgName(String paymentOrgName) {
		this.paymentOrgName = paymentOrgName;
	}

	/**
	 * @return totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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
	 * @return paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return totalRecordsInDB
	 */
	public int getTotalRecordsInDB() {
		return totalRecordsInDB;
	}

	/**
	 * @param totalRecordsInDB
	 */
	public void setTotalRecordsInDB(int totalRecordsInDB) {
		this.totalRecordsInDB = totalRecordsInDB;
	}

	/**
	 * @return empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @param  empCode  
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
}
