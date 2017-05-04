package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 查询现金收款单Dto
 * 
 * @author foss-zhangxiaohui
 * @date Nov 2, 2012 1:50:44 PM
 */
public class BillCashCollectionQueryDto implements Serializable {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = 6197122239465054820L;

	/**
	 * 日期类型
	 */
	private String selectDateType;

	/**
	 * 开始日期
	 */
	private Date startDate;

	/**
	 * 结束日期
	 */
	private Date endDate;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 是否初始化
	 */
	private String isInit;
	/**
	 * 收款部门编码
	 */
	private String collectionOrgCode;

	/**
	 * 收入部门编码
	 */
	private String generatingOrgCode;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 单据状态
	 */
	private String status;

	/**
	 * 付款方式
	 */
	private String paymentType;

	/**
	 * 页面总记录条数
	 */
	private int totalRecords;

	/**
	 * 数据库总记录条数
	 */
	private int totalRecordsInDB;

	/**
	 * 合计总金额
	 */
	private BigDecimal totalAmount;
	
	/**
	 * Tab查询类型
	 */
	private String queryTab;
	
	/**
	 * 员工工号（数据权限）
	 */
	private String empCode;
	
	
	/**
	 * 运单号查询
	 */
	private String waybillNo;
	
	
	/**
	 * 运单号集合
	 */
	private List<String>waybillNos;
	
	List<String> userOrgCodesList;
	

	/**
	 *自定义导出列头 
	 */
	private String[] arrayColumns;
	
	/**
	 *自定义导出列中文名称
	 */
	private String[] arrayColumnNames;
	
	/**
	 * 产品类型
	 */
	private List<String> productCode;
	
	/**
	 * POS串号
	 */
	private String posSerialNum;
	
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
	 * @return totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
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
	 * @return queryTab
	 */
	public String getQueryTab() {
		return queryTab;
	}

	/**
	 * @param  queryTab  
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
	 * @return arrayColumns
	 */
	public String[] getArrayColumns() {
		return arrayColumns;
	}

	/**
	 * @param  arrayColumns  
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
	 * @param  arrayColumnNames  
	 */
	public void setArrayColumnNames(String[] arrayColumnNames) {
		this.arrayColumnNames = arrayColumnNames;
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
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return waybillNos
	 */
	public List<String> getWaybillNos() {
		return waybillNos;
	}

	/**
	 * @param waybillNos
	 */
	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
	}

	/**
	 * @return userOrgCodesList
	 */
	public List<String> getUserOrgCodesList() {
		return userOrgCodesList;
	}

	/**
	 * @param userOrgCodesList
	 */
	public void setUserOrgCodesList(List<String> userOrgCodesList) {
		this.userOrgCodesList = userOrgCodesList;
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
	
	
}
