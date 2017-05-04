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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/dto/UnloadTaskModifyDto.java
 *  
 *  FILE NAME          :UnloadTaskModifyDto.java
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
 * @className: UnloadTaskModifyDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 修改卸车任务时传入的参数
 * @date: 2012-12-14 下午2:35:56
 * 
 */
public class UnloadTaskModifyDto implements Serializable {

	private static final long serialVersionUID = -1781192213882240009L;
	
	//卸车任务ID
	private String unloadTaskId;
	//卸车任务号
	private String unloadTaskNo;
	//月台编号
	private String platformNo;
	//月台ID
	private String platformId;
	//新增的单据list
	private List<UnloadBillDetailEntity> addedBillList;
	//删除的单据list
	private List<UnloadBillDetailEntity> deletedBillList;
	//新增的卸车员
	private List<LoaderParticipationEntity> addedLoaderList;
	//删除的卸车员
	private List<LoaderParticipationEntity> deletedLoaderList;
	
	public String getPlatformId() {
		return platformId;
	}
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	public String getUnloadTaskId() {
		return unloadTaskId;
	}
	public void setUnloadTaskId(String unloadTaskId) {
		this.unloadTaskId = unloadTaskId;
	}
	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}
	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}
	public String getPlatformNo() {
		return platformNo;
	}
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	public List<UnloadBillDetailEntity> getAddedBillList() {
		return addedBillList;
	}
	public void setAddedBillList(List<UnloadBillDetailEntity> addedBillList) {
		this.addedBillList = addedBillList;
	}
	public List<UnloadBillDetailEntity> getDeletedBillList() {
		return deletedBillList;
	}
	public void setDeletedBillList(List<UnloadBillDetailEntity> deletedBillList) {
		this.deletedBillList = deletedBillList;
	}
	public List<LoaderParticipationEntity> getAddedLoaderList() {
		return addedLoaderList;
	}
	public void setAddedLoaderList(List<LoaderParticipationEntity> addedLoaderList) {
		this.addedLoaderList = addedLoaderList;
	}
	public List<LoaderParticipationEntity> getDeletedLoaderList() {
		return deletedLoaderList;
	}
	public void setDeletedLoaderList(
			List<LoaderParticipationEntity> deletedLoaderList) {
		this.deletedLoaderList = deletedLoaderList;
	}
	
}