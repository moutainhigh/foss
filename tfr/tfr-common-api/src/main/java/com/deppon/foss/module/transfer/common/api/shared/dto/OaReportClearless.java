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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/dto/OaReportClearless.java
 *  
 *  FILE NAME          :OaReportClearless.java
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
 * FILE    NAME: OaReportClearless.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 有单无货（少货）
 * @author 046130-foss-xuduowei
 * @date 2012-11-28 上午9:05:39
 */
public class OaReportClearless {
	/**
	 *  运单号
	 */
	private String wayBillId;
	
	/**
	 *  上报时间
	 */
	private Date reportTime;
	
	/**
	 *  运输类型
	 */
	private String transportType;
	
	/**
	 *  返单类别
	 */
	private String returnBillType;
	
	/**
	 *  托运人
	 */
	private String shipper;

	/** 
	 *  发货客户编码
	 */ 
	private String deliveryCustomerCode;
	
	/** 
	 *  收货客户编码
	 */ 
	private String receiveCustomerCode;
	
	/** 
	 *  差异流水号
	 */ 
	private String serialNoList;
		
	/**
	 *  运输性质
	 */
	private String transportProduct;
	
	/**
	 *  配载类型
	 */
	private String stowageType;
	
	/**
	 *  收货人电话
	 */
	private String receiverTel;
	
	/**
	 *  提货方式
	 */
	private String groupSendFlag;
	
	/**
	 *  储运事项
	 */
	private String remark;
	
	/**
	 *  毛重
	 */
	private double weight;
	
	/**
	 *  体积
	 */
	private double vloume;
	
	/**
	 *  货物名称
	 */
	private String goods;
	
	/**
	 *  发货时间
	 */
	private String sendTime;
	
	/**
	 *  目的站
	 */
	private String destination;
	
	/**
	 *  收货人
	 */
	private String receiver;
	
	/**
	 *  收货部门
	 */
	private String receivingDept;
	
	/**
	 * 收货部门orgid
	 */
	
	private String receivingDeptID;
	
	/**
	 *  付款方式
	 */
	private String payType;
	
	/**
	 *  保险金额
	 */
	private BigDecimal insuranceMoney;
	
	/**
	 *  货物包装
	 */
	private String goodsPacking;
	
	/**
	 *  运费总额
	 */
	private BigDecimal total;
	
	/**
	 *  事件经过
	 */
	private String eventReport;
	
	/**
	 *  件数
	 */
	private int goodsCount;
	
	/**
	 * 少货件数
	 */
	private int nogoodscount;
	
	/**
	 *  少货类型
	 */
	private String lostGoodsType;
	
	/**
	 *  装车部门
	 */
	private String previousclDept;
	
	/**
	 *  装车部门编号
	 */
	private String previousclDeptId;
	
	/**
	 *  卸车部门
	 */
	private String unloadDept;
	
	/**
	 *  卸车部门编号
	 */
	private String unloadDeptId;
	
	/**
	 *  交接单
	 */
	private String replayBill;
	
	/**
	 *  车牌号
	 */
	private String carNumber;
	
	/**
	 *  上报人员工号
	 */
	private String userId;
	
	/**
	 *  责任部门
	 */
	private String responsibleDept;
	
	/**
	 *  责任部门编号
	 */
	private String responsibleDeptId;
	
	/**
	 *  上一环节部门
	 */
	private String upTacheDept;
	
	/**
	 *  开单部门
	 */
	private String sheetDept;
	
	/**
	 *  开单部门编号
	 */
	private String sheetDeptId;
	
	/**
	 * 责任事业部标杆编码
	 */
	
	private String finalSysCode;
	
	
	/**
	 * 是否是驻地部门
	* @fields station
	* @author 14022-foss-songjie
	* @update 2015年1月9日 上午9:46:56
	* @version V1.0
	*/
	private String station;
	
	/**
	 * 是否上门接货
	* @fields pickupToDoor
	* @author 14022-foss-songjie
	* @update 2015年1月9日 上午9:46:59
	* @version V1.0
	*/
	private String pickupToDoor;
	
	/**
	 * 业务渠道
	 */
	private String businessChannel;
	
	public String getBusinessChannel() {
		return businessChannel;
	}

	public void setBusinessChannel(String businessChannel) {
		this.businessChannel = businessChannel;
	}

	public int getNogoodscount() {
		return nogoodscount;
	}

	public void setNogoodscount(int nogoodscount) {
		this.nogoodscount = nogoodscount;
	}

	/**
	 * 设置 保险金额.
	 *
	 * @param insuranceMoney the new 保险金额
	 */
	public void setInsuranceMoney(BigDecimal insuranceMoney) {
		this.insuranceMoney = insuranceMoney;
	}
	
	/**
	 * 
	 */
	public OaReportClearless(){
		
	}
		
	/**
	 * 获取 责任事业部标杆编码.
	 *
	 * @return the 责任事业部标杆编码
	 */
	public String getFinalSysCode() {
		return finalSysCode;
	}
	
	/**
	 * 设置 责任事业部标杆编码.
	 *
	 * @param finalSysCode the new 责任事业部标杆编码
	 */
	public void setFinalSysCode(String finalSysCode) {
		this.finalSysCode = finalSysCode;
	}
	
	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWayBillId() {
		return wayBillId;
	}

	/**
	 * 设置 运单号.
	 *
	 * @param wayBillId the new 运单号
	 */
	public void setWayBillId(String wayBillId) {
		this.wayBillId = wayBillId;
	}

	/**
	 * 获取 上报时间.
	 *
	 * @return the 上报时间
	 */
	public Date getReportTime() {
		return reportTime;
	}

	/**
	 * 设置 上报时间.
	 *
	 * @param reportTime the new 上报时间
	 */
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	/**
	 * 获取 运输类型.
	 *
	 * @return the 运输类型
	 */
	public String getTransportType() {
		return transportType;
	}

	/**
	 * 设置 运输类型.
	 *
	 * @param transportType the new 运输类型
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	/**
	 * 获取 返单类别.
	 *
	 * @return the 返单类别
	 */
	public String getReturnBillType() {
		return returnBillType;
	}

	/**
	 * 设置 返单类别.
	 *
	 * @param returnBillType the new 返单类别
	 */
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	/**
	 * 获取 托运人.
	 *
	 * @return the 托运人
	 */
	public String getShipper() {
		return shipper;
	}

	/**
	 * 设置 托运人.
	 *
	 * @param shipper the new 托运人
	 */
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}
	
	/**
	 * 获取发货客户编码
	 * 
	 * @return the 发货客户编码
	 */ 
	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	/**
	 * 设置发货客户编码
	 * 
	 * @param finalSysCode the new 发货客户编码
	 */
	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}
	
	/**
	 * 获取收货客户编码
	 * 
	 * @return the 收货客户编码
	 */ 
	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	/**
	 * 设置收货客户编码
	 * 
	 * @param finalSysCode the new 收货客户编码
	 */
	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}
	
	/**
	 * 获取差异流水号
	 * 
	 * @return the 差异流水号
	 */ 
	public String getSerialNoList() {
		return serialNoList;
	}

	/**
	 * 设置差异流水号
	 * 
	 * @param finalSysCode the new 差异流水号
	 */
	public void setSerialNoList(String serialNoList) {
		this.serialNoList = serialNoList;
	}
	
	/**
	 * 获取 运输性质.
	 *
	 * @return the 运输性质
	 */
	public String getTransportProduct() {
		return transportProduct;
	}

	/**
	 * 设置 运输性质.
	 *
	 * @param transportProduct the new 运输性质
	 */
	public void setTransportProduct(String transportProduct) {
		this.transportProduct = transportProduct;
	}

	/**
	 * 获取 配载类型.
	 *
	 * @return the 配载类型
	 */
	public String getStowageType() {
		return stowageType;
	}

	/**
	 * 设置 配载类型.
	 *
	 * @param stowageType the new 配载类型
	 */
	public void setStowageType(String stowageType) {
		this.stowageType = stowageType;
	}

	/**
	 * 获取 收货人电话.
	 *
	 * @return the 收货人电话
	 */
	public String getReceiverTel() {
		return receiverTel;
	}

	/**
	 * 设置 收货人电话.
	 *
	 * @param receiverTel the new 收货人电话
	 */
	public void setReceiverTel(String receiverTel) {
		this.receiverTel = receiverTel;
	}

	/**
	 * 获取 提货方式.
	 *
	 * @return the 提货方式
	 */
	public String getGroupSendFlag() {
		return groupSendFlag;
	}

	/**
	 * 设置 提货方式.
	 *
	 * @param groupSendFlag the new 提货方式
	 */
	public void setGroupSendFlag(String groupSendFlag) {
		this.groupSendFlag = groupSendFlag;
	}

	/**
	 * 获取 储运事项.
	 *
	 * @return the 储运事项
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置 储运事项.
	 *
	 * @param remark the new 储运事项
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取 毛重.
	 *
	 * @return the 毛重
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * 设置 毛重.
	 *
	 * @param weight the new 毛重
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * 获取 体积.
	 *
	 * @return the 体积
	 */
	public double getVloume() {
		return vloume;
	}

	/**
	 * 设置 体积.
	 *
	 * @param vloume the new 体积
	 */
	public void setVloume(double vloume) {
		this.vloume = vloume;
	}

	/**
	 * 获取 货物名称.
	 *
	 * @return the 货物名称
	 */
	public String getGoods() {
		return goods;
	}

	/**
	 * 设置 货物名称.
	 *
	 * @param goods the new 货物名称
	 */
	public void setGoods(String goods) {
		this.goods = goods;
	}

	/**
	 * 获取 发货时间.
	 *
	 * @return the 发货时间
	 */
	public String getSendTime() {
		return sendTime;
	}

	/**
	 * 设置 发货时间.
	 *
	 * @param sendTime the new 发货时间
	 */
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * 获取 目的站.
	 *
	 * @return the 目的站
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * 设置 目的站.
	 *
	 * @param destination the new 目的站
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * 获取 收货人.
	 *
	 * @return the 收货人
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * 设置 收货人.
	 *
	 * @param receiver the new 收货人
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * 获取 收货部门.
	 *
	 * @return the 收货部门
	 */
	public String getReceivingDept() {
		return receivingDept;
	}

	/**
	 * 设置 收货部门.
	 *
	 * @param receivingDept the new 收货部门
	 */
	public void setReceivingDept(String receivingDept) {
		this.receivingDept = receivingDept;
	}

	/**
	 * 获取 付款方式.
	 *
	 * @return the 付款方式
	 */
	public String getPayType() {
		return payType;
	}

	/**
	 * 设置 付款方式.
	 *
	 * @param payType the new 付款方式
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}

	/**
	 * 获取 保险金额.
	 *
	 * @return the 保险金额
	 */
	public BigDecimal getInsuranceMoney() {
		return insuranceMoney;
	}

	/**
	 * 获取 货物包装.
	 *
	 * @return the 货物包装
	 */
	public String getGoodsPacking() {
		return goodsPacking;
	}

	/**
	 * 设置 货物包装.
	 *
	 * @param goodsPacking the new 货物包装
	 */
	public void setGoodsPacking(String goodsPacking) {
		this.goodsPacking = goodsPacking;
	}

	/**
	 * 获取 运费总额.
	 *
	 * @return the 运费总额
	 */
	public BigDecimal getTotal() {
		return total;
	}

	/**
	 * 设置 运费总额.
	 *
	 * @param total the new 运费总额
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	/**
	 * 获取 事件经过.
	 *
	 * @return the 事件经过
	 */
	public String getEventReport() {
		return eventReport;
	}

	/**
	 * 设置 事件经过.
	 *
	 * @param eventReport the new 事件经过
	 */
	public void setEventReport(String eventReport) {
		this.eventReport = eventReport;
	}

	/**
	 * 获取 件数.
	 *
	 * @return the 件数
	 */
	public int getGoodsCount() {
		return goodsCount;
	}

	/**
	 * 设置 件数.
	 *
	 * @param goodsCount the new 件数
	 */
	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}

	/**
	 * 获取 少货类型.
	 *
	 * @return the 少货类型
	 */
	public String getLostGoodsType() {
		return lostGoodsType;
	}

	/**
	 * 设置 少货类型.
	 *
	 * @param lostGoodsType the new 少货类型
	 */
	public void setLostGoodsType(String lostGoodsType) {
		this.lostGoodsType = lostGoodsType;
	}

	/**
	 * 获取 装车部门.
	 *
	 * @return the 装车部门
	 */
	public String getPreviousclDept() {
		return previousclDept;
	}

	/**
	 * 设置 装车部门.
	 *
	 * @param previousclDept the new 装车部门
	 */
	public void setPreviousclDept(String previousclDept) {
		this.previousclDept = previousclDept;
	}

	/**
	 * 获取 装车部门编号.
	 *
	 * @return the 装车部门编号
	 */
	public String getPreviousclDeptId() {
		return previousclDeptId;
	}

	/**
	 * 设置 装车部门编号.
	 *
	 * @param previousclDeptId the new 装车部门编号
	 */
	public void setPreviousclDeptId(String previousclDeptId) {
		this.previousclDeptId = previousclDeptId;
	}

	/**
	 * 获取 卸车部门.
	 *
	 * @return the 卸车部门
	 */
	public String getUnloadDept() {
		return unloadDept;
	}

	/**
	 * 设置 卸车部门.
	 *
	 * @param unloadDept the new 卸车部门
	 */
	public void setUnloadDept(String unloadDept) {
		this.unloadDept = unloadDept;
	}

	/**
	 * 获取 卸车部门编号.
	 *
	 * @return the 卸车部门编号
	 */
	public String getUnloadDeptId() {
		return unloadDeptId;
	}

	/**
	 * 设置 卸车部门编号.
	 *
	 * @param unloadDeptId the new 卸车部门编号
	 */
	public void setUnloadDeptId(String unloadDeptId) {
		this.unloadDeptId = unloadDeptId;
	}

	/**
	 * 获取 交接单.
	 *
	 * @return the 交接单
	 */
	public String getReplayBill() {
		return replayBill;
	}

	/**
	 * 设置 交接单.
	 *
	 * @param replayBill the new 交接单
	 */
	public void setReplayBill(String replayBill) {
		this.replayBill = replayBill;
	}

	/**
	 * 获取 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getCarNumber() {
		return carNumber;
	}

	/**
	 * 设置 车牌号.
	 *
	 * @param carNumber the new 车牌号
	 */
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	/**
	 * 获取 上报人员工号.
	 *
	 * @return the 上报人员工号
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置 上报人员工号.
	 *
	 * @param userId the new 上报人员工号
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取 责任部门.
	 *
	 * @return the 责任部门
	 */
	public String getResponsibleDept() {
		return responsibleDept;
	}

	/**
	 * 设置 责任部门.
	 *
	 * @param responsibleDept the new 责任部门
	 */
	public void setResponsibleDept(String responsibleDept) {
		this.responsibleDept = responsibleDept;
	}

	/**
	 * 获取 责任部门编号.
	 *
	 * @return the 责任部门编号
	 */
	public String getResponsibleDeptId() {
		return responsibleDeptId;
	}

	/**
	 * 设置 责任部门编号.
	 *
	 * @param responsibleDeptId the new 责任部门编号
	 */
	public void setResponsibleDeptId(String responsibleDeptId) {
		this.responsibleDeptId = responsibleDeptId;
	}

	/**
	 * 获取 上一环节部门.
	 *
	 * @return the 上一环节部门
	 */
	public String getUpTacheDept() {
		return upTacheDept;
	}

	/**
	 * 设置 上一环节部门.
	 *
	 * @param upTacheDept the new 上一环节部门
	 */
	public void setUpTacheDept(String upTacheDept) {
		this.upTacheDept = upTacheDept;
	}

	/**
	 * 获取 开单部门.
	 *
	 * @return the 开单部门
	 */
	public String getSheetDept() {
		return sheetDept;
	}

	/**
	 * 设置 开单部门.
	 *
	 * @param sheetDept the new 开单部门
	 */
	public void setSheetDept(String sheetDept) {
		this.sheetDept = sheetDept;
	}

	/**
	 * 获取 开单部门编号.
	 *
	 * @return the 开单部门编号
	 */
	public String getSheetDeptId() {
		return sheetDeptId;
	}

	/**
	 * 设置 开单部门编号.
	 *
	 * @param sheetDeptId the new 开单部门编号
	 */
	public void setSheetDeptId(String sheetDeptId) {
		this.sheetDeptId = sheetDeptId;
	}

	/**
	 * 获取 收货部门orgid.
	 *
	 * @return the 收货部门orgid
	 */
	public String getReceivingDeptID() {
		return receivingDeptID;
	}

	/**
	 * 设置 收货部门orgid.
	 *
	 * @param receivingDeptID the new 收货部门orgid
	 */
	public void setReceivingDeptID(String receivingDeptID) {
		this.receivingDeptID = receivingDeptID;
	}

	
	/**
	* @description 是否是驻地部门
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年1月9日 上午9:47:39
	*/
	public String getStation() {
		return station;
	}

	
	/**
	* @description 是否是驻地部门
	* @param station
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年1月9日 上午9:47:41
	*/
	public void setStation(String station) {
		this.station = station;
	}

	
	/**
	* @description 是否上门接货
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年1月9日 上午9:47:57
	*/
	public String getPickupToDoor() {
		return pickupToDoor;
	}

	
	/**
	* @description 是否上门接货
	* @param pickupToDoor
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年1月9日 上午9:48:01
	*/
	public void setPickupToDoor(String pickupToDoor) {
		this.pickupToDoor = pickupToDoor;
	}
	
	
		
}