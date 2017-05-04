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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/ShowChartDto.java
 * 
 *  FILE NAME     :ShowChartDto.java
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
package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *  货量预测DTO
 * @author huyue
 * @date 2012-10-15 下午12:50:34
 */
public class ShowChartDto implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = -2470702017293793261L;
	/**
	 * 总重量
	 */
	private BigDecimal totalWeight;
	/**
	 * 总体积
	 */
	private BigDecimal totalVolume;
	/**
	 * 警戒重量
	 */
	private BigDecimal warnWeight;
	/**
	 * 警戒体积
	 */
	private BigDecimal warnVolume;
	/**
	 * 预测发起日期
	 */
    private Date statisticsDate;
    /**
     * 预测发起小时分钟
     */
    private String statisticsHHMM;
    /**
     * 实际重量
     */
	private BigDecimal realWeight;
	/**
	 * 实际体积
	 */
	private BigDecimal realVolume;

	/**
	 * 获取 总重量.
	 *
	 * @return the 总重量
	 */
	public BigDecimal getTotalWeight() {
		return totalWeight;
	}

	/**
	 * 设置 总重量.
	 *
	 * @param totalWeight the new 总重量
	 */
	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}

	/**
	 * 获取 总体积.
	 *
	 * @return the 总体积
	 */
	public BigDecimal getTotalVolume() {
		return totalVolume;
	}

	/**
	 * 设置 总体积.
	 *
	 * @param totalVolume the new 总体积
	 */
	public void setTotalVolume(BigDecimal totalVolume) {
		this.totalVolume = totalVolume;
	}

	/**
	 * 获取 警戒重量.
	 *
	 * @return the 警戒重量
	 */
	public BigDecimal getWarnWeight() {
		return warnWeight;
	}

	/**
	 * 设置 警戒重量.
	 *
	 * @param warnWeight the new 警戒重量
	 */
	public void setWarnWeight(BigDecimal warnWeight) {
		this.warnWeight = warnWeight;
	}

	/**
	 * 获取 警戒体积.
	 *
	 * @return the 警戒体积
	 */
	public BigDecimal getWarnVolume() {
		return warnVolume;
	}

	/**
	 * 设置 警戒体积.
	 *
	 * @param warnVolume the new 警戒体积
	 */
	public void setWarnVolume(BigDecimal warnVolume) {
		this.warnVolume = warnVolume;
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
	 * 获取 实际重量.
	 *
	 * @return the 实际重量
	 */
	public BigDecimal getRealWeight() {
		return realWeight;
	}

	/**
	 * 设置 实际重量.
	 *
	 * @param realWeight the new 实际重量
	 */
	public void setRealWeight(BigDecimal realWeight) {
		this.realWeight = realWeight;
	}

	/**
	 * 获取 实际体积.
	 *
	 * @return the 实际体积
	 */
	public BigDecimal getRealVolume() {
		return realVolume;
	}

	/**
	 * 设置 实际体积.
	 *
	 * @param realVolume the new 实际体积
	 */
	public void setRealVolume(BigDecimal realVolume) {
		this.realVolume = realVolume;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}