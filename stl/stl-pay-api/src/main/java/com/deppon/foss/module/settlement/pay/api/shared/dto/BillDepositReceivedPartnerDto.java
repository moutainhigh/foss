/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-pay-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/pay/api/shared/dto/BillDepositReceivedPayDto.java
 * 
 * FILE NAME        	: BillDepositReceivedPayDto.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

/*
 * PROJECT NAME: stl-pay-api
 * PACKAGE NAME: com.deppon.foss.module.settlement.pay.api.shared.dto
 * FILE    NAME: BillDepositReceivedPayDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * 预收单Dto
 * @author foss-pengzhen
 * @date 2012-11-19 下午4:41:37
 * @since
 * @version
 */
public class BillDepositReceivedPartnerDto implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1126807791190749615L;
	
	/**
	 * 查询判断条件
	 */
//	private String queryByTab;
	
	/**
	 * 收银员、会计参数
	 */
//	private String cashierAccounting;
	
	/**
	 * 预收单list
	 */
//	private List<BillDepositReceivedEntity> billDepositReceivedEntityList;

	/**
	 * 预收单号
	 */
	private String depositReceivedNo;
	/**
	 * 预收单号集合
	 */
//	private List<String> depositReceivedNos;
	
	/**
	 * 客户编码
	 */
	private String customerCode;
	
	/**
	 * 客户名称
	 */
	private String customerName;
	
	/**
	 * 登录用户编码
	 */
//	private String empCode;
	/**
	 * 运输类型
	 */
	private String transportType;
	
	/**
	 * 收款部门编码
	 */
	private String collectionOrgCode;
	
	/**
	 * 收款部门名称
	 */
	private String collectionOrgName;
	
	/**
	 * 预收部门编码
	 */
	private String generatingOrgCode;
	
	
	/**
	 * 预收部门名称
	 */
	private String generatingOrgName;
	
	/**
	 * 金额
	 */
	private BigDecimal amount;
	
	/**
	 * 汇款编号
	 */
	private String remitNo;
	
	/**
	 * 是否红单
	 */
	private String isRedBack;
	
	/**
	 * 是否初始化
	 */
	private String isInit;
	
	/**
	 * 付款单明细列表
	 */
//	private List<BillPaymentAddDto> addDtoList = new ArrayList<BillPaymentAddDto>();
	
	/**
	 * 付款单实体
	 */
//	private BillPaymentEntity paymentEntity = new BillPaymentEntity();
	
	/**
	 * 总条数
	 */
//	private long totalNum;
	
	/**
	 * 总金额
	 */
	private BigDecimal totalAmount;
	
	/**
	 * 已核销总金额
	 */
	private BigDecimal totalVerifyAmount;
	
	/**
	 * 未核销总金额
	 */
	private BigDecimal totalUnverifyAmount;
	
	/**
	 * 业务日期
	 */
	private Date businessDate;
	
	
	/**
	 * 业务开始时间
	 */
	private Date startBusinessDate;
	
	/**
	 * 业务结束时间
	 */
	private Date endBusinessDate;
	
	/**
	 * 付款类型
	 */
	private String paymentType;
	
	/**
	 * 付款编号
	 */
	private String paymentNo;
	
	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * 备注
	 */
	private String notes;
	
	/**
	 * 发票标记
	 */
	private String invoiceMark;
	
	/**
	 * 预收单添加成功标识
	 */
	private Boolean isSuccess;

	/**
	 * 汇款人名称
	 */
	private String remiterName ;
	
	/**
	 * 消息
	 */
	private String msg ;
	
    /**
     * 组织标杆编码
     */	
    private String unifiedCode;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRemiterName() {
		return remiterName;
	}

	public void setRemiterName(String remiterName) {
		this.remiterName = remiterName;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getInvoiceMark() {
		return invoiceMark;
	}

	public void setInvoiceMark(String invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	/**
	 * 付款单明细之来源单据类型
	 */
//	String sourceBillTypeFkd;

	/**
	 * @return  the cashierAccounting
	 */
//	public String getCashierAccounting() {
//		return cashierAccounting;
//	}


	
	/**
	 * @param cashierAccounting the cashierAccounting to set
	 */
//	public void setCashierAccounting(String cashierAccounting) {
//		this.cashierAccounting = cashierAccounting;
//	}


	/**
	 * @return  the billDepositReceivedEntityList
	 */
//	public List<BillDepositReceivedEntity> getBillDepositReceivedEntityList() {
//		return billDepositReceivedEntityList;
//	}

	
	/**
	 * @param billDepositReceivedEntityList the billDepositReceivedEntityList to set
	 */
//	public void setBillDepositReceivedEntityList(
//			List<BillDepositReceivedEntity> billDepositReceivedEntityList) {
//		this.billDepositReceivedEntityList = billDepositReceivedEntityList;
//	}

	
	
	/**
	 * @return  the transportType
	 */
	public String getTransportType() {
		return transportType;
	}


	
	/**
	 * @param transportType the transportType to set
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}


	
	/**
	 * @return  the empCode
	 */
//	public String getEmpCode() {
//		return empCode;
//	}



	
	/**
	 * @param empCode the empCode to set
	 */
//	public void setEmpCode(String empCode) {
//		this.empCode = empCode;
//	}



	/**
	 * @return  the depositReceivedNo
	 */
	public String getDepositReceivedNo() {
		return depositReceivedNo;
	}

	
	/**
	 * @param depositReceivedNo the depositReceivedNo to set
	 */
	public void setDepositReceivedNo(String depositReceivedNo) {
		this.depositReceivedNo = depositReceivedNo;
	}

	
	/**
	 * @return  the depositReceivedNos
	 */
//	public List<String> getDepositReceivedNos() {
//		return depositReceivedNos;
//	}

	
	/**
	 * @param depositReceivedNos the depositReceivedNos to set
	 */
//	public void setDepositReceivedNos(List<String> depositReceivedNos) {
//		this.depositReceivedNos = depositReceivedNos;
//	}

	

	
	/**
	 * @return  the generatingOrgCodes
	 */
//	public Set<String> getGeneratingOrgCodes() {
//		return generatingOrgCodes;
//	}



	
	/**
	 * @param generatingOrgCodes the generatingOrgCodes to set
	 */
//	public void setGeneratingOrgCodes(Set<String> generatingOrgCodes) {
//		this.generatingOrgCodes = generatingOrgCodes;
//	}



	/**
	 * @return  the queryByTab
	 */
//	public String getQueryByTab() {
//		return queryByTab;
//	}


	
	/**
	 * @param queryByTab the queryByTab to set
	 */
//	public void setQueryByTab(String queryByTab) {
//		this.queryByTab = queryByTab;
//	}


	/**
	 * @return  the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	
	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	
	/**
	 * @return  the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	
	/**
	 * @return  the collectionOrgCode
	 */
	public String getCollectionOrgCode() {
		return collectionOrgCode;
	}

	
	/**
	 * @param collectionOrgCode the collectionOrgCode to set
	 */
	public void setCollectionOrgCode(String collectionOrgCode) {
		this.collectionOrgCode = collectionOrgCode;
	}

	
	/**
	 * @return  the collectionOrgName
	 */
	public String getCollectionOrgName() {
		return collectionOrgName;
	}

	
	/**
	 * @param collectionOrgName the collectionOrgName to set
	 */
	public void setCollectionOrgName(String collectionOrgName) {
		this.collectionOrgName = collectionOrgName;
	}

	
	/**
	 * @return  the generatingOrgCode
	 */
	public String getGeneratingOrgCode() {
		return generatingOrgCode;
	}

	
	/**
	 * @param generatingOrgCode the generatingOrgCode to set
	 */
	public void setGeneratingOrgCode(String generatingOrgCode) {
		this.generatingOrgCode = generatingOrgCode;
	}

	
	/**
	 * @return  the generatingOrgName
	 */
	public String getGeneratingOrgName() {
		return generatingOrgName;
	}

	
	/**
	 * @param generatingOrgName the generatingOrgName to set
	 */
	public void setGeneratingOrgName(String generatingOrgName) {
		this.generatingOrgName = generatingOrgName;
	}

	
	/**
	 * @return  the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	
	/**
	 * @return  the remitNo
	 */
	public String getRemitNo() {
		return remitNo;
	}

	
	/**
	 * @param remitNo the remitNo to set
	 */
	public void setRemitNo(String remitNo) {
		this.remitNo = remitNo;
	}

	
	/**
	 * @return  the isRedBack
	 */
	public String getIsRedBack() {
		return isRedBack;
	}

	
	/**
	 * @param isRedBack the isRedBack to set
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
	}

	
	/**
	 * @return  the addDtoList
	 */
//	public List<BillPaymentAddDto> getAddDtoList() {
//		return addDtoList;
//	}

	
	/**
	 * @param addDtoList the addDtoList to set
	 */
//	public void setAddDtoList(List<BillPaymentAddDto> addDtoList) {
//		this.addDtoList = addDtoList;
//	}

	
	/**
	 * @return  the paymentEntity
	 */
//	public BillPaymentEntity getPaymentEntity() {
//		return paymentEntity;
//	}

	
	/**
	 * @param paymentEntity the paymentEntity to set
	 */
//	public void setPaymentEntity(BillPaymentEntity paymentEntity) {
//		this.paymentEntity = paymentEntity;
//	}

	
	/**
	 * @return  the totalNum
	 */
//	public long getTotalNum() {
//		return totalNum;
//	}

	
	/**
	 * @param totalNum the totalNum to set
	 */
//	public void setTotalNum(long totalNum) {
//		this.totalNum = totalNum;
//	}

	
	/**
	 * @return  the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	
	/**
	 * @return  the totalVerifyAmount
	 */
	public BigDecimal getTotalVerifyAmount() {
		return totalVerifyAmount;
	}

	
	/**
	 * @param totalVerifyAmount the totalVerifyAmount to set
	 */
	public void setTotalVerifyAmount(BigDecimal totalVerifyAmount) {
		this.totalVerifyAmount = totalVerifyAmount;
	}

	
	/**
	 * @return  the totalUnverifyAmount
	 */
	public BigDecimal getTotalUnverifyAmount() {
		return totalUnverifyAmount;
	}

	
	/**
	 * @param totalUnverifyAmount the totalUnverifyAmount to set
	 */
	public void setTotalUnverifyAmount(BigDecimal totalUnverifyAmount) {
		this.totalUnverifyAmount = totalUnverifyAmount;
	}

	
	/**
	 * @return  the startBusinessDate
	 */
	public Date getStartBusinessDate() {
		return startBusinessDate;
	}

	
	/**
	 * @param startBusinessDate the startBusinessDate to set
	 */
	public void setStartBusinessDate(Date startBusinessDate) {
		this.startBusinessDate = startBusinessDate;
	}

	
	/**
	 * @return  the endBusinessDate
	 */
	public Date getEndBusinessDate() {
		return endBusinessDate;
	}

	
	/**
	 * @param endBusinessDate the endBusinessDate to set
	 */
	public void setEndBusinessDate(Date endBusinessDate) {
		this.endBusinessDate = endBusinessDate;
	}

	
	/**
	 * @return  the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	
	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	
	/**
	 * @return  the paymentNo
	 */
	public String getPaymentNo() {
		return paymentNo;
	}

	
	/**
	 * @param paymentNo the paymentNo to set
	 */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	
	/**
	 * @return  the active
	 */
	public String getActive() {
		return active;
	}

	
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	
	/**
	 * @return  the notes
	 */
	public String getNotes() {
		return notes;
	}

	
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}



	
	/**
	 * @get
	 * @return sourceBillTypeFkd
	 */
//	public String getSourceBillTypeFkd() {
//		/*
//		 * @get
//		 * @return sourceBillTypeFkd
//		 */
//		return sourceBillTypeFkd;
//	}



	
	/**
	 * @set
	 * @param sourceBillTypeFkd
	 */
//	public void setSourceBillTypeFkd(String sourceBillTypeFkd) {
//		/*
//		 *@set
//		 *@this.sourceBillTypeFkd = sourceBillTypeFkd
//		 */
//		this.sourceBillTypeFkd = sourceBillTypeFkd;
//	}



	
	/**
	 * @return  the isInit
	 */
	public String getIsInit() {
		return isInit;
	}



	
	/**
	 * @param isInit the isInit to set
	 */
	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	public String getUnifiedCode() {
		return unifiedCode;
	}

	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}
	
	
	
}
