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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/TruckTaskHandOverDto.java
 *  
 *  FILE NAME          :TruckTaskHandOverDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.dto
 * FILE    NAME: TaskTruckHandOverDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 未生成任务车辆交接单
 * @author dp-duyi
 * @date 2012-11-7 上午11:39:46
 */
public class TruckTaskHandOverDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8433898528946511288L;
	/**交接单号*/
	private String handOverBillNo;
	/**车牌号*/
	private String vehicleNo;
	/**交接类型*/
	private String handOverType;
	/**出发部门*/
	private String origOrgCode;
	/**到达 部门*/
	private String destOrgCode;
	/**出发部门名称*/
	private String origOrgName;
	/**到达部门 名称*/
	private String destOrgName;
	/**发车计划id*/
	private String deptPlanDetailId;
	/**线路名称*/
	private String lineName;
	/**线路虚拟编码*/
	private String lineVirtualCode;
	/**班次*/
	private String frequecyNo;
	/**司机1*/
	private String driverCode1;
	/**司机2*/
	private String driverCode2;
	/**司机名称1*/
	private String driverName1;
	/**司机名称2*/
	private String driverName2;
	/**司机电话1*/
	private String driverPhone1;
	/**司机电话2*/
	private String driverPhone2;
	/**计划出发时间*/
	private Date planDepartTime;
	/**计划到达世间*/
	private Date planArriveTime;
	/**任务车辆id*/
	private String truckTaskId;
	/**任务车辆明细id*/
	private String truckTaskDettailId;
	/**gpsid*/
	private String truckGPSTaskId;
	/**是否整车*/
	private String beCarLoad;
	/**封签id*/
	private String sealId;
	/**出发id*/
	private String truckDepartId;
	/**实际出发世间*/
	private Date actualDepartTime;
	/**理货员名称*/
	private String loaderName;
	/**理货员编号*/
	private String loaderCode;
	/**装车任务编号*/
	private String loadTaskNo;
	/**制单时间*/
	private Date billingTime;
	/**总金额*/
	private double feeTotal;
	/**实际放行类型*/
	private String actualDepartType;
	/**总重量*/
	private float weightTotal;
	/**总体积*/
	private float volumeTotal;
	/**总件数*/
	private int goodsQtyTotal;
	/**总票数*/
	private int wayBillQtyTotal;
	/**是否挂牌号*/
	private String beTrailerVehicleNo;
	/**挂牌号*/
	private String trailerVehicleNo;
	
	
	public String getTrailerVehicleNo() {
		return trailerVehicleNo;
	}

	public void setTrailerVehicleNo(String trailerVehicleNo) {
		this.trailerVehicleNo = trailerVehicleNo;
	}

	public String getBeTrailerVehicleNo() {
		return beTrailerVehicleNo;
	}

	public void setBeTrailerVehicleNo(String beTrailerVehicleNo) {
		this.beTrailerVehicleNo = beTrailerVehicleNo;
	}

	public int getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(int goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public int getWayBillQtyTotal() {
		return wayBillQtyTotal;
	}

	public void setWayBillQtyTotal(int wayBillQtyTotal) {
		this.wayBillQtyTotal = wayBillQtyTotal;
	}

	/**
	 * Gets the 交接单号.
	 *
	 * @return the 交接单号
	 */
	public String getHandOverBillNo() {
		return handOverBillNo;
	}
	
	/**
	 * Sets the 交接单号.
	 *
	 * @param handOverBillNo the new 交接单号
	 */
	public void setHandOverBillNo(String handOverBillNo) {
		this.handOverBillNo = handOverBillNo;
	}
	
	/**
	 * Gets the 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * Sets the 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * Gets the 交接类型.
	 *
	 * @return the 交接类型
	 */
	public String getHandOverType() {
		return handOverType;
	}
	
	/**
	 * Sets the 交接类型.
	 *
	 * @param handOverType the new 交接类型
	 */
	public void setHandOverType(String handOverType) {
		this.handOverType = handOverType;
	}
	
	/**
	 * Gets the 出发部门.
	 *
	 * @return the 出发部门
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	
	/**
	 * Sets the 出发部门.
	 *
	 * @param origOrgCode the new 出发部门
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	
	/**
	 * Gets the 到达 部门.
	 *
	 * @return the 到达 部门
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}
	
	/**
	 * Sets the 到达 部门.
	 *
	 * @param destOrgCode the new 到达 部门
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	
	/**
	 * Gets the 发车计划id.
	 *
	 * @return the 发车计划id
	 */
	public String getDeptPlanDetailId() {
		return deptPlanDetailId;
	}
	
	/**
	 * Sets the 发车计划id.
	 *
	 * @param deptPlanDetailId the new 发车计划id
	 */
	public void setDeptPlanDetailId(String deptPlanDetailId) {
		this.deptPlanDetailId = deptPlanDetailId;
	}
	
	/**
	 * Gets the 线路名称.
	 *
	 * @return the 线路名称
	 */
	public String getLineName() {
		return lineName;
	}
	
	/**
	 * Sets the 线路名称.
	 *
	 * @param lineName the new 线路名称
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	
	/**
	 * Gets the 线路虚拟编码.
	 *
	 * @return the 线路虚拟编码
	 */
	public String getLineVirtualCode() {
		return lineVirtualCode;
	}
	
	/**
	 * Sets the 线路虚拟编码.
	 *
	 * @param lineVirtualCode the new 线路虚拟编码
	 */
	public void setLineVirtualCode(String lineVirtualCode) {
		this.lineVirtualCode = lineVirtualCode;
	}
	
	/**
	 * Gets the 班次.
	 *
	 * @return the 班次
	 */
	public String getFrequecyNo() {
		return frequecyNo;
	}
	
	/**
	 * Sets the 班次.
	 *
	 * @param frequecyNo the new 班次
	 */
	public void setFrequecyNo(String frequecyNo) {
		this.frequecyNo = frequecyNo;
	}
	
	/**
	 * Gets the 计划出发时间.
	 *
	 * @return the 计划出发时间
	 */
	public Date getPlanDepartTime() {
		return planDepartTime;
	}
	
	/**
	 * Sets the 计划出发时间.
	 *
	 * @param planDepartTime the new 计划出发时间
	 */
	public void setPlanDepartTime(Date planDepartTime) {
		this.planDepartTime = planDepartTime;
	}
	
	/**
	 * Gets the 计划到达世间安.
	 *
	 * @return the 计划到达世间安
	 */
	public Date getPlanArriveTime() {
		return planArriveTime;
	}
	
	/**
	 * Sets the 计划到达世间安.
	 *
	 * @param planArriveTime the new 计划到达世间安
	 */
	public void setPlanArriveTime(Date planArriveTime) {
		this.planArriveTime = planArriveTime;
	}
	
	/**
	 * Gets the 任务车辆id.
	 *
	 * @return the 任务车辆id
	 */
	public String getTruckTaskId() {
		return truckTaskId;
	}
	
	/**
	 * Sets the 任务车辆id.
	 *
	 * @param truckTaskId the new 任务车辆id
	 */
	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}
	
	/**
	 * Gets the 任务车辆明细id.
	 *
	 * @return the 任务车辆明细id
	 */
	public String getTruckTaskDettailId() {
		return truckTaskDettailId;
	}
	
	/**
	 * Sets the 任务车辆明细id.
	 *
	 * @param truckTaskDettailId the new 任务车辆明细id
	 */
	public void setTruckTaskDettailId(String truckTaskDettailId) {
		this.truckTaskDettailId = truckTaskDettailId;
	}
	
	/**
	 * Gets the 司机1.
	 *
	 * @return the 司机1
	 */
	public String getDriverCode1() {
		return driverCode1;
	}
	
	/**
	 * Sets the 司机1.
	 *
	 * @param driverCode1 the new 司机1
	 */
	public void setDriverCode1(String driverCode1) {
		this.driverCode1 = driverCode1;
	}
	
	/**
	 * Gets the 司机2.
	 *
	 * @return the 司机2
	 */
	public String getDriverCode2() {
		return driverCode2;
	}
	
	/**
	 * Sets the 司机2.
	 *
	 * @param driverCode2 the new 司机2
	 */
	public void setDriverCode2(String driverCode2) {
		this.driverCode2 = driverCode2;
	}
	
	/**
	 * Gets the 司机名称1.
	 *
	 * @return the 司机名称1
	 */
	public String getDriverName1() {
		return driverName1;
	}
	
	/**
	 * Sets the 司机名称1.
	 *
	 * @param driverName1 the new 司机名称1
	 */
	public void setDriverName1(String driverName1) {
		this.driverName1 = driverName1;
	}
	
	/**
	 * Gets the 司机名称2.
	 *
	 * @return the 司机名称2
	 */
	public String getDriverName2() {
		return driverName2;
	}
	
	/**
	 * Sets the 司机名称2.
	 *
	 * @param driverName2 the new 司机名称2
	 */
	public void setDriverName2(String driverName2) {
		this.driverName2 = driverName2;
	}
	
	/**
	 * Gets the 司机电话1.
	 *
	 * @return the 司机电话1
	 */
	public String getDriverPhone1() {
		return driverPhone1;
	}
	
	/**
	 * Sets the 司机电话1.
	 *
	 * @param driverPhone1 the new 司机电话1
	 */
	public void setDriverPhone1(String driverPhone1) {
		this.driverPhone1 = driverPhone1;
	}
	
	/**
	 * Gets the 司机电话2.
	 *
	 * @return the 司机电话2
	 */
	public String getDriverPhone2() {
		return driverPhone2;
	}
	
	/**
	 * Sets the 司机电话2.
	 *
	 * @param driverPhone2 the new 司机电话2
	 */
	public void setDriverPhone2(String driverPhone2) {
		this.driverPhone2 = driverPhone2;
	}
	
	/**
	 * Gets the 出发部门名称.
	 *
	 * @return the 出发部门名称
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}
	
	/**
	 * Sets the 出发部门名称.
	 *
	 * @param origOrgName the new 出发部门名称
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}
	
	/**
	 * Gets the 到达部门 名称.
	 *
	 * @return the 到达部门 名称
	 */
	public String getDestOrgName() {
		return destOrgName;
	}
	
	/**
	 * Sets the 到达部门 名称.
	 *
	 * @param destOrgName the new 到达部门 名称
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}
	
	/**
	 * Gets the 是否整车.
	 *
	 * @return the 是否整车
	 */
	public String getBeCarLoad() {
		return beCarLoad;
	}
	
	/**
	 * Sets the 是否整车.
	 *
	 * @param beCarLoad the new 是否整车
	 */
	public void setBeCarLoad(String beCarLoad) {
		this.beCarLoad = beCarLoad;
	}
	
	/**
	 * Gets the 封签id.
	 *
	 * @return the 封签id
	 */
	public String getSealId() {
		return sealId;
	}
	
	/**
	 * Sets the 封签id.
	 *
	 * @param sealId the new 封签id
	 */
	public void setSealId(String sealId) {
		this.sealId = sealId;
	}
	
	/**
	 * Gets the 出发id.
	 *
	 * @return the 出发id
	 */
	public String getTruckDepartId() {
		return truckDepartId;
	}
	
	/**
	 * Sets the 出发id.
	 *
	 * @param truckDepartId the new 出发id
	 */
	public void setTruckDepartId(String truckDepartId) {
		this.truckDepartId = truckDepartId;
	}
	
	/**
	 * Gets the 实际出发世间安.
	 *
	 * @return the 实际出发世间安
	 */
	public Date getActualDepartTime() {
		return actualDepartTime;
	}
	
	/**
	 * Sets the 实际出发世间安.
	 *
	 * @param actualDepartTime the new 实际出发世间安
	 */
	public void setActualDepartTime(Date actualDepartTime) {
		this.actualDepartTime = actualDepartTime;
	}
	
	/**
	 * Gets the 理货员名称.
	 *
	 * @return the 理货员名称
	 */
	public String getLoaderName() {
		return loaderName;
	}
	
	/**
	 * Sets the 理货员名称.
	 *
	 * @param loaderName the new 理货员名称
	 */
	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}
	
	/**
	 * Gets the 理货员编号.
	 *
	 * @return the 理货员编号
	 */
	public String getLoaderCode() {
		return loaderCode;
	}
	
	/**
	 * Sets the 理货员编号.
	 *
	 * @param loaderCode the new 理货员编号
	 */
	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}
	
	/**
	 * Gets the 装车任务编号.
	 *
	 * @return the 装车任务编号
	 */
	public String getLoadTaskNo() {
		return loadTaskNo;
	}
	
	/**
	 * Sets the 装车任务编号.
	 *
	 * @param loadTaskNo the new 装车任务编号
	 */
	public void setLoadTaskNo(String loadTaskNo) {
		this.loadTaskNo = loadTaskNo;
	}
	
	/**
	 * Gets the 制单时间.
	 *
	 * @return the 制单时间
	 */
	public Date getBillingTime() {
		return billingTime;
	}
	
	/**
	 * Sets the 制单时间.
	 *
	 * @param billingTime the new 制单时间
	 */
	public void setBillingTime(Date billingTime) {
		this.billingTime = billingTime;
	}
	
	/**
	 * Gets the 总金额.
	 *
	 * @return the 总金额
	 */
	public double getFeeTotal() {
		return feeTotal;
	}
	
	/**
	 * Sets the 总金额.
	 *
	 * @param feeTotal the new 总金额
	 */
	public void setFeeTotal(double feeTotal) {
		this.feeTotal = feeTotal;
	}
	
	/**
	 * Gets the 实际放行类型.
	 *
	 * @return the 实际放行类型
	 */
	public String getActualDepartType() {
		return actualDepartType;
	}
	
	/**
	 * Sets the 实际放行类型.
	 *
	 * @param actualDepartType the new 实际放行类型
	 */
	public void setActualDepartType(String actualDepartType) {
		this.actualDepartType = actualDepartType;
	}
	
	/**
	 * Gets the 总重量.
	 *
	 * @return the 总重量
	 */
	public float getWeightTotal() {
		return weightTotal;
	}
	
	/**
	 * Sets the 总重量.
	 *
	 * @param weightTotal the new 总重量
	 */
	public void setWeightTotal(float weightTotal) {
		this.weightTotal = weightTotal;
	}
	
	/**
	 * Gets the 总体积.
	 *
	 * @return the 总体积
	 */
	public float getVolumeTotal() {
		return volumeTotal;
	}
	
	/**
	 * Sets the 总体积.
	 *
	 * @param volumeTotal the new 总体积
	 */
	public void setVolumeTotal(float volumeTotal) {
		this.volumeTotal = volumeTotal;
	}
	
	/**
	 * Gets the gpsid.
	 *
	 * @return the gpsid
	 */
	public String getTruckGPSTaskId() {
		return truckGPSTaskId;
	}
	
	/**
	 * Sets the gpsid.
	 *
	 * @param truckGPSTaskId the new gpsid
	 */
	public void setTruckGPSTaskId(String truckGPSTaskId) {
		this.truckGPSTaskId = truckGPSTaskId;
	}
}