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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/dto/OtherSignBillDto.java
 *  
 *  FILE NAME          :OtherSignBillDto.java
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
import java.util.List;


/**
 * 其他签单Entity
 * 
 * @author dp-liming
 * @date 2012-11-29 上午10:50:47
 */
public class OtherSignBillDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1308650775806977750L;

	/**
	 * 开始 日期
	 */
	private String beginSignBillDate; 

	/**
	 * 开始 日期
	 */
	private String endSignBillDate; 

	/**
	 * 用车类型
	 */
	private String useTruckType;

	/**
	 * 签单类型
	 */
	private String signBillType;

	/**
	 * id
	 */
	private String id;

	/**
	 * 签单编号
	 */
	private String signBillNo;

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
	 * 车型
	 */
	private String vehicleTypeLength;

	/**
	 * 司机个数
	 */
	private Integer driverCount; 

	/**
	 * 总线路里程
	 */
	private BigDecimal lineDistanceTotal; 

	/**
	 *  总体积
	 */
	private BigDecimal volumeTotal;

	/**
	 * 总重量
	 */
	private BigDecimal weightTotal; 

	/**
	 * 总票数
	 */
	private Integer billQtyCount; 

	/**
	 *  总的用车时间
	 */
	private BigDecimal useTruckDurationTotal;

	/**
	 *  总其它金额
	 */
	private BigDecimal otherFeeTotal;
	
	/**
	 * 部门
	 */
	private List<String> orgCodes;

	public String getBeginSignBillDate() {
		return beginSignBillDate;
	}

	public void setBeginSignBillDate(String beginSignBillDate) {
		this.beginSignBillDate = beginSignBillDate;
	}

	public String getEndSignBillDate() {
		return endSignBillDate;
	}

	public void setEndSignBillDate(String endSignBillDate) {
		this.endSignBillDate = endSignBillDate;
	}

	public String getUseTruckType() {
		return useTruckType;
	}

	public void setUseTruckType(String useTruckType) {
		this.useTruckType = useTruckType;
	}

	public String getSignBillType() {
		return signBillType;
	}

	public void setSignBillType(String signBillType) {
		this.signBillType = signBillType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSignBillNo() {
		return signBillNo;
	}

	public void setSignBillNo(String signBillNo) {
		this.signBillNo = signBillNo;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getArrvRegionName() {
		return arrvRegionName;
	}

	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}

	public String getUseTruckOrgCode() {
		return useTruckOrgCode;
	}

	public void setUseTruckOrgCode(String useTruckOrgCode) {
		this.useTruckOrgCode = useTruckOrgCode;
	}

	public String getUseTruckOrgName() {
		return useTruckOrgName;
	}

	public void setUseTruckOrgName(String useTruckOrgName) {
		this.useTruckOrgName = useTruckOrgName;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleTypeLength() {
		return vehicleTypeLength;
	}

	public void setVehicleTypeLength(String vehicleTypeLength) {
		this.vehicleTypeLength = vehicleTypeLength;
	}

	public Integer getDriverCount() {
		return driverCount;
	}

	public void setDriverCount(Integer driverCount) {
		this.driverCount = driverCount;
	}

	public BigDecimal getLineDistanceTotal() {
		return lineDistanceTotal;
	}

	public void setLineDistanceTotal(BigDecimal lineDistanceTotal) {
		this.lineDistanceTotal = lineDistanceTotal;
	}

	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}

	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}

	public BigDecimal getWeightTotal() {
		return weightTotal;
	}

	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}

	public Integer getBillQtyCount() {
		return billQtyCount;
	}

	public void setBillQtyCount(Integer billQtyCount) {
		this.billQtyCount = billQtyCount;
	}

	public BigDecimal getUseTruckDurationTotal() {
		return useTruckDurationTotal;
	}

	public void setUseTruckDurationTotal(BigDecimal useTruckDurationTotal) {
		this.useTruckDurationTotal = useTruckDurationTotal;
	}

	public BigDecimal getOtherFeeTotal() {
		return otherFeeTotal;
	}

	public void setOtherFeeTotal(BigDecimal otherFeeTotal) {
		this.otherFeeTotal = otherFeeTotal;
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
	 * Date:2013-5-7下午2:49:17
	 */
	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}
}