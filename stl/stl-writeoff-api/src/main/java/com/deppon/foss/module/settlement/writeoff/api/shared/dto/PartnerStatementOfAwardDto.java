package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.writeoff.api.shared.define.SettlementWriteoffConstants;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardEntity;

public class PartnerStatementOfAwardDto {

	/**
	 * 对账单明细
	 */
	private List<PartnerStatementOfAwardDEntity> partnerStatementOfAwardDList;
	/**
	 * 对账单
	 */
	private List<PartnerStatementOfAwardEntity> partnerStatementOfAwardList;
	/**
	 * 奖罚对账单实体
	 */
	private PartnerStatementOfAwardEntity partnerStatementOfAwardEntity;
	/**
	 * 是否包含应收单
	 */
	private Boolean isReceivable = false;

	/**
	 * 是否包含应付单
	 */
	private Boolean isPayable = false;
	/**
	 * 单号集合
	 */
	private List<String> numbers;
	/**
	 * 对账单单号集合
	 */
	private List<String> statementBillNos;
	/**
	 * 运单单号集合
	 */
	private List<String> wayBillNos;
	/**
	 * 对账单单号
	 */
	private String statementBillNo;
	/**
	 * 期间开始日期
	 */
	private Date periodBeginDate;
	/**
	 * 期间结束日期
	 */
	private Date periodEndDate;
	/**
	 * 明细单据类型,如应收应付等
	 */
	private String[] billDetailTypes;
	/**
	 * 客户编码
	 */
	private String customerCode;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 用户工号
	 */
	private String empCode;
	/**
	 * 用户姓名
	 */
	private String empName;
	/**
	 * 查询类型
	 */
	private String queryTabType;
	/**
	 * 总行数
	 */
	private long count;
	/**
	 * 部门编码
	 */
	private String orgCode;
	/**
	 * 部门名称
	 */
	private String orgName;
	/**
	 * 所属子公司编码
	 */
	private String companyCode;

	/**
	 * 所属子公司名称
	 */
	private String companyName;

	/**
	 * 发生金额
	 */
	private BigDecimal periodAmount;
	/**
	 * 本期已还金额
	 */
	private BigDecimal paidAmount;
	/**
	 * 未核销金额
	 */
	private BigDecimal unpaidAmount;
	/**
	 * 应付金额
	 */
	private BigDecimal periodPayAmount;
	/**
	 * 应收金额
	 */
	private BigDecimal periodRecAmount;
	/**
	 * 本期剩余应收金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal periodUnverifyRecAmount;

	/**
	 * 本期剩余应付金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal periodUnverifyPayAmount;

	/**
	 * 单据类型
	 */
	private String billType;
	/**
	 * 确认状态
	 */
	private String confirmStatus;
	/**
	 * 结清状态
	 */
	private String settleStatus;

	/**
	 * 是否统一结算
	 */
	private String receivableUnified;

	/**
	 * 子公司账号
	 */
	private String companyAccountBankNo;

	/**
	 * 开户名
	 */
	private String accountUserName;

	/**
	 * 支行名称
	 */
	private String bankBranchName;

	/**
	 * 制作部门标杆编码
	 */
	private String unifiedCode;

	/**
	 * 确认时间
	 */
	private Date confirmTime;

	/**
	 * 确认人工号
	 */
	private String confirmUserCode;

	/**
	 * 确认人名称
	 */
	private String confirmUserName;

	/**
	 * 银行账号信息异常
	 */
	private String accountException;

	/**
	 * 备注
	 */
	private String notes;	
	
	/**
	 * 可查询部门列表
	 */
	private List<String> orgCodeList;
	
	/**
	 * 对账单部门
	 */
	private String statementOrgCode;
	
	/**
	 * 大区
	 */
	private String largeRegion;
	
	/**
	 * 小区
	 */
	private String smallRegion;
	
	/**
	 * 结账次数
	 */
	private Short settleNum;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 修改人编号
	 */
	private String modifyUserCode;
	
	/**
	 * 修改人姓名
	 */
	private String modifyUserName;
	
	/**
	 * 原始来源单号--应收应付单号
	 */
	private String sourceBillNo;
	
	/**
	 * 导出对账单列头英文名称
	 */
	private String[] arrayColumns;
	/**
	 * 导出对账单列头中文名称
	 */
	private String[] arrayColumnNames;
	
	
	/******************常量设置*******************/
	
	/**
	 * 结账状态--已结清
	 */
	
	private static  String statementSettleStatus = SettlementWriteoffConstants.STATEMENT_SETTLEMENTSTATE_SETTLETYPE;

	/**
	 * 结账状态--未结清
	 */
	
	private static  String statementUnSettleStatus = SettlementWriteoffConstants.STATEMENT_SETTLEMENTSTATE_UNSETTLEEDTYPE;	
	
	public List<PartnerStatementOfAwardDEntity> getPartnerStatementOfAwardDList() {
		return partnerStatementOfAwardDList;
	}

	public void setPartnerStatementOfAwardDList(
			List<PartnerStatementOfAwardDEntity> partnerStatementOfAwardDList) {
		this.partnerStatementOfAwardDList = partnerStatementOfAwardDList;
	}

	public List<PartnerStatementOfAwardEntity> getPartnerStatementOfAwardList() {
		return partnerStatementOfAwardList;
	}

	public void setPartnerStatementOfAwardList(
			List<PartnerStatementOfAwardEntity> partnerStatementOfAwardList) {
		this.partnerStatementOfAwardList = partnerStatementOfAwardList;
	}

	public List<String> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<String> numbers) {
		this.numbers = numbers;
	}

	public String getStatementBillNo() {
		return statementBillNo;
	}

	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
	}

	public Date getPeriodBeginDate() {
		return periodBeginDate;
	}

	public void setPeriodBeginDate(Date periodBeginDate) {
		this.periodBeginDate = periodBeginDate;
	}

	public Date getPeriodEndDate() {
		return periodEndDate;
	}

	public void setPeriodEndDate(Date periodEndDate) {
		this.periodEndDate = periodEndDate;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getQueryTabType() {
		return queryTabType;
	}

	public void setQueryTabType(String queryTabType) {
		this.queryTabType = queryTabType;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public BigDecimal getPeriodAmount() {
		return periodAmount;
	}

	public void setPeriodAmount(BigDecimal periodAmount) {
		this.periodAmount = periodAmount;
	}

	public BigDecimal getUnpaidAmount() {
		return unpaidAmount;
	}

	public void setUnpaidAmount(BigDecimal unpaidAmount) {
		this.unpaidAmount = unpaidAmount;
	}

	public BigDecimal getPeriodPayAmount() {
		return periodPayAmount;
	}

	public void setPeriodPayAmount(BigDecimal periodPayAmount) {
		this.periodPayAmount = periodPayAmount;
	}

	public BigDecimal getPeriodRecAmount() {
		return periodRecAmount;
	}

	public void setPeriodRecAmount(BigDecimal periodRecAmount) {
		this.periodRecAmount = periodRecAmount;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public String getSettleStatus() {
		return settleStatus;
	}

	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}

	public String getReceivableUnified() {
		return receivableUnified;
	}

	public void setReceivableUnified(String receivableUnified) {
		this.receivableUnified = receivableUnified;
	}

	public String getCompanyAccountBankNo() {
		return companyAccountBankNo;
	}

	public void setCompanyAccountBankNo(String companyAccountBankNo) {
		this.companyAccountBankNo = companyAccountBankNo;
	}

	public String getAccountUserName() {
		return accountUserName;
	}

	public void setAccountUserName(String accountUserName) {
		this.accountUserName = accountUserName;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public String getUnifiedCode() {
		return unifiedCode;
	}

	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getConfirmUserCode() {
		return confirmUserCode;
	}

	public void setConfirmUserCode(String confirmUserCode) {
		this.confirmUserCode = confirmUserCode;
	}

	public String getConfirmUserName() {
		return confirmUserName;
	}

	public void setConfirmUserName(String confirmUserName) {
		this.confirmUserName = confirmUserName;
	}

	/**
	 * @return billDetailTypes
	 */
	public String[] getBillDetailTypes() {
		return billDetailTypes;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @param billDetailTypes
	 */
	public void setBillDetailTypes(String[] billDetailTypes) {
		this.billDetailTypes = billDetailTypes;
		/**
		 * 将数据转换成4个boolean类型的值
		 */

		// 如果单据类型不为空
		if (billDetailTypes != null && billDetailTypes.length > 0) {
			// 循环处理
			for (int i = 0; i < billDetailTypes.length; i++) {
				// 如果为应收单类型
				if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE
						.equals(billDetailTypes[i])) {
					// 设置包含应收单状态
					isReceivable = true;
					// 如果为应付单类型
				} else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE
						.equals(billDetailTypes[i])) {
					// 设置包含应付单状态
					isPayable = true;
				}
			}
		}
	}

	/**
	 * @GET
	 * @return accountException
	 */
	public String getAccountException() {
		/*
		 * @get
		 * 
		 * @ return accountException
		 */
		return accountException;
	}

	/**
	 * @SET
	 * @param accountException
	 */
	public void setAccountException(String accountException) {
		/*
		 * @set
		 * 
		 * @this.accountException = accountException
		 */
		this.accountException = accountException;
	}

	public PartnerStatementOfAwardEntity getPartnerStatementOfAwardEntity() {
		return partnerStatementOfAwardEntity;
	}

	public void setPartnerStatementOfAwardEntity(
			PartnerStatementOfAwardEntity partnerStatementOfAwardEntity) {
		this.partnerStatementOfAwardEntity = partnerStatementOfAwardEntity;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Boolean getIsReceivable() {
		return isReceivable;
	}

	public void setIsReceivable(Boolean isReceivable) {
		this.isReceivable = isReceivable;
	}

	public Boolean getIsPayable() {
		return isPayable;
	}

	public void setIsPayable(Boolean isPayable) {
		this.isPayable = isPayable;
	}

	public BigDecimal getPeriodUnverifyRecAmount() {
		return periodUnverifyRecAmount;
	}

	public void setPeriodUnverifyRecAmount(BigDecimal periodUnverifyRecAmount) {
		this.periodUnverifyRecAmount = periodUnverifyRecAmount;
	}

	public BigDecimal getPeriodUnverifyPayAmount() {
		return periodUnverifyPayAmount;
	}

	public void setPeriodUnverifyPayAmount(BigDecimal periodUnverifyPayAmount) {
		this.periodUnverifyPayAmount = periodUnverifyPayAmount;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public List<String> getOrgCodeList() {
		return orgCodeList;
	}

	public void setOrgCodeList(List<String> orgCodeList) {
		this.orgCodeList = orgCodeList;
	}

	public String getStatementOrgCode() {
		return statementOrgCode;
	}

	public void setStatementOrgCode(String statementOrgCode) {
		this.statementOrgCode = statementOrgCode;
	}

	public String getLargeRegion() {
		return largeRegion;
	}

	public void setLargeRegion(String largeRegion) {
		this.largeRegion = largeRegion;
	}

	public String getSmallRegion() {
		return smallRegion;
	}

	public void setSmallRegion(String smallRegion) {
		this.smallRegion = smallRegion;
	}

	public List<String> getStatementBillNos() {
		return statementBillNos;
	}

	public void setStatementBillNos(List<String> statementBillNos) {
		this.statementBillNos = statementBillNos;
	}

	public List<String> getWayBillNos() {
		return wayBillNos;
	}

	public void setWayBillNos(List<String> wayBillNos) {
		this.wayBillNos = wayBillNos;
	}

	public Short getSettleNum() {
		return settleNum;
	}

	public void setSettleNum(Short settleNum) {
		this.settleNum = settleNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static String getStatementSettleStatus() {
		return statementSettleStatus;
		
	}

	public static void setStatementSettleStatus(String statementSettleStatus) {
		PartnerStatementOfAwardDto.statementSettleStatus = statementSettleStatus;
	}

	public static String getStatementUnSettleStatus() {
		return statementUnSettleStatus;
	}

	public static void setStatementUnSettleStatus(String statementUnSettleStatus) {
		PartnerStatementOfAwardDto.statementUnSettleStatus = statementUnSettleStatus;
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

	public String getSourceBillNo() {
		return sourceBillNo;
	}

	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

	public String[] getArrayColumns() {
		return arrayColumns;
	}

	public void setArrayColumns(String[] arrayColumns) {
		this.arrayColumns = arrayColumns;
	}

	public String[] getArrayColumnNames() {
		return arrayColumnNames;
	}

	public void setArrayColumnNames(String[] arrayColumnNames) {
		this.arrayColumnNames = arrayColumnNames;
	}

	
	
	
}
