/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/SaleDepartmentVo.java
 * 
 * FILE NAME        	: SaleDepartmentVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.shared.vo
 * FILE    NAME: SaleDepartmentVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InviteFossVehicleCostEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.InviteFossVehicleCostQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InviteCommonExpressageEntity;

/**
 * 零担外请车单票费用VO
 * @author 332219-foss
 * @date 2017-07-11 下午3:23:54
 */
public class InviteFossVehicleCostVo implements Serializable{
	/**
	*serialVersionUID
	*/
	private static final long serialVersionUID = 8956173309045310168L;
	/**
	 *  查询参数Dto
	 */
	private InviteFossVehicleCostQueryDto inviteFossVehicleCostQueryDto ;
	//零担单票费用
	private InviteFossVehicleCostEntity inviteFossVehicleCostEntity;
	//返回结果
	private List<InviteFossVehicleCostEntity> inviteFossVehicleCostEntityList;
	//快递点部和分部
	private InviteCommonExpressageEntity inviteCommonExpressageEntity;
	//快递点部和分部返回结果
	private List<InviteCommonExpressageEntity> inviteCommonExpressageEntityList;
	//上传文件
	private File uploadFile;
	//上传文件名
	private String uploadFileFileName;

	private String uploadFileContentType;
	//记录没导入成功的行数
	private List<Integer> numList;
	
	/**
	 *getter
	 */
	public InviteFossVehicleCostQueryDto getInviteFossVehicleCostQueryDto() {
		return inviteFossVehicleCostQueryDto;
	}
	/**
	 *setter
	 */
	public void setInviteFossVehicleCostQueryDto(
			InviteFossVehicleCostQueryDto inviteFossVehicleCostQueryDto) {
		this.inviteFossVehicleCostQueryDto = inviteFossVehicleCostQueryDto;
	}
	/**
	 *getter
	 */
	public InviteFossVehicleCostEntity getInviteFossVehicleCostEntity() {
		return inviteFossVehicleCostEntity;
	}
	/**
	 *setter
	 */
	public void setInviteFossVehicleCostEntity(
			InviteFossVehicleCostEntity inviteFossVehicleCostEntity) {
		this.inviteFossVehicleCostEntity = inviteFossVehicleCostEntity;
	}
	/**
	 *getter
	 */
	public List<InviteFossVehicleCostEntity> getInviteFossVehicleCostEntityList() {
		return inviteFossVehicleCostEntityList;
	}
	/**
	 *setter
	 */
	public void setInviteFossVehicleCostEntityList(
			List<InviteFossVehicleCostEntity> inviteFossVehicleCostEntityList) {
		this.inviteFossVehicleCostEntityList = inviteFossVehicleCostEntityList;
	}
	/**
	 *getter
	 */
	public File getUploadFile() {
		return uploadFile;
	}
	/**
	 *setter
	 */
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	/**
	 *getter
	 */
	public String getUploadFileFileName() {
		return uploadFileFileName;
	}
	/**
	 *setter
	 */
	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}
	/**
	 *getter
	 */
	public List<Integer> getNumList() {
		return numList;
	}
	/**
	 *setter
	 */
	public void setNumList(List<Integer> numList) {
		this.numList = numList;
	}
	/**
	 *getter
	 */
	public String getUploadFileContentType() {
		return uploadFileContentType;
	}
	/**
	 *setter
	 */
	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}
	/**
	 *getter
	 */
	public InviteCommonExpressageEntity getInviteCommonExpressageEntity() {
		return inviteCommonExpressageEntity;
	}
	/**
	 *setter
	 */
	public void setInviteCommonExpressageEntity(
			InviteCommonExpressageEntity inviteCommonExpressageEntity) {
		this.inviteCommonExpressageEntity = inviteCommonExpressageEntity;
	}
	/**
	 *getter
	 */
	public List<InviteCommonExpressageEntity> getInviteCommonExpressageEntityList() {
		return inviteCommonExpressageEntityList;
	}
	/**
	 *setter
	 */
	public void setInviteCommonExpressageEntityList(
			List<InviteCommonExpressageEntity> inviteCommonExpressageEntityList) {
		this.inviteCommonExpressageEntityList = inviteCommonExpressageEntityList;
	}
}