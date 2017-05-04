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
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/domain/RegularTruckSignBillEntity.java
 *  
 *  FILE NAME          :RegularTruckSignBillEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 *  专线对发签单Entity
 * 
 * @author dp-liming
 * @date 2012-12-19 上午11:50:47
 */
public class RegularTruckSignBillEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1639270886080311020L;

	/**
	 *  id
	 */
	private String id;

	/**
	 * 签单编号
	 */
	private String signBillNo; 

	/**
	 * 交接单号
	 */
	private String handoverNo; 

	/**
	 * 司机
	 */
	private String driverCode; 

	/**
	 * 司机姓名
	 */
	private String driverName; 

	/**
	 * 目的地
	 */
	private String arrvRegionName;

	/**
	 * 日期
	 */
	private Date signBillDate;

	/**
	 * 用车部门
	 */
	private String useTruckOrgCode;
	
	/**所属车队Code**/
	private String motorcadeCode;
	
	/**所属车队Name**/
	private String motorcadeName;
	
	/**调度签字**/
	private String schedulingSign;
	
	/**
	 * 用车部门名称
	 */
	private String useTruckOrgName;

	/**
	 * 车牌号
	 */
	private String vehicleNo; 

	/**
	 *车型 
	 */
	private String vehicleTypeLength; 

		
	/**
	 * 体积
	 */
	private BigDecimal volume; 

	/**
	 * 重量
	 */
	private BigDecimal weight; 

	/**
	 * 线路
	 */
	private String lineCode; 

	/**
	 * 线路名称
	 */
	private String lineName; 

	/**
	 * 用车费用划分
	 */
	private BigDecimal msldRoyalty; 

	/**
	 * --空车对发单程里程提成
	 * 对发单程线路里程提成
	 */
	private BigDecimal emsldRoyalty; 

	/**
	 * 司机提成总额
	 */
	private BigDecimal driverRoyaltyAmount; 

	/**
	 * 线路里程
	 */
	private BigDecimal lineDistance; 

	/**
	 * 币种
	 */
	private String currencyCode; 

	/**
	 *车型 名称
	 */
	private String vehicleTypeLengthName; 
	
	/**是否空车**/
	private String isEmpty;
	
	/**
	 * 新增部门
	 */
	private String orgCode;
	
	/**
	 * 新增部门
	 */
	private String orgName;
	
	/**
	 * 获取 车型 名称.
	 *
	 * @return the 车型 名称
	 */
	public String getVehicleTypeLengthName() {
		return vehicleTypeLengthName;
	}

	/**
	 * 设置 车型 名称.
	 *
	 * @param vehicleTypeLengthName the new 车型 名称
	 */
	public void setVehicleTypeLengthName(String vehicleTypeLengthName) {
		this.vehicleTypeLengthName = vehicleTypeLengthName;
	}
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * 获取 签单编号.
	 *
	 * @return the 签单编号
	 */
	public String getSignBillNo() {
		return signBillNo;
	}

	/**
	 * 设置 签单编号.
	 *
	 * @param signBillNo the new 签单编号
	 */
	public void setSignBillNo(String signBillNo) {
		this.signBillNo = signBillNo;
	}

	/**
	 * 获取 交接单号.
	 *
	 * @return the 交接单号
	 */
	public String getHandoverNo() {
		return handoverNo;
	}

	/**
	 * 设置 交接单号.
	 *
	 * @param handoverNo the new 交接单号
	 */
	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	/**
	 * 获取 司机.
	 *
	 * @return the 司机
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * 设置 司机.
	 *
	 * @param driverCode the new 司机
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
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
	 * 获取 目的地.
	 *
	 * @return the 目的地
	 */
	public String getArrvRegionName() {
		return arrvRegionName;
	}

	/**
	 * 设置 目的地.
	 *
	 * @param arrvRegionName the new 目的地
	 */
	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}

	/**
	 * 获取 日期.
	 *
	 * @return the 日期
	 */
	public Date getSignBillDate() {
		return signBillDate;
	}

	/**
	 * 设置 日期.
	 *
	 * @param signBillDate the new 日期
	 */
	public void setSignBillDate(Date signBillDate) {
		this.signBillDate = signBillDate;
	}

	/**
	 * 获取 用车部门.
	 *
	 * @return the 用车部门
	 */
	public String getUseTruckOrgCode() {
		return useTruckOrgCode;
	}

	/**
	 * 设置 用车部门.
	 *
	 * @param useTruckOrgCode the new 用车部门
	 */
	public void setUseTruckOrgCode(String useTruckOrgCode) {
		this.useTruckOrgCode = useTruckOrgCode;
	}

	/**
	 * 获取 用车部门名称.
	 *
	 * @return the 用车部门名称
	 */
	public String getUseTruckOrgName() {
		return useTruckOrgName;
	}

	/**
	 * 设置 用车部门名称.
	 *
	 * @param useTruckOrgName the new 用车部门名称
	 */
	public void setUseTruckOrgName(String useTruckOrgName) {
		this.useTruckOrgName = useTruckOrgName;
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
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 车型.
	 *
	 * @return the 车型
	 */
	public String getVehicleTypeLength() {
		return vehicleTypeLength;
	}

	/**
	 * 设置 车型.
	 *
	 * @param vehicleTypeLength the new 车型
	 */
	public void setVehicleTypeLength(String vehicleTypeLength) {
		this.vehicleTypeLength = vehicleTypeLength;
	}

	/**
	 * 获取 体积.
	 *
	 * @return the 体积
	 */
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * 设置 体积.
	 *
	 * @param volume the new 体积
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	/**
	 * 获取 重量.
	 *
	 * @return the 重量
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * 设置 重量.
	 *
	 * @param weight the new 重量
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * 获取 线路.
	 *
	 * @return the 线路
	 */
	public String getLineCode() {
		return lineCode;
	}

	/**
	 * 设置 线路.
	 *
	 * @param lineCode the new 线路
	 */
	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
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
	 * @param lineName the new 线路名称
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	/**
	 * 获取 对发单程线路里程提成.
	 *
	 * @return the 对发单程线路里程提成
	 */
	public BigDecimal getMsldRoyalty() {
		return msldRoyalty;
	}

	/**
	 * 设置 对发单程线路里程提成.
	 *
	 * @param msldRoyalty the new 对发单程线路里程提成
	 */
	public void setMsldRoyalty(BigDecimal msldRoyalty) {
		this.msldRoyalty = msldRoyalty;
	}

	/**
	 * 获取 空车对发单程里程提成.
	 *
	 * @return the 空车对发单程里程提成
	 */
	public BigDecimal getEmsldRoyalty() {
		return emsldRoyalty;
	}

	/**
	 * 设置 空车对发单程里程提成.
	 *
	 * @param emsldRoyalty the new 空车对发单程里程提成
	 */
	public void setEmsldRoyalty(BigDecimal emsldRoyalty) {
		this.emsldRoyalty = emsldRoyalty;
	}

	/**
	 * 获取 司机提成总额.
	 *
	 * @return the 司机提成总额
	 */
	public BigDecimal getDriverRoyaltyAmount() {
		return driverRoyaltyAmount;
	}

	/**
	 * 设置 司机提成总额.
	 *
	 * @param driverRoyaltyAmount the new 司机提成总额
	 */
	public void setDriverRoyaltyAmount(BigDecimal driverRoyaltyAmount) {
		this.driverRoyaltyAmount = driverRoyaltyAmount;
	}

	/**
	 * 获取 线路里程.
	 *
	 * @return the 线路里程
	 */
	public BigDecimal getLineDistance() {
		return lineDistance;
	}

	/**
	 * 设置 线路里程.
	 *
	 * @param lineDistance the new 线路里程
	 */
	public void setLineDistance(BigDecimal lineDistance) {
		this.lineDistance = lineDistance;
	}

	/**
	 * 获取 币种.
	 *
	 * @return the 币种
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 设置 币种.
	 *
	 * @param currencyCode the new 币种
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**   
	 * motorcadeCode   
	 *   
	 * @return  the motorcadeCode   
	 */
	
	public String getMotorcadeCode() {
		return motorcadeCode;
	}

	/**   
	 * @param motorcadeCode the motorcadeCode to set
	 * Date:2013-4-3下午2:20:32
	 */
	public void setMotorcadeCode(String motorcadeCode) {
		this.motorcadeCode = motorcadeCode;
	}

	/**   
	 * motorcadeName   
	 *   
	 * @return  the motorcadeName   
	 */
	
	public String getMotorcadeName() {
		return motorcadeName;
	}

	/**   
	 * @param motorcadeName the motorcadeName to set
	 * Date:2013-4-3下午2:20:32
	 */
	public void setMotorcadeName(String motorcadeName) {
		this.motorcadeName = motorcadeName;
	}

	/**   
	 * schedulingSign   
	 *   
	 * @return  the schedulingSign   
	 */
	
	public String getSchedulingSign() {
		return schedulingSign;
	}

	/**   
	 * @param schedulingSign the schedulingSign to set
	 * Date:2013-4-3下午2:20:32
	 */
	public void setSchedulingSign(String schedulingSign) {
		this.schedulingSign = schedulingSign;
	}

	/**   
	 * isEmpty   
	 *   
	 * @return  the isEmpty   
	 */
	
	public String getIsEmpty() {
		return isEmpty;
	}

	/**   
	 * @param isEmpty the isEmpty to set
	 * Date:2013-4-3下午3:28:29
	 */
	public void setIsEmpty(String isEmpty) {
		this.isEmpty = isEmpty;
	}

	/**   
	 * orgCode   
	 *   
	 * @return  the orgCode   
	 */
	
	public String getOrgCode() {
		return orgCode;
	}

	/**   
	 * @param orgCode the orgCode to set
	 * Date:2013-5-7下午2:33:28
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**   
	 * orgName   
	 *   
	 * @return  the orgName   
	 */
	
	public String getOrgName() {
		return orgName;
	}

	/**   
	 * @param orgName the orgName to set
	 * Date:2013-5-7下午2:33:28
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}