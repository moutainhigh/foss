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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/BorrowVehicleDto.java
 * 
 *  FILE NAME     :BorrowVehicleDto.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.dto
 * FILE    NAME: BorrowVehicleDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditBorrowApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.BorrowVehicleEntity;

/**
 * 借车 dto
 * @author 104306-foss-wangLong
 * @date 2012-12-3 下午1:53:08
 */
public class BorrowVehicleDto extends BorrowVehicleEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2404850695376614762L;
	
	/** 车牌号 */
	private String vehicleNo;
	
	/** 车辆所属车队  可能为小组 */
	private String vehicleOrganizationName;
	
	/** 受理时间 */
	private Date auditTime;
	
	/** 受理时间 */
	private Date auditBeginTime;
	
	/**
	 * 
	 */
	private Date auditEndTime;
	
	/** 申请时间 */
	private Date applyBeginTime;
	
	/**
	 * 
	 */
	private Date applyEndTime;
	
	/** 借车单号List */
	private List<String> borrowNoList;
	
	/**  实际分配 车型  */
	private String orderVehicleModelName;
	
	/** 借车审核log */
	private List<AuditBorrowApplyEntity> auditBorrowApplyList;
	
	/**
	 * 查询类型，用“且”还是用“或”
	 */
	private String queryType;
	
	/**
	 * 获取 查询类型，用“且”还是用“或”.
	 *
	 * @return the 查询类型，用“且”还是用“或”
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * 设置 查询类型，用“且”还是用“或”.
	 *
	 * @param queryType the new 查询类型，用“且”还是用“或”
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	/**
	 * 获得vehicleNo
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * 设置vehicleNo
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获得auditTime
	 * @return the auditTime
	 */
	public Date getAuditTime() {
		return auditTime;
	}

	/**
	 * 设置auditTime
	 * @param auditTime the auditTime to set
	 */
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	/**
	 * 获得auditBeginTime
	 * @return the auditBeginTime
	 */
	public Date getAuditBeginTime() {
		return auditBeginTime;
	}

	/**
	 * 设置auditBeginTime
	 * @param auditBeginTime the auditBeginTime to set
	 */
	public void setAuditBeginTime(Date auditBeginTime) {
		this.auditBeginTime = auditBeginTime;
	}

	/**
	 * 获得auditEndTime
	 * @return the auditEndTime
	 */
	public Date getAuditEndTime() {
		return auditEndTime;
	}

	/**
	 * 设置auditEndTime
	 * @param auditEndTime the auditEndTime to set
	 */
	public void setAuditEndTime(Date auditEndTime) {
		this.auditEndTime = auditEndTime;
	}

	/**
	 * 获得applyBeginTime
	 * @return the applyBeginTime
	 */
	public Date getApplyBeginTime() {
		return applyBeginTime;
	}

	/**
	 * 设置applyBeginTime
	 * @param applyBeginTime the applyBeginTime to set
	 */
	public void setApplyBeginTime(Date applyBeginTime) {
		this.applyBeginTime = applyBeginTime;
	}

	/**
	 * 获得applyEndTime
	 * @return the applyEndTime
	 */
	public Date getApplyEndTime() {
		return applyEndTime;
	}

	/**
	 * 设置applyEndTime
	 * @param applyEndTime the applyEndTime to set
	 */
	public void setApplyEndTime(Date applyEndTime) {
		this.applyEndTime = applyEndTime;
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
	 * 获得orderVehicleModelName
	 * @return the orderVehicleModelName
	 */
	public String getOrderVehicleModelName() {
		return orderVehicleModelName;
	}

	/**
	 * 设置orderVehicleModelName
	 * @param orderVehicleModelName the orderVehicleModelName to set
	 */
	public void setOrderVehicleModelName(String orderVehicleModelName) {
		this.orderVehicleModelName = orderVehicleModelName;
	}

	/**
	 * 获得driverauditBorrowApplyListPhone
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
	 * 获得vehicleOrganizationName
	 * @return the vehicleOrganizationName
	 */
	public String getVehicleOrganizationName() {
		return vehicleOrganizationName;
	}

	/**
	 * 设置vehicleOrganizationName
	 * @param vehicleOrganizationName the vehicleOrganizationName to set
	 */
	public void setVehicleOrganizationName(String vehicleOrganizationName) {
		this.vehicleOrganizationName = vehicleOrganizationName;
	}
}