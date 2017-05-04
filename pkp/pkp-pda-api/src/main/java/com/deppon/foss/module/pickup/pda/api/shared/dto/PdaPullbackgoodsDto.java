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
 * PROJECT NAME	: pkp-pda-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/shared/dto/PdaPullbackgoodsDto.java
 * 
 * FILE NAME        	: PdaPullbackgoodsDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 拉回货物信息dto
 * 
 * @author foss-meiying
 * @date 2012-12-10 上午10:30:17
 * @since
 * @version
 */
public class PdaPullbackgoodsDto implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 到达联编号
	 */
	private String arrivesheetNo;
	/**
	 * 拉回原因
	 */
	private String pullbackReason;
	/**
	 * 司机工号
	 */
	private String driverCode;
	/**
	 * 拉回时间
	 */
	private Date pullbackTime;
	/**
	 * 备注
	 */
	private String signNote;
	/**
	 * 拉回件数
	 */
	private Integer pullbackQty;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 操作部门
	 */
	private String operateOrgCode;
	/**
	 * 再次派送时间
	 */
	private Date nextDeliverTime;
	/**
	 * 快递员电话
	 */
	private String expressEmpTel;
	/**
	 * Gets the 快递员电话.
	 * @return 快递员电话
	 */
	public String getExpressEmpTel() {
		return expressEmpTel;
	}
	/**
	 * Sets the 快递员电话.
	 * @param expressEmpTel 快递员电话
	 */
	public void setExpressEmpTel(String expressEmpTel) {
		this.expressEmpTel = expressEmpTel;
	}
	/**
	 * Gets the 再次派送时间.
	 * @return 再次派送时间
	 */
	public Date getNextDeliverTime() {
		return nextDeliverTime;
	}
	/**
	 * Sets the 再次派送时间.
	 * @param otherDeliverTime 再次派送时间
	 */
	public void setNextDeliverTime(Date nextDeliverTime) {
		this.nextDeliverTime = nextDeliverTime;
	}
	/**
	 * Gets the 操作部门.
	 *
	 * @return the 操作部门
	 */
	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	/**
	 * Sets the 操作部门.
	 *
	 * @param operateOrgCode the new 操作部门
	 */
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	/**
	 * Gets the 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the 到达联编号.
	 *
	 * @return the 到达联编号
	 */
	public String getArrivesheetNo() {
		return arrivesheetNo;
	}

	/**
	 * Sets the 到达联编号.
	 *
	 * @param arrivesheetNo the new 到达联编号
	 */
	public void setArrivesheetNo(String arrivesheetNo) {
		this.arrivesheetNo = arrivesheetNo;
	}

	/**
	 * Gets the 拉回原因.
	 *
	 * @return the 拉回原因
	 */
	public String getPullbackReason() {
		return pullbackReason;
	}

	/**
	 * Sets the 拉回原因.
	 *
	 * @param pullbackReason the new 拉回原因
	 */
	public void setPullbackReason(String pullbackReason) {
		this.pullbackReason = pullbackReason;
	}

	/**
	 * Gets the 司机工号.
	 *
	 * @return the 司机工号
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * Sets the 司机工号.
	 *
	 * @param driverCode the new 司机工号
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * Gets the 拉回时间.
	 *
	 * @return the 拉回时间
	 */
	public Date getPullbackTime() {
		return pullbackTime;
	}

	/**
	 * Sets the 拉回时间.
	 *
	 * @param pullbackTime the new 拉回时间
	 */
	public void setPullbackTime(Date pullbackTime) {
		this.pullbackTime = pullbackTime;
	}

	/**
	 * Gets the 备注.
	 *
	 * @return the 备注
	 */
	public String getSignNote() {
		return signNote;
	}

	/**
	 * Sets the 备注.
	 *
	 * @param signNote the new 备注
	 */
	public void setSignNote(String signNote) {
		this.signNote = signNote;
	}

	/**
	 * Gets the 拉回件数.
	 *
	 * @return the 拉回件数
	 */
	public Integer getPullbackQty() {
		return pullbackQty;
	}

	/**
	 * Sets the 拉回件数.
	 *
	 * @param pullbackQty the new 拉回件数
	 */
	public void setPullbackQty(Integer pullbackQty) {
		this.pullbackQty = pullbackQty;
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

}