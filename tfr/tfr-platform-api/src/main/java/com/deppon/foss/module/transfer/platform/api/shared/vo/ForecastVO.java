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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/vo/ForecastVO.java
 * 
 *  FILE NAME     :ForecastVO.java
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
package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.BillingEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ChangeQuantityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForecastQuantityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.InTransitEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StatisticalInquiriesEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.SerialnoDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.ShowChartDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.StatisticalInquiriesDto;
/**
 * 货量预测VO
 */
public class ForecastVO implements java.io.Serializable{

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 2675931990243271502L;
	/**
	 * 出发部门code
	 */
	private String origOrgCode;
	/**
	 * 到达部门code
	 */
	private String destOrgCode;
	/**
	 * 发车计划类型
	 */
	private String departureType;
	/**
	 * 预测的时间LIST
	 */
	private List<Date> forecastDateList;
	/**
	 * 最新货量预测时间
	 */
	private Date maxStatisticsTime;
	/**
	 * 天数
	 */
	private int day;
	/**
	 * 预测周期开始时间
	 */
	private Date forecastStartTime;
	/**
	 * 预测周期截止时间
	 */
	private Date forecastEndTime;
	/**
	 * 周期开始时间
	 */
	private String startTime;
	/**
	 * 周期持续小时数
	 */
	private int durationHour;
	/**
	 * 执行预测间隔时间
	 */
	private int executeGapHour;
	
	/**
	 * 货量预测开单表entity
	 */
	private BillingEntity billingEntity;
	
	/**
	 * 修改货量预测货量entity
	 */
	private ChangeQuantityEntity changeQuantityEntity;
	
	/**
	 * 货量预测entity
	 */
	private ForecastQuantityEntity forecastQuantityEntity;
	
	/**
	 * 货量预测在途中表entity
	 */
	private InTransitEntity inTransitEntity;
	
	/**
	 * 货量预测开单表entity list
	 */
	private List<BillingEntity> billingList;
	
	/**
	 * 修改货量预测货量entity list
	 */
	private List<ChangeQuantityEntity> changeQuantityList;
	
	/**
	 * 货量预测entity list
	 */
	private List<ForecastQuantityEntity> forecastQuantityList;
	
	/**
	 * 货量预测在途中表entity list
	 */
	private List<InTransitEntity> inTransitList;
	
	/**
	 * 货量预测DTO list
	 */
	private List<ShowChartDto> showChartDto;
	/**
	 * 统计货量Entity list
	 */
	private List<StatisticalInquiriesEntity> statisticalInquiriesEntityList;
	/**
	 * 统计货量Dto
	 */
	private StatisticalInquiriesDto statisticalInquiriesDto;
	
	/**
	 * count 数 用于分页
	 */
	private String totalCount;
	
	/**
	 * 分页使用
	 */
	private int start;
	/**
	 * 分页使用
	 */
	private int limit;

	/**
	 * 计算总体积
	 */
	private String weightSum;

	/**
	 * 当前总重量
	 */
	private String currentWeightSum;
	
	/**
	 * 计算总重量
	 */
	private String volumeSum;
	
	/**
	 * 计算总体积
	 */
	private String currentVolumeSum;
	
	/**
	 * 流水号Dto
	 */
	private List<SerialnoDto> serialnos = new ArrayList<SerialnoDto>();
	
	
	/**
	 * 线路货量用于过滤线路用的当前部门所属外场
	 */
	private String parentTfrCtrCode;

	public String getParentTfrCtrCode() {
		return parentTfrCtrCode;
	}

	public void setParentTfrCtrCode(String parentTfrCtrCode) {
		this.parentTfrCtrCode = parentTfrCtrCode;
	}

	/**
	 * 获取 出发部门code.
	 *
	 * @return the 出发部门code
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * 设置 出发部门code.
	 *
	 * @param origOrgCode the new 出发部门code
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * 获取 到达部门code.
	 *
	 * @return the 到达部门code
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * 设置 到达部门code.
	 *
	 * @param destOrgCode the new 到达部门code
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * 获取 发车计划类型.
	 *
	 * @return the 发车计划类型
	 */
	public String getDepartureType() {
		return departureType;
	}

	/**
	 * 设置 发车计划类型.
	 *
	 * @param departureType the new 发车计划类型
	 */
	public void setDepartureType(String departureType) {
		this.departureType = departureType;
	}

	/**
	 * 获取 预测的时间LIST.
	 *
	 * @return the 预测的时间LIST
	 */
	public List<Date> getForecastDateList() {
		return forecastDateList;
	}

	/**
	 * 设置 预测的时间LIST.
	 *
	 * @param forecastDateList the new 预测的时间LIST
	 */
	public void setForecastDateList(List<Date> forecastDateList) {
		this.forecastDateList = forecastDateList;
	}

	/**
	 * 获取 天数.
	 *
	 * @return the 天数
	 */
	public int getDay() {
		return day;
	}

	/**
	 * 设置 天数.
	 *
	 * @param day the new 天数
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public Date getMaxStatisticsTime() {
		return maxStatisticsTime;
	}

	/**
	 * 
	 *
	 * @param maxStatisticsTime 
	 */
	public void setMaxStatisticsTime(Date maxStatisticsTime) {
		this.maxStatisticsTime = maxStatisticsTime;
	}

	/**
	 * 获取 周期开始时间.
	 *
	 * @return the 周期开始时间
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * 设置 周期开始时间.
	 *
	 * @param startTime the new 周期开始时间
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * 获取 周期持续小时数.
	 *
	 * @return the 周期持续小时数
	 */
	public int getDurationHour() {
		return durationHour;
	}

	/**
	 * 设置 周期持续小时数.
	 *
	 * @param durationHour the new 周期持续小时数
	 */
	public void setDurationHour(int durationHour) {
		this.durationHour = durationHour;
	}

	/**
	 * 获取 执行预测间隔时间.
	 *
	 * @return the 执行预测间隔时间
	 */
	public int getExecuteGapHour() {
		return executeGapHour;
	}

	/**
	 * 设置 执行预测间隔时间.
	 *
	 * @param executeGapHour the new 执行预测间隔时间
	 */
	public void setExecuteGapHour(int executeGapHour) {
		this.executeGapHour = executeGapHour;
	}

	/**
	 * 获取 货量预测开单表entity.
	 *
	 * @return the 货量预测开单表entity
	 */
	public BillingEntity getBillingEntity() {
		return billingEntity;
	}

	/**
	 * 设置 货量预测开单表entity.
	 *
	 * @param billingEntity the new 货量预测开单表entity
	 */
	public void setBillingEntity(BillingEntity billingEntity) {
		this.billingEntity = billingEntity;
	}

	/**
	 * 获取 修改货量预测货量entity.
	 *
	 * @return the 修改货量预测货量entity
	 */
	public ChangeQuantityEntity getChangeQuantityEntity() {
		return changeQuantityEntity;
	}

	/**
	 * 设置 修改货量预测货量entity.
	 *
	 * @param changeQuantityEntity the new 修改货量预测货量entity
	 */
	public void setChangeQuantityEntity(ChangeQuantityEntity changeQuantityEntity) {
		this.changeQuantityEntity = changeQuantityEntity;
	}

	/**
	 * 获取 货量预测entity.
	 *
	 * @return the 货量预测entity
	 */
	public ForecastQuantityEntity getForecastQuantityEntity() {
		return forecastQuantityEntity;
	}

	/**
	 * 设置 货量预测entity.
	 *
	 * @param forecastQuantityEntity the new 货量预测entity
	 */
	public void setForecastQuantityEntity(
			ForecastQuantityEntity forecastQuantityEntity) {
		this.forecastQuantityEntity = forecastQuantityEntity;
	}

	/**
	 * 获取 货量预测在途中表entity.
	 *
	 * @return the 货量预测在途中表entity
	 */
	public InTransitEntity getInTransitEntity() {
		return inTransitEntity;
	}

	/**
	 * 设置 货量预测在途中表entity.
	 *
	 * @param inTransitEntity the new 货量预测在途中表entity
	 */
	public void setInTransitEntity(InTransitEntity inTransitEntity) {
		this.inTransitEntity = inTransitEntity;
	}

	/**
	 * 获取 货量预测开单表entity list.
	 *
	 * @return the 货量预测开单表entity list
	 */
	public List<BillingEntity> getBillingList() {
		return billingList;
	}

	/**
	 * 设置 货量预测开单表entity list.
	 *
	 * @param billingList the new 货量预测开单表entity list
	 */
	public void setBillingList(List<BillingEntity> billingList) {
		this.billingList = billingList;
	}

	/**
	 * 获取 修改货量预测货量entity list.
	 *
	 * @return the 修改货量预测货量entity list
	 */
	public List<ChangeQuantityEntity> getChangeQuantityList() {
		return changeQuantityList;
	}

	/**
	 * 设置 修改货量预测货量entity list.
	 *
	 * @param changeQuantityList the new 修改货量预测货量entity list
	 */
	public void setChangeQuantityList(List<ChangeQuantityEntity> changeQuantityList) {
		this.changeQuantityList = changeQuantityList;
	}

	/**
	 * 获取 货量预测entity list.
	 *
	 * @return the 货量预测entity list
	 */
	public List<ForecastQuantityEntity> getForecastQuantityList() {
		return forecastQuantityList;
	}

	/**
	 * 设置 货量预测entity list.
	 *
	 * @param forecastQuantityList the new 货量预测entity list
	 */
	public void setForecastQuantityList(
			List<ForecastQuantityEntity> forecastQuantityList) {
		this.forecastQuantityList = forecastQuantityList;
	}
	
	/**
	 * 获取 货量预测在途中表entity list.
	 *
	 * @return the 货量预测在途中表entity list
	 */
	public List<InTransitEntity> getInTransitList() {
		return inTransitList;
	}

	/**
	 * 设置 货量预测在途中表entity list.
	 *
	 * @param inTransitList the new 货量预测在途中表entity list
	 */
	public void setInTransitList(List<InTransitEntity> inTransitList) {
		this.inTransitList = inTransitList;
	}

	/**
	 * 获取 预测周期开始时间.
	 *
	 * @return the 预测周期开始时间
	 */
	public Date getForecastStartTime() {
		return forecastStartTime;
	}

	/**
	 * 设置 预测周期开始时间.
	 *
	 * @param forecastStartTime the new 预测周期开始时间
	 */
	public void setForecastStartTime(Date forecastStartTime) {
		this.forecastStartTime = forecastStartTime;
	}

	/**
	 * 获取 预测周期截止时间.
	 *
	 * @return the 预测周期截止时间
	 */
	public Date getForecastEndTime() {
		return forecastEndTime;
	}

	/**
	 * 设置 预测周期截止时间.
	 *
	 * @param forecastEndTime the new 预测周期截止时间
	 */
	public void setForecastEndTime(Date forecastEndTime) {
		this.forecastEndTime = forecastEndTime;
	}

	/**
	 * 获取 货量预测DTO list.
	 *
	 * @return the 货量预测DTO list
	 */
	public List<ShowChartDto> getShowChartDto() {
		return showChartDto;
	}

	/**
	 * 设置 货量预测DTO list.
	 *
	 * @param showChartDto the new 货量预测DTO list
	 */
	public void setShowChartDto(List<ShowChartDto> showChartDto) {
		this.showChartDto = showChartDto;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the statisticalInquiriesEntityList
	 */
	public List<StatisticalInquiriesEntity> getStatisticalInquiriesEntityList() {
		return statisticalInquiriesEntityList;
	}

	/**
	 * @param statisticalInquiriesEntityList the statisticalInquiriesEntityList to set
	 */
	public void setStatisticalInquiriesEntityList(
			List<StatisticalInquiriesEntity> statisticalInquiriesEntityList) {
		this.statisticalInquiriesEntityList = statisticalInquiriesEntityList;
	}

	/**
	 * @return the statisticalInquiriesDto
	 */
	public StatisticalInquiriesDto getStatisticalInquiriesDto() {
		return statisticalInquiriesDto;
	}

	/**
	 * @param statisticalInquiriesDto the statisticalInquiriesDto to set
	 */
	public void setStatisticalInquiriesDto(
			StatisticalInquiriesDto statisticalInquiriesDto) {
		this.statisticalInquiriesDto = statisticalInquiriesDto;
	}

	/**
	 * @return the totalCount
	 */
	public String getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @return the weightSum
	 */
	public String getWeightSum() {
		return weightSum;
	}

	/**
	 * @param weightSum the weightSum to set
	 */
	public void setWeightSum(String weightSum) {
		this.weightSum = weightSum;
	}

	/**
	 * @return the volumeSum
	 */
	public String getVolumeSum() {
		return volumeSum;
	}

	/**
	 * @param volumeSum the volumeSum to set
	 */
	public void setVolumeSum(String volumeSum) {
		this.volumeSum = volumeSum;
	}

	public String getCurrentWeightSum() {
		return currentWeightSum;
	}

	public void setCurrentWeightSum(String currentWeightSum) {
		this.currentWeightSum = currentWeightSum;
	}

	public String getCurrentVolumeSum() {
		return currentVolumeSum;
	}

	public void setCurrentVolumeSum(String currentVolumeSum) {
		this.currentVolumeSum = currentVolumeSum;
	}

	public List<SerialnoDto> getSerialnos() {
		return serialnos;
	}

	public void setSerialnos(List<SerialnoDto> serialnos) {
		this.serialnos = serialnos;
	}
}