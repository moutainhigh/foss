package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 现金收入缴款报表明细DTO
 * 
 * @author 095793-foss-LiQin
 * @date 2012-12-3 下午4:34:27
 */
public class BillCashRecPayInDDto implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 报表编码
	 */
	private String reportNo;

	/**
	 * 来源单据单号
	 */
	private String sourceBillNo;

	/**
	 * 来源单据类型
	 */
	private String sourceBillType;

	/**
	 * 支付方式
	 */
	private String paymentType;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 收款部门编码
	 */
	private String collectionOrgCode;

	/**
	 * 收款部门名称
	 */
	private String collectionOrgName;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal amount;

	/**
	 * 未缴款 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal overdueAmount;

	/**
	 * 单据状态
	 */
	private String status;

	/**
	 * 已缴款 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal paidAmount;

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
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 收入部门名称
	 */
	private String generatingOrgName;

	
	/**
	 * 版本号
	 */
	private String version;
	
	
	/**
	 * 业务开始日期
	 */
	private Date businessStartDate;
	
	
	/**
	 * 业务结束日期
	 */
	private Date businessEndDate;
	
	
	/**
	 * 当前操作部门
	 */
	private String orgCode;
	
		
	/**
	 * 总条数
	 */
	private long totalCount;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	
	/**
	 * 客户类型
	 */
	private String customerType;
	
	
	/**
	 * 自定义导出列头
	 */
	private String[] arrayColumns;

	/**
	 * 自定义导出列中文名称
	 */
	private String[] arrayColumnNames;
	
	/**
	 * 付款方式
	 */
	private List<String> paymentTypes;
	
	
	/**
	 * 核销类型
	 */
	private int verifyType;
	
	/**
	 * 登陆人编码
	 */
	private String empCode;
	
	
	/**
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @return the verifyType
	 */
	public int getVerifyType() {
		return verifyType;
	}

	/**
	 * @param verifyType the verifyType to set
	 */
	public void setVerifyType(int verifyType) {
		this.verifyType = verifyType;
	}

	/**
	 * @return the paymentTypes
	 */
	public List<String> getPaymentTypes() {
		if(paymentTypes == null) {
			paymentTypes = new ArrayList<String>();
		}
		return paymentTypes;
	}

	/**
	 * @param paymentTypes the paymentTypes to set
	 */
	public void setPaymentTypes(List<String> paymentTypes) {
		this.paymentTypes = paymentTypes;
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
	 * @return collectionOrgCode
	 */
	public String getCollectionOrgCode() {
		return collectionOrgCode;
	}

	/**
	 * @param collectionOrgCode
	 */
	public void setCollectionOrgCode(String collectionOrgCode) {
		this.collectionOrgCode = collectionOrgCode;
	}

	/**
	 * @return collectionOrgName
	 */
	public String getCollectionOrgName() {
		return collectionOrgName;
	}

	/**
	 * @param collectionOrgName
	 */
	public void setCollectionOrgName(String collectionOrgName) {
		this.collectionOrgName = collectionOrgName;
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
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return generatingOrgName
	 */
	public String getGeneratingOrgName() {
		return generatingOrgName;
	}

	/**
	 * @param generatingOrgName
	 */
	public void setGeneratingOrgName(String generatingOrgName) {
		this.generatingOrgName = generatingOrgName;
	}

	/**
	 * @return generatingOrgCode
	 */
	public String getGeneratingOrgCode() {
		return generatingOrgCode;
	}

	/**
	 * @param generatingOrgCode
	 */
	public void setGeneratingOrgCode(String generatingOrgCode) {
		this.generatingOrgCode = generatingOrgCode;
	}

	/**
	 * @return cashConfirmTime
	 */
	public Date getCashConfirmTime() {
		return cashConfirmTime;
	}

	/**
	 * @param cashConfirmTime
	 */
	public void setCashConfirmTime(Date cashConfirmTime) {
		this.cashConfirmTime = cashConfirmTime;
	}

	/**
	 * @return cashConfirmUserCode
	 */
	public String getCashConfirmUserCode() {
		return cashConfirmUserCode;
	}

	/**
	 * @param cashConfirmUserCode
	 */
	public void setCashConfirmUserCode(String cashConfirmUserCode) {
		this.cashConfirmUserCode = cashConfirmUserCode;
	}

	/**
	 * @return cashConfirmUserName
	 */
	public String getCashConfirmUserName() {
		return cashConfirmUserName;
	}

	/**
	 * @param cashConfirmUserName
	 */
	public void setCashConfirmUserName(String cashConfirmUserName) {
		this.cashConfirmUserName = cashConfirmUserName;
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
	 * 收入部门编码
	 */
	private String generatingOrgCode;

	/**
	 * 收银确认时间
	 */
	private Date cashConfirmTime;

	/**
	 * 收银确认人工号
	 */
	private String cashConfirmUserCode;

	/**
	 * 收银确认人名称
	 */
	private String cashConfirmUserName;

	/**
	 * 记账日期
	 */
	private Date accountDate;

	
	/**
	 * @return  version
	 */
	public String getVersion() {
		return version;
	}

	
	/**
	 * @param  version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Get
	 * @return businessStartDate
	 */
	
	public Date getBusinessStartDate() {
		return businessStartDate;
	}

	/**
	 * Set
	 * @param businessStartDate
	 */
	public void setBusinessStartDate(Date businessStartDate) {
		this.businessStartDate = businessStartDate;
	}

	/**
	 * Get
	 * @return businessEndDate
	 */
	
	public Date getBusinessEndDate() {
		return businessEndDate;
	}

	/**
	 * Set
	 * @param businessEndDate
	 */
	public void setBusinessEndDate(Date businessEndDate) {
		this.businessEndDate = businessEndDate;
	}

	/**
	 * Get
	 * @return orgCode
	 */
	
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Set
	 * @param orgCode
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Get
	 * @return totalCount
	 */
	
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * Set
	 * @param totalCount
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * Get
	 * @return modifyTime
	 */
	
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * Set
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * Get
	 * @return waybillNo
	 */
	
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Set
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Get
	 * @return customerType
	 */
	
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * Set
	 * @param customerType
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * Get
	 * @return arrayColumns
	 */
	
	public String[] getArrayColumns() {
		return arrayColumns;
	}

	/**
	 * Set
	 * @param arrayColumns
	 */
	public void setArrayColumns(String[] arrayColumns) {
		this.arrayColumns = arrayColumns;
	}

	/**
	 * Get
	 * @return arrayColumnNames
	 */
	
	public String[] getArrayColumnNames() {
		return arrayColumnNames;
	}

	/**
	 * Set
	 * @param arrayColumnNames
	 */
	public void setArrayColumnNames(String[] arrayColumnNames) {
		this.arrayColumnNames = arrayColumnNames;
	}
}
