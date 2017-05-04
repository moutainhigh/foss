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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/dto/CertificatebagDto.java
 *  
 *  FILE NAME          :CertificatebagDto.java
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

import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagEntity;

/**
 * 证件包Dto
 * 
 * @author 099197-foss-liming
 * @date 2012-11-02 下午4:57:47
 */
public class CertificatebagDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1236964962219324203L;

	/**
	 * 证件包entity
	 */
	private CertificatebagEntity certificatebagEntity;

	/**
	 *  开始 领取时间
	 */
	private String beginActualTakeTime;

	/**
	 * 结束领取时间
	 */
	private String endActualTakeTime; 

	/**
	 * 开始 归还时间
	 */
	private String beginActualReturnTime; 

	/**
	 * 结束 归还时间
	 */
	private String endActualReturnTime; 
	
	/**
	 * 车牌号
	 */
	private String vehicleNo;  

	/**
	 * 货柜号码
	 */
	private String containerNo;

	/**
	 * 获取 证件包entity.
	 *
	 * @return the 证件包entity
	 */
	public CertificatebagEntity getCertificatebagEntity() {
		return certificatebagEntity;
	}

	/**
	 * 设置 证件包entity.
	 *
	 * @param certificatebagEntity the new 证件包entity
	 */
	public void setCertificatebagEntity(CertificatebagEntity certificatebagEntity) {
		this.certificatebagEntity = certificatebagEntity;
	}

	/**
	 * 获取 开始 领取时间.
	 *
	 * @return the 开始 领取时间
	 */
	public String getBeginActualTakeTime() {
		return beginActualTakeTime;
	}

	/**
	 * 设置 开始 领取时间.
	 *
	 * @param beginActualTakeTime the new 开始 领取时间
	 */
	public void setBeginActualTakeTime(String beginActualTakeTime) {
		this.beginActualTakeTime = beginActualTakeTime;
	}

	/**
	 * 获取 结束领取时间.
	 *
	 * @return the 结束领取时间
	 */
	public String getEndActualTakeTime() {
		return endActualTakeTime;
	}

	/**
	 * 设置 结束领取时间.
	 *
	 * @param endActualTakeTime the new 结束领取时间
	 */
	public void setEndActualTakeTime(String endActualTakeTime) {
		this.endActualTakeTime = endActualTakeTime;
	}

	/**
	 * 获取 开始 归还时间.
	 *
	 * @return the 开始 归还时间
	 */
	public String getBeginActualReturnTime() {
		return beginActualReturnTime;
	}

	/**
	 * 设置 开始 归还时间.
	 *
	 * @param beginActualReturnTime the new 开始 归还时间
	 */
	public void setBeginActualReturnTime(String beginActualReturnTime) {
		this.beginActualReturnTime = beginActualReturnTime;
	}

	/**
	 * 获取 结束 归还时间.
	 *
	 * @return the 结束 归还时间
	 */
	public String getEndActualReturnTime() {
		return endActualReturnTime;
	}

	/**
	 * 设置 结束 归还时间.
	 *
	 * @param endActualReturnTime the new 结束 归还时间
	 */
	public void setEndActualReturnTime(String endActualReturnTime) {
		this.endActualReturnTime = endActualReturnTime;
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
	 * 获取 货柜号码.
	 *
	 * @return the 货柜号码
	 */
	public String getContainerNo() {
		return containerNo;
	}

	/**
	 * 设置 货柜号码.
	 *
	 * @param containerNo the new 货柜号码
	 */
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}  
	
	
		
	
}