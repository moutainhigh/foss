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
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/dto/WayBillRefershDTO.java
 *  
 *  FILE NAME          :WayBillRefershDTO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.shared.dto;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
// TODO: Auto-generated Javadoc
/**
 * 更新运单时查询出需要调用接口的值.
 *
 * @author IBM-liubinbin
 * @date 2012-11-2 上午9:59:45
 */
public class WayBillRefershDTO extends BaseEntity{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3703272448562684594L;

	/** ********运单号***********. */
	private String waybillNo;
	
	/** ********流水号***********. */
	private String serialNo;
	
	/** ********当前外场code***********. */
	private String origOrgCode;
	
	/** ********到达外场code***********. */
	private String destOrgCode;
	
	/** ********出发时间***********. */
	private Date actualDepartTime;
	
	/** ********是否整車***********. */
	private String beCarLoad;
	
	/** ********交接单***********. */
	private String billNo; 
	
	public Date getActualArriveTime() {
		return actualArriveTime;
	}

	public void setActualArriveTime(Date actualArriveTime) {
		this.actualArriveTime = actualArriveTime;
	}

	private Date actualArriveTime;
	
	/** ********车牌号***********. */
	private String vehicleNo;
	
	/** ********传入的类型***********. */
	private String updateType;
	
	/** ********绑定类型***********. */
	private String bundType;
	
	/** ********任务车辆明细ID***********. */
	private String truckTaskDetailId;
	
	/** ********是否已经扫描***********. */
	private String beScanInstock;
	/** ********货物状态***********. */
	private String goodsState;
	
	/**
	 * 获取 ********绑定类型***********.
	 *
	 * @return the ********绑定类型***********
	 */
	public String getBundType()
	{
		return bundType;
	}

	/**
	 * 设置 ********绑定类型***********.
	 *
	 * @param bundType the new ********绑定类型***********
	 */
	public void setBundType(String bundType)
	{
		this.bundType = bundType;
	}

	/**
	 * 获取 ********运单号***********.
	 *
	 * @return the ********运单号***********
	 */
	public String getWaybillNo()
	{
		return waybillNo;
	}

	/**
	 * 设置 ********运单号***********.
	 *
	 * @param waybillNo the new ********运单号***********
	 */
	public void setWaybillNo(String waybillNo)
	{
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 ********流水号***********.
	 *
	 * @return the ********流水号***********
	 */
	public String getSerialNo()
	{
		return serialNo;
	}

	/**
	 * 设置 ********流水号***********.
	 *
	 * @param serialNo the new ********流水号***********
	 */
	public void setSerialNo(String serialNo)
	{
		this.serialNo = serialNo;
	}

	/**
	 * 获取 ********当前外场code***********.
	 *
	 * @return the ********当前外场code***********
	 */
	public String getOrigOrgCode()
	{
		return origOrgCode;
	}

	/**
	 * 设置 ********当前外场code***********.
	 *
	 * @param origOrgCode the new ********当前外场code***********
	 */
	public void setOrigOrgCode(String origOrgCode)
	{
		this.origOrgCode = origOrgCode;
	}

	

	

	public Date getActualDepartTime() {
		return actualDepartTime;
	}

	public void setActualDepartTime(Date actualDepartTime) {
		this.actualDepartTime = actualDepartTime;
	}

	/**
	 * 获取 ********车牌号***********.
	 *
	 * @return the ********车牌号***********
	 */
	public String getVehicleNo()
	{
		return vehicleNo;
	}

	/**
	 * 设置 ********车牌号***********.
	 *
	 * @param vehicleNo the new ********车牌号***********
	 */
	public void setVehicleNo(String vehicleNo)
	{
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 ********传入的类型***********.
	 *
	 * @return the ********传入的类型***********
	 */
	public String getUpdateType()
	{
		return updateType;
	}

	/**
	 * 设置 ********传入的类型***********.
	 *
	 * @param updateType the new ********传入的类型***********
	 */
	public void setUpdateType(String updateType)
	{
		this.updateType = updateType;
	}

	/**
	 * 获取 ********任务车辆明细ID***********.
	 *
	 * @return the ********任务车辆明细ID***********
	 */
	public String getTruckTaskDetailId()
	{
		return truckTaskDetailId;
	}

	/**
	 * 设置 ********任务车辆明细ID***********.
	 *
	 * @param truckTaskDetailId the new ********任务车辆明细ID***********
	 */
	public void setTruckTaskDetailId(String truckTaskDetailId)
	{
		this.truckTaskDetailId = truckTaskDetailId;
	}

	/**
	 * Gets the dest org code.
	 *
	 * @return the dest org code
	 */
	public String getDestOrgCode()
	{
		return destOrgCode;
	}

	/**
	 * Sets the dest org code.
	 *
	 * @param destOrgCode the new dest org code
	 */
	public void setDestOrgCode(String destOrgCode)
	{
		this.destOrgCode = destOrgCode;
	}

	/**
	 * Gets the be scan instock.
	 *
	 * @return the be scan instock
	 */
	public String getBeScanInstock() {
		return beScanInstock;
	}

	/**
	 * Sets the be scan instock.
	 *
	 * @param beScanInstock the new be scan instock
	 */
	public void setBeScanInstock(String beScanInstock) {
		this.beScanInstock = beScanInstock;
	}

	/**
	 * Gets the goods state.
	 *
	 * @return the goods state
	 */
	public String getGoodsState()
	{
		return goodsState;
	}

	/**
	 * Sets the goods state.
	 *
	 * @param goodsState the new goods state
	 */
	public void setGoodsState(String goodsState)
	{
		this.goodsState = goodsState;
	}

	public String getBeCarLoad() {
		return beCarLoad;
	}

	public void setBeCarLoad(String beCarLoad) {
		this.beCarLoad = beCarLoad;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	
}