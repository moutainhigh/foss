package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 理赔输入参数Dto
 * 
 * @author foss-qiaolifeng
 * @date 2013-1-28 下午2:50:38
 */
public class BillClaimQueryDto implements Serializable {

	/**
	 * 理赔输入参数Dto序列号
	 */
	private static final long serialVersionUID = 4079706425794808674L;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 运单号集合数组
	 */
	private String[] wayBillNosArray;

	/**
	 * 运单号集合list
	 */
	private List<String> wayBillNos;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 理赔应付部门编码
	 */
	private String payableOrgCode;

	/**
	 * 支付类别
	 */
	private String paymentCategories;

	/**
	 * 类型（理赔/服务补救/退运费）
	 */
	private String type;

	/**
	 * 开始时间
	 */
	private Date startCreateTime;

	/**
	 * 结束时间
	 */
	private Date endCreateTime;

	/**
	 * （应付单、理赔单）有效
	 */
	private String active;

	/**
	 * （应付单）支付状态
	 */
	private String payStatus;

	/**
	 * 理赔单退回状态
	 */
	private String status;

	/**
	 * 理赔退回原因
	 */
	private String rtnReason;

	/**
	 * 当前登录用户员工编码
	 */
	private String empCode;
	
	/**
	 * 应付单类型必须为理赔
	 */
	private String payableBillType;
	
	/**
	 * 产品类型
	 */
	private List<String> productCode;
	
	/**
	 * @get
	 * @return payableBillType
	 */
	public String getPayableBillType() {
		/*
		 * @get
		 * @return payableBillType
		 */
		return payableBillType;
	}

	
	/**
	 * @set
	 * @param payableBillType
	 */
	public void setPayableBillType(String payableBillType) {
		/*
		 *@set
		 *@this.payableBillType = payableBillType
		 */
		this.payableBillType = payableBillType;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String[] getWayBillNosArray() {
		return wayBillNosArray;
	}

	public void setWayBillNosArray(String[] wayBillNosArray) {
		this.wayBillNosArray = wayBillNosArray;
	}

	public List<String> getWayBillNos() {
		return wayBillNos;
	}

	public void setWayBillNos(List<String> wayBillNos) {
		this.wayBillNos = wayBillNos;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getPayableOrgCode() {
		return payableOrgCode;
	}

	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}

	public String getPaymentCategories() {
		return paymentCategories;
	}

	public void setPaymentCategories(String paymentCategories) {
		this.paymentCategories = paymentCategories;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRtnReason() {
		return rtnReason;
	}

	public void setRtnReason(String rtnReason) {
		this.rtnReason = rtnReason;
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

}
