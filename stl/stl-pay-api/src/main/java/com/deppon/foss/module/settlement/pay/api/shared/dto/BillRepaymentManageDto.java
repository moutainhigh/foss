package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 还款单DTO
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-16 下午8:30:15
 */
/**
 * 
 * @author 095793-foss-LiQin
 * @date 2012-12-18 上午9:55:44
 */
public class BillRepaymentManageDto implements Serializable {

	/**
	 * 还款单序列号
	 */
	private static final long serialVersionUID = 9044270534281367320L;

	/**
	 * 还款单ID
	 */
	private String ID;

	/**
	 *  还款单号
	 */
	private String repaymentNo;

	// 还款单业务开始日期
	private Date businessStartDate;

	// 还款单业务结速日期
	private Date businessEndDate;

	// 还款单记账开始日期
	private Date accountStartDate;

	// 还款单记账结束日期
	private Date accountEndDate;

	// 收入部门编号
	private String generatingOrgCode;

	// 收入部门名称
	private String generatingOrgName;

	// 客户编号
	private String customerCode;

	// 客户名称
	private String customerName;

	// 是否有效
	private String active;

	// 单据状态
	private String status;

	// 界面勾选的还款单编号
	private String selectBillRepayNos;

	// 大区名称
	private String largeArea;
	
	
	// 小区名称
	private String smallArea;

	// 还款方式
	private String paymentType;

	// 对账单号
	private String statementBillNo;

	// 还款部门编号
	private String collectionOrgCode;
	
	//还款部门List
	private List <String> collectionOrgCodeList=new ArrayList<String>();

	// 还款部门名称
	private String collectionOrgName;
	

	// 还款金额
	private BigDecimal amount;

	/**
	 *  反核销金额
	 */
	private BigDecimal bverifyAmount;

	/**
	 *  实际还款金额
	 */
	private BigDecimal trueAmount;

	/**
	 *  审核状态
	 */
	private String auditStatus;

	/**
	 *  备注
	 */
	private String notes;

	/**
	 *  核销类型
	 */
	private List<String> writeoffType;

	/**
	 *  还款条数
	 */
	private Long repayTotalRows;

	/**
	 *  是否红单*/	
	private String isRedBack;

	/**
	 * 还款单集合
	 */
	private List<String> repaymentNos;

	/**
	 * 对账单集合
	 */
	private List<String> statementBillNos;

	/**
	 * 运单号集合
	 */
	private List<String> wayBillNos;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 来源单号集合
	 */
	private List<String>sourceBillNos;

	// 审核状态
	private List<String> auditStatusList;

	// 还款方式
	private List<String> paymentTypeList;

	// 币种
	private String currencyCode;

	// 单据类型
	private String billType;

	// 收款部门所属子公司编码
	private String collectionCompanyCode;
	
	//收款部门所属公司名称
	private String collectionCompanyName;

	//
	/**
	 * 来源单据类型
	 */
	private String sourceBillType;
	

	// 收入部门所属子公司编码
	private String generatingCompanyCode;

	// 收入部门所属子公司名称
	private String generatingCompanyName;

	// 确定收入日期
	private Date conrevenDate;

	// 版本号
	private String versionNo;

	// 来源单号编号
	private String sourceBillNo;

	// 业务时间
	private Date businessDate;

	// 账号日期
	private Date accountDate;

	// 查询Tab
	private String queryType;
	

	// 日期查询radio
	private String queryDateFlag;
	
	
	/**
	 * 是否初始化
	 */
	private String isInit;

	
	/**
	 * 生成方式
	 */
	private String createType;
	
	
	/**
	 * OA汇款编号 
	 */
	private String oaPaymentNo;
	
	
	/**
	 * 录入部门编码
	 */
	private String createOrgCode;
	
	
	/**
	 * 录入部门名称
	 */
	private String createOrgName;
	
	
	/**
	 * 在线支付编号
	 */
	private String onlinePaymentNo;
	
	
	/**
	 * 审核人工号
	 */
	private String auditUserCode;
	
	
	/**
	 * 审核人名称
	 */
	private String auditUserName;
	
	
	/**
	 * 修改人工号
	 */
	private String modifyUserCode;
	
	
	/**
	 * 修改人名称
	 */
	private String modifyUserName;
	
	
	/**
	 * 收银确认人工号
	 */
	private String cashConfirmUserCode;
	
	
	/**
	 * 收银确认人名称
	 */
	private String cashConfirmUserName;
	
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	
	/**
	 * 做废时间
	 */
	private Date cashConfirmTime;
	
	
	/**
	 * 自定义导出列头
	 */
	private String[] arrayColumns;

	/**
	 * 自定义导出列中文名称
	 */
	private String[] arrayColumnNames;

	/**
	 * 当前登录用户员工编码
	 */
	private String empCode;

	/**
	 * oa汇款人
	 */
	private String oaPayee;
	
	/**
	 * POS串号
	 */
	private String posSerialNum;
	
	/**
	 * 银联交易流水号
	 */
	private String batchNo;
	
	/**
	 * 来源单号集合
	 */
	private List<String> batchNos;
	
	
	/**
	 * @get
	 * @return oaPayee
	 */
	public String getOaPayee() {
		/*
		 * @get
		 * @return oaPayee
		 */
		return oaPayee;
	}


	
	/**
	 * @set
	 * @param oaPayee
	 */
	public void setOaPayee(String oaPayee) {
		/*
		 *@set
		 *@this.oaPayee = oaPayee
		 */
		this.oaPayee = oaPayee;
	}


	/**
	 * @return iD
	 */
	public String getID() {
		return ID;
	}


	/**
	 * @param iD
	 */
	public void setID(String iD) {
		ID = iD;
	}


	/**
	 * @return repaymentNo
	 */
	public String getRepaymentNo() {
		return repaymentNo;
	}

	/**
	 * @param repaymentNo
	 */
	public void setRepaymentNo(String repaymentNo) {
		this.repaymentNo = repaymentNo;
	}

	/**
	 * @return businessStartDate
	 */
	public Date getBusinessStartDate() {
		return businessStartDate;
	}

	/**
	 * @param businessStartDate
	 */
	public void setBusinessStartDate(Date businessStartDate) {
		this.businessStartDate = businessStartDate;
	}

	/**
	 * @return businessEndDate
	 */
	public Date getBusinessEndDate() {
		return businessEndDate;
	}

	/**
	 * @param businessEndDate
	 */
	public void setBusinessEndDate(Date businessEndDate) {
		this.businessEndDate = businessEndDate;
	}

	/**
	 * @return accountStartDate
	 */
	public Date getAccountStartDate() {
		return accountStartDate;
	}

	/**
	 * @param accountStartDate
	 */
	public void setAccountStartDate(Date accountStartDate) {
		this.accountStartDate = accountStartDate;
	}

	/**
	 * @return accountEndDate
	 */
	public Date getAccountEndDate() {
		return accountEndDate;
	}

	/**
	 * @param accountEndDate
	 */
	public void setAccountEndDate(Date accountEndDate) {
		this.accountEndDate = accountEndDate;
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
	 * @return selectBillRepayNos
	 */
	public String getSelectBillRepayNos() {
		return selectBillRepayNos;
	}

	/**
	 * @param selectBillRepayNos
	 */
	public void setSelectBillRepayNos(String selectBillRepayNos) {
		this.selectBillRepayNos = selectBillRepayNos;
	}

	/**
	 * @return largeArea
	 */
	public String getLargeArea() {
		return largeArea;
	}

	/**
	 * @param largeArea
	 */
	public void setLargeArea(String largeArea) {
		this.largeArea = largeArea;
	}

	/**
	 * @return smallArea
	 */
	public String getSmallArea() {
		return smallArea;
	}

	/**
	 * @param smallArea
	 */
	public void setSmallArea(String smallArea) {
		this.smallArea = smallArea;
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
	 * @return bverifyAmount
	 */
	public BigDecimal getBverifyAmount() {
		return bverifyAmount;
	}

	/**
	 * @param bverifyAmount
	 */
	public void setBverifyAmount(BigDecimal bverifyAmount) {
		this.bverifyAmount = bverifyAmount;
	}

	/**
	 * @return trueAmount
	 */
	public BigDecimal getTrueAmount() {
		return trueAmount;
	}

	/**
	 * @param trueAmount
	 */
	public void setTrueAmount(BigDecimal trueAmount) {
		this.trueAmount = trueAmount;
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
	 * @return writeoffType
	 */
	public List<String> getWriteoffType() {
		return writeoffType;
	}

	/**
	 * @param writeoffType
	 */
	public void setWriteoffType(List<String> writeoffType) {
		this.writeoffType = writeoffType;
	}

	/**
	 * @return repayTotalRows
	 */
	public Long getRepayTotalRows() {
		return repayTotalRows;
	}

	/**
	 * @param repayTotalRows
	 */
	public void setRepayTotalRows(Long repayTotalRows) {
		this.repayTotalRows = repayTotalRows;
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
	 * @return repaymentNos
	 */
	public List<String> getRepaymentNos() {
		return repaymentNos;
	}

	/**
	 * @param repaymentNos
	 */
	public void setRepaymentNos(List<String> repaymentNos) {
		this.repaymentNos = repaymentNos;
	}

	/**
	 * @return statementBillNos
	 */
	public List<String> getStatementBillNos() {
		return statementBillNos;
	}

	/**
	 * @param statementBillNos
	 */
	public void setStatementBillNos(List<String> statementBillNos) {
		this.statementBillNos = statementBillNos;
	}

	/**
	 * @return wayBillNos
	 */
	public List<String> getWayBillNos() {
		return wayBillNos;
	}

	/**
	 * @param wayBillNos
	 */
	public void setWayBillNos(List<String> wayBillNos) {
		this.wayBillNos = wayBillNos;
	}

	/**
	 * @return auditStatusList
	 */
	public List<String> getAuditStatusList() {
		return auditStatusList;
	}

	/**
	 * @param auditStatusList
	 */
	public void setAuditStatusList(List<String> auditStatusList) {
		this.auditStatusList = auditStatusList;
	}

	/**
	 * @return paymentTypeList
	 */
	public List<String> getPaymentTypeList() {
		return paymentTypeList;
	}

	/**
	 * @param paymentTypeList
	 */
	public void setPaymentTypeList(List<String> paymentTypeList) {
		this.paymentTypeList = paymentTypeList;
	}

	/**
	 * @return currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return billType
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * @param billType
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * @return collectionCompanyCode
	 */
	public String getCollectionCompanyCode() {
		return collectionCompanyCode;
	}

	/**
	 * @param collectionCompanyCode
	 */
	public void setCollectionCompanyCode(String collectionCompanyCode) {
		this.collectionCompanyCode = collectionCompanyCode;
	}

	/**
	 * @return generatingCompanyCode
	 */
	public String getGeneratingCompanyCode() {
		return generatingCompanyCode;
	}

	/**
	 * @param generatingCompanyCode
	 */
	public void setGeneratingCompanyCode(String generatingCompanyCode) {
		this.generatingCompanyCode = generatingCompanyCode;
	}

	/**
	 * @return generatingCompanyName
	 */
	public String getGeneratingCompanyName() {
		return generatingCompanyName;
	}

	/**
	 * @param generatingCompanyName
	 */
	public void setGeneratingCompanyName(String generatingCompanyName) {
		this.generatingCompanyName = generatingCompanyName;
	}

	/**
	 * @return conrevenDate
	 */
	public Date getConrevenDate() {
		return conrevenDate;
	}

	/**
	 * @param conrevenDate
	 */
	public void setConrevenDate(Date conrevenDate) {
		this.conrevenDate = conrevenDate;
	}

	/**
	 * @return versionNo
	 */
	public String getVersionNo() {
		return versionNo;
	}

	/**
	 * @param versionNo
	 */
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
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
	 * @return queryType
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * @param queryType
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	/**
	 * @return queryDateFlag
	 */
	public String getQueryDateFlag() {
		return queryDateFlag;
	}

	/**
	 * @param queryDateFlag
	 */
	public void setQueryDateFlag(String queryDateFlag) {
		this.queryDateFlag = queryDateFlag;
	}

	/**
	 * @return arrayColumns
	 */
	public String[] getArrayColumns() {
		return arrayColumns;
	}

	/**
	 * @param arrayColumns
	 */
	public void setArrayColumns(String[] arrayColumns) {
		this.arrayColumns = arrayColumns;
	}

	/**
	 * @return arrayColumnNames
	 */
	public String[] getArrayColumnNames() {
		return arrayColumnNames;
	}

	/**
	 * @param arrayColumnNames
	 */
	public void setArrayColumnNames(String[] arrayColumnNames) {
		this.arrayColumnNames = arrayColumnNames;
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
	 * @return collectionCompanyName
	 */
	public String getCollectionCompanyName() {
		return collectionCompanyName;
	}

	/**
	 * @param collectionCompanyName
	 */
	public void setCollectionCompanyName(String collectionCompanyName) {
		this.collectionCompanyName = collectionCompanyName;
	}

	
	/**
	 * @return  collectionOrgCodeList
	 */
	public List<String> getCollectionOrgCodeList() {
		return collectionOrgCodeList;
	}

	
	/**
	 * @param  collectionOrgCodeList
	 */
	public void setCollectionOrgCodeList(List<String> collectionOrgCodeList) {
		this.collectionOrgCodeList = collectionOrgCodeList;
	}

	
	/**
	 * @return  sourceBillNos
	 */
	public List<String> getSourceBillNos() {
		return sourceBillNos;
	}

	
	/**
	 * @param  sourceBillNos
	 */
	public void setSourceBillNos(List<String> sourceBillNos) {
		this.sourceBillNos = sourceBillNos;
	}


	/**
	 * Get
	 * @return isInit
	 */
	
	public String getIsInit() {
		return isInit;
	}


	/**
	 * Set
	 * @param isInit
	 */
	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}


	/**
	 * Get
	 * @return createType
	 */
	
	public String getCreateType() {
		return createType;
	}


	/**
	 * Set
	 * @param createType
	 */
	public void setCreateType(String createType) {
		this.createType = createType;
	}


	/**
	 * Get
	 * @return oaPaymentNo
	 */
	
	public String getOaPaymentNo() {
		return oaPaymentNo;
	}


	/**
	 * Set
	 * @param oaPaymentNo
	 */
	public void setOaPaymentNo(String oaPaymentNo) {
		this.oaPaymentNo = oaPaymentNo;
	}


	/**
	 * Get
	 * @return createOrgCode
	 */
	
	public String getCreateOrgCode() {
		return createOrgCode;
	}


	/**
	 * Set
	 * @param createOrgCode
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}


	/**
	 * Get
	 * @return createOrgName
	 */
	
	public String getCreateOrgName() {
		return createOrgName;
	}


	/**
	 * Set
	 * @param createOrgName
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}


	/**
	 * Get
	 * @return onlinePaymentNo
	 */
	
	public String getOnlinePaymentNo() {
		return onlinePaymentNo;
	}


	/**
	 * Set
	 * @param onlinePaymentNo
	 */
	public void setOnlinePaymentNo(String onlinePaymentNo) {
		this.onlinePaymentNo = onlinePaymentNo;
	}


	/**
	 * Get
	 * @return auditUserCode
	 */
	
	public String getAuditUserCode() {
		return auditUserCode;
	}


	/**
	 * Set
	 * @param auditUserCode
	 */
	public void setAuditUserCode(String auditUserCode) {
		this.auditUserCode = auditUserCode;
	}


	/**
	 * Get
	 * @return auditUserName
	 */
	
	public String getAuditUserName() {
		return auditUserName;
	}


	/**
	 * Set
	 * @param auditUserName
	 */
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}


	/**
	 * Get
	 * @return modifyUserCode
	 */
	
	public String getModifyUserCode() {
		return modifyUserCode;
	}


	/**
	 * Set
	 * @param modifyUserCode
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}


	/**
	 * Get
	 * @return modifyUserName
	 */
	
	public String getModifyUserName() {
		return modifyUserName;
	}


	/**
	 * Set
	 * @param modifyUserName
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}


	/**
	 * Get
	 * @return cashConfirmUserCode
	 */
	
	public String getCashConfirmUserCode() {
		return cashConfirmUserCode;
	}


	/**
	 * Set
	 * @param cashConfirmUserCode
	 */
	public void setCashConfirmUserCode(String cashConfirmUserCode) {
		this.cashConfirmUserCode = cashConfirmUserCode;
	}


	/**
	 * Get
	 * @return cashConfirmUserName
	 */
	
	public String getCashConfirmUserName() {
		return cashConfirmUserName;
	}


	/**
	 * Set
	 * @param cashConfirmUserName
	 */
	public void setCashConfirmUserName(String cashConfirmUserName) {
		this.cashConfirmUserName = cashConfirmUserName;
	}

	
	/**
	 * Get
	 * @return createTime
	 */
	
	public Date getCreateTime() {
		return createTime;
	}



	/**
	 * Set
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * @return cashConfirmTime
	 */
	
	public Date getCashConfirmTime() {
		return cashConfirmTime;
	}



	/**
	 * Set
	 * @param cashConfirmTime
	 */
	public void setCashConfirmTime(Date cashConfirmTime) {
		this.cashConfirmTime = cashConfirmTime;
	}



	/**
	 * Get
	 * @return empCode
	 */
	
	public String getEmpCode() {
		return empCode;
	}


	/**
	 * Set
	 * @param empCode
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}



	/**
	 * @GET
	 * @return posSerialNum
	 */
	public String getPosSerialNum() {
		/*
		 *@get
		 *@ return posSerialNum
		 */
		return posSerialNum;
	}



	/**
	 * @SET
	 * @param posSerialNum
	 */
	public void setPosSerialNum(String posSerialNum) {
		/*
		 *@set
		 *@this.posSerialNum = posSerialNum
		 */
		this.posSerialNum = posSerialNum;
	}



	/**
	 * @GET
	 * @return batchNo
	 */
	public String getBatchNo() {
		/*
		 *@get
		 *@ return batchNo
		 */
		return batchNo;
	}



	/**
	 * @SET
	 * @param batchNo
	 */
	public void setBatchNo(String batchNo) {
		/*
		 *@set
		 *@this.batchNo = batchNo
		 */
		this.batchNo = batchNo;
	}



	/**
	 * @GET
	 * @return batchNos
	 */
	public List<String> getBatchNos() {
		/*
		 *@get
		 *@ return batchNos
		 */
		return batchNos;
	}



	/**
	 * @SET
	 * @param batchNos
	 */
	public void setBatchNos(List<String> batchNos) {
		/*
		 *@set
		 *@this.batchNos = batchNos
		 */
		this.batchNos = batchNos;
	}



	/**
	 * @return the wayBillNo
	 */
	public String getWayBillNo() {
		return waybillNo;
	}



	/**
	 * @param wayBillNo the wayBillNo to set
	 */
	public void setWayBillNo(String wayBillNo) {
		this.waybillNo = wayBillNo;
	}
	
	
	
}
