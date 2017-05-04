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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/WayBillNoLocusDTO.java
 * 
 * FILE NAME        	: WayBillNoLocusDTO.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * 
 * 运单执行轨迹
 * 
 * @author IBM-liubinbin
 * @date 2012-11-5 下午4:11:08
 */
public class WayBillNoLocusDTO implements Serializable,Comparator
{

	private static final long serialVersionUID = -3703272448562684594L;

	/************* 运单号****************/
	private String waybillNo;
	/************* 操作部门名称 ****************/
	private String operateOrgName;
	/************* 操作部门标杆编码 ****************/
	private String unifiedCode;
	/************* 操作部门编码 ****************/
	private String operateOrgCode;
	/************* 操作部门所在城市编码 ****************/
	private String operateCityCode;
	/************* 操作部门所在城市名称 ****************/
	private String operateCityName;
	/************* 操作类型****************/
	private String operateType;
	/************* 操作类型名称****************/
	private String operateTypeName;
	/************* 操作内容 ****************/
	private String operateContent;
	/************* 操作时间 ****************/
	private Date operateTime;
	/************* 操作人姓名 ****************/
	private String operateName;
	/************* 操作件数 ****************/
	private Integer operateNumber;
	/************* 单据编号 ****************/
	private String billNo;
	/************* 车牌号 ****************/
	private String vehicleNo;
	/************* 备注 ****************/
	private String notes;
	/************* 流水号 ****************/
	private String serialNo;
	/************* 下一站部门编码 ****************/
	private String nextOrgCode;
	/************* 下一站部门名称 ****************/
	private String nextOrgName;
	/************* 下一站所在城市编码 ****************/
	private String nextCityCode;
	/************* 下一站所在城市名称 ****************/
	private String nextCityName;
	/************* 目的站部门编码 ****************/
	private String destinationStationOrgCode;
	/************* 目的站部门名称 ****************/
	private String destinationStationOrgName;
	/************* 目的站部门所在城市编码****************/
	private String destinationStationCityCode;
	/************* 目的站部门所在城市名称 ****************/
	private String destinationStationCityName;
	/************* 离开后预计到达下一操作部门时间 ****************/
	private Date planArriveTime;
	/************* 派送人员姓名****************/
	private String deliveryName;
	/************* 派送人员电话 ****************/
	private String deliveryPhone;
	/************* 签收人姓名 ****************/
	private String signManName;
	/************* 货物状态 ****************/
	private String currentStatus;
	/************* 第一次签收时间 ****************/
	private Date firstSignTime;
	/**********设置预计到达时间 200968 2016-02-20 *****/
	private Date preArriveTime;
	public Date getPreArriveTime() {
		return preArriveTime;
	}
	public void setPreArriveTime(Date preArriveTime) {
		this.preArriveTime = preArriveTime;
	}
	/************** 定位编号 zwd 200968 20141221*********************/
	private String stockPositionNumber; 
	/************** 定位编号 zwd 200968 20141221*********************/
	public String getStockPositionNumber() {
		return stockPositionNumber;
	}
	/************** 定位编号 zwd 200968 20141221*********************/
	public void setStockPositionNumber(String stockPositionNumber) {
		this.stockPositionNumber = stockPositionNumber;
	}
	@Override
	public int compare(Object o1, Object o2) {
		if(o1==null)
			return -1;
		if(o2==null)
			return 1;
		WayBillNoLocusDTO d1 = (WayBillNoLocusDTO)o1;
		WayBillNoLocusDTO d2 = (WayBillNoLocusDTO)o2;
		if(d1.getOperateTime()==null)
			return -1;
		if(d2.getOperateTime()==null)
			return 1;
		if (d1.getOperateTime().before(d2.getOperateTime()))
			return -1;
		if (d2.getOperateTime().before(d1.getOperateTime()))
			return 1;
		return 0;
	}
	/**
	 * 获取 *********** 运单号***************.
	 *
	 * @return the *********** 运单号***************
	 */
	public String getWaybillNo()
	{
		return waybillNo;
	}
	
	/**
	 * 设置 *********** 运单号***************.
	 *
	 * @param waybillNo the new *********** 运单号***************
	 */
	public void setWaybillNo(String waybillNo)
	{
		this.waybillNo = waybillNo;
	}
	
	/**
	 * 获取 *********** 操作部门名称 ***************.
	 *
	 * @return the *********** 操作部门名称 ***************
	 */
	public String getOperateOrgName()
	{
		return operateOrgName;
	}
	
	/**
	 * 设置 *********** 操作部门名称 ***************.
	 *
	 * @param operateOrgName the new *********** 操作部门名称 ***************
	 */
	public void setOperateOrgName(String operateOrgName)
	{
		this.operateOrgName = operateOrgName;
	}
	
	
	
	/**
	 * 获取 *********** 操作部门所在城市编码 ***************.
	 *
	 * @return the *********** 操作部门所在城市编码 ***************
	 */
	public String getOperateCityCode()
	{
		return operateCityCode;
	}
	
	/**
	 * 设置 *********** 操作部门所在城市编码 ***************.
	 *
	 * @param operateCityCode the new *********** 操作部门所在城市编码 ***************
	 */
	public void setOperateCityCode(String operateCityCode)
	{
		this.operateCityCode = operateCityCode;
	}
	

	
	/**
	 * 获取 *********** 操作内容 ***************.
	 *
	 * @return the *********** 操作内容 ***************
	 */
	public String getOperateContent()
	{
		return operateContent;
	}
	
	/**
	 * 设置 *********** 操作内容 ***************.
	 *
	 * @param operateContent the new *********** 操作内容 ***************
	 */
	public void setOperateContent(String operateContent)
	{
		this.operateContent = operateContent;
	}
	
	/**
	 * 获取 *********** 操作时间 ***************.
	 *
	 * @return the *********** 操作时间 ***************
	 */
	public Date getOperateTime()
	{
		return operateTime;
	}
	
	/**
	 * 设置 *********** 操作时间 ***************.
	 *
	 * @param operateTime the new *********** 操作时间 ***************
	 */
	public void setOperateTime(Date operateTime)
	{
		this.operateTime = operateTime;
	}
	
	
	
	/**
	 * 获取 *********** 操作件数 ***************.
	 *
	 * @return the *********** 操作件数 ***************
	 */
	public Integer getOperateNumber()
	{
		return operateNumber;
	}
	
	/**
	 * 设置 *********** 操作件数 ***************.
	 *
	 * @param operateNumber the new *********** 操作件数 ***************
	 */
	public void setOperateNumber(Integer operateNumber)
	{
		this.operateNumber = operateNumber;
	}
	
	
	
	/**
	 * 获取 *********** 车牌号 ***************.
	 *
	 * @return the *********** 车牌号 ***************
	 */
	public String getVehicleNo()
	{
		return vehicleNo;
	}
	
	/**
	 * 设置 *********** 车牌号 ***************.
	 *
	 * @param vehicleNo the new *********** 车牌号 ***************
	 */
	public void setVehicleNo(String vehicleNo)
	{
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * 获取 *********** 备注 ***************.
	 *
	 * @return the *********** 备注 ***************
	 */
	public String getNotes()
	{
		return notes;
	}
	
	/**
	 * 设置 *********** 备注 ***************.
	 *
	 * @param notes the new *********** 备注 ***************
	 */
	public void setNotes(String notes)
	{
		this.notes = notes;
	}
	
	
	/**
	 * 获取 *********** 下一站部门编码 ***************.
	 *
	 * @return the *********** 下一站部门编码 ***************
	 */
	public String getNextOrgCode()
	{
		return nextOrgCode;
	}
	
	/**
	 * 设置 *********** 下一站部门编码 ***************.
	 *
	 * @param nextOrgCode the new *********** 下一站部门编码 ***************
	 */
	public void setNextOrgCode(String nextOrgCode)
	{
		this.nextOrgCode = nextOrgCode;
	}
	
	/**
	 * 获取 *********** 下一站部门名称 ***************.
	 *
	 * @return the *********** 下一站部门名称 ***************
	 */
	public String getNextOrgName()
	{
		return nextOrgName;
	}
	
	/**
	 * 设置 *********** 下一站部门名称 ***************.
	 *
	 * @param nextOrgName the new *********** 下一站部门名称 ***************
	 */
	public void setNextOrgName(String nextOrgName)
	{
		this.nextOrgName = nextOrgName;
	}
	
	/**
	 * 获取 *********** 下一站所在城市编码 ***************.
	 *
	 * @return the *********** 下一站所在城市编码 ***************
	 */
	public String getNextCityCode()
	{
		return nextCityCode;
	}
	
	/**
	 * 设置 *********** 下一站所在城市编码 ***************.
	 *
	 * @param nextCityCode the new *********** 下一站所在城市编码 ***************
	 */
	public void setNextCityCode(String nextCityCode)
	{
		this.nextCityCode = nextCityCode;
	}
	
	/**
	 * 获取 *********** 下一站所在城市名称 ***************.
	 *
	 * @return the *********** 下一站所在城市名称 ***************
	 */
	public String getNextCityName()
	{
		return nextCityName;
	}
	
	/**
	 * 设置 *********** 下一站所在城市名称 ***************.
	 *
	 * @param nextCityName the new *********** 下一站所在城市名称 ***************
	 */
	public void setNextCityName(String nextCityName)
	{
		this.nextCityName = nextCityName;
	}
	
	/**
	 * 获取 *********** 目的站部门编码 ***************.
	 *
	 * @return the *********** 目的站部门编码 ***************
	 */
	public String getDestinationStationOrgCode()
	{
		return destinationStationOrgCode;
	}
	
	/**
	 * 设置 *********** 目的站部门编码 ***************.
	 *
	 * @param destinationStationOrgCode the new *********** 目的站部门编码 ***************
	 */
	public void setDestinationStationOrgCode(String destinationStationOrgCode)
	{
		this.destinationStationOrgCode = destinationStationOrgCode;
	}
	
	/**
	 * 获取 *********** 目的站部门名称 ***************.
	 *
	 * @return the *********** 目的站部门名称 ***************
	 */
	public String getDestinationStationOrgName()
	{
		return destinationStationOrgName;
	}
	
	/**
	 * 设置 *********** 目的站部门名称 ***************.
	 *
	 * @param destinationStationOrgName the new *********** 目的站部门名称 ***************
	 */
	public void setDestinationStationOrgName(String destinationStationOrgName)
	{
		this.destinationStationOrgName = destinationStationOrgName;
	}
	
	/**
	 * 获取 *********** 目的站部门所在城市编码***************.
	 *
	 * @return the *********** 目的站部门所在城市编码***************
	 */
	public String getDestinationStationCityCode()
	{
		return destinationStationCityCode;
	}
	
	/**
	 * 设置 *********** 目的站部门所在城市编码***************.
	 *
	 * @param destinationStationCityCode the new *********** 目的站部门所在城市编码***************
	 */
	public void setDestinationStationCityCode(String destinationStationCityCode)
	{
		this.destinationStationCityCode = destinationStationCityCode;
	}
	
	/**
	 * 获取 *********** 目的站部门所在城市名称 ***************.
	 *
	 * @return the *********** 目的站部门所在城市名称 ***************
	 */
	public String getDestinationStationCityName()
	{
		return destinationStationCityName;
	}
	
	/**
	 * 设置 *********** 目的站部门所在城市名称 ***************.
	 *
	 * @param destinationStationCityName the new *********** 目的站部门所在城市名称 ***************
	 */
	public void setDestinationStationCityName(String destinationStationCityName)
	{
		this.destinationStationCityName = destinationStationCityName;
	}
	
	
	
	/**
	 * 获取 *********** 派送人员姓名***************.
	 *
	 * @return the *********** 派送人员姓名***************
	 */
	public String getDeliveryName()
	{
		return deliveryName;
	}
	
	/**
	 * 设置 *********** 派送人员姓名***************.
	 *
	 * @param deliveryName the new *********** 派送人员姓名***************
	 */
	public void setDeliveryName(String deliveryName)
	{
		this.deliveryName = deliveryName;
	}
	
	/**
	 * 获取 *********** 派送人员电话 ***************.
	 *
	 * @return the *********** 派送人员电话 ***************
	 */
	public String getDeliveryPhone()
	{
		return deliveryPhone;
	}
	
	/**
	 * 设置 *********** 派送人员电话 ***************.
	 *
	 * @param deliveryPhone the new *********** 派送人员电话 ***************
	 */
	public void setDeliveryPhone(String deliveryPhone)
	{
		this.deliveryPhone = deliveryPhone;
	}
	
	/**
	 * 获取 *********** 签收人姓名 ***************.
	 *
	 * @return the *********** 签收人姓名 ***************
	 */
	public String getSignManName()
	{
		return signManName;
	}
	
	/**
	 * 设置 *********** 签收人姓名 ***************.
	 *
	 * @param signManName the new *********** 签收人姓名 ***************
	 */
	public void setSignManName(String signManName)
	{
		this.signManName = signManName;
	}

	/**
	 * 获取 *********** 操作部门编码 ***************.
	 *
	 * @return the *********** 操作部门编码 ***************
	 */
	public String getOperateOrgCode()
	{
		return operateOrgCode;
	}

	/**
	 * 设置 *********** 操作部门编码 ***************.
	 *
	 * @param operateOrgCode the new *********** 操作部门编码 ***************
	 */
	public void setOperateOrgCode(String operateOrgCode)
	{
		this.operateOrgCode = operateOrgCode;
	}

	/**
	 * 获取 *********** 操作部门所在城市名称 ***************.
	 *
	 * @return the *********** 操作部门所在城市名称 ***************
	 */
	public String getOperateCityName()
	{
		return operateCityName;
	}

	/**
	 * 设置 *********** 操作部门所在城市名称 ***************.
	 *
	 * @param operateCityName the new *********** 操作部门所在城市名称 ***************
	 */
	public void setOperateCityName(String operateCityName)
	{
		this.operateCityName = operateCityName;
	}

	/**
	 * 获取 *********** 操作类型***************.
	 *
	 * @return the *********** 操作类型***************
	 */
	public String getOperateType()
	{
		return operateType;
	}

	/**
	 * 设置 *********** 操作类型***************.
	 *
	 * @param operateType the new *********** 操作类型***************
	 */
	public void setOperateType(String operateType)
	{
		this.operateType = operateType;
	}

	/**
	 * 获取 *********** 操作人姓名 ***************.
	 *
	 * @return the *********** 操作人姓名 ***************
	 */
	public String getOperateName()
	{
		return operateName;
	}

	/**
	 * 设置 *********** 操作人姓名 ***************.
	 *
	 * @param operateName the new *********** 操作人姓名 ***************
	 */
	public void setOperateName(String operateName)
	{
		this.operateName = operateName;
	}

	/**
	 * 获取 *********** 单据编号 ***************.
	 *
	 * @return the *********** 单据编号 ***************
	 */
	public String getBillNo()
	{
		return billNo;
	}

	/**
	 * 设置 *********** 单据编号 ***************.
	 *
	 * @param billNo the new *********** 单据编号 ***************
	 */
	public void setBillNo(String billNo)
	{
		this.billNo = billNo;
	}

	/**
	 * 获取 *********** 离开后预计到达下一操作部门时间 ***************.
	 *
	 * @return the *********** 离开后预计到达下一操作部门时间 ***************
	 */
	public Date getPlanArriveTime()
	{
		return planArriveTime;
	}

	/**
	 * 设置 *********** 离开后预计到达下一操作部门时间 ***************.
	 *
	 * @param planArriveTime the new *********** 离开后预计到达下一操作部门时间 ***************
	 */
	public void setPlanArriveTime(Date planArriveTime)
	{
		this.planArriveTime = planArriveTime;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getUnifiedCode() {
		return unifiedCode;
	}

	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}

	public String getOperateTypeName()
	{
		return operateTypeName;
	}

	public void setOperateTypeName(String operateTypeName)
	{
		this.operateTypeName = operateTypeName;
	}

	public String getCurrentStatus()
	{
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus)
	{
		this.currentStatus = currentStatus;
	}
	public Date getFirstSignTime() {
		return firstSignTime;
	}
	public void setFirstSignTime(Date firstSignTime) {
		this.firstSignTime = firstSignTime;
	}

}
