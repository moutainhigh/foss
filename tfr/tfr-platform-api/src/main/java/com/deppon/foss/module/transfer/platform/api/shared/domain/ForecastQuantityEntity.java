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
package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;

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
	private BigDecimal waybillQtyTotal;

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
	private BigDecimal gpsEnabledResQtyTotal;
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
	private BigDecimal precisionIfsQtyTotal;
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
	private BigDecimal inventoryQtyTotal;
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
	private BigDecimal billingQtyTotal;
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
	private BigDecimal intransitQtyTotal;
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
	 * 预测的类型
	 */
	private String departurearrival;

	/**数据类型**/
	private String dataType;
	
	/**预测未开单货物总重量**/
	private BigDecimal unbillingWeightTot;
	
	/**预测未开单货物总体积**/
	private BigDecimal unbillingVolumeTot;
	
	/**预测未开单货物总票数**/
	private Integer unbillingWaybillQtyTot;
	
	/**货物总重量(已开单+未开单\weithtTotal + unbillingWeightTot)**/
	private BigDecimal weightTot;

	/**货物总体积(已开单+未开单\volumeTotal + unbillingVolumeTot)**/
	private BigDecimal volumeTot;

	/**货物总体积(已开单+未开单\waybillQtyTot + unbillingWaybillQtyTot)**/
	private Integer waybillQtyTot;
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
	public BigDecimal getWaybillQtyTotal() {
		return waybillQtyTotal;
	}

	/**
	 * 设置 总票数.
	 *
	 * @param waybillQtyTotal the new 总票数
	 */
	public void setWaybillQtyTotal(BigDecimal waybillQtyTotal) {
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
	public BigDecimal getGpsEnabledResQtyTotal() {
		return gpsEnabledResQtyTotal;
	}

	/**
	 * 设置 卡航票数.
	 *
	 * @param gpsEnabledResQtyTotal the new 卡航票数
	 */
	public void setGpsEnabledResQtyTotal(BigDecimal gpsEnabledResQtyTotal) {
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
	public BigDecimal getPrecisionIfsQtyTotal() {
		return precisionIfsQtyTotal;
	}

	/**
	 * 设置 城运票数.
	 *
	 * @param precisionIfsQtyTotal the new 城运票数
	 */
	public void setPrecisionIfsQtyTotal(BigDecimal precisionIfsQtyTotal) {
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
	public BigDecimal getInventoryQtyTotal() {
		return inventoryQtyTotal;
	}

	/**
	 * 设置 在库票数.
	 *
	 * @param inventoryQtyTotal the new 在库票数
	 */
	public void setInventoryQtyTotal(BigDecimal inventoryQtyTotal) {
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
	public BigDecimal getBillingQtyTotal() {
		return billingQtyTotal;
	}

	/**
	 * 设置 开单票数.
	 *
	 * @param billingQtyTotal the new 开单票数
	 */
	public void setBillingQtyTotal(BigDecimal billingQtyTotal) {
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
	public BigDecimal getIntransitQtyTotal() {
		return intransitQtyTotal;
	}

	/**
	 * 设置 在途票数.
	 *
	 * @param intransitQtyTotal the new 在途票数
	 */
	public void setIntransitQtyTotal(BigDecimal intransitQtyTotal) {
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

	public String getDeparturearrival() {
		return departurearrival;
	}

	public void setDeparturearrival(String departurearrival) {
		this.departurearrival = departurearrival;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public BigDecimal getUnbillingWeightTot() {
		return unbillingWeightTot;
	}

	public void setUnbillingWeightTot(BigDecimal unbillingWeightTot) {
		this.unbillingWeightTot = unbillingWeightTot;
	}

	public BigDecimal getUnbillingVolumeTot() {
		return unbillingVolumeTot;
	}

	public void setUnbillingVolumeTot(BigDecimal unbillingVolumeTot) {
		this.unbillingVolumeTot = unbillingVolumeTot;
	}

	public Integer getUnbillingWaybillQtyTot() {
		return unbillingWaybillQtyTot;
	}

	public void setUnbillingWaybillQtyTot(Integer unbillingWaybillQtyTot) {
		this.unbillingWaybillQtyTot = unbillingWaybillQtyTot;
	}

	public BigDecimal getWeightTot() {
		return weightTot;
	}

	public void setWeightTot(BigDecimal weightTot) {
		this.weightTot = weightTot;
	}

	public BigDecimal getVolumeTot() {
		return volumeTot;
	}

	public void setVolumeTot(BigDecimal volumeTot) {
		this.volumeTot = volumeTot;
	}

	public Integer getWaybillQtyTot() {
		return waybillQtyTot;
	}

	public void setWaybillQtyTot(Integer waybillQtyTot) {
		this.waybillQtyTot = waybillQtyTot;
	}
	
	/**
	 * 货量相加
	 * @author 163580
	 * @date 2014-4-12 下午2:27:19
	 * @param augend 被加数
	 * @return
	 * @see
	 */
	public ForecastQuantityEntity add(ForecastQuantityEntity augend) {
		if(augend == null || this == null) {
			return newForecastQuantityEntity();
		}
		ForecastQuantityEntity forecastQuantity = newForecastQuantityEntity();
		forecastQuantity.setWeightTotal(this.weightTotal.add(augend.getWeightTotal()));
		forecastQuantity.setVolumeTotal(this.volumeTotal.add(augend.getVolumeTotal()));
		forecastQuantity.setWaybillQtyTotal(this.waybillQtyTotal.add(augend.getWaybillQtyTotal()));
		
		forecastQuantity.setGpsEnabledResWeightTotal(this.gpsEnabledResWeightTotal.add(augend.getGpsEnabledResWeightTotal()));
		forecastQuantity.setGpsEnabledResVolumeTotal(this.gpsEnabledResVolumeTotal.add(augend.getGpsEnabledResVolumeTotal()));
		forecastQuantity.setGpsEnabledResQtyTotal(this.gpsEnabledResQtyTotal.add(augend.getGpsEnabledResQtyTotal()));
		
		forecastQuantity.setPrecisionIfsWeightTotal(this.precisionIfsWeightTotal.add(augend.getPrecisionIfsWeightTotal()));
		forecastQuantity.setPrecisionIfsVolumeTotal(this.precisionIfsVolumeTotal.add(augend.getPrecisionIfsVolumeTotal()));
		forecastQuantity.setPrecisionIfsQtyTotal(this.precisionIfsQtyTotal.add(augend.getPrecisionIfsQtyTotal()));

		forecastQuantity.setInventoryWeightTotal(this.inventoryWeightTotal.add(augend.getInventoryWeightTotal()));
		forecastQuantity.setInventoryVolumeTotal(this.inventoryVolumeTotal.add(augend.getInventoryVolumeTotal()));
		forecastQuantity.setInventoryQtyTotal(this.inventoryQtyTotal.add(augend.getInventoryQtyTotal()));
		
		forecastQuantity.setBillingWeightTotal(this.billingWeightTotal.add(augend.getBillingWeightTotal()));
		forecastQuantity.setBillingVolumeTotal(this.billingVolumeTotal.add(augend.getBillingVolumeTotal()));
		forecastQuantity.setBillingQtyTotal(this.billingQtyTotal.add(augend.getBillingQtyTotal()));
		
		forecastQuantity.setIntransitWeightTotal(this.intransitWeightTotal.add(augend.getIntransitWeightTotal()));
		forecastQuantity.setIntransitVolumeTotal(this.intransitVolumeTotal.add(augend.getIntransitVolumeTotal()));
		forecastQuantity.setIntransitQtyTotal(this.intransitQtyTotal.add(augend.getIntransitQtyTotal()));

		forecastQuantity.setDeviationVolume(this.deviationVolume.add(augend.getDeviationVolume()));
		
		return forecastQuantity;
	}
	
	/**
	 * 货量相减
	 * 如果相减后的结果小于0则默认为0
	 * @author 163580
	 * @date 2014-4-12 下午2:27:19
	 * @param subtrahend 被减数
	 * @return
	 * @see
	 */
	public ForecastQuantityEntity subtract(ForecastQuantityEntity subtrahend) {
		if(subtrahend == null || this == null) {
			return newForecastQuantityEntity();
		}
		ForecastQuantityEntity forecastQuantity = newForecastQuantityEntity();
		
		BigDecimal value = this.weightTotal.subtract(subtrahend.getWeightTotal());
		forecastQuantity.setWeightTotal(value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
		
		value = this.volumeTotal.subtract(subtrahend.getVolumeTotal());
		forecastQuantity.setVolumeTotal(value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
		
		value = this.waybillQtyTotal.subtract(subtrahend.getWaybillQtyTotal());
		forecastQuantity.setWaybillQtyTotal(value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
		
		value = this.gpsEnabledResWeightTotal.subtract(subtrahend.getGpsEnabledResWeightTotal());
		forecastQuantity.setGpsEnabledResWeightTotal(value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
		
		value = this.gpsEnabledResVolumeTotal.subtract(subtrahend.getGpsEnabledResVolumeTotal());
		forecastQuantity.setGpsEnabledResVolumeTotal(value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
		
		value = this.gpsEnabledResQtyTotal.subtract(subtrahend.getGpsEnabledResQtyTotal());
		forecastQuantity.setGpsEnabledResQtyTotal(value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
		
		value = this.precisionIfsWeightTotal.subtract(subtrahend.getPrecisionIfsWeightTotal());
		forecastQuantity.setPrecisionIfsWeightTotal(value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
		
		value = this.precisionIfsVolumeTotal.subtract(subtrahend.getPrecisionIfsVolumeTotal());
		forecastQuantity.setPrecisionIfsVolumeTotal(value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
		
		value = this.precisionIfsQtyTotal.subtract(subtrahend.getPrecisionIfsQtyTotal());
		forecastQuantity.setPrecisionIfsQtyTotal(value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
		
		value = this.inventoryWeightTotal.subtract(subtrahend.getInventoryWeightTotal());
		forecastQuantity.setInventoryWeightTotal(value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
		
		value = this.inventoryVolumeTotal.subtract(subtrahend.getInventoryVolumeTotal());
		forecastQuantity.setInventoryVolumeTotal(value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
		
		value = this.inventoryQtyTotal.subtract(subtrahend.getInventoryQtyTotal());
		forecastQuantity.setInventoryQtyTotal(value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
		
		value = this.billingWeightTotal.subtract(subtrahend.getBillingWeightTotal());
		forecastQuantity.setBillingWeightTotal(value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
		
		value = this.billingVolumeTotal.subtract(subtrahend.getBillingVolumeTotal());
		forecastQuantity.setBillingVolumeTotal(value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
		
		value = this.billingQtyTotal.subtract(subtrahend.getBillingQtyTotal());
		forecastQuantity.setBillingQtyTotal(value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
		
		value = this.intransitWeightTotal.subtract(subtrahend.getIntransitWeightTotal());
		forecastQuantity.setIntransitWeightTotal(value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
		
		value = this.intransitVolumeTotal.subtract(subtrahend.getIntransitVolumeTotal());
		forecastQuantity.setIntransitVolumeTotal(value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
		
		value = this.intransitQtyTotal.subtract(subtrahend.getIntransitQtyTotal());
		forecastQuantity.setIntransitQtyTotal(value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
		
		value = this.deviationVolume.subtract(subtrahend.getDeviationVolume());
		forecastQuantity.setDeviationVolume(value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
		
		return forecastQuantity;
	}
	
	/**
	 * 货量相乘
	 * @author 163580
	 * @date 2014-4-12 上午11:23:02
	 * @param multiplicand 被乘数
	 * @return
	 * @see
	 */
	public ForecastQuantityEntity multiply(ForecastQuantityEntity multiplicand) {
		if(multiplicand == null || this == null) {
			return newForecastQuantityEntity();
		}
		ForecastQuantityEntity forecastQuantity = newForecastQuantityEntity();
		forecastQuantity.setWeightTotal(this.weightTotal.multiply(multiplicand.getWeightTotal()));
		forecastQuantity.setVolumeTotal(this.volumeTotal.multiply(multiplicand.getVolumeTotal()));
		forecastQuantity.setWaybillQtyTotal(this.waybillQtyTotal.multiply(multiplicand.getWaybillQtyTotal()));
		
		forecastQuantity.setGpsEnabledResWeightTotal(this.gpsEnabledResWeightTotal.multiply(multiplicand.getGpsEnabledResWeightTotal()));
		forecastQuantity.setGpsEnabledResVolumeTotal(this.gpsEnabledResVolumeTotal.multiply(multiplicand.getGpsEnabledResVolumeTotal()));
		forecastQuantity.setGpsEnabledResQtyTotal(this.gpsEnabledResQtyTotal.multiply(multiplicand.getGpsEnabledResQtyTotal()));
		
		forecastQuantity.setPrecisionIfsWeightTotal(this.precisionIfsWeightTotal.multiply(multiplicand.getPrecisionIfsWeightTotal()));
		forecastQuantity.setPrecisionIfsVolumeTotal(this.precisionIfsVolumeTotal.multiply(multiplicand.getPrecisionIfsVolumeTotal()));
		forecastQuantity.setPrecisionIfsQtyTotal(this.precisionIfsQtyTotal.multiply(multiplicand.getPrecisionIfsQtyTotal()));

		forecastQuantity.setInventoryWeightTotal(this.inventoryWeightTotal.multiply(multiplicand.getInventoryWeightTotal()));
		forecastQuantity.setInventoryVolumeTotal(this.inventoryVolumeTotal.multiply(multiplicand.getInventoryVolumeTotal()));
		forecastQuantity.setInventoryQtyTotal(this.inventoryQtyTotal.multiply(multiplicand.getInventoryQtyTotal()));
		
		forecastQuantity.setBillingWeightTotal(this.billingWeightTotal.multiply(multiplicand.getBillingWeightTotal()));
		forecastQuantity.setBillingVolumeTotal(this.billingVolumeTotal.multiply(multiplicand.getBillingVolumeTotal()));
		forecastQuantity.setBillingQtyTotal(this.billingQtyTotal.multiply(multiplicand.getBillingQtyTotal()));
		
		forecastQuantity.setIntransitWeightTotal(this.intransitWeightTotal.multiply(multiplicand.getIntransitWeightTotal()));
		forecastQuantity.setIntransitVolumeTotal(this.intransitVolumeTotal.multiply(multiplicand.getIntransitVolumeTotal()));
		forecastQuantity.setIntransitQtyTotal(this.intransitQtyTotal.multiply(multiplicand.getIntransitQtyTotal()));

		forecastQuantity.setDeviationVolume(this.deviationVolume.multiply(multiplicand.getDeviationVolume()));
		
		return forecastQuantity;
	}
	
	/**
	 * 货量相除
	 * 注意除数为0的情况
	 * @author 163580
	 * @date 2014-4-12 上午11:23:02
	 * @param divisor 被除数
	 * @return
	 * @see 
	 */
	public ForecastQuantityEntity divide(ForecastQuantityEntity divisor) {
		if(divisor == null || this == null) {
			return newForecastQuantityEntity();
		}
		ForecastQuantityEntity forecastQuantity = newForecastQuantityEntity();
		
		if(divisor.getWeightTotal().compareTo(BigDecimal.ZERO) == 0 ||
				weightTotal.compareTo(BigDecimal.ZERO) == 0) {
			//如果除数为0的话直接赋值结果为0
			forecastQuantity.setWeightTotal(BigDecimal.ZERO);
		} else {
			forecastQuantity.setWeightTotal(this.weightTotal.divide(divisor.getWeightTotal(), PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP));
		}
		if(divisor.getVolumeTotal().compareTo(BigDecimal.ZERO) == 0 ||
				volumeTotal.compareTo(BigDecimal.ZERO) == 0) {
			//如果除数为0的话直接赋值结果为0
			forecastQuantity.setVolumeTotal(BigDecimal.ZERO);
		} else {
			forecastQuantity.setVolumeTotal(this.volumeTotal.divide(divisor.getVolumeTotal(), PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP));
		}
		if(divisor.getWaybillQtyTotal().compareTo(BigDecimal.ZERO) == 0 ||
				waybillQtyTotal.compareTo(BigDecimal.ZERO) == 0) {
			//如果除数为0的话直接赋值结果为0
			forecastQuantity.setWaybillQtyTotal(BigDecimal.ZERO);
		} else {
			forecastQuantity.setWaybillQtyTotal(this.waybillQtyTotal.divide(divisor.getWaybillQtyTotal(), PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP));
		}
		
		if(divisor.getGpsEnabledResWeightTotal().compareTo(BigDecimal.ZERO) == 0 ||
				gpsEnabledResWeightTotal.compareTo(BigDecimal.ZERO) == 0) {
			//如果除数为0的话直接赋值结果为0
			forecastQuantity.setGpsEnabledResWeightTotal(BigDecimal.ZERO);
		} else {
			forecastQuantity.setGpsEnabledResWeightTotal(this.gpsEnabledResWeightTotal.divide(divisor.getGpsEnabledResWeightTotal(), PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP));
		}
		if(divisor.getGpsEnabledResVolumeTotal().compareTo(BigDecimal.ZERO) == 0 ||
				gpsEnabledResVolumeTotal.compareTo(BigDecimal.ZERO) == 0) {
			//如果除数为0的话直接赋值结果为0
			forecastQuantity.setGpsEnabledResVolumeTotal(BigDecimal.ZERO);
		} else {
			forecastQuantity.setGpsEnabledResVolumeTotal(this.gpsEnabledResVolumeTotal.divide(divisor.getGpsEnabledResVolumeTotal(), PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP));
		}
		if(divisor.getGpsEnabledResQtyTotal().compareTo(BigDecimal.ZERO) == 0 ||
				gpsEnabledResQtyTotal.compareTo(BigDecimal.ZERO) == 0) {
			//如果除数为0的话直接赋值结果为0
			forecastQuantity.setGpsEnabledResQtyTotal(BigDecimal.ZERO);
		} else {
			forecastQuantity.setGpsEnabledResQtyTotal(this.gpsEnabledResQtyTotal.divide(divisor.getGpsEnabledResQtyTotal(), PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP));
		}
		
		if(divisor.getPrecisionIfsWeightTotal().compareTo(BigDecimal.ZERO) == 0 ||
				precisionIfsWeightTotal.compareTo(BigDecimal.ZERO) == 0) {
			//如果除数为0的话直接赋值结果为0
			forecastQuantity.setPrecisionIfsWeightTotal(BigDecimal.ZERO);
		} else {
			forecastQuantity.setPrecisionIfsWeightTotal(this.precisionIfsWeightTotal.divide(divisor.getPrecisionIfsWeightTotal(), PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP));
		}
		if(divisor.getPrecisionIfsVolumeTotal().compareTo(BigDecimal.ZERO) == 0 ||
				precisionIfsVolumeTotal.compareTo(BigDecimal.ZERO) == 0) {
			//如果除数为0的话直接赋值结果为0
			forecastQuantity.setPrecisionIfsVolumeTotal(BigDecimal.ZERO);
		} else {
			forecastQuantity.setPrecisionIfsVolumeTotal(this.precisionIfsVolumeTotal.divide(divisor.getPrecisionIfsVolumeTotal(), PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP));
		}
		if(divisor.getPrecisionIfsQtyTotal().compareTo(BigDecimal.ZERO) == 0 ||
				precisionIfsQtyTotal.compareTo(BigDecimal.ZERO) == 0) {
			//如果除数为0的话直接赋值结果为0
			forecastQuantity.setPrecisionIfsQtyTotal(BigDecimal.ZERO);
		} else {
			forecastQuantity.setPrecisionIfsQtyTotal(this.precisionIfsQtyTotal.divide(divisor.getPrecisionIfsQtyTotal(), PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP));
		}
		
		if(divisor.getInventoryWeightTotal().compareTo(BigDecimal.ZERO) == 0 ||
				inventoryWeightTotal.compareTo(BigDecimal.ZERO) == 0) {
			//如果除数为0的话直接赋值结果为0
			forecastQuantity.setInventoryWeightTotal(BigDecimal.ZERO);
		} else {
			forecastQuantity.setInventoryWeightTotal(this.inventoryWeightTotal.divide(divisor.getInventoryWeightTotal(), PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP));
		}
		if(divisor.getInventoryVolumeTotal().compareTo(BigDecimal.ZERO) == 0 ||
				inventoryVolumeTotal.compareTo(BigDecimal.ZERO) == 0) {
			//如果除数为0的话直接赋值结果为0
			forecastQuantity.setInventoryVolumeTotal(BigDecimal.ZERO);
		} else {
			forecastQuantity.setInventoryVolumeTotal(this.inventoryVolumeTotal.divide(divisor.getInventoryVolumeTotal(), PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP));
		}
		if(divisor.getInventoryQtyTotal().compareTo(BigDecimal.ZERO) == 0 ||
				inventoryQtyTotal.compareTo(BigDecimal.ZERO) == 0) {
			//如果除数为0的话直接赋值结果为0
			forecastQuantity.setInventoryQtyTotal(BigDecimal.ZERO);
		} else {
			forecastQuantity.setInventoryQtyTotal(this.inventoryQtyTotal.divide(divisor.getInventoryQtyTotal(), PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP));
		}
		
		if(divisor.getBillingWeightTotal().compareTo(BigDecimal.ZERO) == 0 ||
				billingWeightTotal.compareTo(BigDecimal.ZERO) == 0) {
			//如果除数为0的话直接赋值结果为0
			forecastQuantity.setBillingWeightTotal(BigDecimal.ZERO);
		} else {
			forecastQuantity.setBillingWeightTotal(this.billingWeightTotal.divide(divisor.getBillingWeightTotal(), PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP));
		}
		if(divisor.getBillingVolumeTotal().compareTo(BigDecimal.ZERO) == 0 ||
				billingVolumeTotal.compareTo(BigDecimal.ZERO) == 0) {
			//如果除数为0的话直接赋值结果为0
			forecastQuantity.setBillingVolumeTotal(BigDecimal.ZERO);
		} else {
			forecastQuantity.setBillingVolumeTotal(this.billingVolumeTotal.divide(divisor.getBillingVolumeTotal(), PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP));
		}
		if(divisor.getBillingQtyTotal().compareTo(BigDecimal.ZERO) == 0 ||
				billingQtyTotal.compareTo(BigDecimal.ZERO) == 0) {
			//如果除数为0的话直接赋值结果为0
			forecastQuantity.setBillingQtyTotal(BigDecimal.ZERO);
		} else {
			forecastQuantity.setBillingQtyTotal(this.billingQtyTotal.divide(divisor.getBillingQtyTotal(), PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP));
		}
		
		if(divisor.getIntransitWeightTotal().compareTo(BigDecimal.ZERO) == 0 ||
				intransitWeightTotal.compareTo(BigDecimal.ZERO) == 0) {
			//如果除数为0的话直接赋值结果为0
			forecastQuantity.setIntransitWeightTotal(BigDecimal.ZERO);
		} else {
			forecastQuantity.setIntransitWeightTotal(this.intransitWeightTotal.divide(divisor.getIntransitWeightTotal(), PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP));
		}
		if(divisor.getIntransitVolumeTotal().compareTo(BigDecimal.ZERO) == 0 ||
				intransitVolumeTotal.compareTo(BigDecimal.ZERO) == 0) {
			//如果除数为0的话直接赋值结果为0
			forecastQuantity.setIntransitVolumeTotal(BigDecimal.ZERO);
		} else {
			forecastQuantity.setIntransitVolumeTotal(this.intransitVolumeTotal.divide(divisor.getIntransitVolumeTotal(), PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP));
		}
		if(divisor.getIntransitQtyTotal().compareTo(BigDecimal.ZERO) == 0 ||
				intransitQtyTotal.compareTo(BigDecimal.ZERO) == 0) {
			//如果除数为0的话直接赋值结果为0
			forecastQuantity.setIntransitQtyTotal(BigDecimal.ZERO);
		} else {
			forecastQuantity.setIntransitQtyTotal(this.intransitQtyTotal.divide(divisor.getIntransitQtyTotal(), PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP));
		}
		
		if(divisor.getDeviationVolume().compareTo(BigDecimal.ZERO) == 0 ||
				deviationVolume.compareTo(BigDecimal.ZERO) == 0) {
			//如果除数为0的话直接赋值结果为0
			forecastQuantity.setDeviationVolume(BigDecimal.ZERO);
		} else {
			forecastQuantity.setDeviationVolume(this.deviationVolume.divide(divisor.getDeviationVolume(), PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP));
		}
		
		return forecastQuantity;
	}
	
	/**
	 * 返回 一个新的对象货量等值..默认为0
	 * @author 163580
	 * @date 2014-4-12 上午11:25:57
	 * @return
	 * @see
	 */
	public static ForecastQuantityEntity newForecastQuantityEntity() {
		ForecastQuantityEntity forecastQuantity = new ForecastQuantityEntity();
		forecastQuantity.setWeightTotal(BigDecimal.ZERO);
		forecastQuantity.setVolumeTotal(BigDecimal.ZERO);
		forecastQuantity.setWaybillQtyTotal(BigDecimal.ZERO);
		forecastQuantity.setGpsEnabledResWeightTotal(BigDecimal.ZERO);
		forecastQuantity.setGpsEnabledResVolumeTotal(BigDecimal.ZERO);
		forecastQuantity.setGpsEnabledResQtyTotal(BigDecimal.ZERO);
		forecastQuantity.setPrecisionIfsWeightTotal(BigDecimal.ZERO);
		forecastQuantity.setPrecisionIfsVolumeTotal(BigDecimal.ZERO);
		forecastQuantity.setPrecisionIfsQtyTotal(BigDecimal.ZERO);
		forecastQuantity.setInventoryWeightTotal(BigDecimal.ZERO);
		forecastQuantity.setInventoryVolumeTotal(BigDecimal.ZERO);
		forecastQuantity.setInventoryQtyTotal(BigDecimal.ZERO);
		forecastQuantity.setBillingWeightTotal(BigDecimal.ZERO);
		forecastQuantity.setBillingVolumeTotal(BigDecimal.ZERO);
		forecastQuantity.setBillingQtyTotal(BigDecimal.ZERO);
		forecastQuantity.setIntransitWeightTotal(BigDecimal.ZERO);
		forecastQuantity.setIntransitVolumeTotal(BigDecimal.ZERO);
		forecastQuantity.setIntransitQtyTotal(BigDecimal.ZERO);
		forecastQuantity.setDeviationVolume(BigDecimal.ZERO);
		return forecastQuantity;
	}
}