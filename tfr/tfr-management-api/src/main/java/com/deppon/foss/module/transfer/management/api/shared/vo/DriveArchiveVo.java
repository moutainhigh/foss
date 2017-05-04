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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/vo/DriveArchiveVo.java
 *  
 *  FILE NAME          :DriveArchiveVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.management.api.shared.domain.DriveArchiveEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.DriveArchiveDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.DriveArchiveLineInfoDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.DriveArchiveVehicleInfoDto;

/**
 * 行驶档案VO
 * @author foss-liuxue(for IBM)
 * @date 2012-12-29 下午4:35:35
 */
public class DriveArchiveVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4792380274306504825L;
	
	/**
	 * 行驶档案列表
	 */
	private List<DriveArchiveEntity> driveArchiveList;
	
	/**
	 * 行驶档案实体
	 */
	private DriveArchiveEntity driveArchiveEntity;
	
	/** 
	 * 车辆信息实体
	 */
	private DriveArchiveVehicleInfoDto archiveVehicleInfoDto;
	
	/**
	 * 行驶档案dto
	 */
	private DriveArchiveDto driveArchiveDto;
	
	/**
	 * 线路信息dto
	 */
	private DriveArchiveLineInfoDto driveArchiveLineInfoDto;
	
	/**
	 * 行驶档案id
	 */
	private String id;

	/**
	 * 获取 线路信息dto.
	 *
	 * @return the 线路信息dto
	 */
	public DriveArchiveLineInfoDto getDriveArchiveLineInfoDto() {
		return driveArchiveLineInfoDto;
	}

	/**
	 * 设置 线路信息dto.
	 *
	 * @param driveArchiveLineInfoDto the new 线路信息dto
	 */
	public void setDriveArchiveLineInfoDto(
			DriveArchiveLineInfoDto driveArchiveLineInfoDto) {
		this.driveArchiveLineInfoDto = driveArchiveLineInfoDto;
	}

	/**
	 * 获取 车辆信息实体.
	 *
	 * @return the 车辆信息实体
	 */
	public DriveArchiveVehicleInfoDto getArchiveVehicleInfoDto() {
		return archiveVehicleInfoDto;
	}

	/**
	 * 设置 车辆信息实体.
	 *
	 * @param archiveVehicleInfoDto the new 车辆信息实体
	 */
	public void setArchiveVehicleInfoDto(
			DriveArchiveVehicleInfoDto archiveVehicleInfoDto) {
		this.archiveVehicleInfoDto = archiveVehicleInfoDto;
	}

	/**
	 * 获取 行驶档案id.
	 *
	 * @return the 行驶档案id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置 行驶档案id.
	 *
	 * @param id the new 行驶档案id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 行驶档案列表.
	 *
	 * @return the 行驶档案列表
	 */
	public List<DriveArchiveEntity> getDriveArchiveList() {
		return driveArchiveList;
	}

	/**
	 * 设置 行驶档案列表.
	 *
	 * @param driveArchiveList the new 行驶档案列表
	 */
	public void setDriveArchiveList(List<DriveArchiveEntity> driveArchiveList) {
		this.driveArchiveList = driveArchiveList;
	}

	/**
	 * 获取 行驶档案实体.
	 *
	 * @return the 行驶档案实体
	 */
	public DriveArchiveEntity getDriveArchiveEntity() {
		return driveArchiveEntity;
	}

	/**
	 * 设置 行驶档案实体.
	 *
	 * @param driveArchiveEntity the new 行驶档案实体
	 */
	public void setDriveArchiveEntity(DriveArchiveEntity driveArchiveEntity) {
		this.driveArchiveEntity = driveArchiveEntity;
	}

	/**
	 * 获取 行驶档案dto.
	 *
	 * @return the 行驶档案dto
	 */
	public DriveArchiveDto getDriveArchiveDto() {
		return driveArchiveDto;
	}

	/**
	 * 设置 行驶档案dto.
	 *
	 * @param driveArchiveDto the new 行驶档案dto
	 */
	public void setDriveArchiveDto(DriveArchiveDto driveArchiveDto) {
		this.driveArchiveDto = driveArchiveDto;
	}
	
}