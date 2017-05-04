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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/vo/BorrowVehicleVo.java
 * 
 *  FILE NAME     :BorrowVehicleVo.java
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
package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.BorrowVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.BorrowVehicleDto;

/**
 * 借车申请Vo
 * @author 104306-foss-wangLong
 * @date 2012-10-15 下午1:14:32
 */
public class BorrowVehicleVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 91218740177370768L;
								
	/**
	 * 
	 */
	private BorrowVehicleEntity borrowVehicleEntity;
	
	/**
	 * 
	 */
	private BorrowVehicleDto borrowVehicleDto;
	
	/**
	 * 
	 */
	private List<BorrowVehicleDto> borrowVehicleList;
	
	/** 借车受理时间 默认位15 如果超过前段颜色高亮警示  */
	private int acceptanceTime;
	
	/** 借车编号List */
	private List<String> borrowNoList;
	
	/**
	 * 申请部门list
	 */
	private List<String> applyOrgCodeList;
	
	/** 服务端时间  */
	private Date currentTime;
	
	/**
	 * 获得borrowVehicleEntity
	 * @return the borrowVehicleEntity
	 */
	public BorrowVehicleEntity getBorrowVehicleEntity() {
		return borrowVehicleEntity;
	}
	
	/**
	 * 设置borrowVehicleEntity
	 * @param borrowVehicleEntity the borrowVehicleEntity to set
	 */
	public void setBorrowVehicleEntity(BorrowVehicleEntity borrowVehicleEntity) {
		this.borrowVehicleEntity = borrowVehicleEntity;
	}

	/**
	 * 获得borrowVehicleDto
	 * @return the borrowVehicleDto
	 */
	public BorrowVehicleDto getBorrowVehicleDto() {
		return borrowVehicleDto;
	}

	/**
	 * 设置borrowVehicleDto
	 * @param borrowVehicleDto the borrowVehicleDto to set
	 */
	public void setBorrowVehicleDto(BorrowVehicleDto borrowVehicleDto) {
		this.borrowVehicleDto = borrowVehicleDto;
	}

	/**
	 * 获得borrowVehicleList
	 * @return the borrowVehicleList
	 */
	public List<BorrowVehicleDto> getBorrowVehicleList() {
		return borrowVehicleList;
	}

	/**
	 * 设置borrowVehicleList
	 * @param borrowVehicleList the borrowVehicleList to set
	 */
	public void setBorrowVehicleList(List<BorrowVehicleDto> borrowVehicleList) {
		this.borrowVehicleList = borrowVehicleList;
	}
	
	/**
	 * 获得borrowNoList
	 * @return the borrowNoList
	 */
	public List<String> getBorrowNoList() {
		return borrowNoList;
	}

	/**
	 * 设置borrowNoList
	 * @param borrowNoList the borrowNoList to set
	 */
	public void setBorrowNoList(List<String> borrowNoList) {
		this.borrowNoList = borrowNoList;
	}

	/**
	 * 获得acceptanceTime
	 * @return the acceptanceTime
	 */
	public int getAcceptanceTime() {
		return acceptanceTime;
	}

	/**
	 * 设置acceptanceTime
	 * @param acceptanceTime the acceptanceTime to set
	 */
	public void setAcceptanceTime(int acceptanceTime) {
		this.acceptanceTime = acceptanceTime;
	}

	/**
	 * 获得currentTime
	 * @return the currentTime
	 */
	public Date getCurrentTime() {
		return currentTime;
	}

	/**
	 * 设置currentTime
	 * @param currentTime the currentTime to set
	 */
	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	/**
	 * 获取 申请部门list.
	 *
	 * @return the 申请部门list
	 */
	public List<String> getApplyOrgCodeList() {
		return applyOrgCodeList;
	}

	/**
	 * 设置 申请部门list.
	 *
	 * @param applyOrgCodeList the new 申请部门list
	 */
	public void setApplyOrgCodeList(List<String> applyOrgCodeList) {
		this.applyOrgCodeList = applyOrgCodeList;
	}
}