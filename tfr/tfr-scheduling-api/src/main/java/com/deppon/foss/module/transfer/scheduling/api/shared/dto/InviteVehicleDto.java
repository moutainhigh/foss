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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/InviteVehicleDto.java
 * 
 *  FILE NAME     :InviteVehicleDto.java
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
 * FILE    NAME: InviteVehicleDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditInviteApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEntity;

/**
 * 外请车DTO
 * @author 104306-foss-wangLong
 * @date 2012-12-15 下午1:47:40
 */
public class InviteVehicleDto extends InviteVehicleEntity {

	private static final long serialVersionUID = -5250810105383484040L;

	/** 安排车牌号  */
	private String vehicleNo;
	
	/** 派车车队名称  */
	private String dispatchTransDeptName;
	
	/** 请车价格区 */
	private BigDecimal inviteCost;
	/**预计车辆到达时间*/
	private Date predictArriveTime;
	
	/** 受理时间 */
	private Date auditTime;
	
	/** 车型 */
	private String vehicleLengthName;
	
	/** 请车价格区间 用于查询条件 */
	private String beginInviteCostStr;
	
	private String endInviteCostStr;
	
	/** 受理时间 区间*/
	private Date beginAuditTime;

	private Date endAuditTime;
	
	/** 用车时间  区间*/
	private Date beginPredictUseTime;

	private Date endPredictUseTime;
	
	/**  外请约车编号list  */
	private List<String> inviteNoList;
	
	/** 申请人所在部门名称  */
	private String applyEmpOrgName;
	
	/** 受理log  */
	private List<AuditInviteApplyEntity> auditInviteVehicleList;
	
	/** 外请审核受理 车辆司机信息 */
	private VehicleDriverWithDto vehicleDriverWithDto;
	
	/** 经过到达部门名称  */
	private String arrivedDeptName;
	
	/** 外请车约车状态集合*/
	private List<String> inviteVehicleStatusList;
	
	/** 合同线路  */
	private String contractLine;
	
	/** 回程价格 */
	private String beginReturnCostStr;
	
	private String endReturnCostStr;
	
	/**受理人*/
	private String acceptEmpName;
	/**车辆类型*/
	private String vehicleType;
	/**司机姓名*/
	private String driverName;
	/**司机编码*/
	private String driverCode;
	/**司机电话*/
	private String driverPhone;
	/**是否开蓬*/
	private String isOpenVehicle;
	/**司机身份证号码*/
	private String driverIdCard;
	/**车辆额定载重*/
	private String vehicleDeadLoad;
	/**车辆净空 */
    private BigDecimal vehicleSelfVolume;
    /**是否有GPS*/
    private String vehicleGps;
    /**是否有尾板 */
    private String tailBoard;
    /**是否敞篷车 */
    private String openVehicle;
    /**具体车车长 */
    private BigDecimal vehicleLength;
    /**车型*/
    private String vehicleLengthCode;
    /**具体车辆宽度 */
    private BigDecimal vehicleWidth;
    /**具体车辆高度 */
    private BigDecimal vehicleHeight;
    /**外请车出厂日期*/
    private Date vehicleBeginTime;
    /**报废日期*/
    private Date vehicleEndTime;
    /**是否合同车 */
    private String bargainVehicle; 
    /**当前登陆部门编号*/
    private String currentDeptCode;
    /**当前登陆部门所对应的外场的编码*/
    private String currentTransferCenterCode;
    /**顶级车队CODE*/
    private String topFleetCode;
    /**顶级车队name*/
    private String topFleetName;
    /**配载车次号*/
    private String vehicleassembleNo;
    /**询价编码*/
    private String inquiryNo;
    /** 单票费用标准 */
    private String vehicleCost;;
    
	/**
	 * 获得beginInviteCost
	 * @return the beginInviteCost
	 */
	public String getBeginInviteCostStr() {
		return beginInviteCostStr;
	}

	/**
	 * 设置endInviteCost
	 * @param endInviteCost the endInviteCost to set
	 */
	public void setBeginInviteCostStr(String beginInviteCostStr) {
		this.beginInviteCostStr = beginInviteCostStr;
	}

	/**
	 * 获得endInviteCost
	 * @return the endInviteCost
	 */
	public String getEndInviteCostStr() {
		return endInviteCostStr;
	}

	/**
	 * 设置endInviteCost
	 * @param endInviteCost the endInviteCost to set
	 */
	public void setEndInviteCostStr(String endInviteCostStr) {
		this.endInviteCostStr = endInviteCostStr;
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
	 * 获得endAuditTime
	 * @return the endAuditTime
	 */
	public Date getBeginAuditTime() {
		return beginAuditTime;
	}

	/**
	 * 设置endAuditTime
	 * @param endAuditTime the endAuditTime to set
	 */
	@DateFormat
	public void setBeginAuditTime(Date beginAuditTime) {
		this.beginAuditTime = beginAuditTime;
	}

	/**
	 * 获得endAuditTime
	 * @return the endAuditTime
	 */
	public Date getEndAuditTime() {
		return endAuditTime;
	}

	/**
	 * 设置endAuditTime
	 * @param endAuditTime the endAuditTime to set
	 */
	@DateFormat
	public void setEndAuditTime(Date endAuditTime) {
		this.endAuditTime = endAuditTime;
	}

	/**
	 * 获得beginPredictUseTime
	 * @return the beginPredictUseTime
	 */
	public Date getBeginPredictUseTime() {
		return beginPredictUseTime;
	}

	/**
	 * 设置beginPredictUseTime
	 * @param beginPredictUseTime the beginPredictUseTime to set
	 */
	@DateFormat
	public void setBeginPredictUseTime(Date beginPredictUseTime) {
		this.beginPredictUseTime = beginPredictUseTime;
	}

	/**
	 * 获得endPredictUseTime
	 * @return the endPredictUseTime
	 */
	public Date getEndPredictUseTime() {
		return endPredictUseTime;
	}

	/**
	 * 设置endPredictUseTime
	 * @param endPredictUseTime the endPredictUseTime to set
	 */
	@DateFormat
	public void setEndPredictUseTime(Date endPredictUseTime) {
		this.endPredictUseTime = endPredictUseTime;
	}

	/**
	 * 获得inviteCost
	 * @return the inviteCost
	 */
	public BigDecimal getInviteCost() {
		return inviteCost;
	}

	/**
	 * 设置inviteCost
	 * @param inviteCost the inviteCost to set
	 */
	public void setInviteCost(BigDecimal inviteCost) {
		this.inviteCost = inviteCost;
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
	 * 获得vehicleLengthName
	 * @return the vehicleLengthName
	 */
	public String getVehicleLengthName() {
		return vehicleLengthName;
	}

	/**
	 * 设置vehicleLengthName
	 * @param vehicleLengthName the vehicleLengthName to set
	 */
	public void setVehicleLengthName(String vehicleLengthName) {
		this.vehicleLengthName = vehicleLengthName;
	}

	/**
	 * 获得dispatchTransDeptName
	 * @return the dispatchTransDeptName
	 */
	public String getDispatchTransDeptName() {
		return dispatchTransDeptName;
	}

	/**
	 * 设置dispatchTransDeptName
	 * @param dispatchTransDeptName the dispatchTransDeptName to set
	 */
	public void setDispatchTransDeptName(String dispatchTransDeptName) {
		this.dispatchTransDeptName = dispatchTransDeptName;
	}

	/**
	 * 获得inviteNoList
	 * @return the inviteNoList
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
	 * 获得applyEmpOrgName
	 * @return the applyEmpOrgName
	 */
	public String getApplyEmpOrgName() {
		return applyEmpOrgName;
	}

	/**
	 * 设置applyEmpOrgName
	 * @param applyEmpOrgName the applyEmpOrgName to set
	 */
	public void setApplyEmpOrgName(String applyEmpOrgName) {
		this.applyEmpOrgName = applyEmpOrgName;
	}

	/**
	 * 获得auditInviteVehicleList
	 * @return the auditInviteVehicleList
	 */
	public List<AuditInviteApplyEntity> getAuditInviteVehicleList() {
		return auditInviteVehicleList;
	}

	/**
	 * 设置auditInviteVehicleList
	 * @param auditInviteVehicleList the auditInviteVehicleList to set
	 */
	public void setAuditInviteVehicleList(
			List<AuditInviteApplyEntity> auditInviteVehicleList) {
		this.auditInviteVehicleList = auditInviteVehicleList;
	}

	/**
	 * 获得vehicleDriverWithDto
	 * @return the vehicleDriverWithDto
	 */
	public VehicleDriverWithDto getVehicleDriverWithDto() {
		return vehicleDriverWithDto;
	}

	/**
	 * 设置vehicleDriverWithDto
	 * @param vehicleDriverWithDto the vehicleDriverWithDto to set
	 */
	public void setVehicleDriverWithDto(VehicleDriverWithDto vehicleDriverWithDto) {
		this.vehicleDriverWithDto = vehicleDriverWithDto;
	}

	/**
	 * 获得arrivedDeptName
	 * @return the arrivedDeptName
	 */
	public String getArrivedDeptName() {
		return arrivedDeptName;
	}

	/**
	 * 设置arrivedDeptName
	 * @param arrivedDeptName the arrivedDeptName to set
	 */
	public void setArrivedDeptName(String arrivedDeptName) {
		this.arrivedDeptName = arrivedDeptName;
	}

	/**
	 * 设置setInviteVehicleStatusList
	 * @param setInviteVehicleStatusList the setInviteVehicleStatusList to set
	 */
	public List<String> getInviteVehicleStatusList() {
		return inviteVehicleStatusList;
	}

	/**
	 * 设置setInviteVehicleStatusList
	 * @param setInviteVehicleStatusList the setInviteVehicleStatusList to set
	 */
	public void setInviteVehicleStatusList(List<String> inviteVehicleStatusList) {
		this.inviteVehicleStatusList = inviteVehicleStatusList;
	}

	/**
	 * 获得contractLine
	 * @return the contractLine
	 */
	public String getContractLine() {
		return contractLine;
	}

	/**
	 * 设置contractLine
	 * @param contractLine the contractLine to set
	 */
	public void setContractLine(String contractLine) {
		this.contractLine = contractLine;
	}

	/**
	 * 获得beginReturnCostStr
	 * @return the beginReturnCostStr
	 */
	public String getBeginReturnCostStr() {
		return beginReturnCostStr;
	}

	/**
	 * 设置beginReturnCostStr
	 * @param beginReturnCostStr the beginReturnCostStr to set
	 */
	public void setBeginReturnCostStr(String beginReturnCostStr) {
		this.beginReturnCostStr = beginReturnCostStr;
	}

	/**
	 * 获得endReturnCostStr
	 * @return the endReturnCostStr
	 */
	public String getEndReturnCostStr() {
		return endReturnCostStr;
	}

	/**
	 * 设置endReturnCostStr
	 * @param endReturnCostStr the endReturnCostStr to set
	 */
	public void setEndReturnCostStr(String endReturnCostStr) {
		this.endReturnCostStr = endReturnCostStr;
	}

	/**
	 * 获取 受理人.
	 *
	 * @return the 受理人
	 */
	public String getAcceptEmpName() {
		return acceptEmpName;
	}

	/**
	 * 设置 受理人.
	 *
	 * @param acceptEmpName the new 受理人
	 */
	public void setAcceptEmpName(String acceptEmpName) {
		this.acceptEmpName = acceptEmpName;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getIsOpenVehicle() {
		return isOpenVehicle;
	}

	public void setIsOpenVehicle(String isOpenVehicle) {
		this.isOpenVehicle = isOpenVehicle;
	}

	public String getDriverIdCard() {
		return driverIdCard;
	}

	public void setDriverIdCard(String driverIdCard) {
		this.driverIdCard = driverIdCard;
	}

	public String getVehicleDeadLoad() {
		return vehicleDeadLoad;
	}

	public void setVehicleDeadLoad(String vehicleDeadLoad) {
		this.vehicleDeadLoad = vehicleDeadLoad;
	}

	public BigDecimal getVehicleSelfVolume() {
		return vehicleSelfVolume;
	}

	public void setVehicleSelfVolume(BigDecimal vehicleSelfVolume) {
		this.vehicleSelfVolume = vehicleSelfVolume;
	}

	public String getVehicleGps() {
		return vehicleGps;
	}

	public void setVehicleGps(String vehicleGps) {
		this.vehicleGps = vehicleGps;
	}

	public String getTailBoard() {
		return tailBoard;
	}

	public void setTailBoard(String tailBoard) {
		this.tailBoard = tailBoard;
	}

	public String getOpenVehicle() {
		return openVehicle;
	}

	public void setOpenVehicle(String openVehicle) {
		this.openVehicle = openVehicle;
	}

	public BigDecimal getVehicleLength() {
		return vehicleLength;
	}

	public void setVehicleLength(BigDecimal vehicleLength) {
		this.vehicleLength = vehicleLength;
	}

	public BigDecimal getVehicleWidth() {
		return vehicleWidth;
	}

	public void setVehicleWidth(BigDecimal vehicleWidth) {
		this.vehicleWidth = vehicleWidth;
	}

	public BigDecimal getVehicleHeight() {
		return vehicleHeight;
	}

	public void setVehicleHeight(BigDecimal vehicleHeight) {
		this.vehicleHeight = vehicleHeight;
	}

	public Date getVehicleBeginTime() {
		return vehicleBeginTime;
	}

	public void setVehicleBeginTime(Date vehicleBeginTime) {
		this.vehicleBeginTime = vehicleBeginTime;
	}

	public Date getVehicleEndTime() {
		return vehicleEndTime;
	}

	public void setVehicleEndTime(Date vehicleEndTime) {
		this.vehicleEndTime = vehicleEndTime;
	}

	public String getVehicleLengthCode() {
		return vehicleLengthCode;
	}

	public void setVehicleLengthCode(String vehicleLengthCode) {
		this.vehicleLengthCode = vehicleLengthCode;
	}

	public String getBargainVehicle() {
		return bargainVehicle;
	}

	public void setBargainVehicle(String bargainVehicle) {
		this.bargainVehicle = bargainVehicle;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	public Date getPredictArriveTime() {
		return predictArriveTime;
	}

	public void setPredictArriveTime(Date predictArriveTime) {
		this.predictArriveTime = predictArriveTime;
	}

	/**
	 * 当前登陆部门所对应的外场的编码
	 * @return the currentTransferCenterCode
	 */
	public String getCurrentTransferCenterCode() {
		return currentTransferCenterCode;
	}

	/**
	 * 当前登陆部门所对应的外场的编码
	 * @param currentTransferCenterCode the currentTransferCenterCode to set
	 */
	public void setCurrentTransferCenterCode(String currentTransferCenterCode) {
		this.currentTransferCenterCode = currentTransferCenterCode;
	}

	/**
	 * 顶级车队CODE
	 * @return the topFleetCode
	 */
	public String getTopFleetCode() {
		return topFleetCode;
	}

	/**
	 * 顶级车队CODE
	 * @param topFleetCode the topFleetCode to set
	 */
	public void setTopFleetCode(String topFleetCode) {
		this.topFleetCode = topFleetCode;
	}

	/**
	 * 顶级车队NAME
	 * @return the topFleetName
	 */
	public String getTopFleetName() {
		return topFleetName;
	}

	/**
	 * 顶级车队NAME
	 * @param topFleetName the topFleetName to set
	 */
	public void setTopFleetName(String topFleetName) {
		this.topFleetName = topFleetName;
	}

	/**
	 * @return the vehicleassembleNo
	 */
	public String getVehicleassembleNo() {
		return vehicleassembleNo;
	}

	/**
	 * @param vehicleassembleNo the vehicleassembleNo to set
	 */
	public void setVehicleassembleNo(String vehicleassembleNo) {
		this.vehicleassembleNo = vehicleassembleNo;
	}

	/**
	 * 询价编号
	 * @return the inquiryNo
	 */
	public String getInquiryNo() {
		return inquiryNo;
	}

	/**
	 * 询价编号
	 * @param inquiryNo the inquiryNo to set
	 */
	public void setInquiryNo(String inquiryNo) {
		this.inquiryNo = inquiryNo;
	}
	
	/**
	 * @return the vehicleCost
	 */
	public String getVehicleCost() {
		return vehicleCost;
	}

	/**
	 * @param vehicleCost the vehicleCost to set
	 */
	public void setVehicleCost(String vehicleCost) {
		this.vehicleCost = vehicleCost;
	}
}