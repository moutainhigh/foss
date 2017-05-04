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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/LdpHandOverDto.java
 *  
 *  FILE NAME          :LdpHandOverDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/** 
 * @author: ibm-liuzhaowei
 * @description: 交接单实体（落地配公司查询落地配交接单时使用）
 * @date: 2013-08-05 下午03:39:39
 * 
 */
public class LdpHandOverDto implements Serializable {
	 
	private static final long serialVersionUID = 5317125647528567319L;
	/**
	 * 交接单号
	 * 
	 */
	private String handoverNo;
	/**
	 * 德邦部门编码
	 * 
	 */
	private String dpOrgCode;
	/**
	 * 德帮部门名称
	 * 
	 */
	private String dpOrgName;
	/**
	 * 代理公司编码
	 * 
	 */
	private String agentCompanyCode;
	/**
	 * 代理公司名称
	 * 
	 */
	private String agentCompanyName;
	/**
	 * 司机编号
	 * 
	 */
	private String driverCode;
	/**
	 * 司机姓名
	 * 
	 */
	private String driverName;
	/**
	 * 司机手机号
	 * 
	 */
	private String driverPhoneNo;
	/**
	 * 车牌号
	 * 
	 */
	private String vehicleNo;
	/**
	 * 交接单操作用户编码
	 * 
	 */
	private String userCode;
	/**
	 * 交接单操作用户姓名
	 * 
	 */
	private String userName;
	/**
	 * 装车完成时间(yyyy-mm-dd HH24:mm:ss)
	 * 
	 */
	private Date loadFinishTime;
	/**
	 * 总件数
	 * 
	 */
	private Integer goodsQTYTotal;
	/**
	 * 总票数
	 * 
	 */
	private Integer waybillQTYTotal;
	/**
	 * 总重量(KG)
	 * 
	 */
	private BigDecimal weightTotal;
	/**
	 * 总体积(m3)
	 * 
	 */
	private BigDecimal volumeTotal;
	/**
	 * 备注
	 * 
	 */
	private String notes;
	/**
	 *修改时间(yyyy-mm-dd HH24:mm:ss)
	 * 
	 */
	private Date modifyTime;
	/**
	 * 交接单明细
	 * 
	 */
	private List<LdpHandOverDetailDto> details;
	
	/**
	 * 交接单号
	 * 
	 */
	public String getHandoverNo() {
		return handoverNo;
	}
	/**
	 * 交接单号
	 * 
	 */
	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}
	/**
	 * 德邦部门编码
	 * 
	 */
	public String getDpOrgCode() {
		return dpOrgCode;
	}
	/**
	 * 德邦部门编码
	 * 
	 */
	public void setDpOrgCode(String dpOrgCode) {
		this.dpOrgCode = dpOrgCode;
	}
	/**
	 * 德帮部门名称
	 * 
	 */
	public String getDpOrgName() {
		return dpOrgName;
	}
	/**
	 * 德帮部门名称
	 * 
	 */
	public void setDpOrgName(String dpOrgName) {
		this.dpOrgName = dpOrgName;
	}
	/**
	 * 代理公司编码
	 * 
	 */
	public String getAgentCompanyCode() {
		return agentCompanyCode;
	}
	/**
	 * 代理公司编码
	 * 
	 */
	public void setAgentCompanyCode(String agentCompanyCode) {
		this.agentCompanyCode = agentCompanyCode;
	}
	/**
	 * 代理公司名称
	 * 
	 */
	public String getAgentCompanyName() {
		return agentCompanyName;
	}
	/**
	 * 代理公司名称
	 * 
	 */
	public void setAgentCompanyName(String agentCompanyName) {
		this.agentCompanyName = agentCompanyName;
	}
	/**
	 * 司机编号
	 * 
	 */
	public String getDriverCode() {
		return driverCode;
	}
	/**
	 * 司机编号
	 * 
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	/**
	 * 司机姓名
	 * 
	 */
	public String getDriverName() {
		return driverName;
	}
	/**
	 * 司机姓名
	 * 
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	/**
	 * 司机手机号
	 * 
	 */
	public String getDriverPhoneNo() {
		return driverPhoneNo;
	}
	/**
	 * 司机手机号
	 * 
	 */
	public void setDriverPhoneNo(String driverPhoneNo) {
		this.driverPhoneNo = driverPhoneNo;
	}
	/**
	 * 车牌号
	 * 
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	/**
	 * 车牌号
	 * 
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	/**
	 * 交接单操作用户编码
	 * 
	 */
	public String getUserCode() {
		return userCode;
	}
	/**
	 * 交接单操作用户编码
	 * 
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	/**
	 * 交接单操作用户姓名
	 * 
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 交接单操作用户姓名
	 * 
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 装车完成时间(yyyy-mm-dd HH24:mm:ss)
	 * 
	 */
	public Date getLoadFinishTime() {
		return loadFinishTime;
	}
	/**
	 * 装车完成时间(yyyy-mm-dd HH24:mm:ss)
	 * 
	 */
	public void setLoadFinishTime(Date loadFinishTime) {
		this.loadFinishTime = loadFinishTime;
	}
	/**
	 * 总件数
	 * 
	 */
	public Integer getGoodsQTYTotal() {
		return goodsQTYTotal;
	}
	/**
	 * 总件数
	 * 
	 */
	public void setGoodsQTYTotal(Integer goodsQTYTotal) {
		this.goodsQTYTotal = goodsQTYTotal;
	}
	/**
	 * 总票数
	 * 
	 */
	public Integer getWaybillQTYTotal() {
		return waybillQTYTotal;
	}
	/**
	 * 总票数
	 * 
	 */
	public void setWaybillQTYTotal(Integer waybillQTYTotal) {
		this.waybillQTYTotal = waybillQTYTotal;
	}
	/**
	 * 总重量(KG)
	 * 
	 */
	public BigDecimal getWeightTotal() {
		return weightTotal;
	}
	/**
	 * 总重量(KG)
	 * 
	 */
	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}
	/**
	 * 总体积(m3)
	 * 
	 */
	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}
	/**
	 * 总体积(m3)
	 * 
	 */
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}
	/**
	 * 备注
	 * 
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * 备注
	 * 
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/**
	 *修改时间(yyyy-mm-dd HH24:mm:ss)
	 * 
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 *修改时间(yyyy-mm-dd HH24:mm:ss)
	 * 
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * 交接单明细
	 * 
	 */
	public List<LdpHandOverDetailDto> getDetails() {
		return details;
	}
	/**
	 * 交接单明细
	 * 
	 */
	public void setDetails(List<LdpHandOverDetailDto> details) {
		this.details = details;
	}
	
	
}
