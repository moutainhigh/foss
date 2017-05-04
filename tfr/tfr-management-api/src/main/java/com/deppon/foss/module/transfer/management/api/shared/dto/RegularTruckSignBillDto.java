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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/dto/RegularTruckSignBillDto.java
 *  
 *  FILE NAME          :RegularTruckSignBillDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 专线对发签单Dto
 * 
 * @author dp-liming
 * @date 2012-12-19 上午10:50:47
 */
public class RegularTruckSignBillDto implements Serializable {
	private static final long serialVersionUID = -9146051761467706766L;
	
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
	 * 对发单程线路里程提成
	 */
	private Long msldRoyalty; 

	/**
	 * 空车对发单程里程提成
	 */
	private Long emsldRoyalty; 

	/**
	 * 司机提成总额
	 */
	private Long driverRoyaltyAmount; 

	/**
	 * 线路里程
	 */
	private BigDecimal lineDistance; 
	
	
	/**
	 * 开始 日期
	 */
	private String beginSignBillDate;// 

	/**
	 * 开始 日期
	 */
	private String endSignBillDate;// 

	/**
	 * 司机个数
	 */
	private Integer driverCount;// 

	/**
	 * 总线路里程
	 */
	private BigDecimal lineDistanceTotal;// 

	/**
	 * 总体积
	 */
	private BigDecimal volumeTotal;// 

	/**
	 * 总重量
	 */
	private BigDecimal weightTotal;
	
	/**用车类型**/
	private String isEmpty;
	
	/**所属车队**/
	private String motorcadeCode;
	
	/**
	 * 部门
	 */
	private List<String> orgCodes;
	

	/**
	 * 获取 id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置 id.
	 *
	 * @param id the new id
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
	public Long getMsldRoyalty() {
		return msldRoyalty;
	}

	/**
	 * 设置 对发单程线路里程提成.
	 *
	 * @param msldRoyalty the new 对发单程线路里程提成
	 */
	public void setMsldRoyalty(Long msldRoyalty) {
		this.msldRoyalty = msldRoyalty;
	}

	/**
	 * 获取 空车对发单程里程提成.
	 *
	 * @return the 空车对发单程里程提成
	 */
	public Long getEmsldRoyalty() {
		return emsldRoyalty;
	}

	/**
	 * 设置 空车对发单程里程提成.
	 *
	 * @param emsldRoyalty the new 空车对发单程里程提成
	 */
	public void setEmsldRoyalty(Long emsldRoyalty) {
		this.emsldRoyalty = emsldRoyalty;
	}

	/**
	 * 获取 司机提成总额.
	 *
	 * @return the 司机提成总额
	 */
	public Long getDriverRoyaltyAmount() {
		return driverRoyaltyAmount;
	}

	/**
	 * 设置 司机提成总额.
	 *
	 * @param driverRoyaltyAmount the new 司机提成总额
	 */
	public void setDriverRoyaltyAmount(Long driverRoyaltyAmount) {
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
	 * 获取 开始 日期.
	 *
	 * @return the 开始 日期
	 */
	public String getBeginSignBillDate() {
		return beginSignBillDate;
	}

	/**
	 * 设置 开始 日期.
	 *
	 * @param beginSignBillDate the new 开始 日期
	 */
	public void setBeginSignBillDate(String beginSignBillDate) {
		this.beginSignBillDate = beginSignBillDate;
	}

	/**
	 * 获取 开始 日期.
	 *
	 * @return the 开始 日期
	 */
	public String getEndSignBillDate() {
		return endSignBillDate;
	}

	/**
	 * 设置 开始 日期.
	 *
	 * @param endSignBillDate the new 开始 日期
	 */
	public void setEndSignBillDate(String endSignBillDate) {
		this.endSignBillDate = endSignBillDate;
	}

	/**
	 * 获取 司机个数.
	 *
	 * @return the 司机个数
	 */
	public Integer getDriverCount() {
		return driverCount;
	}

	/**
	 * 设置 司机个数.
	 *
	 * @param driverCount the new 司机个数
	 */
	public void setDriverCount(Integer driverCount) {
		this.driverCount = driverCount;
	}

	/**
	 * 获取 总线路里程.
	 *
	 * @return the 总线路里程
	 */
	public BigDecimal getLineDistanceTotal() {
		return lineDistanceTotal;
	}

	/**
	 * 设置 总线路里程.
	 *
	 * @param lineDistanceTotal the new 总线路里程
	 */
	public void setLineDistanceTotal(BigDecimal lineDistanceTotal) {
		this.lineDistanceTotal = lineDistanceTotal;
	}

	/**
	 * 获取 总体积.
	 *
	 * @return the 总体积
	 */
	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}

	/**
	 * 设置 总体积.
	 *
	 * @param volumeTotal the new 总体积
	 */
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}

	/**
	 * 获取 总重量.
	 *
	 * @return the 总重量
	 */
	public BigDecimal getWeightTotal() {
		return weightTotal;
	}

	/**
	 * 设置 总重量.
	 *
	 * @param weightTotal the new 总重量
	 */
	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
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
	 * Date:2013-4-3下午4:12:21
	 */
	public void setIsEmpty(String isEmpty) {
		this.isEmpty = isEmpty;
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
	 * Date:2013-4-3下午4:12:21
	 */
	public void setMotorcadeCode(String motorcadeCode) {
		this.motorcadeCode = motorcadeCode;
	}

	/**   
	 * orgCodes   
	 *   
	 * @return  the orgCodes   
	 */
	
	public List<String> getOrgCodes() {
		return orgCodes;
	}

	/**   
	 * @param orgCodes the orgCodes to set
	 * Date:2013-5-7下午3:19:16
	 */
	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}
}