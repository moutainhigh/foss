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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/vo/PassBorrowApplyVo.java
 * 
 *  FILE NAME     :PassBorrowApplyVo.java
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
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditBorrowApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.BorrowVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassBorrowApplyEntity;

/**
 * 借车审核Vo
 * @author 104306-foss-wangLong
 * @date 2012-10-15 下午1:14:32
 */
public class PassBorrowApplyVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 62889665426377808L;

	/** 借车受理  */
	private PassBorrowApplyEntity passBorrowApplyEntity;
	
	/** 借车受理 log */
	private AuditBorrowApplyEntity auditBorrowApplyEntity;
	
	/** 借车编号List */
	private List<String> borrowNoList;
	
	/** 借车申请实体  */
	private List<BorrowVehicleEntity> borrowVehicleList;
	
	/** 借车审核log */
	private List<AuditBorrowApplyEntity> auditBorrowApplyList;
	
	/** 公司车List */
	private List<VehicleAssociationDto> ownTruckList;
	
	/** 公司车查询对象 */
	private OwnTruckEntity ownTruckEntity;
	
	/**
	 * 车队编码
	 */
	private String transDepartment;
	
	/**
	 * 车队小组list
	 */
	private List<OrgAdministrativeInfoEntity> transTeamList; 

	/**
	 * 获取 车队小组list.
	 *
	 * @return the 车队小组list
	 */
	public List<OrgAdministrativeInfoEntity> getTransTeamList() {
		return transTeamList;
	}

	/**
	 * 设置 车队小组list.
	 *
	 * @param transTeamList the new 车队小组list
	 */
	public void setTransTeamList(List<OrgAdministrativeInfoEntity> transTeamList) {
		this.transTeamList = transTeamList;
	}

	/**
	 * 获取 车队编码.
	 *
	 * @return the 车队编码
	 */
	public String getTransDepartment() {
		return transDepartment;
	}

	/**
	 * 设置 车队编码.
	 *
	 * @param transDepartment the new 车队编码
	 */
	public void setTransDepartment(String transDepartment) {
		this.transDepartment = transDepartment;
	}

	/**
	 * 获得passBorrowApplyEntity
	 * @return the passBorrowApplyEntity
	 */
	public PassBorrowApplyEntity getPassBorrowApplyEntity() {
		return passBorrowApplyEntity;
	}
	
	/**
	 * 设置passBorrowApplyEntity
	 * @param passBorrowApplyEntity the passBorrowApplyEntity to set
	 */
	public void setPassBorrowApplyEntity(PassBorrowApplyEntity passBorrowApplyEntity) {
		this.passBorrowApplyEntity = passBorrowApplyEntity;
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
	 * 获得borrowVehicleList
	 * @return the borrowVehicleList
	 */
	public List<BorrowVehicleEntity> getBorrowVehicleList() {
		return borrowVehicleList;
	}

	/**
	 * 设置borrowVehicleList
	 * @param borrowVehicleList the borrowVehicleList to set
	 */
	public void setBorrowVehicleList(List<BorrowVehicleEntity> borrowVehicleList) {
		this.borrowVehicleList = borrowVehicleList;
	}

	/**
	 * 获得auditBorrowApplyEntity
	 * @return the auditBorrowApplyEntity
	 */
	public AuditBorrowApplyEntity getAuditBorrowApplyEntity() {
		return auditBorrowApplyEntity;
	}

	/**
	 * 设置auditBorrowApplyEntity
	 * @param auditBorrowApplyEntity the auditBorrowApplyEntity to set
	 */
	public void setAuditBorrowApplyEntity(
			AuditBorrowApplyEntity auditBorrowApplyEntity) {
		this.auditBorrowApplyEntity = auditBorrowApplyEntity;
	}

	/**
	 * 获得auditBorrowApplyList
	 * @return the auditBorrowApplyList
	 */
	public List<AuditBorrowApplyEntity> getAuditBorrowApplyList() {
		return auditBorrowApplyList;
	}

	/**
	 * 设置auditBorrowApplyList
	 * @param auditBorrowApplyList the auditBorrowApplyList to set
	 */
	public void setAuditBorrowApplyList(
			List<AuditBorrowApplyEntity> auditBorrowApplyList) {
		this.auditBorrowApplyList = auditBorrowApplyList;
	}

	/**
	 * 获得ownTruckList
	 * @return the ownTruckList
	 */
	public List<VehicleAssociationDto> getOwnTruckList() {
		return ownTruckList;
	}

	/**
	 * 设置ownTruckList
	 * @param ownTruckList the ownTruckList to set
	 */
	public void setOwnTruckList(List<VehicleAssociationDto> ownTruckList) {
		this.ownTruckList = ownTruckList;
	}

	/**
	 * 获得ownTruckEntity
	 * @return the ownTruckEntity
	 */
	public OwnTruckEntity getOwnTruckEntity() {
		return ownTruckEntity;
	}

	/**
	 * 设置ownTruckEntity
	 * @param ownTruckEntity the ownTruckEntity to set
	 */
	public void setOwnTruckEntity(OwnTruckEntity ownTruckEntity) {
		this.ownTruckEntity = ownTruckEntity;
	}
}