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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/vo/PlatformDispatchVo.java
 * 
 *  FILE NAME     :PlatformDispatchVo.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.vo
 * FILE    NAME: PlatformDispatchVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryProgressResultDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformDistributeDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TransferDeptInfo;

/**
 * 月台view Object
 * @author 104306-foss-wangLong
 * @date 2012-11-8 上午8:12:30
 */
public class PlatformDispatchVo implements Serializable {

	private static final long serialVersionUID = 40768665211708910L;
	
	private PlatformDistributeDto platformDistributeDto;
	
	private List<PlatformDistributeDto> platformDistributeDtoList;
	
	/** 月台使用实体 */
	private PlatformDistributeEntity platformDistributeEntity;
	
	/** 装卸车进度  */
	private QueryProgressResultDto queryProgressResultDto; 
	
	/** 月台详情 */
	private PlatformEntity platformEntity;
	
	/** 月台使用时间  是否大于等于当前时间  */
	private boolean userTimeIfGreaterThanOrEqualCurrentTime;
	/** 部门所属外场信息 */
	private TransferDeptInfo transferDeptInfo;
	
	/**
	 * 部门编号
	 */
	private String deptCode;
	/**
	 * 月台号
	 */
	private String platFormNo;
	/**
	 * 获得platformDistributeDto
	 * @return the platformDistributeDto
	 */
	public PlatformDistributeDto getPlatformDistributeDto() {
		return platformDistributeDto;
	}
	
	/**
	 * 获得platformDistributeDtoList
	 * @return the platformDistributeDtoList
	 */
	public List<PlatformDistributeDto> getPlatformDistributeDtoList() {
		return platformDistributeDtoList;
	}
	
	public TransferDeptInfo getTransferDeptInfo() {
		return transferDeptInfo;
	}

	public void setTransferDeptInfo(TransferDeptInfo transferDeptInfo) {
		this.transferDeptInfo = transferDeptInfo;
	}

	/**
	 * 获得platformDistributeEntity
	 * @return the platformDistributeEntity
	 */
	public PlatformDistributeEntity getPlatformDistributeEntity() {
		return platformDistributeEntity;
	}
	
	/**
	 * 获得platformEntity
	 * @return the platformEntity
	 */
	public PlatformEntity getPlatformEntity() {
		return platformEntity;
	}
	
	/**
	 * 获得userTimeIfGreaterThanOrEqualCurrentTime
	 * @return the userTimeIfGreaterThanOrEqualCurrentTime
	 */
	public boolean isUserTimeIfGreaterThanOrEqualCurrentTime() {
		return userTimeIfGreaterThanOrEqualCurrentTime;
	}

	/**
	 * 设置platformDistributeDto
	 * @param platformDistributeDto the platformDistributeDto to set
	 */
	public void setPlatformDistributeDto(PlatformDistributeDto platformDistributeDto) {
		this.platformDistributeDto = platformDistributeDto;
	}
	
	/**
	 * 设置platformDistributeDtoList
	 * @param platformDistributeDtoList the platformDistributeDtoList to set
	 */
	public void setPlatformDistributeDtoList(
			List<PlatformDistributeDto> platformDistributeDtoList) {
		this.platformDistributeDtoList = platformDistributeDtoList;
	}
	
	/**
	 * 设置platformDistributeEntity
	 * @param platformDistributeEntity the platformDistributeEntity to set
	 */
	public void setPlatformDistributeEntity(
			PlatformDistributeEntity platformDistributeEntity) {
		this.platformDistributeEntity = platformDistributeEntity;
	}
	
	/**
	 * 设置platformEntity
	 * @param platformEntity the platformEntity to set
	 */
	public void setPlatformEntity(PlatformEntity platformEntity) {
		this.platformEntity = platformEntity;
	}

	/**
	 * 设置userTimeIfGreaterThanOrEqualCurrentTime
	 * @param userTimeIfGreaterThanOrEqualCurrentTime the userTimeIfGreaterThanOrEqualCurrentTime to set
	 */
	public void setUserTimeIfGreaterThanOrEqualCurrentTime(
			boolean userTimeIfGreaterThanOrEqualCurrentTime) {
		this.userTimeIfGreaterThanOrEqualCurrentTime = userTimeIfGreaterThanOrEqualCurrentTime;
	}

	/**
	 * 设置queryProgressResultDto
	 * @param queryProgressResultDto the queryProgressResultDto to set
	 */
	public void setQueryProgressResultDto(
			QueryProgressResultDto queryProgressResultDto) {
		this.queryProgressResultDto = queryProgressResultDto;
	}

	/**
	 * 获得queryProgressResultDto
	 * @return the queryProgressResultDto
	 */
	public QueryProgressResultDto getQueryProgressResultDto() {
		return queryProgressResultDto;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getPlatFormNo() {
		return platFormNo;
	}

	public void setPlatFormNo(String platFormNo) {
		this.platFormNo = platFormNo;
	}
	
	
}