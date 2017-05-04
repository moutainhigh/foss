/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-writeoff-api
 * PACKAGE NAME: com.deppon.foss.module.settlement.writeoff.api.shared.dto
 * FILE    NAME: BillRecWriteoffBillPayResultDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;

/**
 * 应收冲应付Result Dto
 * @author 095793-foss-LiQin
 * @date 2012-10-16 下午8:30:15
 */
public class BillRecWriteoffBillPayResultDto implements Serializable {

    /**
	 * 应收单序列号
	 */
	private static final long serialVersionUID = -5081644084166686248L;

	/**
     * 应收单
     */
    private BillReceivableEntity billReceivableEntity;
    
    /**
     * 应收单列表
     */
    private List<BillReceivableEntity> billReceivableEntityList = new ArrayList<BillReceivableEntity>();
    
    
    /**
     * 应付单
     */
    private BillPayableEntity billPayableEntity;
    
    
    /**
     * 应付单列表
     */
    private List<BillPayableEntity> billPayableEntityList = new ArrayList<BillPayableEntity>();
    
    /**
     * 应收总条数
     */
    private int recTotalNum;
    
    /**
     * 应付总条数
     */
    private int payTotalNum;
    
    
    /**
     * 应收总金额
     */
    private BigDecimal recTotalAmount=BigDecimal.ZERO;
    
	/**
     * 应付总金额
     */
    private BigDecimal payTotalAmount=BigDecimal.ZERO;
    
    
    /**
     * 应收未核消总金额
     */
    private BigDecimal recUnverifyTotalAmount=BigDecimal.ZERO;
    
    
    /**
     * 应付未核消总金额
     */
    private BigDecimal payUnverifyTotalAmount=BigDecimal.ZERO;
    
    
    
    /**
     * 应收已核消总金额
     */
    private BigDecimal recVerifyTotalAmount=BigDecimal.ZERO;
    

	/**
     * 应付已核消总金额
     */
    private BigDecimal payVerifyTotalAmount=BigDecimal.ZERO;

    

    /**
     * 页面最大显示条数
     */
    private int maxShowNum;
   
    /**
     * 应收单客户编码
     */
    private String customerCode;
    
    
	/**
	 * 应收单客户类型
	 */
	private String customerType;
    

	/**
     * 登录用户所属部门
     */
    private String generatingOrgCode;
    
    
    /**
     * 应收单号集
     */
    private List<String> receivableNos;
    

	/**
     * 应付单号集
     */
    private List<String> payableNos;
    
    /**
     * 是否有效
     */
    private String active ;
    
    /**
     * 是否非红单
     */
    private String isRedBack;
    
    /**
     * 是否对账
     */
    private String refundStatus;
    
    /**
     * 登录部门
     */
    private String isGrenerateStatement;
    
    /**
     * 状态
     */
    private List<String> statusList;

    /**
     * 单据类型
     */
	private List<String>  billTypeList;

	
	/**
	 * @return 
		billReceivableEntity
	 */
	public BillReceivableEntity getBillReceivableEntity() {
		return billReceivableEntity;
	}

	
	/**
	 * @param 
		billReceivableEntity
	 */
	public void setBillReceivableEntity(BillReceivableEntity billReceivableEntity) {
		this.billReceivableEntity = billReceivableEntity;
	}

	
	/**
	 * @return 
		billReceivableEntityList
	 */
	public List<BillReceivableEntity> getBillReceivableEntityList() {
		return billReceivableEntityList;
	}

	
	/**
	 * @param 
		billReceivableEntityList
	 */
	public void setBillReceivableEntityList(List<BillReceivableEntity> billReceivableEntityList) {
		this.billReceivableEntityList = billReceivableEntityList;
	}

	
	/**
	 * @return 
		billPayableEntity
	 */
	public BillPayableEntity getBillPayableEntity() {
		return billPayableEntity;
	}

	
	/**
	 * @param 
		billPayableEntity
	 */
	public void setBillPayableEntity(BillPayableEntity billPayableEntity) {
		this.billPayableEntity = billPayableEntity;
	}

	
	/**
	 * @return 
		billPayableEntityList
	 */
	public List<BillPayableEntity> getBillPayableEntityList() {
		return billPayableEntityList;
	}

	
	/**
	 * @param 
		billPayableEntityList
	 */
	public void setBillPayableEntityList(
			List<BillPayableEntity> billPayableEntityList) {
		this.billPayableEntityList = billPayableEntityList;
	}

	
	/**
	 * @return 
		recTotalNum
	 */
	public int getRecTotalNum() {
		return recTotalNum;
	}

	
	/**
	 * @param 
		recTotalNum
	 */
	public void setRecTotalNum(int recTotalNum) {
		this.recTotalNum = recTotalNum;
	}

	
	/**
	 * @return 
		payTotalNum
	 */
	public int getPayTotalNum() {
		return payTotalNum;
	}

	
	/**
	 * @param 
		payTotalNum
	 */
	public void setPayTotalNum(int payTotalNum) {
		this.payTotalNum = payTotalNum;
	}

	
	/**
	 * @return 
		recTotalAmount
	 */
	public BigDecimal getRecTotalAmount() {
		return recTotalAmount;
	}

	
	/**
	 * @param 
		recTotalAmount
	 */
	public void setRecTotalAmount(BigDecimal recTotalAmount) {
		this.recTotalAmount = recTotalAmount;
	}

	
	/**
	 * @return 
		payTotalAmount
	 */
	public BigDecimal getPayTotalAmount() {
		return payTotalAmount;
	}

	
	/**
	 * @param 
		payTotalAmount
	 */
	public void setPayTotalAmount(BigDecimal payTotalAmount) {
		this.payTotalAmount = payTotalAmount;
	}

	
	/**
	 * @return 
		recUnverifyTotalAmount
	 */
	public BigDecimal getRecUnverifyTotalAmount() {
		return recUnverifyTotalAmount;
	}

	
	/**
	 * @param 
		recUnverifyTotalAmount
	 */
	public void setRecUnverifyTotalAmount(BigDecimal recUnverifyTotalAmount) {
		this.recUnverifyTotalAmount = recUnverifyTotalAmount;
	}

	
	/**
	 * @return 
		payUnverifyTotalAmount
	 */
	public BigDecimal getPayUnverifyTotalAmount() {
		return payUnverifyTotalAmount;
	}

	
	/**
	 * @param 
		payUnverifyTotalAmount
	 */
	public void setPayUnverifyTotalAmount(BigDecimal payUnverifyTotalAmount) {
		this.payUnverifyTotalAmount = payUnverifyTotalAmount;
	}

	
	/**
	 * @return 
		recVerifyTotalAmount
	 */
	public BigDecimal getRecVerifyTotalAmount() {
		return recVerifyTotalAmount;
	}

	
	/**
	 * @param 
		recVerifyTotalAmount
	 */
	public void setRecVerifyTotalAmount(BigDecimal recVerifyTotalAmount) {
		this.recVerifyTotalAmount = recVerifyTotalAmount;
	}

	
	/**
	 * @return 
		payVerifyTotalAmount
	 */
	public BigDecimal getPayVerifyTotalAmount() {
		return payVerifyTotalAmount;
	}

	
	/**
	 * @param 
		payVerifyTotalAmount
	 */
	public void setPayVerifyTotalAmount(BigDecimal payVerifyTotalAmount) {
		this.payVerifyTotalAmount = payVerifyTotalAmount;
	}

	
	
	/**
	 * @return 
		maxShowNum
	 */
	public int getMaxShowNum() {
		return maxShowNum;
	}

	
	/**
	 * @param 
		maxShowNum
	 */
	public void setMaxShowNum(int maxShowNum) {
		this.maxShowNum = maxShowNum;
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
		customerType
	 */
	public String getCustomerType() {
		return customerType;
	}

	
	/**
	 * @param 
		customerType
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	
	/**
	 * @return 
		generatingOrgCode
	 */
	public String getGeneratingOrgCode() {
		return generatingOrgCode;
	}

	
	/**
	 * @param 
		generatingOrgCode
	 */
	public void setGeneratingOrgCode(String generatingOrgCode) {
		this.generatingOrgCode = generatingOrgCode;
	}

	
	/**
	 * @return 
		receivableNos
	 */
	public List<String> getReceivableNos() {
		return receivableNos;
	}

	
	/**
	 * @param 
		receivableNos
	 */
	public void setReceivableNos(List<String> receivableNos) {
		this.receivableNos = receivableNos;
	}

	
	/**
	 * @return 
		payableNos
	 */
	public List<String> getPayableNos() {
		return payableNos;
	}

	
	/**
	 * @param 
		payableNos
	 */
	public void setPayableNos(List<String> payableNos) {
		this.payableNos = payableNos;
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
		refundStatus
	 */
	public String getRefundStatus() {
		return refundStatus;
	}

	
	/**
	 * @param 
		refundStatus
	 */
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	
	/**
	 * @return 
		isGrenerateStatement
	 */
	public String getIsGrenerateStatement() {
		return isGrenerateStatement;
	}

	
	/**
	 * @param 
		isGrenerateStatement
	 */
	public void setIsGrenerateStatement(String isGrenerateStatement) {
		this.isGrenerateStatement = isGrenerateStatement;
	}

	
	/**
	 * @return 
		statusList
	 */
	public List<String> getStatusList() {
		return statusList;
	}

	
	/**
	 * @param 
		statusList
	 */
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}

	
	/**
	 * @return 
		billTypeList
	 */
	public List<String> getBillTypeList() {
		return billTypeList;
	}

	
	/**
	 * @param 
		billTypeList
	 */
	public void setBillTypeList(List<String> billTypeList) {
		this.billTypeList = billTypeList;
	}
    
	
	
}
