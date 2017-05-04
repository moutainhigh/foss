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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/vo/PassInviteApplyVo.java
 * 
 *  FILE NAME     :PassInviteApplyVo.java
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassInviteApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.VehicleDriverWithDto;

/**
 * @author 104306-foss-wangLong
 * @date 2012-12-15 下午12:54:39
 */
public class PassInviteApplyVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 67424986988375368L;
	
	/**
	 * 
	 */
	private PassInviteApplyEntity passInviteApplyEntity;
	
	/**
	 * 
	 */
	private List<InviteVehicleDto> inviteVehicleDtoList;
	
	/**
	 * 
	 */
	private InviteVehicleDto inviteVehicleDto = new InviteVehicleDto();
	
	/**
	 * 
	 */
	private WhitelistAuditEntity whitelistAuditEntity;
	
	/**
	 * 
	 */
	private List<WhitelistAuditEntity> whitelistAuditList;
	/**外请车查询列表*/
	private List<VehicleDriverWithDto> vehicleDriverWithDtoList;
	/**外请车查询总数量*/
	private Long vehicleDriverWithDtoCount;
	
	private List<InviteVehicleEntity> inviteVehicleList; 
	
	/**
	 * 外请车单号
	 */
	private String inviteNo;
	
	/**
	 * 外请车单号list
	 */
	private List<String> inviteNoList;
 	
	/**
	 * 审核结果备注
	 */
	private String notes;
	
	/** 
	 * 是否营业部自请车
	 */
	private String isSaleDepartmentCompany;
	
	/** 
	 * 拼车类型
	 */
	private String carpoolingType;
	
	/**
	 * 预计到达时间
	 */
	private Date perdictArriveTime;
	
	/**
	 * 请车价格
	 */
	private BigDecimal inviteCost;
	/**
	 * 外请车牌号
	 */
	private String vehicleNo;
	
	/**
	 * 车型
	 */
	private String vehicleType;
	
	/**
	 * 司机姓名
	 */
	private String driverName;
	
	/**
	 * 司机手机
	 */
	private String driverPhone;
	
	/**
	 * 是否开蓬
	 */
	private String isOpenVehicle;
	
	/**
	 * 车型
	 */
	private String vehcleLengthName;
	
	/**
	 * 申请人部门
	 */
	private String applyOrgCode;
	
	/**
	 * 是否加载全部外请车
	 */
	private boolean inviteVehicleIsLoadAll;
	
	/**外请车log*/
	private List<PassInviteApplyEntity> passInviteApplyList;
	
	
	/**
	 * 判断 是否加载全部外请车.
	 *
	 * @return the 是否加载全部外请车
	 */
	public boolean isInviteVehicleIsLoadAll() {
		return inviteVehicleIsLoadAll;
	}

	/**
	 * 设置 是否加载全部外请车.
	 *
	 * @param inviteVehicleIsLoadAll the new 是否加载全部外请车
	 */
	public void setInviteVehicleIsLoadAll(boolean inviteVehicleIsLoadAll) {
		this.inviteVehicleIsLoadAll = inviteVehicleIsLoadAll;
	}

	/**
	 * 获得passInviteApplyEntity
	 * @return the passInviteApplyEntity
	 */
	public PassInviteApplyEntity getPassInviteApplyEntity() {
		return passInviteApplyEntity;
	}
	
	/**
	 * 设置passInviteApplyEntity
	 * @param passInviteApplyEntity the passInviteApplyEntity to set
	 */
	public void setPassInviteApplyEntity(PassInviteApplyEntity passInviteApplyEntity) {
		this.passInviteApplyEntity = passInviteApplyEntity;
	}

	/**
	 * 设置inviteVehicleDtoList
	 * @param inviteVehicleDtoList the inviteVehicleDtoList to set
	 */
	public List<InviteVehicleDto> getInviteVehicleDtoList() {
		return inviteVehicleDtoList;
	}
	
	/**
	 * 设置inviteVehicleDtoList
	 * @param inviteVehicleDtoList the inviteVehicleDtoList to set
	 */
	public void setInviteVehicleDtoList(List<InviteVehicleDto> inviteVehicleDtoList) {
		this.inviteVehicleDtoList = inviteVehicleDtoList;
	}

	/**
	 * 设置inviteNo
	 * @param inviteNo the inviteNo to set
	 */
	public String getInviteNo() {
		return inviteNo;
	}

	/**
	 * 设置inviteNo
	 * @param inviteNo the inviteNo to set
	 */
	public void setInviteNo(String inviteNo) {
		this.inviteNo = inviteNo;
	}

	/**
	 * 设置inviteVehicleDto
	 * @param inviteVehicleDto the inviteVehicleDto to get
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
	 * 设置notes
	 * @param notes the notes to get
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * 设置notes
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * 设置whitelistAuditEntity
	 * @param whitelistAuditEntity the whitelistAuditEntity to get
	 */
	public WhitelistAuditEntity getWhitelistAuditEntity() {
		return whitelistAuditEntity;
	}

	/**
	 * 设置whitelistAuditEntity
	 * @param whitelistAuditEntity the whitelistAuditEntity to set
	 */
	public void setWhitelistAuditEntity(WhitelistAuditEntity whitelistAuditEntity) {
		this.whitelistAuditEntity = whitelistAuditEntity;
	}
	
	/**
	 * 设置whitelistAuditList
	 * @param whitelistAuditList the whitelistAuditList to get
	 */
	public List<WhitelistAuditEntity> getWhitelistAuditList() {
		return whitelistAuditList;
	}

	/**
	 * 设置whitelistAuditList
	 * @param whitelistAuditList the whitelistAuditList to set
	 */
	public void setWhitelistAuditList(List<WhitelistAuditEntity> whitelistAuditList) {
		this.whitelistAuditList = whitelistAuditList;
	}

	/**
	 * 获取 预计到达时间.
	 *
	 * @return the 预计到达时间
	 */
	public Date getPerdictArriveTime() {
		return perdictArriveTime;
	}

	/**
	 * 设置 预计到达时间.
	 *
	 * @param perdictArriveTime the new 预计到达时间
	 */
	public void setPerdictArriveTime(Date perdictArriveTime) {
		this.perdictArriveTime = perdictArriveTime;
	}

	/**
	 * 获取 请车价格.
	 *
	 * @return the 请车价格
	 */
	public BigDecimal getInviteCost() {
		return inviteCost;
	}

	/**
	 * 设置 请车价格.
	 *
	 * @param inviteCost the new 请车价格
	 */
	public void setInviteCost(BigDecimal inviteCost) {
		this.inviteCost = inviteCost;
	}

	/**
	 * 获取 外请车牌号.
	 *
	 * @return the 外请车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置 外请车牌号.
	 *
	 * @param vehicleNo the new 外请车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 车型.
	 *
	 * @return the 车型
	 */
	public String getVehicleType() {
		return vehicleType;
	}

	/**
	 * 设置 车型.
	 *
	 * @param vehicleType the new 车型
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * 获取 司机姓名.
	 *
	 * @return the 司机姓名
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * 设置 司机姓名.
	 *
	 * @param driverName the new 司机姓名
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * 获取 司机手机.
	 *
	 * @return the 司机手机
	 */
	public String getDriverPhone() {
		return driverPhone;
	}

	/**
	 * 设置 司机手机.
	 *
	 * @param driverPhone the new 司机手机
	 */
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public List<VehicleDriverWithDto> getVehicleDriverWithDtoList() {
		return vehicleDriverWithDtoList;
	}

	/**
	 * 
	 *
	 * @param vehicleDriverWithDtoList 
	 */
	public void setVehicleDriverWithDtoList(List<VehicleDriverWithDto> vehicleDriverWithDtoList) {
		this.vehicleDriverWithDtoList = vehicleDriverWithDtoList;
	}

	/**
	 * 获取 车型.
	 *
	 * @return the 车型
	 */
	public String getVehcleLengthName() {
		return vehcleLengthName;
	}

	/**
	 * 设置 车型.
	 *
	 * @param vehcleLengthName the new 车型
	 */
	public void setVehcleLengthName(String vehcleLengthName) {
		this.vehcleLengthName = vehcleLengthName;
	}

	/**
	 * 获取 是否开蓬.
	 *
	 * @return the 是否开蓬
	 */
	public String getIsOpenVehicle() {
		return isOpenVehicle;
	}

	/**
	 * 设置 是否开蓬.
	 *
	 * @param isOpenVehicle the new 是否开蓬
	 */
	public void setIsOpenVehicle(String isOpenVehicle) {
		this.isOpenVehicle = isOpenVehicle;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public List<InviteVehicleEntity> getInviteVehicleList() {
		return inviteVehicleList;
	}

	/**
	 * 
	 *
	 * @param inviteVehicleList 
	 */
	public void setInviteVehicleList(List<InviteVehicleEntity> inviteVehicleList) {
		this.inviteVehicleList = inviteVehicleList;
	}

	/**
	 * 获取 申请人部门.
	 *
	 * @return the 申请人部门
	 */
	public String getApplyOrgCode() {
		return applyOrgCode;
	}

	/**
	 * 设置 申请人部门.
	 *
	 * @param applyOrgCode the new 申请人部门
	 */
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}

	/**
	 * 获取 外请车单号list.
	 *
	 * @return the 外请车单号list
	 */
	public List<String> getInviteNoList() {
		return inviteNoList;
	}

	/**
	 * 设置 外请车单号list.
	 *
	 * @param inviteNoList the new 外请车单号list
	 */
	public void setInviteNoList(List<String> inviteNoList) {
		this.inviteNoList = inviteNoList;
	}

	public Long getVehicleDriverWithDtoCount() {
		return vehicleDriverWithDtoCount;
	}

	public void setVehicleDriverWithDtoCount(Long vehicleDriverWithDtoCount) {
		this.vehicleDriverWithDtoCount = vehicleDriverWithDtoCount;
	}

	/**
	 *  外请车log
	 * @return the auditInviteApplyList
	 */
	public List<PassInviteApplyEntity> getPassInviteApplyList() {
		return passInviteApplyList;
	}

	/**
	 * 外请车log
	 * @param auditInviteApplyList the auditInviteApplyList to set
	 */
	public void setPassInviteApplyList(
			List<PassInviteApplyEntity> passInviteApplyList) {
		this.passInviteApplyList = passInviteApplyList;
	}

	public String getIsSaleDepartmentCompany() {
		return isSaleDepartmentCompany;
	}

	public void setIsSaleDepartmentCompany(String isSaleDepartmentCompany) {
		this.isSaleDepartmentCompany = isSaleDepartmentCompany;
	}

	public String getCarpoolingType() {
		return carpoolingType;
	}

	public void setCarpoolingType(String carpoolingType) {
		this.carpoolingType = carpoolingType;
	}
}