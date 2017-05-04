package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 快递代理代收货款审核Dto
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-29 下午5:24:29
 */
public class LandBillPaidCODGridDto {

	/**
	 * 代收货款记录id
	 */
	private String id;
	
	/**
	 * 运单单号
	 */
	private String waybillNo;

	/**
	 * 代收货款类型
	 */
	private String codType;
	
	
	/**
	 * 快递代理代收货款状态
	 */
	private String landStatus;	
	
	
	/**
	 * 快递代理代收货款审核人编码
	 */
	private String landOrgAuditUserCode;

	/**
	 * 快递代理代收货款审核名称
	 */
	private String landOrgAuditUserName;
	
	
	/**
	 * 快递代理审核时间
	 */
	private Date landAuditTime;
	
	
	/**
	 * 应付部门（出发部门）编码
	 */
	private String payableOrgCode;

	/**
	 * 应付部门名称
	 */
	private String payableOrgName;	
	

	/**
	 * 发货人（发货客户编码）
	 */
	private String customerCode;
	
	/**
	 * 发货人（发货客户名称）
	 */
	private String customerName;	
	

	/**
	 * 签收人
	 */
	private String signer;

	/**
	 * 签收代理编码
	 */
	private String agencyCode;
	
	/**
	 * 签收代理名称
	 */
	private String agencyName;


	/**
	 * 目的站
	 */
	private String destination;

	/**
	 * 代收货款金额
	 */
	private String codAmount;

	/**
	 * 签收时间
	 */
	private Date signDate;

	/**
	 * 产品类型
	 */
	private String productType;

	/**
	 * 到达部门编码
	 */
	private String destDeptCode;
	
	/**
	 * 到达部门编码
	 */
	private String destDeptName;

	/**
	 * 收货人名称
	 */
	private String arriveCustomer;
	
	/**
	 * 数据库符合条件的数据总条数
	 */
	private Long totalCount;

	/**
	 * 合计总金额
	 */
	private BigDecimal totalAmount;
	
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
	 * @return landStatus
	 */
	public String getLandStatus() {
		return landStatus;
	}

	/**
	 * @param landStatus
	 */
	public void setLandStatus(String landStatus) {
		this.landStatus = landStatus;
	}

	/**
	 * @return codType
	 */
	public String getCodType() {
		return codType;
	}

	/**
	 * @param codType
	 */
	public void setCodType(String codType) {
		this.codType = codType;
	}

	/**
	 * @return payableOrgCode
	 */
	public String getPayableOrgCode() {
		return payableOrgCode;
	}

	/**
	 * @param payableOrgCode
	 */
	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}

	/**
	 * @return payableOrgName
	 */
	public String getPayableOrgName() {
		return payableOrgName;
	}

	/**
	 * @param payableOrgName
	 */
	public void setPayableOrgName(String payableOrgName) {
		this.payableOrgName = payableOrgName;
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
	 * @return signer
	 */
	public String getSigner() {
		return signer;
	}

	/**
	 * @param signer
	 */
	public void setSigner(String signer) {
		this.signer = signer;
	}
 

	/**
	 * @return destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return codAmount
	 */
	public String getCodAmount() {
		return codAmount;
	}

	/**
	 * @param codAmount
	 */
	public void setCodAmount(String codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * @return signDate
	 */
	public Date getSignDate() {
		return signDate;
	}

	/**
	 * @param signDate
	 */
	public void setSignDate(Date businessDate) {
		this.signDate = businessDate;
	}

	/**
	 * @return productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	 

	/**
	 * @return arriveCustomer
	 */
	public String getArriveCustomer() {
		return arriveCustomer;
	}

	/**
	 * @param arriveCustomer
	 */
	public void setArriveCustomer(String arriveCustomer) {
		this.arriveCustomer = arriveCustomer;
	}

	
	/**
	 * @return landOrgAuditUserCode
	 */
	public String getLandOrgAuditUserCode() {
		return landOrgAuditUserCode;
	}

	
	/**
	 * @param landOrgAuditUserCode
	 */
	public void setLandOrgAuditUserCode(String landOrgAuditUserCode) {
		this.landOrgAuditUserCode = landOrgAuditUserCode;
	}

	
	/**
	 * @return landOrgAuditUserName
	 */
	public String getLandOrgAuditUserName() {
		return landOrgAuditUserName;
	}

	
	/**
	 * @param landOrgAuditUserName
	 */
	public void setLandOrgAuditUserName(String landOrgAuditUserName) {
		this.landOrgAuditUserName = landOrgAuditUserName;
	}

	
	/**
	 * @return landAuditTime
	 */
	public Date getLandAuditTime() {
		return landAuditTime;
	}

	
	/**
	 * @param landAuditTime
	 */
	public void setLandAuditTime(Date landAuditDate) {
		this.landAuditTime = landAuditDate;
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
	 * @return destDeptCode
	 */
	public String getDestDeptCode() {
		return destDeptCode;
	}

	/**
	 * @param destDeptCode
	 */
	public void setDestDeptCode(String destDeptCode) {
		this.destDeptCode = destDeptCode;
	}

	/**
	 * @return destDeptName
	 */
	public String getDestDeptName() {
		return destDeptName;
	}

	/**
	 * @param destDeptName
	 */
	public void setDestDeptName(String destDeptName) {
		this.destDeptName = destDeptName;
	}

	/**
	 * @return agencyCode
	 */
	public String getAgencyCode() {
		return agencyCode;
	}

	/**
	 * @param agencyCode
	 */
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	/**
	 * @return agencyName
	 */
	public String getAgencyName() {
		return agencyName;
	}

	/**
	 * @param agencyName
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	/**
	 * @return totalCount
	 */
	public Long getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 */
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
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

}
