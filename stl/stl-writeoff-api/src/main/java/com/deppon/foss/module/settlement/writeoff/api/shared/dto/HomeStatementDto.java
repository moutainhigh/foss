package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementEntity;

/**
 * 家装DTO
 * 
 * @ClassName: HomeStatementDto
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-11-24 下午3:41:07
 */
public class HomeStatementDto {
	/**
	 * 家装对账单明细
	 */
	private List<HomeStatementDEntity> homeStatementDList;

	/**
	 * 家装 对账单
	 */
	private List<HomeStatementEntity> homeStatementList;

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
	 * 所属子公司名称
	 */
	private String subCompanyCode;

	/**
	 * 单据子类型
	 */
	private String[] billTypes;

	/**
	 * 查询应付单
	 */
	private String queryPayable;

	/**
	 * 查询应收单
	 */
	private String queryReceive;

	/**
	 * 单据类型
	 */
	private String billType;

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
	 * 总金额
	 */
	private BigDecimal amount;
	
	/**
	 * 总的已核销金额
	 */
	private BigDecimal sumVerifyAmount;
	
	/**
	 * 总的未核销金额
	 */
	private BigDecimal sumUnverifyAmount;

	/******** getter/setter ************/
	public List<HomeStatementDEntity> getHomeStatementDList() {
		return homeStatementDList;
	}

	public void setHomeStatementDList(
			List<HomeStatementDEntity> homeStatementDList) {
		this.homeStatementDList = homeStatementDList;
	}

	public List<HomeStatementEntity> getHomeStatementList() {
		return homeStatementList;
	}

	public void setHomeStatementList(List<HomeStatementEntity> homeStatementList) {
		this.homeStatementList = homeStatementList;
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

	public String getSubCompanyCode() {
		return subCompanyCode;
	}

	public void setSubCompanyCode(String subCompanyCode) {
		this.subCompanyCode = subCompanyCode;
	}

	public String[] getBillTypes() {
		return billTypes;
	}

	public void setBillTypes(String[] billTypes) {
		this.billTypes = billTypes;
	}

	public String getQueryPayable() {
		return queryPayable;
	}

	public void setQueryPayable(String queryPayable) {
		this.queryPayable = queryPayable;
	}

	public String getQueryReceive() {
		return queryReceive;
	}

	public void setQueryReceive(String queryReceive) {
		this.queryReceive = queryReceive;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getSumVerifyAmount() {
		return sumVerifyAmount;
	}

	public void setSumVerifyAmount(BigDecimal sumVerifyAmount) {
		this.sumVerifyAmount = sumVerifyAmount;
	}

	public BigDecimal getSumUnverifyAmount() {
		return sumUnverifyAmount;
	}

	public void setSumUnverifyAmount(BigDecimal sumUnverifyAmount) {
		this.sumUnverifyAmount = sumUnverifyAmount;
	}

}
