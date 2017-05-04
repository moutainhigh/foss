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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/DeliverbillDetailDto.java
 * 
 * FILE NAME        	: DeliverbillDetailDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 派送单明细dto
 * @author foss-meiying
 * @date 2012-11-29 上午10:24:00
 * @since
 * @version
 */
public class DeliverbillDetailDto implements Serializable{
	private static final long serialVersionUID = 8730503489738701986L;
	/**
	 *  派送单id
	 */
	private String id;
	/**
	 *  派送单编号
	 */
	private String deliverbillNo;
	/**
	 *  运单号
	 */
	private String waybillNo;
	/**
	 *  到达联编号
	 */
	private String arrivesheetNo;
	/**
	 *  车牌号
	 */
	private String vehicleNo;
	/**
	 *  司机工号
	 */
	private String driverCode;
	/**
	 *  签收人
	 */
	private String deliverymanName;
	/**
	 *  到达联是否有效 */
	private String active;
	/** 
	 * 到达联状态集合
	 */
	private List<String> status;
	/**
	 *  派送单状态
	 */
	private String deliverStatus;
	/**
	 *  派送单状态集合
	 */
	private List<String> deliverStatusList;
	/** 
	 * 最终配载部门（判断是否为本部门）
	 */
	private String lastLoadOrgCode;
	/**
	 *  创建人
	 */
	private String createUserName;
	/**
	 *  创建人编码
	 */
	private String createUserCode;
	/**
	 *  创建时间
	 */
	private Date createTime;
	/** 
	 * 派送单id集合
	 */
	private List<String> ids;
	/**
	 * 派送单编号集合
	 */
	private List<String> deliverbillNos;
	/**
	 *  到达联是否作废
	 */
	private String destroyed;
	/**
	 * 状态为签收
	 */
	private String statusSign;
	/**
	 * 交接id
	 */
	private String tSrvStayHandoverId;
	/**
	 * 到达联情况
	 */
	private String situation;
	/** 所属部门List */
	private List<String> orgIdList;
	/** 部门codeList */
	private List<String> subOrgCodeList;
	/**
	 * 到达联件数
	 */
	private Integer arriveSheetGoodsQty;
	/** 创建部门名称. */
	private String createOrgName;
	/**
	 * 到达联状态
	 */
	private String arriveSheetStatus;
	
	/**
	 * 派送单状态 Y：新派送单
	 */
	private String createType;
	
	/**
	 * 派送单修改时的时间
	 */
	private Date modifyTime;
	
	/**
	 * 合伙人初始化数据过滤410几点前后运单
	 */
	private Date conBillTime;
	
	/**
	 * @return the conBillTime
	 */
	public Date getConBillTime() {
		return conBillTime;
	}

	/**
	 * @param conBillTime the conBillTime to set
	 */
	public void setConBillTime(Date conBillTime) {
		this.conBillTime = conBillTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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
	 * Gets the 派送单id.
	 *
	 * @return the 派送单id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the 派送单id.
	 *
	 * @param id the new 派送单id
	 */
	public void setId(String id) {
		this.id = id;
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
	 * Gets the 派送单编号.
	 *
	 * @return the 派送单编号
	 */
	public String getDeliverbillNo() {
		return deliverbillNo;
	}

	/**
	 * Sets the 派送单编号.
	 *
	 * @param deliverbillNo the new 派送单编号
	 */
	public void setDeliverbillNo(String deliverbillNo) {
		this.deliverbillNo = deliverbillNo;
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
	 * Gets the 签收人.
	 *
	 * @return the 签收人
	 */
	public String getDeliverymanName() {
		return deliverymanName;
	}

	/**
	 * Sets the 签收人.
	 *
	 * @param deliverymanName the new 签收人
	 */
	public void setDeliverymanName(String deliverymanName) {
		this.deliverymanName = deliverymanName;
	}

	/**
	 * Gets the 到达联状态集合.
	 *
	 * @return the 到达联状态集合
	 */
	public List<String> getStatus() {
		return status;
	}

	/**
	 * Sets the 到达联状态集合.
	 *
	 * @param status the new 到达联状态集合
	 */
	public void setStatus(List<String> status) {
		this.status = status;
	}

	/**
	 * Gets the 派送单状态.
	 *
	 * @return the 派送单状态
	 */
	public String getDeliverStatus() {
		return deliverStatus;
	}

	/**
	 * Sets the 派送单状态.
	 *
	 * @param deliverStatus the new 派送单状态
	 */
	public void setDeliverStatus(String deliverStatus) {
		this.deliverStatus = deliverStatus;
	}

	/**
	 * Gets the 到达联是否有效.
	 *
	 * @return the 到达联是否有效
	 */
	public String getActive() {
		return active;
	}

	/**
	 * Sets the 到达联是否有效.
	 *
	 * @param active the new 到达联是否有效
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * Gets the 最终配载部门（判断是否为本部门）.
	 *
	 * @return the 最终配载部门（判断是否为本部门）
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	/**
	 * Sets the 最终配载部门（判断是否为本部门）.
	 *
	 * @param lastLoadOrgCode the new 最终配载部门（判断是否为本部门）
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	/**
	 * Gets the 创建人.
	 *
	 * @return the 创建人
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * Sets the 创建人.
	 *
	 * @param createUserName the new 创建人
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * Gets the 创建人编码.
	 *
	 * @return the 创建人编码
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * Sets the 创建人编码.
	 *
	 * @param createUserCode the new 创建人编码
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * Gets the 创建时间.
	 *
	 * @return the 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Sets the 创建时间.
	 *
	 * @param createTime the new 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * Gets the 派送单id集合.
	 *
	 * @return the 派送单id集合
	 */
	public List<String> getIds() {
		return ids;
	}

	/**
	 * Sets the 派送单id集合.
	 *
	 * @param ids the new 派送单id集合
	 */
	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	/**
	 * Gets the 派送单状态集合.
	 *
	 * @return the 派送单状态集合
	 */
	public List<String> getDeliverStatusList() {
		return deliverStatusList;
	}

	/**
	 * Sets the 派送单状态集合.
	 *
	 * @param deliverStatusList the new 派送单状态集合
	 */
	public void setDeliverStatusList(List<String> deliverStatusList) {
		this.deliverStatusList = deliverStatusList;
	}

	/**
	 * Gets the 到达联是否作废.
	 *
	 * @return the 到达联是否作废
	 */
	public String getDestroyed() {
		return destroyed;
	}

	/**
	 * Sets the 到达联是否作废.
	 *
	 * @param destroyed the new 到达联是否作废
	 */
	public void setDestroyed(String destroyed) {
		this.destroyed = destroyed;
	}


	/**
	 * Gets the 派送单编号集合.
	 *
	 * @return the 派送单编号集合
	 */
	public List<String> getDeliverbillNos() {
		return deliverbillNos;
	}

	/**
	 * Sets the 派送单编号集合.
	 *
	 * @param deliverbillNos the new 派送单编号集合
	 */
	public void setDeliverbillNos(List<String> deliverbillNos) {
		this.deliverbillNos = deliverbillNos;
	}

	public String gettSrvStayHandoverId() {
		return tSrvStayHandoverId;
	}

	public void settSrvStayHandoverId(String tSrvStayHandoverId) {
		this.tSrvStayHandoverId = tSrvStayHandoverId;
	}

	/**
	 * Gets the 状态为签收.
	 *
	 * @return the 状态为签收
	 */
	public String getStatusSign() {
		return statusSign;
	}

	/**
	 * Sets the 状态为签收.
	 *
	 * @param statusSign the new 状态为签收
	 */
	public void setStatusSign(String statusSign) {
		this.statusSign = statusSign;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public List<String> getOrgIdList() {
		return orgIdList;
	}

	public void setOrgIdList(List<String> orgIdList) {
		this.orgIdList = orgIdList;
	}

	public List<String> getSubOrgCodeList() {
		return subOrgCodeList;
	}

	public void setSubOrgCodeList(List<String> subOrgCodeList) {
		this.subOrgCodeList = subOrgCodeList;
	}

	public void setArriveSheetGoodsQty(Integer arriveSheetGoodsQty) {
		this.arriveSheetGoodsQty = arriveSheetGoodsQty;
	}

	
	public Integer getArriveSheetGoodsQty() {
		return arriveSheetGoodsQty;
	}


	public String getArriveSheetStatus() {
		return arriveSheetStatus;
	}

	public void setArriveSheetStatus(String arriveSheetStatus) {
		this.arriveSheetStatus = arriveSheetStatus;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getCreateType() {
		return createType;
	}

	public void setCreateType(String createType) {
		this.createType = createType;
	}


}