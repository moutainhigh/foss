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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/ForecastQuantityEntity.java
 * 
 *  FILE NAME     :ForecastQuantityEntity.java
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
package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 货量预测entity
 */
public class ForecastQuantityEntity extends BaseEntity{

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 7478016885266340426L;
	/**
	 * 主键ID
	 */
	private String forecastQuantityId;
	/**
	 * 出发部门、所属部门
	 */
	private String belongOrgCode;
	
	private String belongOrgCodeName;
	/**
	 * 区域
	 */
	private String region;
	
	private String regionName;
	 /**
	  * 关联部门
	  */
	private String relevantOrgCode;
	
	private String relevantOrgCodeName;
	/**
	 * 总重量
	 */
	private BigDecimal weightTotal;
	/**
	 * 总体积
	 */
	private BigDecimal volumeTotal;
	/**
	 * 总票数
	 */
	private Integer waybillQtyTotal;
	/**
	 * 卡航重量
	 */
	private BigDecimal gpsEnabledResWeightTotal;
	/**
	 * 卡航体积
	 */
	private BigDecimal gpsEnabledResVolumeTotal;
	/**
	 * 卡航票数
	 */
	private Integer gpsEnabledResQtyTotal;
	/**
	 * 城运重量
	 */
	private BigDecimal precisionIfsWeightTotal;
	/**
	 * 城运体积
	 */
	private BigDecimal precisionIfsVolumeTotal;
	/**
	 * 城运票数
	 */
	private Integer precisionIfsQtyTotal;
	/**
     * 快递重量
     */
    private BigDecimal expressWeightTotal;
    /**
     * 快递体积
     */
    private BigDecimal expressVolumeTotal;
    /**
     * 快递票数
     */
    private Integer expressQtyTotal;
	/**
	 * 在库重量
	 */
	private BigDecimal inventoryWeightTotal;
	/**
	 * 在库体积
	 */
	private BigDecimal inventoryVolumeTotal;
	/**
	 * 在库票数
	 */
	private Integer inventoryQtyTotal;
	/**
	 * 开单重量
	 */
	private BigDecimal billingWeightTotal;
	/**
	 * 开单体积
	 */
	private BigDecimal billingVolumeTotal;
	/**
	 * 开单票数
	 */
	private Integer billingQtyTotal;
	/**
	 * 在途重量
	 */
	private BigDecimal intransitWeightTotal;
	/**
	 * 在途体积
	 */
	private BigDecimal intransitVolumeTotal;
	/**
	 * 在途票数
	 */
	private Integer intransitQtyTotal;
	/**
	 * 合车体积
	 */
	private BigDecimal deviationVolume;
	/**
	 * 预测发起时间
	 */
	private Date statisticsTime;
	/**
	 * 预测类型 出发/到达
	 */
	private String type;
	/**
	 * 预测的日期
	 */
    private Date forecastTime;
    /**
     * 预测发起日期
     */
    private Date statisticsDate;
    /**
     * 预测发起小时分钟
     */
    private String statisticsHHMM;
    
	/**
	 * 获取 主键ID.
	 *
	 * @return the 主键ID
	 */
	public String getForecastQuantityId() {
		return forecastQuantityId;
	}
    
	/**
	 * 设置 主键ID.
	 *
	 * @param forecastQuantityId the new 主键ID
	 */
	public void setForecastQuantityId(String forecastQuantityId) {
		this.forecastQuantityId = forecastQuantityId;
	}

	/**
	 * 获取 出发部门、所属部门.
	 *
	 * @return the 出发部门、所属部门
	 */
	public String getBelongOrgCode() {
		return belongOrgCode;
	}

	/**
	 * 设置 出发部门、所属部门.
	 *
	 * @param belongOrgCode the new 出发部门、所属部门
	 */
	public void setBelongOrgCode(String belongOrgCode) {
		this.belongOrgCode = belongOrgCode;
	}

	/**
	 * 获取 区域.
	 *
	 * @return the 区域
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * 设置 区域.
	 *
	 * @param region the new 区域
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * 获取 关联部门.
	 *
	 * @return the 关联部门
	 */
	public String getRelevantOrgCode() {
		return relevantOrgCode;
	}

	/**
	 * 设置 关联部门.
	 *
	 * @param relevantOrgCode the new 关联部门
	 */
	public void setRelevantOrgCode(String relevantOrgCode) {
		this.relevantOrgCode = relevantOrgCode;
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
	 * @param weightTotal the new 总重量
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
	 * @param volumeTotal the new 总体积
	 */
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}

	/**
	 * 获取 总票数.
	 *
	 * @return the 总票数
	 */
	public Integer getWaybillQtyTotal() {
		return waybillQtyTotal;
	}

	/**
	 * 设置 总票数.
	 *
	 * @param waybillQtyTotal the new 总票数
	 */
	public void setWaybillQtyTotal(Integer waybillQtyTotal) {
		this.waybillQtyTotal = waybillQtyTotal;
	}

	/**
	 * 获取 卡航重量.
	 *
	 * @return the 卡航重量
	 */
	public BigDecimal getGpsEnabledResWeightTotal() {
		return gpsEnabledResWeightTotal;
	}

	/**
	 * 设置 卡航重量.
	 *
	 * @param gpsEnabledResWeightTotal the new 卡航重量
	 */
	public void setGpsEnabledResWeightTotal(BigDecimal gpsEnabledResWeightTotal) {
		this.gpsEnabledResWeightTotal = gpsEnabledResWeightTotal;
	}

	/**
	 * 获取 卡航体积.
	 *
	 * @return the 卡航体积
	 */
	public BigDecimal getGpsEnabledResVolumeTotal() {
		return gpsEnabledResVolumeTotal;
	}

	/**
	 * 设置 卡航体积.
	 *
	 * @param gpsEnabledResVolumeTotal the new 卡航体积
	 */
	public void setGpsEnabledResVolumeTotal(BigDecimal gpsEnabledResVolumeTotal) {
		this.gpsEnabledResVolumeTotal = gpsEnabledResVolumeTotal;
	}

	/**
	 * 获取 卡航票数.
	 *
	 * @return the 卡航票数
	 */
	public Integer getGpsEnabledResQtyTotal() {
		return gpsEnabledResQtyTotal;
	}

	/**
	 * 设置 卡航票数.
	 *
	 * @param gpsEnabledResQtyTotal the new 卡航票数
	 */
	public void setGpsEnabledResQtyTotal(Integer gpsEnabledResQtyTotal) {
		this.gpsEnabledResQtyTotal = gpsEnabledResQtyTotal;
	}

	/**
	 * 获取 城运重量.
	 *
	 * @return the 城运重量
	 */
	public BigDecimal getPrecisionIfsWeightTotal() {
		return precisionIfsWeightTotal;
	}

	/**
	 * 设置 城运重量.
	 *
	 * @param precisionIfsWeightTotal the new 城运重量
	 */
	public void setPrecisionIfsWeightTotal(BigDecimal precisionIfsWeightTotal) {
		this.precisionIfsWeightTotal = precisionIfsWeightTotal;
	}

	/**
	 * 获取 城运体积.
	 *
	 * @return the 城运体积
	 */
	public BigDecimal getPrecisionIfsVolumeTotal() {
		return precisionIfsVolumeTotal;
	}

	/**
	 * 设置 城运体积.
	 *
	 * @param precisionIfsVolumeTotal the new 城运体积
	 */
	public void setPrecisionIfsVolumeTotal(BigDecimal precisionIfsVolumeTotal) {
		this.precisionIfsVolumeTotal = precisionIfsVolumeTotal;
	}

	/**
	 * 获取 城运票数.
	 *
	 * @return the 城运票数
	 */
	public Integer getPrecisionIfsQtyTotal() {
		return precisionIfsQtyTotal;
	}

	/**
	 * 设置 城运票数.
	 *
	 * @param precisionIfsQtyTotal the new 城运票数
	 */
	public void setPrecisionIfsQtyTotal(Integer precisionIfsQtyTotal) {
		this.precisionIfsQtyTotal = precisionIfsQtyTotal;
	}

	public BigDecimal getExpressWeightTotal() {
		return expressWeightTotal;
	}

	public void setExpressWeightTotal(BigDecimal expressWeightTotal) {
		this.expressWeightTotal = expressWeightTotal;
	}

	public BigDecimal getExpressVolumeTotal() {
		return expressVolumeTotal;
	}

	public void setExpressVolumeTotal(BigDecimal expressVolumeTotal) {
		this.expressVolumeTotal = expressVolumeTotal;
	}

	public Integer getExpressQtyTotal() {
		return expressQtyTotal;
	}

	public void setExpressQtyTotal(Integer expressQtyTotal) {
		this.expressQtyTotal = expressQtyTotal;
	}

	/**
	 * 获取 在库重量.
	 *
	 * @return the 在库重量
	 */
	public BigDecimal getInventoryWeightTotal() {
		return inventoryWeightTotal;
	}

	/**
	 * 设置 在库重量.
	 *
	 * @param inventoryWeightTotal the new 在库重量
	 */
	public void setInventoryWeightTotal(BigDecimal inventoryWeightTotal) {
		this.inventoryWeightTotal = inventoryWeightTotal;
	}

	/**
	 * 获取 在库体积.
	 *
	 * @return the 在库体积
	 */
	public BigDecimal getInventoryVolumeTotal() {
		return inventoryVolumeTotal;
	}

	/**
	 * 设置 在库体积.
	 *
	 * @param inventoryVolumeTotal the new 在库体积
	 */
	public void setInventoryVolumeTotal(BigDecimal inventoryVolumeTotal) {
		this.inventoryVolumeTotal = inventoryVolumeTotal;
	}

	/**
	 * 获取 在库票数.
	 *
	 * @return the 在库票数
	 */
	public Integer getInventoryQtyTotal() {
		return inventoryQtyTotal;
	}

	/**
	 * 设置 在库票数.
	 *
	 * @param inventoryQtyTotal the new 在库票数
	 */
	public void setInventoryQtyTotal(Integer inventoryQtyTotal) {
		this.inventoryQtyTotal = inventoryQtyTotal;
	}

	/**
	 * 获取 开单重量.
	 *
	 * @return the 开单重量
	 */
	public BigDecimal getBillingWeightTotal() {
		return billingWeightTotal;
	}

	/**
	 * 设置 开单重量.
	 *
	 * @param billingWeightTotal the new 开单重量
	 */
	public void setBillingWeightTotal(BigDecimal billingWeightTotal) {
		this.billingWeightTotal = billingWeightTotal;
	}

	/**
	 * 获取 开单体积.
	 *
	 * @return the 开单体积
	 */
	public BigDecimal getBillingVolumeTotal() {
		return billingVolumeTotal;
	}

	/**
	 * 设置 开单体积.
	 *
	 * @param billingVolumeTotal the new 开单体积
	 */
	public void setBillingVolumeTotal(BigDecimal billingVolumeTotal) {
		this.billingVolumeTotal = billingVolumeTotal;
	}

	/**
	 * 获取 开单票数.
	 *
	 * @return the 开单票数
	 */
	public Integer getBillingQtyTotal() {
		return billingQtyTotal;
	}

	/**
	 * 设置 开单票数.
	 *
	 * @param billingQtyTotal the new 开单票数
	 */
	public void setBillingQtyTotal(Integer billingQtyTotal) {
		this.billingQtyTotal = billingQtyTotal;
	}

	/**
	 * 获取 在途重量.
	 *
	 * @return the 在途重量
	 */
	public BigDecimal getIntransitWeightTotal() {
		return intransitWeightTotal;
	}

	/**
	 * 设置 在途重量.
	 *
	 * @param intransitWeightTotal the new 在途重量
	 */
	public void setIntransitWeightTotal(BigDecimal intransitWeightTotal) {
		this.intransitWeightTotal = intransitWeightTotal;
	}

	/**
	 * 获取 在途体积.
	 *
	 * @return the 在途体积
	 */
	public BigDecimal getIntransitVolumeTotal() {
		return intransitVolumeTotal;
	}

	/**
	 * 设置 在途体积.
	 *
	 * @param intransitVolumeTotal the new 在途体积
	 */
	public void setIntransitVolumeTotal(BigDecimal intransitVolumeTotal) {
		this.intransitVolumeTotal = intransitVolumeTotal;
	}

	/**
	 * 获取 在途票数.
	 *
	 * @return the 在途票数
	 */
	public Integer getIntransitQtyTotal() {
		return intransitQtyTotal;
	}

	/**
	 * 设置 在途票数.
	 *
	 * @param intransitQtyTotal the new 在途票数
	 */
	public void setIntransitQtyTotal(Integer intransitQtyTotal) {
		this.intransitQtyTotal = intransitQtyTotal;
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
	 * @param deviationVolume the new 合车体积
	 */
	public void setDeviationVolume(BigDecimal deviationVolume) {
		this.deviationVolume = deviationVolume;
	}

	/**
	 * 获取 预测发起时间.
	 *
	 * @return the 预测发起时间
	 */
	@DateFormat
	public Date getStatisticsTime() {
		return statisticsTime;
	}

	/**
	 * 设置 预测发起时间.
	 *
	 * @param statisticsTime the new 预测发起时间
	 */
	@DateFormat
	public void setStatisticsTime(Date statisticsTime) {
		this.statisticsTime = statisticsTime;
	}

	/**
	 * 获取 预测类型 出发/到达.
	 *
	 * @return the 预测类型 出发/到达
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置 预测类型 出发/到达.
	 *
	 * @param type the new 预测类型 出发/到达
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取 预测的日期.
	 *
	 * @return the 预测的日期
	 */
	public Date getForecastTime() {
		return forecastTime;
	}

	/**
	 * 设置 预测的日期.
	 *
	 * @param forecastTime the new 预测的日期
	 */
	public void setForecastTime(Date forecastTime) {
		this.forecastTime = forecastTime;
	}

	/**
	 * 获取 预测发起日期.
	 *
	 * @return the 预测发起日期
	 */
	public Date getStatisticsDate() {
		return statisticsDate;
	}

	/**
	 * 设置 预测发起日期.
	 *
	 * @param statisticsDate the new 预测发起日期
	 */
	public void setStatisticsDate(Date statisticsDate) {
		this.statisticsDate = statisticsDate;
	}

	/**
	 * 获取 预测发起小时分钟.
	 *
	 * @return the 预测发起小时分钟
	 */
	public String getStatisticsHHMM() {
		return statisticsHHMM;
	}

	/**
	 * 设置 预测发起小时分钟.
	 *
	 * @param statisticsHHMM the new 预测发起小时分钟
	 */
	public void setStatisticsHHMM(String statisticsHHMM) {
		this.statisticsHHMM = statisticsHHMM;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getBelongOrgCodeName() {
		return belongOrgCodeName;
	}

	/**
	 * 
	 *
	 * @param belongOrgCodeName 
	 */
	public void setBelongOrgCodeName(String belongOrgCodeName) {
		this.belongOrgCodeName = belongOrgCodeName;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * 
	 *
	 * @param regionName 
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getRelevantOrgCodeName() {
		return relevantOrgCodeName;
	}

	/**
	 * 
	 *
	 * @param relevantOrgCodeName 
	 */
	public void setRelevantOrgCodeName(String relevantOrgCodeName) {
		this.relevantOrgCodeName = relevantOrgCodeName;
	}

	/**
	 * 版本号
	 *
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}