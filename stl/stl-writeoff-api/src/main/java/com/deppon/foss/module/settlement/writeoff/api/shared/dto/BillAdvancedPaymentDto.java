/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-writeoff-api
 * PACKAGE NAME: com.deppon.foss.module.settlement.writeoff.api.shared.dto
 * FILE    NAME: BillAdvancedPaymentDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;

/**
 * 预付单Dto
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:jefferson,date:2012-10-16 下午8:18:09,content:
 * </p>
 * @author foss-pengzhen
 * @date 2012-10-16 下午8:18:09
 */
public class BillAdvancedPaymentDto extends BillAdvancedPaymentEntity {

	/**
	 * VO序列号
	 */
	private static final long serialVersionUID = -5495516538180496912L;

	/**
	 * 预付单号实体
	 */
	private BillAdvancedPaymentEntity billAdvancedPaymentEntity;

	/**
	 * 预付单号列表
	 */
	private List<BillAdvancedPaymentEntity> billAdvancedPaymentEntityList;

	/**
	 * 预付单业务开始时间
	 */
	private Date startBusinessDate;

	/**
	 * 预付单业务结束时间
	 */
	private Date endBusinessDate;

	/**
	 * 预付单客户编码
	 */
	private String customerCode;

	/**
	 * 预付单客户编码
	 */
	private String collectionOrgCode;

	/**
	 * 预付单是否有效
	 */
	private String active;

	/**
	 * 预付单是否红单
	 */
	private String isRedBack;

	/**
	 * 预付单汇款审批状态
	 */
	private String auditStatus;

	/**
	 * 预付单号集
	 */
	private List<String> advancesNos;

	
	/**
	 * @return 
		billAdvancedPaymentEntity
	 */
	public BillAdvancedPaymentEntity getBillAdvancedPaymentEntity() {
		return billAdvancedPaymentEntity;
	}

	
	/**
	 * @param 
		billAdvancedPaymentEntity
	 */
	public void setBillAdvancedPaymentEntity(BillAdvancedPaymentEntity billAdvancedPaymentEntity) {
		this.billAdvancedPaymentEntity = billAdvancedPaymentEntity;
	}

	
	/**
	 * @return 
		billAdvancedPaymentEntityList
	 */
	public List<BillAdvancedPaymentEntity> getBillAdvancedPaymentEntityList() {
		return billAdvancedPaymentEntityList;
	}

	
	/**
	 * @param 
		billAdvancedPaymentEntityList
	 */
	public void setBillAdvancedPaymentEntityList(List<BillAdvancedPaymentEntity> billAdvancedPaymentEntityList) {
		this.billAdvancedPaymentEntityList = billAdvancedPaymentEntityList;
	}

	
	/**
	 * @return 
		startBusinessDate
	 */
	public Date getStartBusinessDate() {
		return startBusinessDate;
	}

	
	/**
	 * @param 
		startBusinessDate
	 */
	public void setStartBusinessDate(Date startBusinessDate) {
		this.startBusinessDate = startBusinessDate;
	}

	
	/**
	 * @return 
		endBusinessDate
	 */
	public Date getEndBusinessDate() {
		return endBusinessDate;
	}

	
	/**
	 * @param 
		endBusinessDate
	 */
	public void setEndBusinessDate(Date endBusinessDate) {
		this.endBusinessDate = endBusinessDate;
	}

	
	/**
	 * @return 
		customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	
	/**
	 * @param 
		customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	
	/**
	 * @return 
		collectionOrgCode
	 */
	public String getCollectionOrgCode() {
		return collectionOrgCode;
	}

	
	/**
	 * @param 
		collectionOrgCode
	 */
	public void setCollectionOrgCode(String collectionOrgCode) {
		this.collectionOrgCode = collectionOrgCode;
	}

	
	/**
	 * @return 
		active
	 */
	public String getActive() {
		return active;
	}

	
	/**
	 * @param 
		active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	
	/**
	 * @return 
		isRedBack
	 */
	public String getIsRedBack() {
		return isRedBack;
	}

	
	/**
	 * @param 
		isRedBack
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
	}

	
	/**
	 * @return 
		auditStatus
	 */
	public String getAuditStatus() {
		return auditStatus;
	}

	
	/**
	 * @param 
		auditStatus
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	
	/**
	 * @return 
		advancesNos
	 */
	public List<String> getAdvancesNos() {
		return advancesNos;
	}

	
	/**
	 * @param 
		advancesNos
	 */
	public void setAdvancesNos(List<String> advancesNos) {
		this.advancesNos = advancesNos;
	}

	
}
