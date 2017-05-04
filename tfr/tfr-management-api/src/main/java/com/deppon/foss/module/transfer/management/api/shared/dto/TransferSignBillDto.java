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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/dto/TransferSignBillDto.java
 *  
 *  FILE NAME          :TransferSignBillDto.java
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

import com.deppon.foss.module.transfer.management.api.shared.domain.TransferSignBillEntity;



/**
 * 转货车签单entity
 * 
 * @author 099197-foss-liming
 * @date 2012-11-29 上午11:49:47
 */
public class TransferSignBillDto extends TransferSignBillEntity implements Serializable {
	private static final long serialVersionUID = 4074093689417496597L;

	/**
	 * 开始 日期
	 */
	private String beginSignBillDate; 

	/**
	 * 开始 日期
	 */
	private String endSignBillDate; 
	
	/**
	 * id 
	 */
	private String id;

    /**
     * 签单编号
     */
    private String signBillNo;

    /**
     * 用车部门
     */
    private String useTruckOrgCode;

    /**
     * 用车部门名称
     */
    private String useTruckOrgName;

    /**
     * 司机
     */
    private String driverCode;

    /**
     * 司机姓名
     */
    private String driverName;

    /**
     * 是否第一个部门转货
     */
    private String isFirstTransfer;

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
	private Integer  driverCount;
	
   /**
    * 总的转货里程
    */
   private BigDecimal transferDistanceTotal;

    /**
     * 总体积
     */
    private BigDecimal volumeTotal;

    /**
     * 总重量
     */
    private BigDecimal weightTotal;

    /**
     * 总的用车时间
     */
    private BigDecimal useTruckDurationTotal;
    
    /**
     * 当前部门的事业部下所有的子部门
     */
    private List<String> orgCodes;

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
	 * 获取 是否第一个部门转货.
	 *
	 * @return the 是否第一个部门转货
	 */
	public String getIsFirstTransfer() {
		return isFirstTransfer;
	}

	/**
	 * 设置 是否第一个部门转货.
	 *
	 * @param isFirstTransfer the new 是否第一个部门转货
	 */
	public void setIsFirstTransfer(String isFirstTransfer) {
		this.isFirstTransfer = isFirstTransfer;
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
	 * 获取 总的转货里程.
	 *
	 * @return the 总的转货里程
	 */
	public BigDecimal getTransferDistanceTotal() {
		return transferDistanceTotal;
	}

	/**
	 * 设置 总的转货里程.
	 *
	 * @param transferDistanceTotal the new 总的转货里程
	 */
	public void setTransferDistanceTotal(BigDecimal transferDistanceTotal) {
		this.transferDistanceTotal = transferDistanceTotal;
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
	 * 获取 总的用车时间.
	 *
	 * @return the 总的用车时间
	 */
	public BigDecimal getUseTruckDurationTotal() {
		return useTruckDurationTotal;
	}

	/**
	 * 设置 总的用车时间.
	 *
	 * @param useTruckDurationTotal the new 总的用车时间
	 */
	public void setUseTruckDurationTotal(BigDecimal useTruckDurationTotal) {
		this.useTruckDurationTotal = useTruckDurationTotal;
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
	 * Date:2013-5-2下午2:53:02
	 */
	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}
    
}