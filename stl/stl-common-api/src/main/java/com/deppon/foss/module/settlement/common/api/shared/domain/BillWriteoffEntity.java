package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 核销单(用于记录凡涉及到应收单的未收回金额、应付单的未支付金额发生变化的操作) 分区键记账日期 accountDate
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-10-16 上午10:04:53
 */
public class BillWriteoffEntity extends BaseEntity {

	/**
	 * 序列号编号
	 */
	private static final long serialVersionUID = 6958664602348764243L;

	/**
	 * 核销批次编号
	 */
	private String writeoffBatchNo;

	/**
	 * 核销单号
	 */
	private String writeoffBillNo;

	/**
	 * 开始来源运单
	 */
	private String beginWaybillNo;

	/**
	 * 开始单号记账日期
	 */
	private Date beginAccountDate;

	/**
	 * 目的来源运单
	 */
	private String endWaybillNo;

	/**
	 * 目的单号记账日期
	 */
	private Date endAccountDate;

	/**
	 * 开始单号
	 */
	private String beginNo;
	
	/**
	 * 开始id
	 */
	private String beginId;

	/**
	 * 目的单号
	 */
	private String endNo;
	
	/**
	 * 开始id
	 */
	private String endId;


	/**
	 * 对账单号
	 */
	private String statementBillNo;

	/**
	 * 核销方式
	 */
	private String createType;

	/**
	 * 核销类型
	 */
	private String writeoffType;

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
	 * 核销金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal amount;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 是否红单
	 */
	private String isRedBack;

	/**
	 * 核销人编码
	 */
	private String createUserCode;

	/**
	 * 核销人名称
	 */
	private String createUserName;

	/**
	 * 红冲人编码
	 */
	private String redImpactUserCode;

	/**
	 * 红冲人名称
	 */
	private String redImpactUserName;

	/**
	 * 核销时间
	 */
	private Date writeoffTime;

	/**
	 * 记账日期
	 */
	private Date accountDate;

	/**
	 * 是否初始化
	 */
	private String isInit;

	/**
	 * 版本号
	 */
	private Short versionNo;
	
	/**
	 * @return writeoffBatchNo
	 */
	public String getWriteoffBatchNo() {
		return writeoffBatchNo;
	}

	/**
	 * @param writeoffBatchNo
	 */
	public void setWriteoffBatchNo(String writeoffBatchNo) {
		this.writeoffBatchNo = writeoffBatchNo;
	}

	/**
	 * @return writeoffBillNo
	 */
	public String getWriteoffBillNo() {
		return writeoffBillNo;
	}

	/**
	 * @param writeoffBillNo
	 */
	public void setWriteoffBillNo(String writeoffBillNo) {
		this.writeoffBillNo = writeoffBillNo;
	}

	/**
	 * @return beginWaybillNo
	 */
	public String getBeginWaybillNo() {
		return beginWaybillNo;
	}

	/**
	 * @param beginWaybillNo
	 */
	public void setBeginWaybillNo(String beginWaybillNo) {
		this.beginWaybillNo = beginWaybillNo;
	}

	/**
	 * @return beginAccountDate
	 */
	public Date getBeginAccountDate() {
		return beginAccountDate;
	}

	/**
	 * @param beginAccountDate
	 */
	public void setBeginAccountDate(Date beginAccountDate) {
		this.beginAccountDate = beginAccountDate;
	}

	/**
	 * @return endWaybillNo
	 */
	public String getEndWaybillNo() {
		return endWaybillNo;
	}

	/**
	 * @param endWaybillNo
	 */
	public void setEndWaybillNo(String endWaybillNo) {
		this.endWaybillNo = endWaybillNo;
	}

	/**
	 * @return endAccountDate
	 */
	public Date getEndAccountDate() {
		return endAccountDate;
	}

	/**
	 * @param endAccountDate
	 */
	public void setEndAccountDate(Date endAccountDate) {
		this.endAccountDate = endAccountDate;
	}

	/**
	 * @return beginNo
	 */
	public String getBeginNo() {
		return beginNo;
	}

	/**
	 * @param beginNo
	 */
	public void setBeginNo(String beginNo) {
		this.beginNo = beginNo;
	}

	/**
	 * @return endNo
	 */
	public String getEndNo() {
		return endNo;
	}

	/**
	 * @param endNo
	 */
	public void setEndNo(String endNo) {
		this.endNo = endNo;
	}

	/**
	 * @return statementBillNo
	 */
	public String getStatementBillNo() {
		return statementBillNo;
	}

	/**
	 * @param statementBillNo
	 */
	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
	}

	/**
	 * @return createType
	 */
	public String getCreateType() {
		return createType;
	}

	/**
	 * @param createType
	 */
	public void setCreateType(String createType) {
		this.createType = createType;
	}

	/**
	 * @return writeoffType
	 */
	public String getWriteoffType() {
		return writeoffType;
	}

	/**
	 * @param writeoffType
	 */
	public void setWriteoffType(String writeoffType) {
		this.writeoffType = writeoffType;
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
	 * @return isRedBack
	 */
	public String getIsRedBack() {
		return isRedBack;
	}

	/**
	 * @param isRedBack
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
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
	 * @return redImpactUserCode
	 */
	public String getRedImpactUserCode() {
		return redImpactUserCode;
	}

	/**
	 * @param redImpactUserCode
	 */
	public void setRedImpactUserCode(String redImpactUserCode) {
		this.redImpactUserCode = redImpactUserCode;
	}

	/**
	 * @return redImpactUserName
	 */
	public String getRedImpactUserName() {
		return redImpactUserName;
	}

	/**
	 * @param redImpactUserName
	 */
	public void setRedImpactUserName(String redImpactUserName) {
		this.redImpactUserName = redImpactUserName;
	}

	/**
	 * @return writeoffTime
	 */
	public Date getWriteoffTime() {
		return writeoffTime;
	}

	/**
	 * @param writeoffTime
	 */
	public void setWriteoffTime(Date writeoffTime) {
		this.writeoffTime = writeoffTime;
	}

	/**
	 * @return accountDate
	 */
	public Date getAccountDate() {
		return accountDate;
	}

	/**
	 * @param accountDate
	 */
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	/**
	 * @return isInit
	 */
	public String getIsInit() {
		return isInit;
	}

	/**
	 * @param isInit
	 */
	public void setIsInit(String isInit) {
		this.isInit = isInit;
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

	/**
	 * @GET
	 * @return beginId
	 */
	public String getBeginId() {
		/*
		 *@get
		 *@ return beginId
		 */
		return beginId;
	}

	/**
	 * @SET
	 * @param beginId
	 */
	public void setBeginId(String beginId) {
		/*
		 *@set
		 *@this.beginId = beginId
		 */
		this.beginId = beginId;
	}

	/**
	 * @GET
	 * @return endId
	 */
	public String getEndId() {
		/*
		 *@get
		 *@ return endId
		 */
		return endId;
	}

	/**
	 * @SET
	 * @param endId
	 */
	public void setEndId(String endId) {
		/*
		 *@set
		 *@this.endId = endId
		 */
		this.endId = endId;
	}


}
