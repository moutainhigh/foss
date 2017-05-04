package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

//import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.define.SettlementWriteoffConstants;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementEntity;

public class HomeItemDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	 * 付款单明细
	 */
	//private List<BillPaymentDEntity> billPaymentList;
	/**
	 * 对账单明细
	 */
	private List<HomeStatementDEntity> homeStatementDList;
	/**
	 * 对账单
	 */
	private List<HomeStatementEntity> homeList;
	/**
	 * 单号集合
	 */
	private List<String> numbers;
	/**
	 * 开始时间
	 */
	private Date curDate;
	/**
	 * 结束时间
	 */
	private Date preDate;
	/**
	 * 客户信息编码
	 */
	private String homeSupplyCode;
	/**
	 * 所属子公司编码
	 */
	private String subCompanyCode;
	/**
	 * 确认状态
	 */
	private String confirmStatus;
	/**
	 * 结账状态
	 */
	private String settled;
	/**
	 * 查询类型
	 */
	private String queryTabType;
	/**
	 * 家装对账单号
	 */
    private String statementBillNo;
    /**
	 * 部门编码
	 */
    private String orgCode;
    
    /**
     * 总行数
     * 
     */
	private long count;
	/**
	 * 用户工号
	 */
	private String empCode;
	/**
	 * 用户姓名
	 */
	private String empName;
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
    
	public Date getCurDate() {
		return curDate;
	}
	public void setCurDate(Date curDate) {
		this.curDate = curDate;
	}
	public Date getPreDate() {
		return preDate;
	}
	public void setPreDate(Date preDate) {
		this.preDate = preDate;
	}
	public String getHomeSupplyCode() {
		return homeSupplyCode;
	}
	public void setHomeSupplyCode(String homeSupplyCode) {
		this.homeSupplyCode = homeSupplyCode;
	}
	public String getSubCompanyCode() {
		return subCompanyCode;
	}
	public void setSubCompanyCode(String subCompanyCode) {
		this.subCompanyCode = subCompanyCode;
	}
	public String getConfirmStatus() {
		return confirmStatus;
	}
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	public String getSettled() {
		return settled;
	}
	public void setSettled(String settled) {
		this.settled = settled;
	}
	public String getQueryTabType() {
		return queryTabType;
	}
	public void setQueryTabType(String queryTabType) {
		this.queryTabType = queryTabType;
	}
	public String getStatementBillNo() {
		return statementBillNo;
	}
	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public List<HomeStatementEntity> getHomeList() {
		return homeList;
	}
	public void setHomeList(List<HomeStatementEntity> homeList) {
		this.homeList = homeList;
	}
	public List<String> getNumbers() {
		return numbers;
	}
	public void setNumbers(List<String> numbers) {
		this.numbers = numbers;
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
	public List<HomeStatementDEntity> getHomeStatementDList() {
		return homeStatementDList;
	}
	public void setHomeStatementDList(List<HomeStatementDEntity> homeStatementDList) {
		this.homeStatementDList = homeStatementDList;
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
	/*public List<BillPaymentDEntity> getBillPaymentList() {
		return billPaymentList;
	}
	public void setBillPaymentList(List<BillPaymentDEntity> billPaymentList) {
		this.billPaymentList = billPaymentList;
	}*/
	
}
