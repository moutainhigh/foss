package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 合伙人日明细Dto
 * @author 311396
 * @date 2016-3-22 下午2:28:00
 */
public class MvrPtpAllDetailsDto implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 4925210131917786102L;

	/**
	 * 新报表类型
	 */
	private String reportType;
	
	/**
	 * 发票标记
	 */
	private String voucherMark;
	
	/**
	 * 开始日期
	 */
	private Date startDate;

	/**
	 * 开始期间
	 */
	private String startPeriod;

	/**
	 * 结束日期
	 */
	private Date endDate;

	/**
	 * 结束期间
	 */
	private String endPeriod;

	/**
	 * 业务类型
	 */
	private String productType;
	
	/**
	 * 业务类型集合
	 */
	private List<String> productTypeList;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 出发部门编码
	 */
	private String origOrgCode;

	/**
	 * 到达部门编码
	 */
	private String destOrgCode;
	
	/**
	 * 借方始发部门编码
	 */
	private String  creditOrgCode;
	
	/**
	 * 借方始发部门名称
	 */
	private String creditOrgName;
	/**
	 * 贷方始发部门编码
	 */
	private String  debitOrgCode;
	
	/**
	 * 贷方始发部门名称
	 */
	private String debitOrgName;

	/**
	 * 报表类型
	 */
	private String rptType;

	/**
	 * 大类显示名称
	 */
	private String typeCode;

	/**
	 * 小类显示名称
	 */
	private String subTypeCode;

	/**
	 * 业务场景
	 */
	private List<String> businessCaseList;

	/** 用户编码-设置用户数据查询权限. */
	private String userCode;
	
	/**
	 * 部门状态
	 */
	private String codeStatus;

	/**
	 * 查询视图名称
	 */
	private String viewName;

	/**
	 * 当前登陆用户部门编码
	 */
	private String currentDeptCode;

	public String getRptType() {
		return rptType;
	}

	public void setRptType(String rptType) {
		this.rptType = rptType;
	}

	/**
	 * @get
	 * @return userCode
	 */
	public String getUserCode() {
		/*
		 * @get
		 * 
		 * @return userCode
		 */
		return userCode;
	}

	/**
	 * @set
	 * @param userCode
	 */
	public void setUserCode(String userCode) {
		/*
		 * @set
		 * 
		 * @this.userCode = userCode
		 */
		this.userCode = userCode;
	}

	/**
	 * @GET
	 * @return startDate
	 */
	public Date getStartDate() {
		/*
		 * @get
		 * 
		 * @ return startDate
		 */
		return startDate;
	}

	/**
	 * @SET
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		/*
		 * @set
		 * 
		 * @this.startDate = startDate
		 */
		this.startDate = startDate;
	}

	/**
	 * @GET
	 * @return endDate
	 */
	public Date getEndDate() {
		/*
		 * @get
		 * 
		 * @ return endDate
		 */
		return endDate;
	}

	/**
	 * @SET
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		/*
		 * @set
		 * 
		 * @this.endDate = endDate
		 */
		this.endDate = endDate;
	}

	/**
	 * @GET
	 * @return productType
	 */
	public String getProductType() {
		/*
		 * @get
		 * 
		 * @ return productType
		 */
		return productType;
	}

	/**
	 * @SET
	 * @param productType
	 */
	public void setProductType(String productType) {
		/*
		 * @set
		 * 
		 * @this.productType = productType
		 */
		this.productType = productType;
	}

	/**
	 * @GET
	 * @return customerCode
	 */
	public String getCustomerCode() {
		/*
		 * @get
		 * 
		 * @ return customerCode
		 */
		return customerCode;
	}

	/**
	 * @SET
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		/*
		 * @set
		 * 
		 * @this.customerCode = customerCode
		 */
		this.customerCode = customerCode;
	}

	/**
	 * @GET
	 * @return origOrgCode
	 */
	public String getOrigOrgCode() {
		/*
		 * @get
		 * 
		 * @ return origOrgCode
		 */
		return origOrgCode;
	}

	/**
	 * @SET
	 * @param origOrgCode
	 */
	public void setOrigOrgCode(String origOrgCode) {
		/*
		 * @set
		 * 
		 * @this.origOrgCode = origOrgCode
		 */
		this.origOrgCode = origOrgCode;
	}

	/**
	 * @GET
	 * @return destOrgCode
	 */
	public String getDestOrgCode() {
		/*
		 * @get
		 * 
		 * @ return destOrgCode
		 */
		return destOrgCode;
	}

	/**
	 * @SET
	 * @param destOrgCode
	 */
	public void setDestOrgCode(String destOrgCode) {
		/*
		 * @set
		 * 
		 * @this.destOrgCode = destOrgCode
		 */
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @GET
	 * @return typeCode
	 */
	public String getTypeCode() {
		/*
		 * @get
		 * 
		 * @ return typeCode
		 */
		return typeCode;
	}

	/**
	 * @SET
	 * @param typeCode
	 */
	public void setTypeCode(String typeCode) {
		/*
		 * @set
		 * 
		 * @this.typeCode = typeCode
		 */
		this.typeCode = typeCode;
	}

	/**
	 * @GET
	 * @return subTypeCode
	 */
	public String getSubTypeCode() {
		/*
		 * @get
		 * 
		 * @ return subTypeCode
		 */
		return subTypeCode;
	}

	/**
	 * @SET
	 * @param subTypeCode
	 */
	public void setSubTypeCode(String subTypeCode) {
		/*
		 * @set
		 * 
		 * @this.subTypeCode = subTypeCode
		 */
		this.subTypeCode = subTypeCode;
	}

	/**
	 * @GET
	 * @return businessCaseList
	 */
	public List<String> getBusinessCaseList() {
		/*
		 * @get
		 * 
		 * @ return businessCaseList
		 */
		return businessCaseList;
	}

	/**
	 * @SET
	 * @param businessCaseList
	 */
	public void setBusinessCaseList(List<String> businessCaseList) {
		/*
		 * @set
		 * 
		 * @this.businessCaseList = businessCaseList
		 */
		this.businessCaseList = businessCaseList;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(String startPeriod) {
		this.startPeriod = startPeriod;
	}

	public String getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(String endPeriod) {
		this.endPeriod = endPeriod;
	}

	/**
	 * @return codeStatus
	 */
	public String getCodeStatus() {
		return codeStatus;
	}

	/**
	 * @param codeStatus
	 */
	public void setCodeStatus(String codeStatus) {
		this.codeStatus = codeStatus;
	}

	/**
	 * @return productTypeList
	 */
	public List<String> getProductTypeList() {
		return productTypeList;
	}

	/**
	 * @param productTypeList
	 */
	public void setProductTypeList(List<String> productTypeList) {
		this.productTypeList = productTypeList;
	}
	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}
	
	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getVoucherMark() {
		return voucherMark;
	}

	public void setVoucherMark(String voucherMark) {
		this.voucherMark = voucherMark;
	}
	
	public String getCreditOrgCode() {
		return creditOrgCode;
	}

	public void setCreditOrgCode(String creditOrgCode) {
		this.creditOrgCode = creditOrgCode;
	}

	public String getCreditOrgName() {
		return creditOrgName;
	}

	public void setCreditOrgName(String creditOrgName) {
		this.creditOrgName = creditOrgName;
	}

	public String getDebitOrgCode() {
		return debitOrgCode;
	}

	public void setDebitOrgCode(String debitOrgCode) {
		this.debitOrgCode = debitOrgCode;
	}

	public String getDebitOrgName() {
		return debitOrgName;
	}

	public void setDebitOrgName(String debitOrgName) {
		this.debitOrgName = debitOrgName;
	}
}
