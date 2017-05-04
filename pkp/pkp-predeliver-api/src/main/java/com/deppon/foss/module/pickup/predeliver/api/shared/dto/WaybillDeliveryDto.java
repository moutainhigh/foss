/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/WaybillDeliveryDto.java
 * 
 * FILE NAME        	: WaybillDeliveryDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.util.Date;
import java.util.List;

/**
 * 
 * 运单派送信息DTO
 * 
 * @author ibm-wangxiexu
 * @date 2013-1-8 下午8:09:28
 */
public class WaybillDeliveryDto {
	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 操作部门CODE
	 */
	private String operateOrgCode;

	/**
	 * 操作时间
	 */
	private Date operateTime;

	/**
	 * 操作人姓名
	 */
	private String operatorName;
	
	/**
	 * 司机Code
	 */
	private String driverCode;

	/**
	 * 操作件数
	 */
	private Integer operateGoodsQty;

	/**
	 * 派送单编号
	 */
	private String deliverbillNo;

	/**
	 * 车牌号
	 */
	private String vehicleNo;

	/**
	 * 流水号列表
	 */
	private List<String> serialNoList;

	/**
	 * 派送人员电话
	 */
	private String driverName;

	/**
	 * 派送人员手机号
	 */
	private String driverPhone;

	/**
	 * 司机有效状态，查询条件
	 */
	private String driverActive;

	/**
	 * 派送单状态列表，查询条件
	 */
	private List<String> deliverbillStatusList;
	
	private String status;
	
	private String isExpress;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Integer getOperateGoodsQty() {
		return operateGoodsQty;
	}

	public void setOperateGoodsQty(Integer operateGoodsQty) {
		this.operateGoodsQty = operateGoodsQty;
	}

	public String getDeliverbillNo() {
		return deliverbillNo;
	}

	public void setDeliverbillNo(String deliverbillNo) {
		this.deliverbillNo = deliverbillNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public List<String> getSerialNoList() {
		return serialNoList;
	}

	public void setSerialNoList(List<String> serialNoList) {
		this.serialNoList = serialNoList;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getDriverActive() {
		return driverActive;
	}

	public void setDriverActive(String driverActive) {
		this.driverActive = driverActive;
	}

	public List<String> getDeliverbillStatusList() {
		return deliverbillStatusList;
	}

	public void setDeliverbillStatusList(List<String> deliverbillStatusList) {
		this.deliverbillStatusList = deliverbillStatusList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}
}