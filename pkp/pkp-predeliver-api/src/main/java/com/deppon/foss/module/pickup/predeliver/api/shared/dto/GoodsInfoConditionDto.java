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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/GoodsInfoConditionDto.java
 * 
 * FILE NAME        	: GoodsInfoConditionDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询货量查询条件dto.
 *
 * @author 043258-foss-zhaobin
 * @date 2012-10-20 下午3:29:56
 */
public class GoodsInfoConditionDto implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** 运单号. */
	private String waybillNo;
	
	/** 收货客户名称. */
	private String receiveCustomerName;
	
	/** 收货客户手机. */
	private String receiveCustomerMobilephone;
	
	/** 收货客户电话. */
	private String receiveCustomerPhone;
	
	/** 提货方式. */
	private String receiveMethod;
	
	/** 运输性质. */
	private String productCode;
	
	/** 定人定区. */
	private String regionVehicleCode;
	
	/** 收货区. */
	private String receiveCustomerCountyCode;
	
	/** 预计到达时间（起）. */
	private Date preArriveTimeBegin;
	
	/** 预计到达时间（止）. */
	private Date preArriveTimeEnd;
	
	/** 在途到达时间（起）. */
	private Date planArriveTimeBegin;
	
	/** 在途到达时间（止）. */
	private Date planArriveTimeEnd;
	
	/** 是否已排单. */
	private String arrangementState;
	
	/** 当前登录部门. */
	private String departmentCode;
	
	/** 总重量. */
	private String goodsWeightTotal;
	
	/** 总体积. */
	private String goodsVolumeTotal;
	
	/** 更改单状态. */
	private String wbrStatus;
	
	/** 运单状态. */
	private String active;
	
	/** 最后库存code. */
	private String endStockOrgCode;	
	
	/** 库区. */
	private String goodsAreaCode;
	
	private String arrivesheetId;
	
	/** 总件数. */
	private String goodsPieceTotal;
	
	/** 总票数. */
	private String goodsWaybillTotal;
	
	/** 总到付金额. */
	private String toPayAmountTotal;

	/**
	 * Gets the waybill no.
	 *
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the waybill no.
	 *
	 * @param waybillNo the waybillNo to see
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the receive customer name.
	 *
	 * @return the receiveCustomerName
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * Sets the receive customer name.
	 *
	 * @param receiveCustomerName the receiveCustomerName to see
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * Gets the receive customer mobilephone.
	 *
	 * @return the receiveCustomerMobilephone
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	/**
	 * Sets the receive customer mobilephone.
	 *
	 * @param receiveCustomerMobilephone the receiveCustomerMobilephone to see
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * Gets the receive customer phone.
	 *
	 * @return the receiveCustomerPhone
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * Sets the receive customer phone.
	 *
	 * @param receiveCustomerPhone the receiveCustomerPhone to see
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * Gets the receive method.
	 *
	 * @return the receiveMethod
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * Sets the receive method.
	 *
	 * @param receiveMethod the receiveMethod to see
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * Gets the product code.
	 *
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the product code.
	 *
	 * @param productCode the productCode to see
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * Gets the region vehicle code.
	 *
	 * @return the regionVehicleCode
	 */
	public String getRegionVehicleCode() {
		return regionVehicleCode;
	}

	/**
	 * Sets the region vehicle code.
	 *
	 * @param regionVehicleCode the regionVehicleCode to see
	 */
	public void setRegionVehicleCode(String regionVehicleCode) {
		this.regionVehicleCode = regionVehicleCode;
	}

	/**
	 * Gets the receive customer county code.
	 *
	 * @return the receiveCustomerCountyCode
	 */
	public String getReceiveCustomerCountyCode() {
		return receiveCustomerCountyCode;
	}

	/**
	 * Sets the receive customer county code.
	 *
	 * @param receiveCustomerCountyCode the receiveCustomerCountyCode to see
	 */
	public void setReceiveCustomerCountyCode(String receiveCustomerCountyCode) {
		this.receiveCustomerCountyCode = receiveCustomerCountyCode;
	}

	/**
	 * Gets the pre arrive time begin.
	 *
	 * @return the preArriveTimeBegin
	 */
	public Date getPreArriveTimeBegin() {
		return preArriveTimeBegin;
	}

	/**
	 * Sets the pre arrive time begin.
	 *
	 * @param preArriveTimeBegin the preArriveTimeBegin to see
	 */
	public void setPreArriveTimeBegin(Date preArriveTimeBegin) {
		this.preArriveTimeBegin = preArriveTimeBegin;
	}

	/**
	 * Gets the pre arrive time end.
	 *
	 * @return the preArriveTimeEnd
	 */
	public Date getPreArriveTimeEnd() {
		return preArriveTimeEnd;
	}

	/**
	 * Sets the pre arrive time end.
	 *
	 * @param preArriveTimeEnd the preArriveTimeEnd to see
	 */
	public void setPreArriveTimeEnd(Date preArriveTimeEnd) {
		this.preArriveTimeEnd = preArriveTimeEnd;
	}

	/**
	 * Gets the arrangement state.
	 *
	 * @return the arrangementState
	 */
	public String getArrangementState() {
		return arrangementState;
	}

	/**
	 * Sets the arrangement state.
	 *
	 * @param arrangementState the arrangementState to see
	 */
	public void setArrangementState(String arrangementState) {
		this.arrangementState = arrangementState;
	}

	/**
	 * Gets the department code.
	 *
	 * @return the departmentCode
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * Sets the department code.
	 *
	 * @param departmentCode the departmentCode to see
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * Gets the plan arrive time begin.
	 *
	 * @return the planArriveTimeBegin
	 */
	public Date getPlanArriveTimeBegin() {
		return planArriveTimeBegin;
	}

	/**
	 * Sets the plan arrive time begin.
	 *
	 * @param planArriveTimeBegin the planArriveTimeBegin to see
	 */
	public void setPlanArriveTimeBegin(Date planArriveTimeBegin) {
		this.planArriveTimeBegin = planArriveTimeBegin;
	}

	/**
	 * Gets the plan arrive time end.
	 *
	 * @return the planArriveTimeEnd
	 */
	public Date getPlanArriveTimeEnd() {
		return planArriveTimeEnd;
	}

	/**
	 * Sets the plan arrive time end.
	 *
	 * @param planArriveTimeEnd the planArriveTimeEnd to see
	 */
	public void setPlanArriveTimeEnd(Date planArriveTimeEnd) {
		this.planArriveTimeEnd = planArriveTimeEnd;
	}

	/**
	 * Gets the goods weight total.
	 *
	 * @return the goodsWeightTotal
	 */
	public String getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	/**
	 * Sets the goods weight total.
	 *
	 * @param goodsWeightTotal the goodsWeightTotal to see
	 */
	public void setGoodsWeightTotal(String goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	/**
	 * Gets the goods volume total.
	 *
	 * @return the goodsVolumeTotal
	 */
	public String getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * Sets the goods volume total.
	 *
	 * @param goodsVolumeTotal the goodsVolumeTotal to see
	 */
	public void setGoodsVolumeTotal(String goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * Gets the wbr status.
	 *
	 * @return the wbrStatus
	 */
	public String getWbrStatus() {
		return wbrStatus;
	}

	/**
	 * Sets the wbr status.
	 *
	 * @param wbrStatus the wbrStatus to see
	 */
	public void setWbrStatus(String wbrStatus) {
		this.wbrStatus = wbrStatus;
	}

	/**
	 * Gets the active.
	 *
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the active to see
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * Gets the end stock org code.
	 *
	 * @return the end stock org code
	 */
	public String getEndStockOrgCode() {
		return endStockOrgCode;
	}

	/**
	 * Sets the end stock org code.
	 *
	 * @param endStockOrgCode the new end stock org code
	 */
	public void setEndStockOrgCode(String endStockOrgCode) {
		this.endStockOrgCode = endStockOrgCode;
	}

	/**
	 * Gets the goods area code.
	 *
	 * @return the goods area code
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	/**
	 * Sets the goods area code.
	 *
	 * @param goodsAreaCode the new goods area code
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	public String getArrivesheetId() {
		return arrivesheetId;
	}

	public void setArrivesheetId(String arrivesheetId) {
		this.arrivesheetId = arrivesheetId;
	}

	public String getGoodsPieceTotal() {
		return goodsPieceTotal;
	}

	public void setGoodsPieceTotal(String goodsPieceTotal) {
		this.goodsPieceTotal = goodsPieceTotal;
	}

	public String getGoodsWaybillTotal() {
		return goodsWaybillTotal;
	}

	public void setGoodsWaybillTotal(String goodsWaybillTotal) {
		this.goodsWaybillTotal = goodsWaybillTotal;
	}

	public String getToPayAmountTotal() {
		return toPayAmountTotal;
	}

	public void setToPayAmountTotal(String toPayAmountTotal) {
		this.toPayAmountTotal = toPayAmountTotal;
	}

}