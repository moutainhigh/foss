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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/dto/DriveArchiveVehicleInfoDto.java
 *  
 *  FILE NAME          :DriveArchiveVehicleInfoDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用于储存根据配载车次号返回的信息
 * @author foss-liuxue(for IBM)
 * @date 2012-12-29 下午3:03:05
 */
public class DriveArchiveVehicleInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7371818718007121379L;

	/**
	 * 配载车次号
	 */
	private String vehicleAssembleNo;
	
	/**
	 * 车牌号
	 */
    private String vehicleNo;

    /**
	 * 货柜号
	 */
    private String containerNo;

    /**
	 * 线路
	 */
    private String line;

    /**
	 * 班次号
	 */
    private String frequencyNo;

    /**
	 * 体积
	 */
    private BigDecimal examineVolume;

    /**
	 * 载重
	 */
    private BigDecimal ratedLoad;

    /**
	 * 实际出发时间
	 */
    private Date actualDepartTime;

    /**
	 * 实际到达时间
	 */
    private Date actualArriveTime;
    
    /**
     * 实际时效
     */
    private BigDecimal preion;
    
    /**
     * 线路名称
     */
    private String lineName;

	/**
	 * 获取 线路名称.
	 *
	 * @return the 线路名称
	 */
	public String getLineName() {
		return lineName;
	}

	/**
	 * 设置 线路名称.
	 *
	 * @param lineName the new 线路名称
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	/**
	 * 获取 实际时效.
	 *
	 * @return the 实际时效
	 */
	public BigDecimal getPreion() {
		return preion;
	}

	/**
	 * 设置 实际时效.
	 *
	 * @param preion the new 实际时效
	 */
	public void setPreion(BigDecimal preion) {
		this.preion = preion;
	}

	/**
	 * 获取 配载车次号.
	 *
	 * @return the 配载车次号
	 */
	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}

	/**
	 * 设置 配载车次号.
	 *
	 * @param vehicleAssembleNo the new 配载车次号
	 */
	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}

	/**
	 * 获取 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 货柜号.
	 *
	 * @return the 货柜号
	 */
	public String getContainerNo() {
		return containerNo;
	}

	/**
	 * 设置 货柜号.
	 *
	 * @param containerNo the new 货柜号
	 */
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	/**
	 * 获取 线路.
	 *
	 * @return the 线路
	 */
	public String getLine() {
		return line;
	}

	/**
	 * 设置 线路.
	 *
	 * @param line the new 线路
	 */
	public void setLine(String line) {
		this.line = line;
	}

	/**
	 * 获取 班次号.
	 *
	 * @return the 班次号
	 */
	public String getFrequencyNo() {
		return frequencyNo;
	}

	/**
	 * 设置 班次号.
	 *
	 * @param frequencyNo the new 班次号
	 */
	public void setFrequencyNo(String frequencyNo) {
		this.frequencyNo = frequencyNo;
	}

	/**
	 * 获取 体积.
	 *
	 * @return the 体积
	 */
	public BigDecimal getExamineVolume() {
		return examineVolume;
	}

	/**
	 * 设置 体积.
	 *
	 * @param examineVolume the new 体积
	 */
	public void setExamineVolume(BigDecimal examineVolume) {
		this.examineVolume = examineVolume;
	}

	/**
	 * 获取 载重.
	 *
	 * @return the 载重
	 */
	public BigDecimal getRatedLoad() {
		return ratedLoad;
	}

	/**
	 * 设置 载重.
	 *
	 * @param ratedLoad the new 载重
	 */
	public void setRatedLoad(BigDecimal ratedLoad) {
		this.ratedLoad = ratedLoad;
	}

	/**
	 * 获取 实际出发时间.
	 *
	 * @return the 实际出发时间
	 */
	public Date getActualDepartTime() {
		return actualDepartTime;
	}

	/**
	 * 设置 实际出发时间.
	 *
	 * @param actualDepartTime the new 实际出发时间
	 */
	public void setActualDepartTime(Date actualDepartTime) {
		this.actualDepartTime = actualDepartTime;
	}

	/**
	 * 获取 实际到达时间.
	 *
	 * @return the 实际到达时间
	 */
	public Date getActualArriveTime() {
		return actualArriveTime;
	}

	/**
	 * 设置 实际到达时间.
	 *
	 * @param actualArriveTime the new 实际到达时间
	 */
	public void setActualArriveTime(Date actualArriveTime) {
		this.actualArriveTime = actualArriveTime;
	}

}