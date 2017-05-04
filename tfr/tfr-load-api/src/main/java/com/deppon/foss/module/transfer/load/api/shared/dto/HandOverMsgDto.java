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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/HandOverMsgDto.java
 *  
 *  FILE NAME          :HandOverMsgDto.java
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
 * FILE    NAME: HandOverMsg.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * 推动到营业部交接单信息
 * @author dp-duyi
 * @date 2012-11-15 下午2:41:13
 */
public class HandOverMsgDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1243502029891451805L;
	/**交接单号*/
	private String handOverNo;
	/**车牌号*/
	private String vehicleNo;
	/**司机*/
	private String driver;
	/**司机电话*/
	private String driverPhone;
	/**出发部门*/
	private String origOrgName;
	/**到达部门*/
	private String destOrigCode;
	/**总票数*/
	private int wayBillQtyTotal;
	/**卡货票数*/
	private int fastWayBillQtyToal;
	/**总重量*/
	private double weigtTotal;
	/**总体积*/
	private double vulomeTotal;
	/**总金额*/
	private double moneyTotal;
	/**出发时间*/
	private Date departTime;
	/**制单人*/
	private String creatorCode;

	/**
	 * Gets the 出发时间.
	 *
	 * @return the 出发时间
	 */
	public Date getDepartTime() {
		return departTime;
	}
	
	/**
	 * Sets the 出发时间.
	 *
	 * @param departTime the new 出发时间
	 */
	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}
	
	/**
	 * Gets the 交接单号.
	 *
	 * @return the 交接单号
	 */
	public String getHandOverNo() {
		return handOverNo;
	}
	
	/**
	 * Sets the 交接单号.
	 *
	 * @param handOverNo the new 交接单号
	 */
	public void setHandOverNo(String handOverNo) {
		this.handOverNo = handOverNo;
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
	 * Gets the 司机.
	 *
	 * @return the 司机
	 */
	public String getDriver() {
		return driver;
	}
	
	/**
	 * Sets the 司机.
	 *
	 * @param driver the new 司机
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	/**
	 * Gets the 司机电话.
	 *
	 * @return the 司机电话
	 */
	public String getDriverPhone() {
		return driverPhone;
	}
	
	/**
	 * Sets the 司机电话.
	 *
	 * @param driverPhone the new 司机电话
	 */
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	
	/**
	 * Gets the 出发部门.
	 *
	 * @return the 出发部门
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}
	
	/**
	 * Sets the 出发部门.
	 *
	 * @param origOrgName the new 出发部门
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}
	
	/**
	 * Gets the 到达部门.
	 *
	 * @return the 到达部门
	 */
	public String getDestOrigCode() {
		return destOrigCode;
	}
	
	/**
	 * Sets the 到达部门.
	 *
	 * @param destOrigCode the new 到达部门
	 */
	public void setDestOrigCode(String destOrigCode) {
		this.destOrigCode = destOrigCode;
	}
	
	/**
	 * Gets the 总票数.
	 *
	 * @return the 总票数
	 */
	public int getWayBillQtyTotal() {
		return wayBillQtyTotal;
	}
	
	/**
	 * Sets the 总票数.
	 *
	 * @param wayBillQtyTotal the new 总票数
	 */
	public void setWayBillQtyTotal(int wayBillQtyTotal) {
		this.wayBillQtyTotal = wayBillQtyTotal;
	}
	
	/**
	 * Gets the 卡货票数.
	 *
	 * @return the 卡货票数
	 */
	public int getFastWayBillQtyToal() {
		return fastWayBillQtyToal;
	}
	
	/**
	 * Sets the 卡货票数.
	 *
	 * @param fastWayBillQtyToal the new 卡货票数
	 */
	public void setFastWayBillQtyToal(int fastWayBillQtyToal) {
		this.fastWayBillQtyToal = fastWayBillQtyToal;
	}
	
	/**
	 * Gets the 总重量.
	 *
	 * @return the 总重量
	 */
	public double getWeigtTotal() {
		return weigtTotal;
	}
	
	/**
	 * Sets the 总重量.
	 *
	 * @param weigtTotal the new 总重量
	 */
	public void setWeigtTotal(double weigtTotal) {
		this.weigtTotal = weigtTotal;
	}
	
	/**
	 * Gets the 总体积.
	 *
	 * @return the 总体积
	 */
	public double getVulomeTotal() {
		return vulomeTotal;
	}
	
	/**
	 * Sets the 总体积.
	 *
	 * @param vulomeTotal the new 总体积
	 */
	public void setVulomeTotal(double vulomeTotal) {
		this.vulomeTotal = vulomeTotal;
	}
	
	/**
	 * Gets the 总金额.
	 *
	 * @return the 总金额
	 */
	public double getMoneyTotal() {
		return moneyTotal;
	}
	
	/**
	 * Sets the 总金额.
	 *
	 * @param moneyTotal the new 总金额
	 */
	public void setMoneyTotal(double moneyTotal) {
		this.moneyTotal = moneyTotal;
	}

	/**
	 * Gets the 制单人.
	 *
	 * @return the 制单人
	 */
	public String getCreatorCode() {
		return creatorCode;
	}

	/**
	 * Sets the 制单人.
	 *
	 * @param creatorCode the new 制单人
	 */
	public void setCreatorCode(String creatorCode) {
		this.creatorCode = creatorCode;
	}
	
}