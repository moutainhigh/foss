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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/dto/UnloadTaskAddnewDto.java
 *  
 *  FILE NAME          :UnloadTaskAddnewDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadBillDetailEntity;

/** 
 * @className: UnloadTaskAddnewDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 新增卸车任务时，前台传入的dto
 * @date: 2012-12-13 下午1:45:49
 * 
 */
public class UnloadTaskAddnewDto implements Serializable {

	private static final long serialVersionUID = 6016365666831121499L;
	
	//车牌号
	private String vehicleNo;
	//月台号
	private String platformCode;
	//月台ID
	private String platformId;
	//添加的单据列表
	private List<UnloadBillDetailEntity> billList;
	//添加的卸车员列表
	private List<LoaderParticipationEntity> loaderList;
	
	public String getPlatformId() {
		return platformId;
	}
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getPlatformCode() {
		return platformCode;
	}
	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}
	public List<UnloadBillDetailEntity> getBillList() {
		return billList;
	}
	public void setBillList(List<UnloadBillDetailEntity> billList) {
		this.billList = billList;
	}
	public List<LoaderParticipationEntity> getLoaderList() {
		return loaderList;
	}
	public void setLoaderList(List<LoaderParticipationEntity> loaderList) {
		this.loaderList = loaderList;
	}

}