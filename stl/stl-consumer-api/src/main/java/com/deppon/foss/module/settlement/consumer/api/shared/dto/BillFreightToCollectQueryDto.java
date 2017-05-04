package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;

/**
 * 到付清查Dto
 * 
 * @author foss-zhangxiaohui
 * @date Oct 29, 2012 6:41:03 PM
 */
public class BillFreightToCollectQueryDto implements Serializable {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = 5300938779575608517L;

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
	 * 部门
	 */
	private String department;

	/**
	 * 大区
	 */
	private String bigArea;

	/**
	 * 小区
	 */
	private String smallArea;
	
	/**
	 * 所选大区、小区的网点编码集合
	 */
	private List<String> orgCodeInArea;

	/**
	 * 单据来源
	 */
	private String billType;

	/**
	 * 出发部门编码
	 */
	private String origOrgCode;

	/**
	 * 到达部门编码
	 */
	private String destOrgCode;

	/**
	 * 运输性质
	 */
	private String productCode;
	
	/**
	 * 运输性质集合
	 */
	private List<String> productCodeList;

	/**
	 * 库存状态
	 */
	private String stockStatus;

	/**
	 * 应收单实体List
	 */
	private List<BillReceivableEntity> billFreightToCollectList;

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
	 * 员工工号（数据权限）
	 */
	private String empCode;
	
	/**
	 * 单据来源
	 */
	private List<String> billTypeList;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 查询部门类型--出发/到达
	 */
	private String deptType;
	
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
	 * @return billTypeList
	 */
	public List<String> getBillTypeList() {
		return billTypeList;
	}

	/**
	 * @param billTypeList
	 */
	public void setBillTypeList(List<String> billTypeList) {
		this.billTypeList = billTypeList;
	}

	/**
	 * @return selectDateType
	 */
	public String getSelectDateType() {
		return selectDateType;
	}

	/**
	 * @param  selectDateType  
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
	 * @param  startDate  
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
	 * @param  endDate  
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
	 * @param  department  
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return bigArea
	 */
	public String getBigArea() {
		return bigArea;
	}

	/**
	 * @param  bigArea  
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
	 * @param  smallArea  
	 */
	public void setSmallArea(String smallArea) {
		this.smallArea = smallArea;
	}

	/**
	 * @return billType
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * @param  billType  
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * @return origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * @param  origOrgCode  
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * @return destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * @param  destOrgCode  
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @return productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param  productCode  
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return stockStatus
	 */
	public String getStockStatus() {
		return stockStatus;
	}

	/**
	 * @param  stockStatus  
	 */
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

	/**
	 * @return billFreightToCollectList
	 */
	public List<BillReceivableEntity> getBillFreightToCollectList() {
		return billFreightToCollectList;
	}

	/**
	 * @param  billFreightToCollectList  
	 */
	public void setBillFreightToCollectList(
			List<BillReceivableEntity> billFreightToCollectList) {
		this.billFreightToCollectList = billFreightToCollectList;
	}

	/**
	 * @return totalRecordsInDB
	 */
	public int getTotalRecordsInDB() {
		return totalRecordsInDB;
	}

	/**
	 * @param  totalRecordsInDB  
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
	 * @param  totalAmount  
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
	 * @param  totalVerifyAmount  
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
	 * @param  totalUnverifyAmount  
	 */
	public void setTotalUnverifyAmount(BigDecimal totalUnverifyAmount) {
		this.totalUnverifyAmount = totalUnverifyAmount;
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
	 * @get
	 * @return orgCodeInArea
	 */
	public List<String> getOrgCodeInArea() {
		/*
		 * @get
		 * @return orgCodeInArea
		 */
		return orgCodeInArea;
	}

	
	/**
	 * @set
	 * @param orgCodeInArea
	 */
	public void setOrgCodeInArea(List<String> orgCodeInArea) {
		/*
		 *@set
		 *@this.orgCodeInArea = orgCodeInArea
		 */
		this.orgCodeInArea = orgCodeInArea;
	}

	/**
	 * @GET
	 * @return deptType
	 */
	public String getDeptType() {
		/*
		 *@get
		 *@ return deptType
		 */
		return deptType;
	}

	/**
	 * @SET
	 * @param deptType
	 */
	public void setDeptType(String deptType) {
		/*
		 *@set
		 *@this.deptType = deptType
		 */
		this.deptType = deptType;
	}

	/**
	 * @return productCodeList
	 */
	public List<String> getProductCodeList() {
		return productCodeList;
	}

	/**
	 * @param productCodeList
	 */
	public void setProductCodeList(List<String> productCodeList) {
		this.productCodeList = productCodeList;
	}
	
}
