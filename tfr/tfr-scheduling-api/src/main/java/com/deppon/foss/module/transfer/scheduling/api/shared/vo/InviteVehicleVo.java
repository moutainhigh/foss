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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/vo/InviteVehicleVo.java
 * 
 *  FILE NAME     :InviteVehicleVo.java
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
 * FILE    NAME: InviteVehicleVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OrgEntity;

/**
 * 外请约车
 * @author 104306-foss-wangLong
 * @date 2012-12-15 上午11:08:37
 */
public class InviteVehicleVo implements Serializable {
	
	private static final long serialVersionUID = 27775203920189384L;
	
	/** 外请约车DTO */
	private InviteVehicleDto inviteVehicleDto;
	
	/** 外请约车 DTO List */
	private List<InviteVehicleDto> inviteVehicleList;
	
	/** 用车地址  */
	private String useVehicleAddress;
	
	/**  外请约车编号list  */
	private List<String> inviteNoList;
	
	/** 外请车合同线路  */
	private LineEntity lineEntity;
	
	private String belongTransforCenter;
	
	// 最大重量综合模块配置参数："MAX_WEIGHT_VEHICLE";
	private String maxWeightVehicle;
	// 提示重量综合模块配置参数："MIN_WEIGHT_VEHICLE";
	private String minWeightVehicle;
	// 最大体积综合模块配置参数："MAX_VOLUME_VEHICLE";
	private String maxVolumeVehicle;
	// 提示体积综合模块配置参数："MIN_VOLUME_VEHICLE";
	private String minVolumeVehicle;
	
	//310248查询费用承担部门
	private List<OrgEntity> comboList;
	
	public List<OrgEntity> getComboList() {
		return comboList;
	}

	public void setComboList(List<OrgEntity> comboList) {
		this.comboList = comboList;
	}
	/**
	 * 获得inviteVehicleDto
	 * @return the inviteVehicleDto
	 */
	public InviteVehicleDto getInviteVehicleDto() {
		return inviteVehicleDto;
	}

	/**
	 * 设置inviteVehicleDto
	 * @param inviteVehicleDto the inviteVehicleDto to set
	 */
	public void setInviteVehicleDto(InviteVehicleDto inviteVehicleDto) {
		this.inviteVehicleDto = inviteVehicleDto;
	}	
	
	/**
	 * 获得inviteVehicleList
	 * @return the inviteVehicleList
	 */
	public List<InviteVehicleDto> getInviteVehicleList() {
		return inviteVehicleList;
	}

	/**
	 * 设置inviteVehicleList
	 * @param inviteVehicleList the inviteVehicleList to set
	 */
	public void setInviteVehicleList(List<InviteVehicleDto> inviteVehicleList) {
		this.inviteVehicleList = inviteVehicleList;
	}

	/**
	 * 获得useVehicleAddress
	 * @return the useVehicleAddress
	 */
	public String getUseVehicleAddress() {
		return useVehicleAddress;
	}

	/**
	 * 设置useVehicleAddress
	 * @param useVehicleAddress the useVehicleAddress to set
	 */
	public void setUseVehicleAddress(String useVehicleAddress) {
		this.useVehicleAddress = useVehicleAddress;
	}

	/**
	 * 设置inviteNoList
	 * @param inviteNoList the inviteNoList to set
	 */
	public List<String> getInviteNoList() {
		return inviteNoList;
	}

	/**
	 * 设置inviteNoList
	 * @param inviteNoList the inviteNoList to set
	 */
	public void setInviteNoList(List<String> inviteNoList) {
		this.inviteNoList = inviteNoList;
	}

	/**
	 * 获得lineEntity
	 * @return the lineEntity
	 */
	public LineEntity getLineEntity() {
		return lineEntity;
	}

	/**
	 * 设置lineEntity
	 * @param lineEntity the lineEntity to set
	 */
	public void setLineEntity(LineEntity lineEntity) {
		this.lineEntity = lineEntity;
	}

	public String getBelongTransforCenter() {
		return belongTransforCenter;
	}

	public void setBelongTransforCenter(String belongTransforCenter) {
		this.belongTransforCenter = belongTransforCenter;
	}

	/**
	 * @return the maxWeightVehicle
	 */
	public String getMaxWeightVehicle() {
		return maxWeightVehicle;
	}

	/**
	 * @param maxWeightVehicle the maxWeightVehicle to set
	 */
	public void setMaxWeightVehicle(String maxWeightVehicle) {
		this.maxWeightVehicle = maxWeightVehicle;
	}

	/**
	 * @return the minWeightVehicle
	 */
	public String getMinWeightVehicle() {
		return minWeightVehicle;
	}

	/**
	 * @param minWeightVehicle the minWeightVehicle to set
	 */
	public void setMinWeightVehicle(String minWeightVehicle) {
		this.minWeightVehicle = minWeightVehicle;
	}

	/**
	 * @return the maxVolumeVehicle
	 */
	public String getMaxVolumeVehicle() {
		return maxVolumeVehicle;
	}

	/**
	 * @param maxVolumeVehicle the maxVolumeVehicle to set
	 */
	public void setMaxVolumeVehicle(String maxVolumeVehicle) {
		this.maxVolumeVehicle = maxVolumeVehicle;
	}

	/**
	 * @return the minVolumeVehicle
	 */
	public String getMinVolumeVehicle() {
		return minVolumeVehicle;
	}

	/**
	 * @param minVolumeVehicle the minVolumeVehicle to set
	 */
	public void setMinVolumeVehicle(String minVolumeVehicle) {
		this.minVolumeVehicle = minVolumeVehicle;
	}
	
}