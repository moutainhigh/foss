/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/DeliverbillDto.java
 * 
 * FILE NAME        	: DeliverbillDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 运单明细总数
 * @author 159231 meiying
 * 2015-6-5  上午8:44:39
 */
public class WaybillDeliverNewCountDto implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	
	/** 待排单总票数. */
	private Long totalGoodsQty;

	/** 待排单总重量. */
	private BigDecimal totalGoodsWeight;

	/** 待排单总体积. */
	private BigDecimal totalGoodsVolume;

	/**
	 * 已排单运单明细页面下方.总票数
	 */
	private int totalTicket;
	/**
	 * 已排单运单明细页面下方.总件数
	 */
	private int totalCount;
	/**
	 * 已排单运单明细页面下方.总重量
	 */
	private BigDecimal totalWeight;
	/**
	 * 已排单运单明细页面下方.总体积
	 */
	private BigDecimal totalVolumn;
	/**
	 * 已排单运单明细页面下方.到付金额
	 */
	private BigDecimal totalPayAmount;
	/**
	 * 已排单运单明细页面下方.装载率(容量和体积)
	 */
	private String loadRate;
	/**
	 * 已排单运单明细页面下方.额定净空/额定载重率
	 */
	private String nominalRate;
	/**
	 * 带货(方)
	 */
	private BigDecimal expectedBringVolume;
	/**
	 * 查询排班信息.班次
	 */
	private String frequencyNo;
	//排程二期-出车任务
	private String carTaskinfo;
	//排程二期-预计出车时间
	private String preCartaskTime;
	//排程二期-带货部门编码
	private String takeGoodsDeptcode;
	//排程二期-带货部门
	private String takeGoodsDeptname;
	//排程二期-转货部门编码
	private String transferDeptcode;
	//排程二期-转货部门
	private String transferDeptname;

	/**
	 * 获取totalGoodsQty  
	 * @return totalGoodsQty totalGoodsQty
	 */
	public Long getTotalGoodsQty() {
		return totalGoodsQty;
	}

	/**
	 * 设置totalGoodsQty  
	 * @param totalGoodsQty totalGoodsQty 
	 */
	public void setTotalGoodsQty(Long totalGoodsQty) {
		this.totalGoodsQty = totalGoodsQty;
	}

	/**
	 * 获取totalGoodsWeight  
	 * @return totalGoodsWeight totalGoodsWeight
	 */
	public BigDecimal getTotalGoodsWeight() {
		return totalGoodsWeight;
	}

	/**
	 * 设置totalGoodsWeight  
	 * @param totalGoodsWeight totalGoodsWeight 
	 */
	public void setTotalGoodsWeight(BigDecimal totalGoodsWeight) {
		this.totalGoodsWeight = totalGoodsWeight;
	}

	/**
	 * 获取totalGoodsVolume  
	 * @return totalGoodsVolume totalGoodsVolume
	 */
	public BigDecimal getTotalGoodsVolume() {
		return totalGoodsVolume;
	}

	/**
	 * 设置totalGoodsVolume  
	 * @param totalGoodsVolume totalGoodsVolume 
	 */
	public void setTotalGoodsVolume(BigDecimal totalGoodsVolume) {
		this.totalGoodsVolume = totalGoodsVolume;
	}

	

	/**
	 * 获取loadRate  
	 * @return loadRate loadRate
	 */
	public String getLoadRate() {
		return loadRate;
	}

	/**
	 * 设置loadRate  
	 * @param loadRate loadRate 
	 */
	public void setLoadRate(String loadRate) {
		this.loadRate = loadRate;
	}

	/**
	 * 获取nominalRate  
	 * @return nominalRate nominalRate
	 */
	public String getNominalRate() {
		return nominalRate;
	}

	/**
	 * 设置nominalRate  
	 * @param nominalRate nominalRate 
	 */
	public void setNominalRate(String nominalRate) {
		this.nominalRate = nominalRate;
	}

	/**
	 * 获取totalTicket  
	 * @return totalTicket totalTicket
	 */
	public int getTotalTicket() {
		return totalTicket;
	}

	/**
	 * 设置totalTicket  
	 * @param totalTicket totalTicket 
	 */
	public void setTotalTicket(int totalTicket) {
		this.totalTicket = totalTicket;
	}

	/**
	 * 获取totalCount  
	 * @return totalCount totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置totalCount  
	 * @param totalCount totalCount 
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 获取totalWeight  
	 * @return totalWeight totalWeight
	 */
	public BigDecimal getTotalWeight() {
		return totalWeight;
	}

	/**
	 * 设置totalWeight  
	 * @param totalWeight totalWeight 
	 */
	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}

	/**
	 * 获取totalVolumn  
	 * @return totalVolumn totalVolumn
	 */
	public BigDecimal getTotalVolumn() {
		return totalVolumn;
	}

	/**
	 * 设置totalVolumn  
	 * @param totalVolumn totalVolumn 
	 */
	public void setTotalVolumn(BigDecimal totalVolumn) {
		this.totalVolumn = totalVolumn;
	}


	/**
	 * 获取expectedBringVolume  
	 * @return expectedBringVolume expectedBringVolume
	 */
	public BigDecimal getExpectedBringVolume() {
		return expectedBringVolume;
	}

	/**
	 * 设置expectedBringVolume  
	 * @param expectedBringVolume expectedBringVolume 
	 */
	public void setExpectedBringVolume(BigDecimal expectedBringVolume) {
		this.expectedBringVolume = expectedBringVolume;
	}

	/**
	 * 获取frequencyNo  
	 * @return frequencyNo frequencyNo
	 */
	public String getFrequencyNo() {
		return frequencyNo;
	}

	/**
	 * 设置frequencyNo  
	 * @param frequencyNo frequencyNo 
	 */
	public void setFrequencyNo(String frequencyNo) {
		this.frequencyNo = frequencyNo;
	}

	/**
	 * 获取totalPayAmount  
	 * @return totalPayAmount totalPayAmount
	 */
	public BigDecimal getTotalPayAmount() {
		return totalPayAmount;
	}

	/**
	 * 设置totalPayAmount  
	 * @param totalPayAmount totalPayAmount 
	 */
	public void setTotalPayAmount(BigDecimal totalPayAmount) {
		this.totalPayAmount = totalPayAmount;
	}

	public String getCarTaskinfo() {
		return carTaskinfo;
	}

	public void setCarTaskinfo(String carTaskinfo) {
		this.carTaskinfo = carTaskinfo;
	}

	public String getPreCartaskTime() {
		return preCartaskTime;
	}

	public void setPreCartaskTime(String preCartaskTime) {
		this.preCartaskTime = preCartaskTime;
	}

	public String getTakeGoodsDeptcode() {
		return takeGoodsDeptcode;
	}

	public void setTakeGoodsDeptcode(String takeGoodsDeptcode) {
		this.takeGoodsDeptcode = takeGoodsDeptcode;
	}

	public String getTakeGoodsDeptname() {
		return takeGoodsDeptname;
	}

	public void setTakeGoodsDeptname(String takeGoodsDeptname) {
		this.takeGoodsDeptname = takeGoodsDeptname;
	}

	public String getTransferDeptcode() {
		return transferDeptcode;
	}

	public void setTransferDeptcode(String transferDeptcode) {
		this.transferDeptcode = transferDeptcode;
	}

	public String getTransferDeptname() {
		return transferDeptname;
	}

	public void setTransferDeptname(String transferDeptname) {
		this.transferDeptname = transferDeptname;
	}
}