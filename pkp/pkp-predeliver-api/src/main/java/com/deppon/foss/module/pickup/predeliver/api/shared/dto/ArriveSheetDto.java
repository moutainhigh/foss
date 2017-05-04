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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/ArriveSheetDto.java
 * 
 * FILE NAME        	: ArriveSheetDto.java
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
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 到达联查询DTO
 * 
 * @author dp-dengtingting
 */
public class ArriveSheetDto implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 到达联编号
	 */
	private String arrivesheetNo;
	/**
	 * 打印次数
	 */
	private Integer printtimes;
	/**
	 * 到达联状态
	 */
	private String status;
	/**
	 * 到达联件数
	 */
	private Integer arriveSheetGoodsQty;
	/**
	 * 创建人
	 */
	private String createUserName;
	/**
	 * 货物总件数
	 */
	private Integer goodsQtyTotal;
	/**
	 * 货物名称
	 */
	private String goodsName;
	/**
	 * 派送单件数
	 */
	private String arrangeGoodsGty;
	/**
	 * 收货客户名称
	 */
	private String receiveCustomerName;
	/**
	 * 收货客户手机
	 */
	private String receiveCustomerMobilephone;
	/**
	 * 创建开始时间
	 */
	private Date createBeginTime;
	/**
	 * 创建结束时间
	 */
	private Date createEndTime;
	/**
	 * 是否打印
	 */
	private String isPrinted;
	/**
	 * 到达未出库件数 == 库存件数
	 */
	private String arriveNotoutGoodsQty;
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 运单是否有效
	 */
	private String waybillActive;
	/**
	 * 是否审批中
	 */
	private String isRfcing;
	/**
	 * 到达联状态集合
	 */
	private List<String> arriveSheetStatus;
	/**
	 * 是否作废
	 */
	private String destroyed;
	
	/**
	 * 再次送货时间
	 */
	private Date nextDeliverTime;

	/**
	 * 派送单编号
	 */
	private String deliverbillNo;

	/**
	 * 最终配载部门
	 */
	private String lastLoadOrgCode;

	// 到达联生成时间
	private Date createTime;
	// 打印时间
	private Date printTime;
	// 打印部门名称
	private String printOrgName;
	// 打印人编码
	private String printUserName;
	private Date modifyDate;
	
	public Date getModifyDate() {
		return modifyDate;
	}
	
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	/**
	 * 合送编码
	 */
	private String togetherSendCode;
	private String[] waybillNos;
	/**
	 * 送货要求
	 */
	private String deliverRequire;

	/**
	 *  收货人是否大客户.
	 */
	private String receiveBigCustomer; 
	
	/**
	 * Gets the 收货人是否大客户.
	 *
	 * @return the 收货人是否大客户.
	 */
	public String getReceiveBigCustomer() {
		return receiveBigCustomer;
	}

	/**
	 * Sets the 收货人是否大客户.
	 *
	 * @param receiveBigCustomer the 收货人是否大客户.
	 */
	public void setReceiveBigCustomer(String receiveBigCustomer) {
		this.receiveBigCustomer = receiveBigCustomer;
	}
	/**
	 * @return the arriveNotoutGoodsQty
	 */
	public String getArriveNotoutGoodsQty() {
		return arriveNotoutGoodsQty;
	}

	/**
	 * @param arriveNotoutGoodsQty the arriveNotoutGoodsQty to see
	 */
	public void setArriveNotoutGoodsQty(String arriveNotoutGoodsQty) {
		this.arriveNotoutGoodsQty = arriveNotoutGoodsQty;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to see
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the arrivesheetNo
	 */
	public String getArrivesheetNo() {
		return arrivesheetNo;
	}

	/**
	 * @param arrivesheetNo the arrivesheetNo to see
	 */
	public void setArrivesheetNo(String arrivesheetNo) {
		this.arrivesheetNo = arrivesheetNo;
	}

	/**
	 * @return the printtimes
	 */
	public Integer getPrinttimes() {
		return printtimes;
	}

	/**
	 * @param printtimes the printtimes to see
	 */
	public void setPrinttimes(Integer printtimes) {
		this.printtimes = printtimes;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to see
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName the createUserName to see
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return the goodsQtyTotal
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * @param goodsQtyTotal the goodsQtyTotal to see
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName the goodsName to see
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * @return the arrangeGoodsGty
	 */
	public String getArrangeGoodsGty() {
		return arrangeGoodsGty;
	}

	/**
	 * @param arrangeGoodsGty the arrangeGoodsGty to see
	 */
	public void setArrangeGoodsGty(String arrangeGoodsGty) {
		this.arrangeGoodsGty = arrangeGoodsGty;
	}

	/**
	 * @return the receiveCustomerName
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * @param receiveCustomerName the receiveCustomerName to see
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * @return the receiveCustomerMobilephone
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	/**
	 * @param receiveCustomerMobilephone the receiveCustomerMobilephone to see
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to see
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the isPrinted
	 */
	public String getIsPrinted() {
		return isPrinted;
	}

	/**
	 * @param isPrinted the isPrinted to see
	 */
	public void setIsPrinted(String isPrinted) {
		this.isPrinted = isPrinted;
	}

	/**
	 * @return the createBeginTime
	 */
	public Date getCreateBeginTime() {
		return createBeginTime;
	}

	/**
	 * @param createBeginTime the createBeginTime to see
	 */
	@DateFormat(formate = "yyyy-MM-dd")
	public void setCreateBeginTime(Date createBeginTime) {
		this.createBeginTime = createBeginTime;
	}

	/**
	 * @return the createEndTime
	 */
	public Date getCreateEndTime() {
		return createEndTime;
	}

	/**
	 * @param createEndTime the createEndTime to see
	 */
	@DateFormat(formate = "yyyy-MM-dd")
	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	/**
	 * @return the arriveSheetGoodsQty
	 */
	public Integer getArriveSheetGoodsQty() {
		return arriveSheetGoodsQty;
	}

	/**
	 * @param arriveSheetGoodsQty the arriveSheetGoodsQty to see
	 */
	public void setArriveSheetGoodsQty(Integer arriveSheetGoodsQty) {
		this.arriveSheetGoodsQty = arriveSheetGoodsQty;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to see
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the waybillActive
	 */
	public String getWaybillActive() {
		return waybillActive;
	}

	/**
	 * @param waybillActive the waybillActive to see
	 */
	public void setWaybillActive(String waybillActive) {
		this.waybillActive = waybillActive;
	}

	/**
	 * @return the isRfcing
	 */
	public String getIsRfcing() {
		return isRfcing;
	}

	/**
	 * @param isRfcing the isRfcing to see
	 */
	public void setIsRfcing(String isRfcing) {
		this.isRfcing = isRfcing;
	}

	/**
	 * @return the arriveSheetStatus
	 */
	public List<String> getArriveSheetStatus() {
		return arriveSheetStatus;
	}

	/**
	 * @param arriveSheetStatus the arriveSheetStatus to see
	 */
	public void setArriveSheetStatus(List<String> arriveSheetStatus) {
		this.arriveSheetStatus = arriveSheetStatus;
	}

	/**
	 * @return the destroyed
	 */
	public String getDestroyed() {
		return destroyed;
	}

	/**
	 * @param destroyed the destroyed to see
	 */
	public void setDestroyed(String destroyed) {
		this.destroyed = destroyed;
	}

	/**
	 * @return the deliverbillNo
	 */
	public String getDeliverbillNo() {
		return deliverbillNo;
	}

	/**
	 * @param deliverbillNo the deliverbillNo to see
	 */
	public void setDeliverbillNo(String deliverbillNo) {
		this.deliverbillNo = deliverbillNo;
	}

	/**
	 * @return the lastLoadOrgCode
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	/**
	 * @param lastLoadOrgCode the lastLoadOrgCode to see
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	public Date getPrintTime() {
		return printTime;
	}

	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	public String getPrintOrgName() {
		return printOrgName;
	}

	public void setPrintOrgName(String printOrgName) {
		this.printOrgName = printOrgName;
	}

	public String getPrintUserName() {
		return printUserName;
	}

	public void setPrintUserName(String printUserName) {
		this.printUserName = printUserName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTogetherSendCode() {
		return togetherSendCode;
	}

	public void setTogetherSendCode(String togetherSendCode) {
		this.togetherSendCode = togetherSendCode;
	}

	public String[] getWaybillNos() {
		return waybillNos;
	}

	public void setWaybillNos(String[] waybillNos) {
		this.waybillNos = waybillNos;
	}

	public String getDeliverRequire() {
		return deliverRequire;
	}

	public void setDeliverRequire(String deliverRequire) {
		this.deliverRequire = deliverRequire;
	}

	public Date getNextDeliverTime() {
		return nextDeliverTime;
	}

	public void setNextDeliverTime(Date nextDeliverTime) {
		this.nextDeliverTime = nextDeliverTime;
	}


}