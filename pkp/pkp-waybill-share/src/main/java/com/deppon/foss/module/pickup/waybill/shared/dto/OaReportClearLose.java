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
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/dto/OaReportClearMore.java
 *  
 *  FILE NAME          :OaReportClearMore.java
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
 * PROJECT NAME: tfr-common-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.common.api.shared.dto
 * FILE    NAME: OaReportClearMore.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

/***
 * @clasaName:com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearLose
 * @author: foss-yuting
 * @description: foss丢货差错自动上报OA
 * @date:2014年12月10日 下午15:59:21
 */
public class OaReportClearLose {

	// 运单号
	private String wayBillId;

	// 上报时间
	private Date reportTime;

	// 运输类型
	private String transportType;

	// 返单类别
	private String returnBillType;

	// 托运人
	private String shipper;

	// 运输性质
	private String transportProduct;

	// 配载类型
	private String stowageType;

	// 收货人电话
	private String receiverTel;

	// 提货方式
	private String groupSendFlag;

	// 储运事项
	private String remark;

	// 毛重
	private double weight;

	// 体积
	private double vloume;

	// 货物名称
	private String goods;

	// 发货时间
	private String sendTime;

	// 目的站
	private String destination;

	// 收货人
	private String receiver;

	// 收货部门
	private String receivingDept;

	// 收货部门orgid

	private String receivingDeptID;

	// 付款方式
	private String payType;

	// 保险金额
	private BigDecimal insuranceMoney;

	// 货物包装
	private String goodsPacking;

	// 运费总额
	private BigDecimal total;

	// 事件经过
	private String eventReport;

	// 件数
	private int goodsCount;

	// 少货类型
	private String lostGoodsType;

	// 装车部门
	private String previousclDept;

	// 装车部门编号
	private String previousclDeptId;

	// 卸车部门(上报部门)
	private String unloadDept;

	// 卸车部门编号
	private String unloadDeptId;

	// 交接单
	private String replayBill;

	// 车牌号
	private String carNumber;

	// 上报人员工号(操作员，发现人)
	private String userId;

	// 责任部门
	private String responsibleDept;

	// 责任部门编号
	private String responsibleDeptId;

	// 上一环节部门
	private String upTacheDept;

	// 开单部门
	private String sheetDept;

	// 开单部门编号
	private String sheetDeptId;

	// 责任事业部标杆编码
	private String finalSysCode;

	// 责任事业部orgid
	private String respDivisionOrgid;

	// 少货件数
	private int nogoodscount;

	// 发货客户编码
	private String deliveryCustomerCode;
	// 收货客户编码
	private String receiveCustomerCode;
	// 差异流水号
	private String serialNoList;
	// 是否整票
	private String isfullticket; 
	/**
	 * 业务渠道
	 */
	private String businessChannel;

	// 是否驻地部门
	private String station;
	// 是否上门接货
	private String pickupToDoor;

	public String getWayBillId() {
		return wayBillId;
	}

	public void setWayBillId(String wayBillId) {
		this.wayBillId = wayBillId;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getReturnBillType() {
		return returnBillType;
	}

	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public String getTransportProduct() {
		return transportProduct;
	}

	public void setTransportProduct(String transportProduct) {
		this.transportProduct = transportProduct;
	}

	public String getStowageType() {
		return stowageType;
	}

	public void setStowageType(String stowageType) {
		this.stowageType = stowageType;
	}

	public String getReceiverTel() {
		return receiverTel;
	}

	public void setReceiverTel(String receiverTel) {
		this.receiverTel = receiverTel;
	}

	public String getGroupSendFlag() {
		return groupSendFlag;
	}

	public void setGroupSendFlag(String groupSendFlag) {
		this.groupSendFlag = groupSendFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getVloume() {
		return vloume;
	}

	public void setVloume(double vloume) {
		this.vloume = vloume;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceivingDept() {
		return receivingDept;
	}

	public void setReceivingDept(String receivingDept) {
		this.receivingDept = receivingDept;
	}

	public String getReceivingDeptID() {
		return receivingDeptID;
	}

	public void setReceivingDeptID(String receivingDeptID) {
		this.receivingDeptID = receivingDeptID;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public BigDecimal getInsuranceMoney() {
		return insuranceMoney;
	}

	public void setInsuranceMoney(BigDecimal insuranceMoney) {
		this.insuranceMoney = insuranceMoney;
	}

	public String getGoodsPacking() {
		return goodsPacking;
	}

	public void setGoodsPacking(String goodsPacking) {
		this.goodsPacking = goodsPacking;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getEventReport() {
		return eventReport;
	}

	public void setEventReport(String eventReport) {
		this.eventReport = eventReport;
	}

	public int getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}

	public String getLostGoodsType() {
		return lostGoodsType;
	}

	public void setLostGoodsType(String lostGoodsType) {
		this.lostGoodsType = lostGoodsType;
	}

	public String getPreviousclDept() {
		return previousclDept;
	}

	public void setPreviousclDept(String previousclDept) {
		this.previousclDept = previousclDept;
	}

	public String getPreviousclDeptId() {
		return previousclDeptId;
	}

	public void setPreviousclDeptId(String previousclDeptId) {
		this.previousclDeptId = previousclDeptId;
	}

	public String getUnloadDept() {
		return unloadDept;
	}

	public void setUnloadDept(String unloadDept) {
		this.unloadDept = unloadDept;
	}

	public String getUnloadDeptId() {
		return unloadDeptId;
	}

	public void setUnloadDeptId(String unloadDeptId) {
		this.unloadDeptId = unloadDeptId;
	}

	public String getReplayBill() {
		return replayBill;
	}

	public void setReplayBill(String replayBill) {
		this.replayBill = replayBill;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getResponsibleDept() {
		return responsibleDept;
	}

	public void setResponsibleDept(String responsibleDept) {
		this.responsibleDept = responsibleDept;
	}

	public String getResponsibleDeptId() {
		return responsibleDeptId;
	}

	public void setResponsibleDeptId(String responsibleDeptId) {
		this.responsibleDeptId = responsibleDeptId;
	}

	public String getUpTacheDept() {
		return upTacheDept;
	}

	public void setUpTacheDept(String upTacheDept) {
		this.upTacheDept = upTacheDept;
	}

	public String getSheetDept() {
		return sheetDept;
	}

	public void setSheetDept(String sheetDept) {
		this.sheetDept = sheetDept;
	}

	public String getSheetDeptId() {
		return sheetDeptId;
	}

	public void setSheetDeptId(String sheetDeptId) {
		this.sheetDeptId = sheetDeptId;
	}

	public String getFinalSysCode() {
		return finalSysCode;
	}

	public void setFinalSysCode(String finalSysCode) {
		this.finalSysCode = finalSysCode;
	}

	public String getRespDivisionOrgid() {
		return respDivisionOrgid;
	}

	public void setRespDivisionOrgid(String respDivisionOrgid) {
		this.respDivisionOrgid = respDivisionOrgid;
	}

	public int getNogoodscount() {
		return nogoodscount;
	}

	public void setNogoodscount(int nogoodscount) {
		this.nogoodscount = nogoodscount;
	}

	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	public String getSerialNoList() {
		return serialNoList;
	}

	public void setSerialNoList(String serialNoList) {
		this.serialNoList = serialNoList;
	}

	public String getIsfullticket() {
		return isfullticket;
	}

	public void setIsfullticket(String isfullticket) {
		this.isfullticket = isfullticket;
	}

	public String getBusinessChannel() {
		return businessChannel;
	}

	public void setBusinessChannel(String businessChannel) {
		this.businessChannel = businessChannel;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getPickupToDoor() {
		return pickupToDoor;
	}

	public void setPickupToDoor(String pickupToDoor) {
		this.pickupToDoor = pickupToDoor;
	}

}