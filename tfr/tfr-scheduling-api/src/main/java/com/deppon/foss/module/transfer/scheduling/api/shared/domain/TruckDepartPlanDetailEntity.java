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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/TruckDepartPlanDetailEntity.java
 * 
 *  FILE NAME     :TruckDepartPlanDetailEntity.java
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
package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 计划明细实体
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-12-25 下午5:40:34
 */
public class TruckDepartPlanDetailEntity extends BaseEntity {

	private static final long serialVersionUID = 6903462549759183520L;

	/**
	 * ID
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String id;

	/**
	 * 发车计划ID
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String truckDepartPlanId;

	/**
	 * 计划类型
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String planType;

	/**
	 * 车牌号
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String vehicleNo;

	/**
	 * 线路
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String lineNo;
	
	/**
	 * 线路类型
	 */
	private String lineNoExpress;

	/**
	 * 发车日期
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private Date departDate;

	/**
	 * 计划发车时间
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private Date planDepartTime;

	/**
	 * 班次编码
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String frequencyNo;

	/**
	 * 出发部门
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String origOrgCode;

	/**
	 * 到达部门
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String destOrgCode;

	/**
	 * 月台分配ID
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String platformDistributeId;

	/**
	 * 是否正班车
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String isOnScheduling;

	/**
	 * 班次类型
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String frequencyType;

	/**
	 * 车型
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String truckModel;

	/**
	 * 车辆归属类型
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String truckType;

	/**
	 * 车辆报道时间
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private Date truckArriveTime;

	/**
	 * 货柜号
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String containerNo;
	/**
	 * 挂牌号
	 * 
	 */
	private String trailerVehicleNo;
	/**
	 * 最大载重
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private BigDecimal maxLoadWeight;

	/**
	 * 实际最大载重
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private BigDecimal actualMaxLoadWeight;

	/**
	 * 车容积
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private BigDecimal truckVolume;

	/**
	 * 预计装载重量
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private BigDecimal planLoadWeight;

	/**
	 * 预计装载体积
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private BigDecimal planLoadVolume;

	/**
	 * 车辆信息备注
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String truckInfoNotes;

	/**
	 * 司机编号1
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String driverCode1;

	/**
	 * 司机姓名1
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String driverName1;

	/**
	 * 联系方式1
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String driverPhone1;

	/**
	 * 司机编号2
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String driverCode2;

	/**
	 * 司机姓名2
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String driverName2;

	/**
	 * 联系方式2
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String driverPhone2;

	/**
	 * 状态
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String status;

	/**
	 * 创建时间
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private Date createTime;

	/**
	 * 创建人编码
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String createUserCode;

	/**
	 * 创建人姓名
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String createUserName;

	/**
	 * 创建人部门
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String createOrgCode;

	/**
	 * 更新时间
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private Date updateTime;

	/**
	 * 更新用户编码
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String updateUserCode;

	/**
	 * 更新用户名
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String updateUserName;

	/**
	 * 更新用户部门
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String updateOrgCode;

	/**
	 * 线路名称
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private String lineName;

	/**
	 * 计划到达时间
	 * 
	 * @mbggenerated Wed Nov 21 17:08:24 CST 2012
	 */
	private Date planArriveTime;
	/**
	 * 修改记录日志
	 */
	private List<TruckDepartPlanUpdateEntity> logList;
	/**
	 * 计划状态
	 */
	private String planStatus;
	/**
	 * 月台使用时间从
	 */
	private Date platformTimeStart;
	/**
	 * 月台使用时间到
	 */
	private Date platformTimeEnd;
	/**
	 * 车队
	 */
	private String longCarGroup;
	/**
	 * 时效导入
	 */
	private String initFlag;
	/**
	 * 未出发标记
	 */
	private String hasLeft;
	/**
	 * 短途排班ID
	 */
	private String scheduleTaskId;

	/**
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-25 下午5:39:07
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-25 下午5:39:07
	 * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 发车计划ID.
	 * 
	 * @return the 发车计划ID
	 */
	public String getTruckDepartPlanId() {
		return truckDepartPlanId;
	}

	/**
	 * 设置 发车计划ID.
	 * 
	 * @param truckDepartPlanId
	 *            the new 发车计划ID
	 */
	public void setTruckDepartPlanId(String truckDepartPlanId) {
		this.truckDepartPlanId = truckDepartPlanId;
	}

	/**
	 * 获取 计划类型.
	 * 
	 * @return the 计划类型
	 */
	public String getPlanType() {
		return planType;
	}

	/**
	 * 设置 计划类型.
	 * 
	 * @param planType
	 *            the new 计划类型
	 */
	public void setPlanType(String planType) {
		this.planType = planType;
	}

	/**
	 * 获取 车牌号.
	 * 
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置 车牌号.
	 * 
	 * @param vehicleNo
	 *            the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 线路.
	 * 
	 * @return the 线路
	 */
	public String getLineNo() {
		return lineNo;
	}

	/**
	 * 设置 线路.
	 * 
	 * @param lineNo
	 *            the new 线路
	 */
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	/**
	 * 获取 发车日期.
	 * 
	 * @return the 发车日期
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public Date getDepartDate() {
		return departDate;
	}

	/**
	 * 设置 发车日期.
	 * 
	 * @param departDate
	 *            the new 发车日期
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public void setDepartDate(Date departDate) {
		this.departDate = departDate;
	}

	/**
	 * 获取 计划发车时间.
	 * 
	 * @return the 计划发车时间
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public Date getPlanDepartTime() {
		return planDepartTime;
	}

	/**
	 * 设置 计划发车时间.
	 * 
	 * @param planDepartTime
	 *            the new 计划发车时间
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public void setPlanDepartTime(Date planDepartTime) {
		this.planDepartTime = planDepartTime;
	}

	/**
	 * 获取 班次编码.
	 * 
	 * @return the 班次编码
	 */
	public String getFrequencyNo() {
		return frequencyNo;
	}

	/**
	 * 设置 班次编码.
	 * 
	 * @param frequencyNo
	 *            the new 班次编码
	 */
	public void setFrequencyNo(String frequencyNo) {
		this.frequencyNo = frequencyNo;
	}

	/**
	 * 获取 出发部门.
	 * 
	 * @return the 出发部门
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * 设置 出发部门.
	 * 
	 * @param origOrgCode
	 *            the new 出发部门
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * 获取 到达部门.
	 * 
	 * @return the 到达部门
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * 设置 到达部门.
	 * 
	 * @param destOrgCode
	 *            the new 到达部门
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * 获取 月台分配ID.
	 * 
	 * @return the 月台分配ID
	 */
	public String getPlatformDistributeId() {
		return platformDistributeId;
	}

	/**
	 * 设置 月台分配ID.
	 * 
	 * @param platformDistributeId
	 *            the new 月台分配ID
	 */
	public void setPlatformDistributeId(String platformDistributeId) {
		this.platformDistributeId = platformDistributeId;
	}

	/**
	 * 获取 是否正班车.
	 * 
	 * @return the 是否正班车
	 */
	public String getIsOnScheduling() {
		return isOnScheduling;
	}

	/**
	 * 设置 是否正班车.
	 * 
	 * @param isOnScheduling
	 *            the new 是否正班车
	 */
	public void setIsOnScheduling(String isOnScheduling) {
		this.isOnScheduling = isOnScheduling;
	}

	/**
	 * 获取 班次类型.
	 * 
	 * @return the 班次类型
	 */
	public String getFrequencyType() {
		return frequencyType;
	}

	/**
	 * 设置 班次类型.
	 * 
	 * @param frequencyType
	 *            the new 班次类型
	 */
	public void setFrequencyType(String frequencyType) {
		this.frequencyType = frequencyType;
	}

	/**
	 * 获取 车型.
	 * 
	 * @return the 车型
	 */
	public String getTruckModel() {
		return truckModel;
	}

	/**
	 * 设置 车型.
	 * 
	 * @param truckModel
	 *            the new 车型
	 */
	public void setTruckModel(String truckModel) {
		this.truckModel = truckModel;
	}

	/**
	 * 获取 车辆归属类型.
	 * 
	 * @return the 车辆归属类型
	 */
	public String getTruckType() {
		return truckType;
	}

	/**
	 * 设置 车辆归属类型.
	 * 
	 * @param truckType
	 *            the new 车辆归属类型
	 */
	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}

	/**
	 * 获取 车辆报道时间.
	 * 
	 * @return the 车辆报道时间
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public Date getTruckArriveTime() {
		return truckArriveTime;
	}

	/**
	 * 设置 车辆报道时间.
	 * 
	 * @param truckArriveTime
	 *            the new 车辆报道时间
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public void setTruckArriveTime(Date truckArriveTime) {
		this.truckArriveTime = truckArriveTime;
	}

	/**
	 * 获取 货柜号.
	 * 
	 * @return the 货柜号
	 */
	public String getContainerNo() {
		return containerNo;
	}

	/**
	 * 设置 货柜号.
	 * 
	 * @param containerNo
	 *            the new 货柜号
	 */
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	/**
	 * 获取 最大载重.
	 * 
	 * @return the 最大载重
	 */
	public BigDecimal getMaxLoadWeight() {
		return maxLoadWeight;
	}

	/**
	 * 设置 最大载重.
	 * 
	 * @param maxLoadWeight
	 *            the new 最大载重
	 */
	public void setMaxLoadWeight(BigDecimal maxLoadWeight) {
		this.maxLoadWeight = maxLoadWeight;
	}

	/**
	 * 获取 实际最大载重.
	 * 
	 * @return the 实际最大载重
	 */
	public BigDecimal getActualMaxLoadWeight() {
		return actualMaxLoadWeight;
	}

	/**
	 * 设置 实际最大载重.
	 * 
	 * @param actualMaxLoadWeight
	 *            the new 实际最大载重
	 */
	public void setActualMaxLoadWeight(BigDecimal actualMaxLoadWeight) {
		this.actualMaxLoadWeight = actualMaxLoadWeight;
	}

	/**
	 * 获取 车容积.
	 * 
	 * @return the 车容积
	 */
	public BigDecimal getTruckVolume() {
		return truckVolume;
	}

	/**
	 * 设置 车容积.
	 * 
	 * @param truckVolume
	 *            the new 车容积
	 */
	public void setTruckVolume(BigDecimal truckVolume) {
		this.truckVolume = truckVolume;
	}

	/**
	 * 获取 预计装载重量.
	 * 
	 * @return the 预计装载重量
	 */
	public BigDecimal getPlanLoadWeight() {
		return planLoadWeight;
	}

	/**
	 * 设置 预计装载重量.
	 * 
	 * @param planLoadWeight
	 *            the new 预计装载重量
	 */
	public void setPlanLoadWeight(BigDecimal planLoadWeight) {
		this.planLoadWeight = planLoadWeight;
	}

	/**
	 * 获取 预计装载体积.
	 * 
	 * @return the 预计装载体积
	 */
	public BigDecimal getPlanLoadVolume() {
		return planLoadVolume;
	}

	/**
	 * 设置 预计装载体积.
	 * 
	 * @param planLoadVolume
	 *            the new 预计装载体积
	 */
	public void setPlanLoadVolume(BigDecimal planLoadVolume) {
		this.planLoadVolume = planLoadVolume;
	}

	/**
	 * 获取 车辆信息备注.
	 * 
	 * @return the 车辆信息备注
	 */
	public String getTruckInfoNotes() {
		return truckInfoNotes;
	}

	/**
	 * 设置 车辆信息备注.
	 * 
	 * @param truckInfoNotes
	 *            the new 车辆信息备注
	 */
	public void setTruckInfoNotes(String truckInfoNotes) {
		this.truckInfoNotes = truckInfoNotes;
	}

	/**
	 * 获取 司机编号1.
	 * 
	 * @return the 司机编号1
	 */
	public String getDriverCode1() {
		return driverCode1;
	}

	/**
	 * 设置 司机编号1.
	 * 
	 * @param driverCode1
	 *            the new 司机编号1
	 */
	public void setDriverCode1(String driverCode1) {
		this.driverCode1 = driverCode1;
	}

	/**
	 * 获取 司机姓名1.
	 * 
	 * @return the 司机姓名1
	 */
	public String getDriverName1() {
		return driverName1;
	}

	/**
	 * 设置 司机姓名1.
	 * 
	 * @param driverName1
	 *            the new 司机姓名1
	 */
	public void setDriverName1(String driverName1) {
		this.driverName1 = driverName1;
	}

	/**
	 * 获取 联系方式1.
	 * 
	 * @return the 联系方式1
	 */
	public String getDriverPhone1() {
		return driverPhone1;
	}

	/**
	 * 设置 联系方式1.
	 * 
	 * @param driverPhone1
	 *            the new 联系方式1
	 */
	public void setDriverPhone1(String driverPhone1) {
		this.driverPhone1 = driverPhone1;
	}

	/**
	 * 获取 司机编号2.
	 * 
	 * @return the 司机编号2
	 */
	public String getDriverCode2() {
		return driverCode2;
	}

	/**
	 * 设置 司机编号2.
	 * 
	 * @param driverCode2
	 *            the new 司机编号2
	 */
	public void setDriverCode2(String driverCode2) {
		this.driverCode2 = driverCode2;
	}

	/**
	 * 获取 司机姓名2.
	 * 
	 * @return the 司机姓名2
	 */
	public String getDriverName2() {
		return driverName2;
	}

	/**
	 * 设置 司机姓名2.
	 * 
	 * @param driverName2
	 *            the new 司机姓名2
	 */
	public void setDriverName2(String driverName2) {
		this.driverName2 = driverName2;
	}

	/**
	 * 获取 联系方式2.
	 * 
	 * @return the 联系方式2
	 */
	public String getDriverPhone2() {
		return driverPhone2;
	}

	/**
	 * 设置 联系方式2.
	 * 
	 * @param driverPhone2
	 *            the new 联系方式2
	 */
	public void setDriverPhone2(String driverPhone2) {
		this.driverPhone2 = driverPhone2;
	}

	/**
	 * 获取 状态.
	 * 
	 * @return the 状态
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置 状态.
	 * 
	 * @param status
	 *            the new 状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取 创建时间.
	 * 
	 * @return the 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置 创建时间.
	 * 
	 * @param createTime
	 *            the new 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取 创建人编码.
	 * 
	 * @return the 创建人编码
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * 设置 创建人编码.
	 * 
	 * @param createUserCode
	 *            the new 创建人编码
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * 获取 创建人姓名.
	 * 
	 * @return the 创建人姓名
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * 设置 创建人姓名.
	 * 
	 * @param createUserName
	 *            the new 创建人姓名
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * 获取 创建人部门.
	 * 
	 * @return the 创建人部门
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * 设置 创建人部门.
	 * 
	 * @param createOrgCode
	 *            the new 创建人部门
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * 获取 更新时间.
	 * 
	 * @return the 更新时间
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置 更新时间.
	 * 
	 * @param updateTime
	 *            the new 更新时间
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取 更新用户编码.
	 * 
	 * @return the 更新用户编码
	 */
	public String getUpdateUserCode() {
		return updateUserCode;
	}

	/**
	 * 设置 更新用户编码.
	 * 
	 * @param updateUserCode
	 *            the new 更新用户编码
	 */
	public void setUpdateUserCode(String updateUserCode) {
		this.updateUserCode = updateUserCode;
	}

	/**
	 * 获取 更新用户名.
	 * 
	 * @return the 更新用户名
	 */
	public String getUpdateUserName() {
		return updateUserName;
	}

	/**
	 * 设置 更新用户名.
	 * 
	 * @param updateUserName
	 *            the new 更新用户名
	 */
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	/**
	 * 获取 更新用户部门.
	 * 
	 * @return the 更新用户部门
	 */
	public String getUpdateOrgCode() {
		return updateOrgCode;
	}

	/**
	 * 设置 更新用户部门.
	 * 
	 * @param updateOrgCode
	 *            the new 更新用户部门
	 */
	public void setUpdateOrgCode(String updateOrgCode) {
		this.updateOrgCode = updateOrgCode;
	}

	/**
	 * 获取 线路名称.
	 * 
	 * @return the 线路名称
	 */
	public String getLineName() {
		return lineName;
	}

	/**
	 * 设置 线路名称.
	 * 
	 * @param lineName
	 *            the new 线路名称
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	/**
	 * 获取 计划到达时间.
	 * 
	 * @return the 计划到达时间
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public Date getPlanArriveTime() {
		return planArriveTime;
	}

	/**
	 * 设置 计划到达时间.
	 * 
	 * @param planArriveTime
	 *            the new 计划到达时间
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public void setPlanArriveTime(Date planArriveTime) {
		this.planArriveTime = planArriveTime;
	}

	/**
	 * 获取 修改记录日志.
	 * 
	 * @return the 修改记录日志
	 */
	public List<TruckDepartPlanUpdateEntity> getLogList() {
		return logList;
	}

	/**
	 * 设置 修改记录日志.
	 * 
	 * @param logList
	 *            the new 修改记录日志
	 */
	public void setLogList(List<TruckDepartPlanUpdateEntity> logList) {
		this.logList = logList;
	}

	/**
	 * 获取 计划状态.
	 * 
	 * @return the 计划状态
	 */
	public String getPlanStatus() {
		return planStatus;
	}

	/**
	 * 设置 计划状态.
	 * 
	 * @param planStatus
	 *            the new 计划状态
	 */
	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	/**
	 * 获取 月台使用时间从.
	 * 
	 * @return the 月台使用时间从
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public Date getPlatformTimeStart() {
		return platformTimeStart;
	}

	/**
	 * 设置 月台使用时间从.
	 * 
	 * @param platformTimeStart
	 *            the new 月台使用时间从
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public void setPlatformTimeStart(Date platformTimeStart) {
		this.platformTimeStart = platformTimeStart;
	}

	/**
	 * 获取 月台使用时间到.
	 * 
	 * @return the 月台使用时间到
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public Date getPlatformTimeEnd() {
		return platformTimeEnd;
	}

	/**
	 * 设置 月台使用时间到.
	 * 
	 * @param platformTimeEnd
	 *            the new 月台使用时间到
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public void setPlatformTimeEnd(Date platformTimeEnd) {
		this.platformTimeEnd = platformTimeEnd;
	}

	/**
	 * 获取 车队.
	 * 
	 * @return the 车队
	 */
	public String getLongCarGroup() {
		return longCarGroup;
	}

	/**
	 * 设置 车队.
	 * 
	 * @param longCarGroup
	 *            the new 车队
	 */
	public void setLongCarGroup(String longCarGroup) {
		this.longCarGroup = longCarGroup;
	}

	/**
	 * 获取 时效导入.
	 * 
	 * @return the 时效导入
	 */
	public String getInitFlag() {
		return initFlag;
	}

	/**
	 * 设置 时效导入.
	 * 
	 * @param initFlag
	 *            the new 时效导入
	 */
	public void setInitFlag(String initFlag) {
		this.initFlag = initFlag;
	}

	/**
	 * 获取 未出发标记.
	 * 
	 * @return the 未出发标记
	 */
	public String getHasLeft() {
		return hasLeft;
	}

	/**
	 * 设置 未出发标记.
	 * 
	 * @param hasLeft
	 *            the new 未出发标记
	 */
	public void setHasLeft(String hasLeft) {
		this.hasLeft = hasLeft;
	}

	/**
	 * 获取 短途排班ID.
	 * 
	 * @return the 短途排班ID
	 */
	public String getScheduleTaskId() {
		return scheduleTaskId;
	}

	/**
	 * 设置 短途排班ID.
	 * 
	 * @param scheduleTaskId
	 *            the new 短途排班ID
	 */
	public void setScheduleTaskId(String scheduleTaskId) {
		this.scheduleTaskId = scheduleTaskId;
	}

	public String getTrailerVehicleNo() {
		return trailerVehicleNo;
	}

	public void setTrailerVehicleNo(String trailerVehicleNo) {
		this.trailerVehicleNo = trailerVehicleNo;
	}

	public String getLineNoExpress() {
		return lineNoExpress;
	}

	public void setLineNoExpress(String lineNoExpress) {
		this.lineNoExpress = lineNoExpress;
	}
}