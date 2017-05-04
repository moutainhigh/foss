package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 标记发票已开记录dto
 * 
 * @author guxinhua
 * @date 2012-11-6 下午4:54:04
 */
public class InvoiceDto implements Serializable {

	private static final long serialVersionUID = 4001653328043158364L;

	/**
	 * ID
	 */
	private String id;

	/**
	 * sourceBillNo 's List
	 */
	private List<String> sourceBillNoList;

	/**
	 * 部门标杆编码
	 */
	private String billingDeptCode;

	/**
	 * 部门编码
	 */
	private String orgCode;

	/**
	 * 部门名称
	 */
	private String orgName;

	/**
	 * 小票单号
	 */
	private String otherRevenueNo;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 来源单号
	 */
	private String sourceBillNo;

	/**
	 * 来源单据类型
	 */
	private String sourceBillType;

	/**
	 * 发票类型
	 */
	private String invoiceType;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 总金额
	 */
	private BigDecimal totalAmount;

	/**
	 * 已开金额
	 */
	private BigDecimal alreadyOpenAmount;

	/**
	 * 申请人名称
	 */
	private String applyUserName;

	/**
	 * 申请人编码
	 */
	private String applyUserCode;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 基本实体类
	 */
	private BaseEntity entity;
	
	/**
	 * 产品类型
	 */
	private String productCode;
	/**
	 *收货部门
	 */
	private String receviceOrgCode;
	
	/**
	 *发票标记 
	 */
	private String invoiceMark;
	
	/**
	 * @return the receviceOrgCode
	 */
	public String getReceviceOrgCode() {
		return receviceOrgCode;
	}

	/**
	 * @param receviceOrgCode the receviceOrgCode to set
	 */
	public void setReceviceOrgCode(String receviceOrgCode) {
		this.receviceOrgCode = receviceOrgCode;
	}

	/**
	 * @return the invoiceMark
	 */
	public String getInvoiceMark() {
		return invoiceMark;
	}

	/**
	 * @param invoiceMark the invoiceMark to set
	 */
	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return sourceBillNoList
	 */
	public List<String> getSourceBillNoList() {
		return sourceBillNoList;
	}

	/**
	 * @param sourceBillNoList
	 */
	public void setSourceBillNoList(List<String> sourceBillNoList) {
		this.sourceBillNoList = sourceBillNoList;
	}

	/**
	 * @return billingDeptCode
	 */
	public String getBillingDeptCode() {
		return billingDeptCode;
	}

	/**
	 * @param billingDeptCode
	 */
	public void setBillingDeptCode(String billingDeptCode) {
		this.billingDeptCode = billingDeptCode;
	}

	/**
	 * @return orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return otherRevenueNo
	 */
	public String getOtherRevenueNo() {
		return otherRevenueNo;
	}

	/**
	 * @param otherRevenueNo
	 */
	public void setOtherRevenueNo(String otherRevenueNo) {
		this.otherRevenueNo = otherRevenueNo;
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
	 * @return sourceBillNo
	 */
	public String getSourceBillNo() {
		return sourceBillNo;
	}

	/**
	 * @param sourceBillNo
	 */
	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
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
	 * @return invoiceType
	 */
	public String getInvoiceType() {
		return invoiceType;
	}

	/**
	 * @param invoiceType
	 */
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
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
	 * @return customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	 * @return alreadyOpenAmount
	 */
	public BigDecimal getAlreadyOpenAmount() {
		return alreadyOpenAmount;
	}

	/**
	 * @param alreadyOpenAmount
	 */
	public void setAlreadyOpenAmount(BigDecimal alreadyOpenAmount) {
		this.alreadyOpenAmount = alreadyOpenAmount;
	}

	/**
	 * @return applyUserName
	 */
	public String getApplyUserName() {
		return applyUserName;
	}

	/**
	 * @param applyUserName
	 */
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	/**
	 * @return applyUserCode
	 */
	public String getApplyUserCode() {
		return applyUserCode;
	}

	/**
	 * @param applyUserCode
	 */
	public void setApplyUserCode(String applyUserCode) {
		this.applyUserCode = applyUserCode;
	}

	/**
	 * @return businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
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
	 * @return entity
	 */
	public BaseEntity getEntity() {
		return entity;
	}

	/**
	 * @param entity
	 */
	public void setEntity(BaseEntity entity) {
		this.entity = entity;
	}

	/**
	 * @GET
	 * @return productCode
	 */
	public String getProductCode() {
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
	public void setProductCode(String productCode) {
		/*
		 *@set
		 *@this.productCode = productCode
		 */
		this.productCode = productCode;
	}

}
