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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/NotifyCustomerConditionDto.java
 * 
 * FILE NAME        	: NotifyCustomerConditionDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.InvoiceInfomationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;

/**
 * 客户通知Dto
 * 
 * @author ibm-wangfei
 * @date Oct 15, 2012 2:22:43 PM
 */
public class NotifyCustomerConditionDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 交接单号
	 */
	private String handoverNo;
	/**
	 * 车次号
	 */
	private String vehicleAssembleNo;
	/**
	 * 派送方式
	 */
	private String receiveMethod;
	/**
	 * 在库天数
	 */
	private String storageDay;
	/**
	 * 在库天数特殊情况（X天以上）
	 */
	private int storageDayStatus;
	/**
	 * 到达部门
	 */
	private String arriveDepart;
	/**
	 * 运输性质
	 */
	private String productCode;
	/**
	 * 通知情况
	 */
	private String noticeResult;
	/**
	 * 入库时间（起）
	 */
	private String inStockTimeFrom;
	/**
	 * 入库时间（止）
	 */
	private String inStockTimeTo;
	/**
	 * 预计到达时间（起）
	 */
	private String arriveTimeFrom;
	/**
	 * 预计到达时间（止）
	 */
	private String arriveTimeTo;
	/**
	 * 预计到达时间（起）
	 */
	private String planArriveTimeFrom;
	/**
	 * 预计到达时间（止）
	 */
	private String planArriveTimeTo;
	/**
	 * 所在部门
	 */
	private String lastLoadOrgCode;
	/**
	 * 默认查询中派送方式
	 */
	private String receiveMethodTmp;
	/**
	 * 默认查询中通知状态
	 */
	private String notificationTypeTmp;
	/**
	 * 默认查询中当期时间
	 */
	private Date deliverDateTmp;
	/**
	 * 默认查询中初始化时间
	 */
	private Date deliverDateDef;
	/**
	 * 默认查询中付款方式
	 */
	private String paidMethod;
	/**
	 * 默认查询中变更状态
	 */
	private List<String> wbrStatus;
	/**
	 * 查询方式
	 */
	private String selectType;
	/**
	 * 选择的运单号列表
	 */
	private String waybillNos;
	/**
	 * 选择的运单号列表
	 */
	private String[] arrayWaybillNos;
	/**
	 * 默认异常类型
	 */
	private String exceptionType;
	/**
	 * 默认异常环节1
	 */
	private String exceptionLink1;
	/**
	 * 默认异常环节2
	 */
	private String exceptionLink2;
	/**
	 * 默认异常处理状态
	 */
	private String[] exceptionStatus;
	/**
	 * 默认车辆到达状态
	 */
	private String taskStatus;
	/**
	 * 默认不查询运单产品
	 */
	private String[] productCodes;
	/**
	 * 默认查询的运单产品集合
	 */
	private String[] productCodesContain;
	/**
	 * 默认运单版本
	 */
	private String active;
	/**
	 * 是否限制送货时间
	 */
	private String isQueryDeliveyDate;

	/**
	 * 运单通知信息
	 */
	private NotifyCustomerDto notifyCustomerDto;

	/**
	 * 运单通知历史记录/批量通知
	 */
	private List<NotificationEntity> notificationEntityList;

	/**
	 * 客户通知信息
	 */
	private NotificationEntity notificationEntity;

	/**
	 * 发票信息
	 */
	private InvoiceInfomationEntity invoiceInfomationEntity;

	/**
	 * 最后库存code
	 */
	private String endStockOrgCode;

	/**
	 * 库区
	 */
	private String goodsAreaCode;
	/**
	 * 快递零担库区
	 */
	private List<String> goodsAreaCodes;
	/**
	 * 库位
	 */
	private String position;
	private Long totalVotes;//总票数
	private Integer goodsQtyTotals;//总件数
	private BigDecimal goodsWeightTotals;//总重量
	private BigDecimal goodsvolumeTotals;//总体积
	
	private List<String> afStatus;//运单状态  
	private List<String> arrStatus;//到达联状态

	private String togetherSendMark ;
	
	private String deliverProv; //省
	private String deliverCity;  //市
	private String deliverDistCode; //行政区域
	private String[] deliverDistCodes; //行政区域集合
	private String receiveMethodCon;  //提货方式条件
	private String[] receiveMethodCons; //提货方式条件集合
	private String[] productCodeCons;  //条件运输性质集合
	private String[] vehicleAssembleNos; //车次号集合
	//add by 329757  到达时间状态
	private String arriveTimeStatus;
	//add by 329757 通知时间开始
	private String noticeTimeFrom;
	//add by 329757 通知时间结束
	private String noticeTimeTo;
	//add by 329757 承诺到达时间
	private Date preCustomerPickupTime;
	//add by 329757 外场名称
	private String outName;
	private BigDecimal pickupFee;//add by 329757 接貨費
	public BigDecimal getPickupFee() {
		return pickupFee;
	}

	public void setPickupFee(BigDecimal pickupFee) {
		this.pickupFee = pickupFee;
	}

	public String getOutName() {
		return outName;
	}

	public void setOutName(String outName) {
		this.outName = outName;
	}

	public String getArriveTimeStatus() {
		return arriveTimeStatus;
	}

	public void setArriveTimeStatus(String arriveTimeStatus) {
		this.arriveTimeStatus = arriveTimeStatus;
	}

	public String getNoticeTimeFrom() {
		return noticeTimeFrom;
	}

	public void setNoticeTimeFrom(String noticeTimeFrom) {
		this.noticeTimeFrom = noticeTimeFrom;
	}

	public String getNoticeTimeTo() {
		return noticeTimeTo;
	}

	public void setNoticeTimeTo(String noticeTimeTo) {
		this.noticeTimeTo = noticeTimeTo;
	}

	public Date getPreCustomerPickupTime() {
		return preCustomerPickupTime;
	}

	public void setPreCustomerPickupTime(Date preCustomerPickupTime) {
		this.preCustomerPickupTime = preCustomerPickupTime;
	}

	/**
	 * 是否快递
	 */
	private  String isExpress;
	/**
	 * 是否合送
	 */
	private String isTogetherSend;
	
	/**
	 * 是否分批配载
	 */
	private String batchStowage; 
	
	public String getTogetherSendMark() {
		return togetherSendMark;
	}

	public void setTogetherSendMark(String togetherSendMark) {
		this.togetherSendMark = togetherSendMark;
	}

	/**
	 * 获取 运单号.
	 * 
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置 运单号.
	 * 
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 派送方式.
	 * 
	 * @return the 派送方式
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * 设置 派送方式.
	 * 
	 * @param receiveMethod the new 派送方式
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * 获取 到达部门.
	 * 
	 * @return the 到达部门
	 */
	public String getArriveDepart() {
		return arriveDepart;
	}

	/**
	 * 设置 到达部门.
	 * 
	 * @param arriveDepart the new 到达部门
	 */
	public void setArriveDepart(String arriveDepart) {
		this.arriveDepart = arriveDepart;
	}

	/**
	 * 获取 运输性质.
	 * 
	 * @return the 运输性质
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * 设置 运输性质.
	 * 
	 * @param productCode the new 运输性质
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * 获取 交接单号.
	 * 
	 * @return the 交接单号
	 */
	public String getHandoverNo() {
		return handoverNo;
	}

	/**
	 * 设置 交接单号.
	 * 
	 * @param handoverNo the new 交接单号
	 */
	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	/**
	 * 获取 车次号.
	 * 
	 * @return the 车次号
	 */
	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}

	/**
	 * 设置 车次号.
	 * 
	 * @param vehicleAssembleNo the new 车次号
	 */
	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 获取 所在部门.
	 * 
	 * @return the 所在部门
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	/**
	 * 设置 所在部门.
	 * 
	 * @param lastLoadOrgCode the new 所在部门
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	/**
	 * 获取 默认查询中派送方式.
	 * 
	 * @return the 默认查询中派送方式
	 */
	public String getReceiveMethodTmp() {
		return receiveMethodTmp;
	}

	/**
	 * 设置 默认查询中派送方式.
	 * 
	 * @param receiveMethodTmp the new 默认查询中派送方式
	 */
	public void setReceiveMethodTmp(String receiveMethodTmp) {
		this.receiveMethodTmp = receiveMethodTmp;
	}

	/**
	 * 获取 默认查询中通知状态.
	 * 
	 * @return the 默认查询中通知状态
	 */
	public String getNotificationTypeTmp() {
		return notificationTypeTmp;
	}

	/**
	 * 设置 默认查询中通知状态.
	 * 
	 * @param notificationTypeTmp the new 默认查询中通知状态
	 */
	public void setNotificationTypeTmp(String notificationTypeTmp) {
		this.notificationTypeTmp = notificationTypeTmp;
	}

	/**
	 * 获取 默认查询中当期时间.
	 * 
	 * @return the 默认查询中当期时间
	 */
	public Date getDeliverDateTmp() {
		return deliverDateTmp;
	}

	/**
	 * 设置 默认查询中当期时间.
	 * 
	 * @param deliverDateTmp the new 默认查询中当期时间
	 */
	public void setDeliverDateTmp(Date deliverDateTmp) {
		this.deliverDateTmp = deliverDateTmp;
	}

	/**
	 * 获取 默认查询中付款方式.
	 * 
	 * @return the 默认查询中付款方式
	 */
	public String getPaidMethod() {
		return paidMethod;
	}

	/**
	 * 设置 默认查询中付款方式.
	 * 
	 * @param paidMethod the new 默认查询中付款方式
	 */
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	/**
	 * 获取 默认查询中变更状态.
	 * 
	 * @return the 默认查询中变更状态
	 */
	public List<String> getWbrStatus() {
		return wbrStatus;
	}

	/**
	 * 设置 默认查询中变更状态.
	 * 
	 * @param wbrStatus the new 默认查询中变更状态
	 */
	public void setWbrStatus(List<String> wbrStatus) {
		this.wbrStatus = wbrStatus;
	}

	/**
	 * 获取 入库时间（起）.
	 * 
	 * @return the 入库时间（起）
	 */
	public String getInStockTimeFrom() {
		return inStockTimeFrom;
	}

	/**
	 * 设置 入库时间（起）.
	 * 
	 * @param inStockTimeFrom the new 入库时间（起）
	 */
	public void setInStockTimeFrom(String inStockTimeFrom) {
		this.inStockTimeFrom = inStockTimeFrom;
	}

	/**
	 * 获取 入库时间（止）.
	 * 
	 * @return the 入库时间（止）
	 */
	public String getInStockTimeTo() {
		return inStockTimeTo;
	}

	/**
	 * 设置 入库时间（止）.
	 * 
	 * @param inStockTimeTo the new 入库时间（止）
	 */
	public void setInStockTimeTo(String inStockTimeTo) {
		this.inStockTimeTo = inStockTimeTo;
	}

	/**
	 * 获取 预计到达时间（起）.
	 * 
	 * @return the 预计到达时间（起）
	 */
	public String getPlanArriveTimeFrom() {
		return planArriveTimeFrom;
	}

	/**
	 * 设置 预计到达时间（起）.
	 * 
	 * @param planArriveTimeFrom the new 预计到达时间（起）
	 */
	public void setPlanArriveTimeFrom(String planArriveTimeFrom) {
		this.planArriveTimeFrom = planArriveTimeFrom;
	}

	/**
	 * 获取 预计到达时间（止）.
	 * 
	 * @return the 预计到达时间（止）
	 */
	public String getPlanArriveTimeTo() {
		return planArriveTimeTo;
	}

	/**
	 * 设置 预计到达时间（止）.
	 * 
	 * @param planArriveTimeTo the new 预计到达时间（止）
	 */
	public void setPlanArriveTimeTo(String planArriveTimeTo) {
		this.planArriveTimeTo = planArriveTimeTo;
	}

	/**
	 * 获取 在库天数.
	 * 
	 * @return the 在库天数
	 */
	public String getStorageDay() {
		return storageDay;
	}

	/**
	 * 设置 在库天数.
	 * 
	 * @param storageDay the new 在库天数
	 */
	public void setStorageDay(String storageDay) {
		this.storageDay = storageDay;
	}

	/**
	 * 获取 查询方式.
	 * 
	 * @return the 查询方式
	 */
	public String getSelectType() {
		return selectType;
	}

	/**
	 * 设置 查询方式.
	 * 
	 * @param selectType the new 查询方式
	 */
	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	/**
	 * 获取 运单通知历史记录/批量通知.
	 * 
	 * @return the 运单通知历史记录/批量通知
	 */
	public List<NotificationEntity> getNotificationEntityList() {
		return notificationEntityList;
	}

	/**
	 * 设置 运单通知历史记录/批量通知.
	 * 
	 * @param notificationEntityList the new 运单通知历史记录/批量通知
	 */
	public void setNotificationEntityList(List<NotificationEntity> notificationEntityList) {
		this.notificationEntityList = notificationEntityList;
	}

	/**
	 * 获取 客户通知信息.
	 * 
	 * @return the 客户通知信息
	 */
	public NotificationEntity getNotificationEntity() {
		return notificationEntity;
	}

	/**
	 * 设置 客户通知信息.
	 * 
	 * @param notificationEntity the new 客户通知信息
	 */
	public void setNotificationEntity(NotificationEntity notificationEntity) {
		this.notificationEntity = notificationEntity;
	}

	/**
	 * 获取 发票信息.
	 * 
	 * @return the 发票信息
	 */
	public InvoiceInfomationEntity getInvoiceInfomationEntity() {
		return invoiceInfomationEntity;
	}

	/**
	 * 设置 发票信息.
	 * 
	 * @param invoiceInfomationEntity the new 发票信息
	 */
	public void setInvoiceInfomationEntity(InvoiceInfomationEntity invoiceInfomationEntity) {
		this.invoiceInfomationEntity = invoiceInfomationEntity;
	}

	/**
	 * 获取 运单通知信息.
	 * 
	 * @return the 运单通知信息
	 */
	public NotifyCustomerDto getNotifyCustomerDto() {
		return notifyCustomerDto;
	}

	/**
	 * 设置 运单通知信息.
	 * 
	 * @param notifyCustomerDto the new 运单通知信息
	 */
	public void setNotifyCustomerDto(NotifyCustomerDto notifyCustomerDto) {
		this.notifyCustomerDto = notifyCustomerDto;
	}

	/**
	 * 获取 选择的运单号列表.
	 * 
	 * @return the 选择的运单号列表
	 */
	public String getWaybillNos() {
		return waybillNos;
	}

	/**
	 * 设置 选择的运单号列表.
	 * 
	 * @param waybillNos the new 选择的运单号列表
	 */
	public void setWaybillNos(String waybillNos) {
		this.waybillNos = waybillNos;
	}

	/**
	 * 获取 默认查询中初始化时间.
	 * 
	 * @return the 默认查询中初始化时间
	 */
	public Date getDeliverDateDef() {
		return deliverDateDef;
	}

	/**
	 * 设置 默认查询中初始化时间.
	 * 
	 * @param deliverDateDef the new 默认查询中初始化时间
	 */
	public void setDeliverDateDef(Date deliverDateDef) {
		this.deliverDateDef = deliverDateDef;
	}

	/**
	 * 获取 默认异常处理状态.
	 * 
	 * @return the 默认异常处理状态
	 */
	public String[] getExceptionStatus() {
		return exceptionStatus;
	}

	/**
	 * 设置 默认异常处理状态.
	 * 
	 * @param exceptionStatus the new 默认异常处理状态
	 */
	public void setExceptionStatus(String[] exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}

	/**
	 * 获取 默认车辆到达状态.
	 * 
	 * @return the 默认车辆到达状态
	 */
	public String getTaskStatus() {
		return taskStatus;
	}

	/**
	 * 设置 默认车辆到达状态.
	 * 
	 * @param taskStatus the new 默认车辆到达状态
	 */
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	/**
	 * 获取 默认不查询运单产品.
	 * 
	 * @return the 默认不查询运单产品
	 */
	public String[] getProductCodes() {
		return productCodes;
	}

	/**
	 * 设置 默认不查询运单产品.
	 * 
	 * @param productCodes the new 默认不查询运单产品
	 */
	public void setProductCodes(String[] productCodes) {
		this.productCodes = productCodes;
	}

	/**
	 * 获取 默认异常类型.
	 * 
	 * @return the 默认异常类型
	 */
	public String getExceptionType() {
		return exceptionType;
	}

	/**
	 * 设置 默认异常类型.
	 * 
	 * @param exceptionType the new 默认异常类型
	 */
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	/**
	 * 获取 默认异常环节1.
	 * 
	 * @return the 默认异常环节1
	 */
	public String getExceptionLink1() {
		return exceptionLink1;
	}

	/**
	 * 设置 默认异常环节1.
	 * 
	 * @param exceptionLink1 the new 默认异常环节1
	 */
	public void setExceptionLink1(String exceptionLink1) {
		this.exceptionLink1 = exceptionLink1;
	}

	/**
	 * 获取 默认异常环节2.
	 * 
	 * @return the 默认异常环节2
	 */
	public String getExceptionLink2() {
		return exceptionLink2;
	}

	/**
	 * 设置 默认异常环节2.
	 * 
	 * @param exceptionLink2 the new 默认异常环节2
	 */
	public void setExceptionLink2(String exceptionLink2) {
		this.exceptionLink2 = exceptionLink2;
	}

	/**
	 * 获取 默认运单版本.
	 * 
	 * @return the 默认运单版本
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 设置 默认运单版本.
	 * 
	 * @param active the new 默认运单版本
	 */
	public void setActive(String active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "NotifyCustomerConditionDto [waybillNo=" + waybillNo + ", handoverNo=" + handoverNo + ", vehicleAssembleNo=" + vehicleAssembleNo + ", receiveMethod=" + receiveMethod + ", storageDay=" + storageDay + ", arriveDepart=" + arriveDepart
				+ ", productCode=" + productCode + ", noticeResult=" + noticeResult + ", inStockTimeFrom=" + inStockTimeFrom + ", inStockTimeTo=" + inStockTimeTo + ", planArriveTimeFrom=" + planArriveTimeFrom + ", planArriveTimeTo="
				+ planArriveTimeTo + ", lastLoadOrgCode=" + lastLoadOrgCode + ", receiveMethodTmp=" + receiveMethodTmp + ", notificationTypeTmp=" + notificationTypeTmp + ", deliverDateTmp=" + deliverDateTmp + ", deliverDateDef=" + deliverDateDef
				+ ", paidMethod=" + paidMethod + ", wbrStatus=" + wbrStatus + ", selectType=" + selectType + ", waybillNos=" + waybillNos + ", exceptionType=" + exceptionType + ", exceptionLink1=" + exceptionLink1 + ", exceptionLink2="
				+ exceptionLink2 + ", exceptionStatus=" + Arrays.toString(exceptionStatus) + ", taskStatus=" + taskStatus + ", productCodes=" + Arrays.toString(productCodes) + ", active=" + active + ", notifyCustomerDto=" + notifyCustomerDto
				+ ", notificationEntityList=" + notificationEntityList + ", notificationEntity=" + notificationEntity + ", invoiceInfomationEntity=" + invoiceInfomationEntity + "]";
	}

	/**
	 * 获取 通知情况.
	 * 
	 * @return the 通知情况
	 */
	public String getNoticeResult() {
		return noticeResult;
	}

	/**
	 * 设置 通知情况.
	 * 
	 * @param noticeResult the new 通知情况
	 */
	public void setNoticeResult(String noticeResult) {
		this.noticeResult = noticeResult;
	}

	/**
	 * 获取 最后库存code.
	 * 
	 * @return the 最后库存code
	 */
	public String getEndStockOrgCode() {
		return endStockOrgCode;
	}

	/**
	 * 设置 最后库存code.
	 * 
	 * @param endStockOrgCode the new 最后库存code
	 */
	public void setEndStockOrgCode(String endStockOrgCode) {
		this.endStockOrgCode = endStockOrgCode;
	}

	/**
	 * 获取 库区.
	 * 
	 * @return the 库区
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	/**
	 * 设置 库区.
	 * 
	 * @param goodsAreaCode the new 库区
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	public int getStorageDayStatus() {
		return storageDayStatus;
	}

	public void setStorageDayStatus(int storageDayStatus) {
		this.storageDayStatus = storageDayStatus;
	}

	public String[] getArrayWaybillNos() {
		return arrayWaybillNos;
	}

	public void setArrayWaybillNos(String[] arrayWaybillNos) {
		this.arrayWaybillNos = arrayWaybillNos;
	}

	public String getArriveTimeFrom() {
		return arriveTimeFrom;
	}

	public void setArriveTimeFrom(String arriveTimeFrom) {
		this.arriveTimeFrom = arriveTimeFrom;
	}

	public String getArriveTimeTo() {
		return arriveTimeTo;
	}

	public void setArriveTimeTo(String arriveTimeTo) {
		this.arriveTimeTo = arriveTimeTo;
	}

	public String getIsQueryDeliveyDate() {
		return isQueryDeliveyDate;
	}

	public void setIsQueryDeliveyDate(String isQueryDeliveyDate) {
		this.isQueryDeliveyDate = isQueryDeliveyDate;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}


	public Long getTotalVotes() {
		return totalVotes;
	}

	public void setTotalVotes(Long totalVotes) {
		this.totalVotes = totalVotes;
	}

	public Integer getGoodsQtyTotals() {
		return goodsQtyTotals;
	}

	public void setGoodsQtyTotals(Integer goodsQtyTotals) {
		this.goodsQtyTotals = goodsQtyTotals;
	}


	public BigDecimal getGoodsWeightTotals() {
		return goodsWeightTotals;
	}

	public void setGoodsWeightTotals(BigDecimal goodsWeightTotals) {
		this.goodsWeightTotals = goodsWeightTotals;
	}

	public BigDecimal getGoodsvolumeTotals() {
		return goodsvolumeTotals;
	}

	public void setGoodsvolumeTotals(BigDecimal goodsvolumeTotals) {
		this.goodsvolumeTotals = goodsvolumeTotals;
	}

	public List<String> getAfStatus() {
		return afStatus;
	}

	public void setAfStatus(List<String> afStatus) {
		this.afStatus = afStatus;
	}

	public List<String> getArrStatus() {
		return arrStatus;
	}

	public void setArrStatus(List<String> arrStatus) {
		this.arrStatus = arrStatus;
	}

	public String[] getProductCodesContain() {
		return productCodesContain;
	}

	public void setProductCodesContain(String[] productCodesContain) {
		this.productCodesContain = productCodesContain;
	}

	public String getIsTogetherSend() {
		return isTogetherSend;
	}

	public void setIsTogetherSend(String isTogetherSend) {
		this.isTogetherSend = isTogetherSend;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}
	public List<String> getGoodsAreaCodes() {
		return goodsAreaCodes;
	}
	public void setGoodsAreaCodes(List<String> goodsAreaCodes) {
		this.goodsAreaCodes = goodsAreaCodes;
	}

	public String getDeliverProv() {
		return deliverProv;
	}

	public void setDeliverProv(String deliverProv) {
		this.deliverProv = deliverProv;
	}

	public String getDeliverCity() {
		return deliverCity;
	}

	public void setDeliverCity(String deliverCity) {
		this.deliverCity = deliverCity;
	}

	public String getDeliverDistCode() {
		return deliverDistCode;
	}

	public void setDeliverDistCode(String deliverDistCode) {
		this.deliverDistCode = deliverDistCode;
	}

	public String getReceiveMethodCon() {
		return receiveMethodCon;
	}

	public void setReceiveMethodCon(String receiveMethodCon) {
		this.receiveMethodCon = receiveMethodCon;
	}

	public String[] getDeliverDistCodes() {
		return deliverDistCodes;
	}

	public void setDeliverDistCodes(String[] deliverDistCodes) {
		this.deliverDistCodes = deliverDistCodes;
	}

	public String[] getReceiveMethodCons() {
		return receiveMethodCons;
	}

	public void setReceiveMethodCons(String[] receiveMethodCons) {
		this.receiveMethodCons = receiveMethodCons;
	}

	public String[] getProductCodeCons() {
		return productCodeCons;
	}

	public void setProductCodeCons(String[] productCodeCons) {
		this.productCodeCons = productCodeCons;
	}

	public String[] getVehicleAssembleNos() {
		return vehicleAssembleNos;
	}

	public void setVehicleAssembleNos(String[] vehicleAssembleNos) {
		this.vehicleAssembleNos = vehicleAssembleNos;
	}
	public String getBatchStowage() {
		return batchStowage;
	}

	public void setBatchStowage(String batchStowage) {
		this.batchStowage = batchStowage;
	}
}