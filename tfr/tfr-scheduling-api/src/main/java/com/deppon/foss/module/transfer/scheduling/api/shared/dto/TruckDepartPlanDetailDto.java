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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/TruckDepartPlanDetailDto.java
 * 
 *  FILE NAME     :TruckDepartPlanDetailDto.java
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
 * FILE    NAME: TruckDepartPlanDetailDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckDepartPlanDetailEntity;

/**
 * 发车计划明细
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-21 下午5:25:27
 */
public class TruckDepartPlanDetailDto extends TruckDepartPlanDetailEntity implements Cloneable {

	private static final long serialVersionUID = -4204637223189884476L;

	/**
	 * 明细ID列表
	 */
	private List<String> ids;
	/**
	 * 用于底层SQL循环
	 */
	private List<String> list;

	/**
	 * 月台号
	 */
	private String platformNo;
	/**
	 * 出发部门
	 */
	private String origOrgName;
	/**
	 * 到达部门
	 */
	private String destOrgName;
	/**
	 * 车辆所处车队
	 */
	private String carOwnerName;
	/**
	 * 班次名称号
	 */
	private String frequencyNoName;
	/**
	 * 车型
	 */
	private String truckModelValue;
	/**
	 * 用户备注
	 */
	private String remark;
	/**
	 * 车队名称(针对longCarGroup的value值)
	 */
	private String carGroupName;
	/**
	 * 月台号(对应platformNo为月台虚拟编码)
	 */
	private String platformNoCode;
	/**
	 * 原始月台编码 （用于记录原有月台虚拟编码比对日志时使用）
	 */
	private String origPlatformNo;
	/**
	 * 下发
	 */
	private String planStatusRelease;
	/**
	 * 新建
	 */
	private String planStatusNew;
	/**
	 * 长途
	 */
	private String planTypeLong;
	/**
	 * 短途
	 */
	private String planTypeShort;

	/**
	 * 获取 明细ID列表.
	 * 
	 * @return the 明细ID列表
	 */
	public List<String> getIds() {
		return ids;
	}

	/**
	 * 设置 明细ID列表.
	 * 
	 * @param ids
	 *            the new 明细ID列表
	 */
	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	/**
	 * 获取 月台号.
	 * 
	 * @return the 月台号
	 */
	public String getPlatformNo() {
		return platformNo;
	}

	/**
	 * 设置 月台号.
	 * 
	 * @param platformNo
	 *            the new 月台号
	 */
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	/**
	 * 获取 出发部门.
	 * 
	 * @return the 出发部门
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * 设置 出发部门.
	 * 
	 * @param origOrgName
	 *            the new 出发部门
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * 获取 到达部门.
	 * 
	 * @return the 到达部门
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * 设置 到达部门.
	 * 
	 * @param destOrgName
	 *            the new 到达部门
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * 获取 车辆所处车队.
	 * 
	 * @return the 车辆所处车队
	 */
	public String getCarOwnerName() {
		return carOwnerName;
	}

	/**
	 * 设置 车辆所处车队.
	 * 
	 * @param carOwnerName
	 *            the new 车辆所处车队
	 */
	public void setCarOwnerName(String carOwnerName) {
		this.carOwnerName = carOwnerName;
	}

	/**
	 * 获取 班次名称号.
	 * 
	 * @return the 班次名称号
	 */
	public String getFrequencyNoName() {
		return frequencyNoName;
	}

	/**
	 * 设置 班次名称号.
	 * 
	 * @param frequencyNoName
	 *            the new 班次名称号
	 */
	public void setFrequencyNoName(String frequencyNoName) {
		this.frequencyNoName = frequencyNoName;
	}

	/**
	 * 获取 车型.
	 * 
	 * @return the 车型
	 */
	public String getTruckModelValue() {
		return truckModelValue;
	}

	/**
	 * 设置 车型.
	 * 
	 * @param truckModelValue
	 *            the new 车型
	 */
	public void setTruckModelValue(String truckModelValue) {
		this.truckModelValue = truckModelValue;
	}

	/**
	 * 获取 用于底层SQL循环.
	 * 
	 * @return the 用于底层SQL循环
	 */
	public List<String> getList() {
		return list;
	}

	/**
	 * 设置 用于底层SQL循环.
	 * 
	 * @param list
	 *            the new 用于底层SQL循环
	 */
	public void setList(List<String> list) {
		this.list = list;
	}

	/**
	 * 获取 用户备注.
	 * 
	 * @return the 用户备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置 用户备注.
	 * 
	 * @param remark
	 *            the new 用户备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取 车队名称(针对longCarGroup的value值).
	 * 
	 * @return the 车队名称(针对longCarGroup的value值)
	 */
	public String getCarGroupName() {
		return carGroupName;
	}

	/**
	 * 设置 车队名称(针对longCarGroup的value值).
	 * 
	 * @param carGroupName
	 *            the new 车队名称(针对longCarGroup的value值)
	 */
	public void setCarGroupName(String carGroupName) {
		this.carGroupName = carGroupName;
	}

	/**
	 * 获取 月台号(对应platformNo为月台虚拟编码).
	 * 
	 * @return the 月台号(对应platformNo为月台虚拟编码)
	 */
	public String getPlatformNoCode() {
		return platformNoCode;
	}

	/**
	 * 设置 月台号(对应platformNo为月台虚拟编码).
	 * 
	 * @param platformNoCode
	 *            the new 月台号(对应platformNo为月台虚拟编码)
	 */
	public void setPlatformNoCode(String platformNoCode) {
		this.platformNoCode = platformNoCode;
	}

	/**
	 * 获取 原始月台编码 （用于记录原有月台虚拟编码比对日志时使用）.
	 * 
	 * @return the 原始月台编码 （用于记录原有月台虚拟编码比对日志时使用）
	 */
	public String getOrigPlatformNo() {
		return origPlatformNo;
	}

	/**
	 * 设置 原始月台编码
	 * 
	 * @param origPlatformNo
	 *            the new 原始月台编码
	 */
	public void setOrigPlatformNo(String origPlatformNo) {
		this.origPlatformNo = origPlatformNo;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * 获取 下发.
	 *
	 * @return the 下发
	 */
	public String getPlanStatusRelease() {
		return planStatusRelease;
	}

	/**
	 * 设置 下发.
	 *
	 * @param planStatusRelease the new 下发
	 */
	public void setPlanStatusRelease(String planStatusRelease) {
		this.planStatusRelease = planStatusRelease;
	}

	/**
	 * 获取 新建.
	 *
	 * @return the 新建
	 */
	public String getPlanStatusNew() {
		return planStatusNew;
	}

	/**
	 * 设置 新建.
	 *
	 * @param planStatusNew the new 新建
	 */
	public void setPlanStatusNew(String planStatusNew) {
		this.planStatusNew = planStatusNew;
	}

	/**
	 * 获取 长途.
	 *
	 * @return the 长途
	 */
	public String getPlanTypeLong() {
		return planTypeLong;
	}

	/**
	 * 设置 长途.
	 *
	 * @param planTypeLong the new 长途
	 */
	public void setPlanTypeLong(String planTypeLong) {
		this.planTypeLong = planTypeLong;
	}

	/**
	 * 获取 短途.
	 *
	 * @return the 短途
	 */
	public String getPlanTypeShort() {
		return planTypeShort;
	}

	/**
	 * 设置 短途.
	 *
	 * @param planTypeShort the new 短途
	 */
	public void setPlanTypeShort(String planTypeShort) {
		this.planTypeShort = planTypeShort;
	}

}