/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-writeoff-api
 * PACKAGE NAME: com.deppon.foss.module.settlement.writeoff.api.shared.vo
 * FILE    NAME: BillRecWriteoffBillPayResultVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayResultDto;

/**
 * 应收冲应付单、应付单界面输入
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午8:58:13
 */
public class BillRecWriteoffBillPayResultVo implements Serializable {

	/**
	 * 应付单参数类序列号
	 */
	private static final long serialVersionUID = -4537894559420160357L;

	/**
	 * 应付单实体
	 */
	private BillPayableEntity billPayableEntity;

	/**
	 * 应收单实体
	 */
	private BillReceivableEntity billReceivableEntity;

	/**
	 * 应付单列表
	 */
	private List<BillPayableEntity> billPayableEntityList = new ArrayList<BillPayableEntity>();

	/**
	 * 应收单列表
	 */
	private List<BillReceivableEntity> billReceivableEntityList = new ArrayList<BillReceivableEntity>();

	/**
	 * 根据应收单号查询列表
	 */
	private List<BillReceivableEntity> billReceivableEntityNosList = new ArrayList<BillReceivableEntity>();

	/**
	 * 根据应付单号查询列表
	 */

	private List<BillPayableEntity> billPayableEntityNosList = new ArrayList<BillPayableEntity>();

	/**
	 * result dto
	 */
	private BillRecWriteoffBillPayResultDto billRecWriteoffBillPayResultDto;

	/**
	 * 应付单业务开始时间
	 */
	private Date businessStartDate;

	/**
	 * 客户类型
	 */
	private String customerType;

	/**
	 * 应付单页面最大显示条数
	 */
	private int maxShowNum;

	/**
	 * 应付单业务结束时间
	 */
	private Date businessEndDate;

	/**
	 * 应付单客户编码
	 */
	private String customerCode;

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
	private BigDecimal recTotalAmount = BigDecimal.ZERO;

	/**
	 * 应付总金额
	 */
	private BigDecimal payTotalAmount = BigDecimal.ZERO;

	/**
	 * 应收未核消总金额
	 */
	private BigDecimal recUnverifyTotalAmount = BigDecimal.ZERO;

	/**
	 * 应付未核消总金额
	 */
	private BigDecimal payUnverifyTotalAmount = BigDecimal.ZERO;

	/**
	 * 应收已核消总金额
	 */
	private BigDecimal recVerifyTotalAmount = BigDecimal.ZERO;

	/**
	 * 应付已核消总金额
	 */
	private BigDecimal payVerifyTotalAmount = BigDecimal.ZERO;

	
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
		billPayableEntityList
	 */
	public List<BillPayableEntity> getBillPayableEntityList() {
		return billPayableEntityList;
	}

	
	/**
	 * @param 
		billPayableEntityList
	 */
	public void setBillPayableEntityList(List<BillPayableEntity> billPayableEntityList) {
		this.billPayableEntityList = billPayableEntityList;
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
		billReceivableEntityNosList
	 */
	public List<BillReceivableEntity> getBillReceivableEntityNosList() {
		return billReceivableEntityNosList;
	}

	
	/**
	 * @param 
		billReceivableEntityNosList
	 */
	public void setBillReceivableEntityNosList(List<BillReceivableEntity> billReceivableEntityNosList) {
		this.billReceivableEntityNosList = billReceivableEntityNosList;
	}

	
	/**
	 * @return 
		billPayableEntityNosList
	 */
	public List<BillPayableEntity> getBillPayableEntityNosList() {
		return billPayableEntityNosList;
	}

	
	/**
	 * @param 
		billPayableEntityNosList
	 */
	public void setBillPayableEntityNosList(List<BillPayableEntity> billPayableEntityNosList) {
		this.billPayableEntityNosList = billPayableEntityNosList;
	}

	
	/**
	 * @return 
		billRecWriteoffBillPayResultDto
	 */
	public BillRecWriteoffBillPayResultDto getBillRecWriteoffBillPayResultDto() {
		return billRecWriteoffBillPayResultDto;
	}

	
	/**
	 * @param 
		billRecWriteoffBillPayResultDto
	 */
	public void setBillRecWriteoffBillPayResultDto(BillRecWriteoffBillPayResultDto billRecWriteoffBillPayResultDto) {
		this.billRecWriteoffBillPayResultDto = billRecWriteoffBillPayResultDto;
	}

	
	/**
	 * @return 
		businessStartDate
	 */
	public Date getBusinessStartDate() {
		return businessStartDate;
	}

	
	/**
	 * @param 
		businessStartDate
	 */
	public void setBusinessStartDate(Date businessStartDate) {
		this.businessStartDate = businessStartDate;
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
		businessEndDate
	 */
	public Date getBusinessEndDate() {
		return businessEndDate;
	}

	
	/**
	 * @param 
		businessEndDate
	 */
	public void setBusinessEndDate(Date businessEndDate) {
		this.businessEndDate = businessEndDate;
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

	

}
