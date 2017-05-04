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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillRfcPrintDto.java
 * 
 * FILE NAME        	: WaybillRfcPrintDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;

/**
 * 
 * 封装更改单打印实体
 * @title WaybillRfcPrintDto.java
 * @package com.deppon.foss.module.pickup.waybill.shared.dto 
 * @author suyujun
 * @version 0.1 2012-12-10
 */
public class WaybillRfcPrintDto implements Serializable {
	private static final long serialVersionUID = 8930705163407821341L;
	//打印次数
	private Integer printTimes;
	//运单号
	private String waybillNo;
	//更改原因
	private String rfcReason;
	//申请部门
	private String applyDept;
	//申请人
	private String applyPerson;
	//申请时间
	private String applyTime;
	//货物基本信息
	//货物名称
	private String goodsName;
	//包装
	private String pack;
	//件数
	private Integer pieces;
	//重量
	private BigDecimal weight;
	//体积
	private BigDecimal volume;
	//尺寸
	private String dimension;
	//更改单ID
	private String rfcId;
	//更改单类型
	private String rfcType;
	
	/**
	 * 运输性质
	 */
	private String productCode;
	
	/**
	 * 提货网点
	 */
	private String customerPickupOrgCode;
	
	/**
	 * 目的站
	 */
	private String targetOrgCode;
	//变更项
	private List<WaybillRfcChangeDetailEntity> changeList;
	/**
	 * @return printTimes : set the property printTimes.
	 */
	public Integer getPrintTimes() {
		return printTimes;
	}
	/**
	 * @param printTimes : return the property printTimes.
	 */
	public void setPrintTimes(Integer printTimes) {
		this.printTimes = printTimes;
	}
	/**
	 * @return waybillNo : set the property waybillNo.
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo : return the property waybillNo.
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return rfcReason : set the property rfcReason.
	 */
	public String getRfcReason() {
		return rfcReason;
	}
	/**
	 * @param rfcReason : return the property rfcReason.
	 */
	public void setRfcReason(String rfcReason) {
		this.rfcReason = rfcReason;
	}
	/**
	 * @return applyDept : set the property applyDept.
	 */
	public String getApplyDept() {
		return applyDept;
	}
	/**
	 * @param applyDept : return the property applyDept.
	 */
	public void setApplyDept(String applyDept) {
		this.applyDept = applyDept;
	}
	/**
	 * @return applyPerson : set the property applyPerson.
	 */
	public String getApplyPerson() {
		return applyPerson;
	}
	/**
	 * @param applyPerson : return the property applyPerson.
	 */
	public void setApplyPerson(String applyPerson) {
		this.applyPerson = applyPerson;
	}
	/**
	 * @return applyTime : set the property applyTime.
	 */
	public String getApplyTime() {
		return applyTime;
	}
	/**
	 * @param applyTime : return the property applyTime.
	 */
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	/**
	 * @return goodsName : set the property goodsName.
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * @param goodsName : return the property goodsName.
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	/**
	 * @return pack : set the property pack.
	 */
	public String getPack() {
		return pack;
	}
	/**
	 * @param pack : return the property pack.
	 */
	public void setPack(String pack) {
		this.pack = pack;
	}
	/**
	 * @return pieces : set the property pieces.
	 */
	public Integer getPieces() {
		return pieces;
	}
	/**
	 * @param pieces : return the property pieces.
	 */
	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}
	/**
	 * @return weight : set the property weight.
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	/**
	 * @param weight : return the property weight.
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	/**
	 * @return volume : set the property volume.
	 */
	public BigDecimal getVolume() {
		return volume;
	}
	/**
	 * @param volume : return the property volume.
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	/**
	 * @return dimension : set the property dimension.
	 */
	public String getDimension() {
		return dimension;
	}
	/**
	 * @param dimension : return the property dimension.
	 */
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	/**
	 * @return changeList : set the property changeList.
	 */
	public List<WaybillRfcChangeDetailEntity> getChangeList() {
		return changeList;
	}
	/**
	 * @param changeList : return the property changeList.
	 */
	public void setChangeList(List<WaybillRfcChangeDetailEntity> changeList) {
		this.changeList = changeList;
	}
	public String getRfcId() {
		return rfcId;
	}
	public void setRfcId(String rfcId) {
		this.rfcId = rfcId;
	}
	public String getRfcType() {
		return rfcType;
	}
	public void setRfcType(String rfcType) {
		this.rfcType = rfcType;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}
	public String getTargetOrgCode() {
		return targetOrgCode;
	}
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}
	
	
	
}