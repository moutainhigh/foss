/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/ForecastForPlanDto.java
 * 
 *  FILE NAME     :ForecastForPlanDto.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.dto
 * FILE    NAME: ForecastForPlanDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 发车计划的货量预测dto
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-12-10 上午11:04:28
 */
public class ForecastForPlanDto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 出发部门
	 */
	private String origOrgName;
	/**
	 * 到达部门
	 */
	private String destOrgName;
	/**
	 * 出发部门
	 */
	private String origOrgCode;

	/**
	 * 到达部门
	 */
	private String destOrgCode;
	/**
	 * 发车日期
	 */
	private Date departDate;

	/**
	 * 卡货重量/体积/票数
	 */
	private String truckInfo;
	/**
	 * 城货重量/体积/票数
	 */
	private String intercityInfo;
	/**
	 * 开单未交接重量/体积/票数
	 */
	private String noneTransferOfBilling;
	/**
	 * 余货重量/体积/票数
	 */
	private String lastGoodsInfo;
	/**
	 * 预计到达重量/体积/票数
	 */
	private String exceptArrivalInfo;
	/**
	 * 总重量/体积/票数
	 */
	private String totalInfo;
	/**
	 * (小计)合发重量/体积/票数
	 */
	private String mergerInfo;
	/**
	 * (汇总)重量/体积/票数
	 */
	private String allTotalGoodsInfo;
	/**
	 * 货量预测时间
	 */
	private Date forecastTime;
	/**
	 * 总重量
	 */
	private BigDecimal weightTotal;
	/**
	 * 总体积
	 */
	private BigDecimal volumeTotal;
	/**
	 * 合车体积
	 */
	private BigDecimal deviationVolume;
	/**
	 * 预测货物体积
	 */
	private BigDecimal forecastVolumeTotal;
	/**
	 * 预测货物重量
	 */
	private BigDecimal forecastWeightTotal;
	/**
	 * 备注
	 */
	private String notes;
	/**
	 * 是否异常
	 */
	private String isIssue;

	/**
	 * 获取 出发部门.
	 * 
	 * @return the 出发部门
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * 设置 出发部门.
	 * 
	 * @param origOrgName
	 *            the new 出发部门
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * 获取 到达部门.
	 * 
	 * @return the 到达部门
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * 设置 到达部门.
	 * 
	 * @param destOrgName
	 *            the new 到达部门
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * 获取 出发部门.
	 * 
	 * @return the 出发部门
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * 设置 出发部门.
	 * 
	 * @param origOrgCode
	 *            the new 出发部门
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * 获取 到达部门.
	 * 
	 * @return the 到达部门
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * 设置 到达部门.
	 * 
	 * @param destOrgCode
	 *            the new 到达部门
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * 获取 发车日期.
	 * 
	 * @return the 发车日期
	 */
	public Date getDepartDate() {
		return departDate;
	}

	/**
	 * 设置 发车日期.
	 * 
	 * @param departDate
	 *            the new 发车日期
	 */
	public void setDepartDate(Date departDate) {
		this.departDate = departDate;
	}

	/**
	 * 获取 卡货重量/体积/票数.
	 * 
	 * @return the 卡货重量/体积/票数
	 */
	public String getTruckInfo() {
		return truckInfo;
	}

	/**
	 * 设置 卡货重量/体积/票数.
	 * 
	 * @param truckInfo
	 *            the new 卡货重量/体积/票数
	 */
	public void setTruckInfo(String truckInfo) {
		this.truckInfo = truckInfo;
	}

	/**
	 * 获取 城货重量/体积/票数.
	 * 
	 * @return the 城货重量/体积/票数
	 */
	public String getIntercityInfo() {
		return intercityInfo;
	}

	/**
	 * 设置 城货重量/体积/票数.
	 * 
	 * @param intercityInfo
	 *            the new 城货重量/体积/票数
	 */
	public void setIntercityInfo(String intercityInfo) {
		this.intercityInfo = intercityInfo;
	}

	/**
	 * 获取 开单未交接重量/体积/票数.
	 * 
	 * @return the 开单未交接重量/体积/票数
	 */
	public String getNoneTransferOfBilling() {
		return noneTransferOfBilling;
	}

	/**
	 * 设置 开单未交接重量/体积/票数.
	 * 
	 * @param noneTransferOfBilling
	 *            the new 开单未交接重量/体积/票数
	 */
	public void setNoneTransferOfBilling(String noneTransferOfBilling) {
		this.noneTransferOfBilling = noneTransferOfBilling;
	}

	/**
	 * 获取 余货重量/体积/票数.
	 * 
	 * @return the 余货重量/体积/票数
	 */
	public String getLastGoodsInfo() {
		return lastGoodsInfo;
	}

	/**
	 * 设置 余货重量/体积/票数.
	 * 
	 * @param lastGoodsInfo
	 *            the new 余货重量/体积/票数
	 */
	public void setLastGoodsInfo(String lastGoodsInfo) {
		this.lastGoodsInfo = lastGoodsInfo;
	}

	/**
	 * 获取 预计到达重量/体积/票数.
	 * 
	 * @return the 预计到达重量/体积/票数
	 */
	public String getExceptArrivalInfo() {
		return exceptArrivalInfo;
	}

	/**
	 * 设置 预计到达重量/体积/票数.
	 * 
	 * @param exceptArrivalInfo
	 *            the new 预计到达重量/体积/票数
	 */
	public void setExceptArrivalInfo(String exceptArrivalInfo) {
		this.exceptArrivalInfo = exceptArrivalInfo;
	}

	/**
	 * 获取 总重量/体积/票数.
	 * 
	 * @return the 总重量/体积/票数
	 */
	public String getTotalInfo() {
		return totalInfo;
	}

	/**
	 * 设置 总重量/体积/票数.
	 * 
	 * @param totalInfo
	 *            the new 总重量/体积/票数
	 */
	public void setTotalInfo(String totalInfo) {
		this.totalInfo = totalInfo;
	}

	/**
	 * 获取 (小计)合发重量/体积/票数.
	 * 
	 * @return the (小计)合发重量/体积/票数
	 */
	public String getMergerInfo() {
		return mergerInfo;
	}

	/**
	 * 设置 (小计)合发重量/体积/票数.
	 * 
	 * @param mergerInfo
	 *            the new (小计)合发重量/体积/票数
	 */
	public void setMergerInfo(String mergerInfo) {
		this.mergerInfo = mergerInfo;
	}

	/**
	 * 获取 (汇总)重量/体积/票数.
	 * 
	 * @return the (汇总)重量/体积/票数
	 */
	public String getAllTotalGoodsInfo() {
		return allTotalGoodsInfo;
	}

	/**
	 * 设置 (汇总)重量/体积/票数.
	 * 
	 * @param allTotalGoodsInfo
	 *            the new (汇总)重量/体积/票数
	 */
	public void setAllTotalGoodsInfo(String allTotalGoodsInfo) {
		this.allTotalGoodsInfo = allTotalGoodsInfo;
	}

	/**
	 * 获取 货量预测时间.
	 * 
	 * @return the 货量预测时间
	 */
	public Date getForecastTime() {
		return forecastTime;
	}

	/**
	 * 设置 货量预测时间.
	 * 
	 * @param forecastTime
	 *            the new 货量预测时间
	 */
	public void setForecastTime(Date forecastTime) {
		this.forecastTime = forecastTime;
	}

	/**
	 * 获取 总重量.
	 * 
	 * @return the 总重量
	 */
	public BigDecimal getWeightTotal() {
		return weightTotal;
	}

	/**
	 * 设置 总重量.
	 * 
	 * @param weightTotal
	 *            the new 总重量
	 */
	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}

	/**
	 * 获取 总体积.
	 * 
	 * @return the 总体积
	 */
	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}

	/**
	 * 设置 总体积.
	 * 
	 * @param volumeTotal
	 *            the new 总体积
	 */
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}

	/**
	 * 获取 合车体积.
	 * 
	 * @return the 合车体积
	 */
	public BigDecimal getDeviationVolume() {
		return deviationVolume;
	}

	/**
	 * 设置 合车体积.
	 * 
	 * @param deviationVolume
	 *            the new 合车体积
	 */
	public void setDeviationVolume(BigDecimal deviationVolume) {
		this.deviationVolume = deviationVolume;
	}

	/**
	 * 获取 预测货物体积.
	 * 
	 * @return the 预测货物体积
	 */
	public BigDecimal getForecastVolumeTotal() {
		return forecastVolumeTotal;
	}

	/**
	 * 设置 预测货物体积.
	 * 
	 * @param forecastVolumeTotal
	 *            the new 预测货物体积
	 */
	public void setForecastVolumeTotal(BigDecimal forecastVolumeTotal) {
		this.forecastVolumeTotal = forecastVolumeTotal;
	}

	/**
	 * 获取 预测货物重量.
	 * 
	 * @return the 预测货物重量
	 */
	public BigDecimal getForecastWeightTotal() {
		return forecastWeightTotal;
	}

	/**
	 * 设置 预测货物重量.
	 * 
	 * @param forecastWeightTotal
	 *            the new 预测货物重量
	 */
	public void setForecastWeightTotal(BigDecimal forecastWeightTotal) {
		this.forecastWeightTotal = forecastWeightTotal;
	}

	/**
	 * 获取 备注.
	 * 
	 * @return the 备注
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * 设置 备注.
	 * 
	 * @param notes
	 *            the new 备注
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * 获取 是否异常.
	 * 
	 * @return the 是否异常
	 */
	public String getIsIssue() {
		return isIssue;
	}

	/**
	 * 设置 是否异常.
	 * 
	 * @param isIssue
	 *            the new 是否异常
	 */
	public void setIsIssue(String isIssue) {
		this.isIssue = isIssue;
	}

}