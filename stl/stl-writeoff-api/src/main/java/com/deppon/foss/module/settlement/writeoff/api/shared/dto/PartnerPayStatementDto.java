/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.writeoff.api.shared.define.SettlementWriteoffConstants;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerPayStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerPayStatementEntity;

public class PartnerPayStatementDto {
	/**
	 * 到达提成
	 */
	@SuppressWarnings("unused")
	private static final String pdfpType = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE;
	
	/**
	 * 委托派费
	 */
	@SuppressWarnings("unused")
	private static final String pddfType = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE;
	/**
	 * 已结清
	 */
	@SuppressWarnings("unused")
	private static final String statementSettleStatus = SettlementWriteoffConstants.STATEMENT_SETTLEMENTSTATE_SETTLETYPE;
	
	/**
	 * 未结清
	 */
	@SuppressWarnings("unused")
	private static final String statementUnSettleStatus = SettlementWriteoffConstants.STATEMENT_SETTLEMENTSTATE_UNSETTLEEDTYPE;
	/**
	 * 合伙人付款对账单实体
	 */
	private PartnerPayStatementEntity partnerPayStatementEntity;
	/**
	 * 对账单明细实体
	 */
	private PartnerPayStatementDEntity partnerPayStatementDEntity;
	/**
	 * 对账单明细list
	 */
	private List<PartnerPayStatementDEntity> partnerPayStatementDList;
	/**
	 * 对账单list
	 */
	private List<PartnerPayStatementEntity> partnerPayStatementList;
	/**
	 * 来源单号集合
	 */
	private List<String> sourceBillNosList;
	/**
	 *对账单号集合
	 */
	private List<String> statementBillNoList;
	/**
	 * 单号集合
	 */
	private String[] billDetailNos;
	/**
	 * 应付单单号集合
	 */
	private List<String> payableBillNosList;
	/**
	 * 付款单号集合
	 */
	private List<String> payableNosList;
	/**
	 * 查询tab页
	 */
	private String queryPage;
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
	 * 客户编码
	 */
	private String customerCode;
	/**
	 * 客户编码
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
	 * 发生金额
	 */
	private BigDecimal periodAmount;
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
	 * 总金额
	 * @return
	 */
	private BigDecimal totalAmount;
	/**
	 * 总条数
	 * @return
	 */
	private long totalCount;
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
	 * 可查询部门列表
	 */
	private List<String> orgCodeList;
	/**
	 * 来源页
	 * @return
	 */
	private String payType;
	/**
	 * id
	 * @return
	 */
	private String id;
	/**
	 * 应付单号
	 */
	private String payableNo;
	
	/**
	 * 对账单号列表集合
	 * @return
	 */
	private String[] statementBillNos;
	
	/**
	 * 结账次数
	 * @return
	 */
	private String settleNum;
	
	/**
	 * 费用承担部门
	 * @return
	 */
	private String expenseBearCode;
	/**
	 * 付款标记
	 * @return
	 */
	private String isPaid;
	
	public PartnerPayStatementEntity getPartnerPayStatementEntity() {
		return partnerPayStatementEntity;
	}

	public void setPartnerPayStatementEntity(
			PartnerPayStatementEntity partnerPayStatementEntity) {
		this.partnerPayStatementEntity = partnerPayStatementEntity;
	}

	public List<PartnerPayStatementDEntity> getPartnerPayStatementDList() {
		return partnerPayStatementDList;
	}

	public void setPartnerPayStatementDList(
			List<PartnerPayStatementDEntity> partnerPayStatementDList) {
		this.partnerPayStatementDList = partnerPayStatementDList;
	}

	public List<PartnerPayStatementEntity> getPartnerPayStatementList() {
		return partnerPayStatementList;
	}

	public void setPartnerPayStatementList(
			List<PartnerPayStatementEntity> partnerPayStatementList) {
		this.partnerPayStatementList = partnerPayStatementList;
	}

	public String[] getBillDetailNos() {
		return billDetailNos;
	}

	public void setBillDetailNos(String[] billDetailNos) {
		this.billDetailNos = billDetailNos;
	}

	public List<String> getPayableBillNosList() {
		return payableBillNosList;
	}

	public void setPayableBillNosList(List<String> payableBillNosList) {
		this.payableBillNosList = payableBillNosList;
	}

	

	public List<String> getPayableNosList() {
		return payableNosList;
	}

	public void setPayableNosList(List<String> payableNosList) {
		this.payableNosList = payableNosList;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	/**
	 * @return 
		queryPage
	 */
	public String getQueryPage() {
		return queryPage;
	}
	/**
	 * @param 
		queryPage
	 */
	public void setQueryPage(String queryPage) {
		this.queryPage = queryPage;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
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

	public List<String> getOrgCodeList() {
		return orgCodeList;
	}

	public void setOrgCodeList(List<String> orgCodeList) {
		this.orgCodeList = orgCodeList;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public PartnerPayStatementDEntity getPartnerPayStatementDEntity() {
		return partnerPayStatementDEntity;
	}

	public void setPartnerPayStatementDEntity(
			PartnerPayStatementDEntity partnerPayStatementDEntity) {
		this.partnerPayStatementDEntity = partnerPayStatementDEntity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getSourceBillNosList() {
		return sourceBillNosList;
	}

	public void setSourceBillNosList(List<String> sourceBillNosList) {
		this.sourceBillNosList = sourceBillNosList;
	}

	public String getPayableNo() {
		return payableNo;
	}

	public void setPayableNo(String payableNo) {
		this.payableNo = payableNo;
	}
	public String[] getStatementBillNos() {
		return statementBillNos;
	}

	public void setStatementBillNos(String[] statementBillNos) {
		this.statementBillNos = statementBillNos;
	}

	public String getSettleNum() {
		return settleNum;
	}

	public void setSettleNum(String settleNum) {
		this.settleNum = settleNum;
	}

	public String getExpenseBearCode() {
		return expenseBearCode;
	}

	public void setExpenseBearCode(String expenseBearCode) {
		this.expenseBearCode = expenseBearCode;
	}

	public List<String> getStatementBillNoList() {
		return statementBillNoList;
	}

	public void setStatementBillNoList(List<String> statementBillNoList) {
		this.statementBillNoList = statementBillNoList;
	}

	public String getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(String isPaid) {
		this.isPaid = isPaid;
	}
	
}
