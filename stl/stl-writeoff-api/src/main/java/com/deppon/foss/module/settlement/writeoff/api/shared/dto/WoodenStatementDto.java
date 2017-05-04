package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.deppon.foss.module.settlement.writeoff.api.shared.define.SettlementWriteoffConstants;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.WoodenStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.WoodenStatementEntity;

public class WoodenStatementDto {
	
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
	 * 对账单明细
	 */
	private List<WoodenStatementDEntity> woodenStatementDList;
	/**
	 * 对账单
	 */
	private List<WoodenStatementEntity> woodenStatementList;
	/**
	 * 单号集合
	 */
	private List<String> numbers;
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
	
	public List<WoodenStatementDEntity> getWoodenStatementDList() {
		return woodenStatementDList;
	}

	public void setWoodenStatementDList(List<WoodenStatementDEntity> woodenStatementDList) {
		this.woodenStatementDList = woodenStatementDList;
	}

	public List<WoodenStatementEntity> getWoodenStatementList() {
		return woodenStatementList;
	}

	public void setWoodenStatementList(List<WoodenStatementEntity> woodenStatementList) {
		this.woodenStatementList = woodenStatementList;
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
	
}
