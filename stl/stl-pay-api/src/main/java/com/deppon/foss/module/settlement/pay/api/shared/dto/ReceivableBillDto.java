package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.util.define.FossConstants;

/**
 * 查询应收单Dto
 * 
 * @author foss-zhangxiaohui
 * @date Oct 17, 2012 4:05:46 PM
 */
public class ReceivableBillDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 确认已签收常量
	 */
	@SuppressWarnings("unused")
	private static final String Receivable_Bill_ISSIGN_YES = FossConstants.YES;

	/**
	 * 确认未签收常量
	 */
	@SuppressWarnings("unused")
	private static final String Receivable_Bill_ISSIGN_NO = FossConstants.NO;

	/**
	 * 日期类型
	 */
	private String selectDateType;
	
	/**
	 * 应收单号
	 */
	private String receivableNo;

	/**
	 * 开始日期
	 */
	private Date startDate;

	/**
	 * 结束日期
	 */
	private Date endDate;

	/**
	 * 部门
	 */
	private String department;
	
	private List<String> departments;

	/**
	 * 大区
	 */
	private String bigArea;

	/**
	 * 小区
	 */
	private String smallArea;

	/**
	 * 单据来源
	 */
	private String sourceBillType;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 单据子类型
	 */
	private String billType;

	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * 是否初始化
	 */
	private String isInit;


	/**
	 * 是否签收
	 */
	private String isSign;

	/**
	 * 数据库总记录条数
	 */
	private int totalRecordsInDB;

	/**
	 * 合计总金额
	 */
	private BigDecimal totalAmount;

	/**
	 * 核销总金额/实收总金额
	 */
	private BigDecimal totalVerifyAmount;

	/**
	 * 未核销总金额/未收金额
	 */
	private BigDecimal totalUnverifyAmount;
	/**
	 * 代收货款费用总金额
	 */
	private BigDecimal CodAmount;
	/**
	 *保价费用总金额
	 */
	private BigDecimal insuranceAmount;
	/**
	 * 公布价运费总金额
	 */
	private BigDecimal transportAmount;

	/**
	 * Tab查询类型
	 */
	private String queryTab;

	/**
	 * 员工工号（数据权限）
	 */
	private String empCode;
	
	/**
	 *自定义导出列头 
	 */
	private String[] arrayColumns;
	
	/**
	 *自定义导出列中文名称
	 */
	private String[] arrayColumnNames;
	
	
	/**
	 * 部门类型（始发或者到达部门）
	 */
	private String deptType;
	
	
	/**
	 * 收款部门类型
	 */
	private String isDept;
	
	
	/**
	 * 催款部门
	 */
	private String dunningDeptCode;
	
	/**
	 * 收入部门
	 */
	private String generatingOrgCode;
	
	/**
	 * 产品类型
	 */
	private List<String> productCode;
	
	/**
	 * 是否折扣
	 */
	private String isDiscount;

	private String[] billTypes;

	private String[] paymentTypes;

	public String[] getBillTypes() {
		return billTypes;
	}

	public void setBillTypes(String[] billTypes) {
		this.billTypes = billTypes;
	}

	public String[] getPaymentTypes() {
		return paymentTypes;
	}

	public void setPaymentTypes(String[] paymentTypes) {
		this.paymentTypes = paymentTypes;
	}

	public BigDecimal getCodAmount() {
		return CodAmount;
	}

	public void setCodAmount(BigDecimal codAmount) {
		CodAmount = codAmount;
	}

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public BigDecimal getTransportAmount() {
		return transportAmount;
	}

	public void setTransportAmount(BigDecimal transportAmount) {
		this.transportAmount = transportAmount;
	}

	/**
	 * @return  the receivableNo
	 */
	public String getReceivableNo() {
		return receivableNo;
	}
	
	/**
	 * @param receivableNo the receivableNo to set
	 */
	public void setReceivableNo(String receivableNo) {
		this.receivableNo = receivableNo;
	}

	/**
	 * @return selectDateType
	 */
	public String getSelectDateType() {
		return selectDateType;
	}

	/**
	 * @param selectDateType
	 */
	public void setSelectDateType(String selectDateType) {
		this.selectDateType = selectDateType;
	}

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
	 * @return department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	
	/**
	 * @return  the departments
	 */
	public List<String> getDepartments() {
		return departments;
	}
	
	/**
	 * @param departments the departments to set
	 */
	public void setDepartments(List<String> departments) {
		this.departments = departments;
	}

	/**
	 * @return bigArea
	 */
	public String getBigArea() {
		return bigArea;
	}

	/**
	 * @param bigArea
	 */
	public void setBigArea(String bigArea) {
		this.bigArea = bigArea;
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
	 * @return isSign
	 */
	public String getIsSign() {
		return isSign;
	}

	/**
	 * @param isSign
	 */
	public void setIsSign(String isSign) {
		this.isSign = isSign;
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
	 * @return totalVerifyAmount
	 */
	public BigDecimal getTotalVerifyAmount() {
		return totalVerifyAmount;
	}

	/**
	 * @param totalVerifyAmount
	 */
	public void setTotalVerifyAmount(BigDecimal totalVerifyAmount) {
		this.totalVerifyAmount = totalVerifyAmount;
	}

	/**
	 * @return totalUnverifyAmount
	 */
	public BigDecimal getTotalUnverifyAmount() {
		return totalUnverifyAmount;
	}

	/**
	 * @param totalUnverifyAmount
	 */
	public void setTotalUnverifyAmount(BigDecimal totalUnverifyAmount) {
		this.totalUnverifyAmount = totalUnverifyAmount;
	}

	/**
	 * @return the queryTab
	 */
	public String getQueryTab() {
		return queryTab;
	}

	/**
	 * @param queryTab
	 *            the queryTab to set
	 */
	public void setQueryTab(String queryTab) {
		this.queryTab = queryTab;
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

	/**
	 * @GET
	 * @return isInit
	 */
	public String getIsInit() {
		/*
		 *@get
		 *@ return isInit
		 */
		return isInit;
	}

	/**
	 * @SET
	 * @param isInit
	 */
	public void setIsInit(String isInit) {
		/*
		 *@set
		 *@this.isInit = isInit
		 */
		this.isInit = isInit;
	}

	/**
	 * @GET
	 * @return arrayColumns
	 */
	public String[] getArrayColumns() {
		/*
		 *@get
		 *@ return arrayColumns
		 */
		return arrayColumns;
	}

	/**
	 * @SET
	 * @param arrayColumns
	 */
	public void setArrayColumns(String[] arrayColumns) {
		/*
		 *@set
		 *@this.arrayColumns = arrayColumns
		 */
		this.arrayColumns = arrayColumns;
	}

	/**
	 * @GET
	 * @return arrayColumnNames
	 */
	public String[] getArrayColumnNames() {
		/*
		 *@get
		 *@ return arrayColumnNames
		 */
		return arrayColumnNames;
	}

	/**
	 * @SET
	 * @param arrayColumnNames
	 */
	public void setArrayColumnNames(String[] arrayColumnNames) {
		/*
		 *@set
		 *@this.arrayColumnNames = arrayColumnNames
		 */
		this.arrayColumnNames = arrayColumnNames;
	}

	/**
	 * @return deptType
	 */
	public String getDeptType() {
		return deptType;
	}

	/**
	 * @param deptType
	 */
	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	/**
	 * @return isDept
	 */
	public String getIsDept() {
		return isDept;
	}

	/**
	 * @param isDept
	 */
	public void setIsDept(String isDept) {
		this.isDept = isDept;
	}

	/**
	 * @return dunningDeptCode
	 */
	public String getDunningDeptCode() {
		return dunningDeptCode;
	}

	/**
	 * @param dunningDeptCode
	 */
	public void setDunningDeptCode(String dunningDeptCode) {
		this.dunningDeptCode = dunningDeptCode;
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
	 * @GET
	 * @return productCode
	 */
	public List<String> getProductCode() {
		/*
		 *@get
		 *@ return productCode
		 */
		return productCode;
	}

	/**
	 * @SET
	 * @param productCode
	 */
	public void setProductCode(List<String> productCode) {
		/*
		 *@set
		 *@this.productCode = productCode
		 */
		this.productCode = productCode;
	}

	public String getIsDiscount() {
		return isDiscount;
	}

	public void setIsDiscount(String isDiscount) {
		this.isDiscount = isDiscount;
	}
	
}
