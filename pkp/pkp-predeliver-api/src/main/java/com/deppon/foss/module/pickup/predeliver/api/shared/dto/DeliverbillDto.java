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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/DeliverbillDto.java
 * 
 * FILE NAME        	: DeliverbillDto.java
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
import java.util.Date;
import java.util.List;

/**
 * 派送单Dto.
 * 
 * @author ibm- wangxiexu
 * @date 2012-10-18 下午1:50:57
 * @since
 * @version
 */
public class DeliverbillDto implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/** 派送单ID. */
	private String id;

	/** 派送单编号. */
	private String deliverbillNo;

	/** 车辆车牌号. */
	private String vehicleNo;

	/** 司机姓名. */
	private String driverName;

	/** 司机工号. */
	private String driverCode;

	/** 司机电话号码. */
	private String driverTel;

	/** 派送单状态. */
	private String status;

	/** 查询条件 提交开始时间. */
	private Date submitTimeBegin;

	/** 查询条件 提交结束时间. */
	private Date submitTimeEnd;

	/** 查询条件 创建开始时间. */
	private Date createTimeBegin;

	/** 查询条件 创建结束时间. */
	private Date createTimeEnd;

	/** 车队服务外场. */
	private String transferCenter;

	/** 提交人. */
	private String createUserName;

	/** 提交时间. */
	private Date submitTime;

	/** 确认时间. */
	private Date operateTime;

	/** 创建部门. */
	private String createOrgName;

	/** 创建部门编码. */
	private String createOrgCode;

	/** 总重量. */
	private BigDecimal weightTotal;

	/** 总体积. */
	private BigDecimal volumeTotal;

	/** 派送成功票数. */
	private Long deliverWaybillQty;

	/** 派送拉回票数. */
	private Long pullbackWaybillQty;

	/** 部门Code. */
	private String orgCode;

	/** 总票数. */
	private String totalGoodsQty;

	/** 总重量. */
	private BigDecimal totalGoodsWeight;

	/** 总体积. */
	private BigDecimal totalGoodsVolume;

	/** 总到付金额. */
	private BigDecimal payAmountTotal;

	/**
	 * 派车类型
	 */
	private String deliverType;
	/** 确认排单件数. */
	private Integer arrangeGoodsQty;
	/**
	 * 派送单状态
	 */
	private List<String> deliverbillStatus;

	private String isExpress;

	/**
	 * 条件：装车完成时间开始
	 */
	private Date loadTimeBegin;
	
	/**
	 * 条件：装车完成时间结束
	 */
	private Date loadTimeEnd;
	
	/**
	 * 装车完成时间
	 */
	private Date loadEndTime;
	
	/**
	 * 是否发送过短信 0：没有      1：已发送
	 */
	private String isSendSMS;
	

	/**
	 *  收货人是否大客户.
	 */
	private String receiveBigCustomer; 
	/**
	 * 收货客户地址备注
	 */
	private String receiveCustomerAddressNote;
	/**
	 * 收货省份
	 */
	private String receiveCustomerProvCode;

	/**
	 * 收货市
	 */
	private String receiveCustomerCityCode;

	/**
	 * 收货区
	 */
	private String receiveCustomerDistCode;

	/**
	 * 收货具体地址
	 */
	private String receiveCustomerAddress;
	
	/**
	 * 差异报告类型
	 */
	private  String  gapType;

	//派送单号集合
	private  List<String> deliverList;
	
	public String getGapType() {
		return gapType;
	}

	public void setGapType(String gapType) {
		this.gapType = gapType;
	}

	public List<String> getDeliverList() {
		return deliverList;
	}

	public void setDeliverList(List<String> deliverList) {
		this.deliverList = deliverList;
	}

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
	 * Gets the deliverbill no.
	 *
	 * @return the deliverbillNo
	 */
	public String getDeliverbillNo() {
		return deliverbillNo;
	}

	/**
	 * Sets the deliverbill no.
	 *
	 * @param deliverbillNo the deliverbillNo to see
	 */
	public void setDeliverbillNo(String deliverbillNo) {
		this.deliverbillNo = deliverbillNo;
	}

	public String getTotalGoodsQty() {
		return totalGoodsQty;
	}

	public void setTotalGoodsQty(String totalGoodsQty) {
		this.totalGoodsQty = totalGoodsQty;
	}

	public BigDecimal getTotalGoodsWeight() {
		return totalGoodsWeight;
	}

	public void setTotalGoodsWeight(BigDecimal totalGoodsWeight) {
		this.totalGoodsWeight = totalGoodsWeight;
	}

	public BigDecimal getTotalGoodsVolume() {
		return totalGoodsVolume;
	}

	public void setTotalGoodsVolume(BigDecimal totalGoodsVolume) {
		this.totalGoodsVolume = totalGoodsVolume;
	}

	/**
	 * Gets the vehicle no.
	 * 
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * Sets the vehicle no.
	 * 
	 * @param vehicleNo
	 *            the vehicleNo to see
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * Gets the driver name.
	 * 
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * Sets the driver name.
	 * 
	 * @param driverName
	 *            the driverName to see
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status
	 *            the status to see
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the submit time begin.
	 * 
	 * @return the submitTimeBegin
	 */
	public Date getSubmitTimeBegin() {
		return submitTimeBegin;
	}

	/**
	 * Sets the submit time begin.
	 * 
	 * @param submitTimeBegin
	 *            the submitTimeBegin to see
	 */
	public void setSubmitTimeBegin(Date submitTimeBegin) {
		this.submitTimeBegin = submitTimeBegin;
	}

	/**
	 * Gets the submit time end.
	 * 
	 * @return the submitTimeEnd
	 */
	public Date getSubmitTimeEnd() {
		return submitTimeEnd;
	}

	/**
	 * Sets the submit time end.
	 * 
	 * @param submitTimeEnd
	 *            the submitTimeEnd to see
	 */
	public void setSubmitTimeEnd(Date submitTimeEnd) {
		this.submitTimeEnd = submitTimeEnd;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the id to see
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the create user name.
	 * 
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * Sets the create user name.
	 * 
	 * @param createUserName
	 *            the createUserName to see
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * Gets the submit time.
	 * 
	 * @return the submitTime
	 */
	public Date getSubmitTime() {
		return submitTime;
	}

	/**
	 * Sets the submit time.
	 * 
	 * @param submitTime
	 *            the submitTime to see
	 */
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	/**
	 * Gets the create time begin.
	 * 
	 * @return the createTimeBegin
	 */
	public Date getCreateTimeBegin() {
		return createTimeBegin;
	}

	/**
	 * Sets the create time begin.
	 * 
	 * @param createTimeBegin
	 *            the createTimeBegin to see
	 */
	public void setCreateTimeBegin(Date createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}

	/**
	 * Gets the create time end.
	 * 
	 * @return the createTimeEnd
	 */
	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	/**
	 * Sets the create time end.
	 * 
	 * @param createTimeEnd
	 *            the createTimeEnd to see
	 */
	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	/**
	 * Gets the operate time.
	 * 
	 * @return the operateTime
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * Sets the operate time.
	 * 
	 * @param operateTime
	 *            the operateTime to see
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * Gets the driver code.
	 * 
	 * @return the driverCode
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * Sets the driver code.
	 * 
	 * @param driverCode
	 *            the driverCode to see
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * Gets the create org name.
	 * 
	 * @return the create org name
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}

	/**
	 * Sets the create org name.
	 * 
	 * @param createOrgName
	 *            the new create org name
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	/**
	 * Gets the create org code.
	 * 
	 * @return the create org code
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * Sets the create org code.
	 * 
	 * @param createOrgCode
	 *            the new create org code
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * Gets the weight total.
	 * 
	 * @return the weight total
	 */
	public BigDecimal getWeightTotal() {
		return weightTotal;
	}

	/**
	 * Sets the weight total.
	 * 
	 * @param weightTotal
	 *            the new weight total
	 */
	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}

	/**
	 * Gets the volume total.
	 * 
	 * @return the volume total
	 */
	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}

	/**
	 * Sets the volume total.
	 * 
	 * @param volumeTotal
	 *            the new volume total
	 */
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}

	/**
	 * Gets the deliver waybill qty.
	 * 
	 * @return the deliver waybill qty
	 */
	public Long getDeliverWaybillQty() {
		return deliverWaybillQty;
	}

	/**
	 * Sets the deliver waybill qty.
	 * 
	 * @param deliverWaybillQty
	 *            the new deliver waybill qty
	 */
	public void setDeliverWaybillQty(Long deliverWaybillQty) {
		this.deliverWaybillQty = deliverWaybillQty;
	}

	/**
	 * Gets the pullback waybill qty.
	 * 
	 * @return the pullback waybill qty
	 */
	public Long getPullbackWaybillQty() {
		return pullbackWaybillQty;
	}

	/**
	 * Sets the pullback waybill qty.
	 * 
	 * @param pullbackWaybillQty
	 *            the new pullback waybill qty
	 */
	public void setPullbackWaybillQty(Long pullbackWaybillQty) {
		this.pullbackWaybillQty = pullbackWaybillQty;
	}

	/**
	 * Gets the org code.
	 * 
	 * @return the org code
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 * 
	 * @param orgCode
	 *            the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getTransferCenter() {
		return transferCenter;
	}

	public void setTransferCenter(String transferCenter) {
		this.transferCenter = transferCenter;
	}

	public String getDeliverType() {
		return deliverType;
	}

	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getDriverTel() {
		return driverTel;
	}

	public void setDriverTel(String driverTel) {
		this.driverTel = driverTel;
	}

	public BigDecimal getPayAmountTotal() {
		return payAmountTotal;
	}

	public void setPayAmountTotal(BigDecimal payAmountTotal) {
		this.payAmountTotal = payAmountTotal;
	}

	public Integer getArrangeGoodsQty() {
		return arrangeGoodsQty;
	}

	public void setArrangeGoodsQty(Integer arrangeGoodsQty) {
		this.arrangeGoodsQty = arrangeGoodsQty;
	}

	public List<String> getDeliverbillStatus() {
		return deliverbillStatus;
	}

	public void setDeliverbillStatus(List<String> deliverbillStatus) {
		this.deliverbillStatus = deliverbillStatus;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}

	public Date getLoadTimeBegin() {
		return loadTimeBegin;
	}

	public void setLoadTimeBegin(Date loadTimeBegin) {
		this.loadTimeBegin = loadTimeBegin;
	}

	public Date getLoadTimeEnd() {
		return loadTimeEnd;
	}

	public void setLoadTimeEnd(Date loadTimeEnd) {
		this.loadTimeEnd = loadTimeEnd;
	}

	public Date getLoadEndTime() {
		return loadEndTime;
	}
	
	public void setLoadEndTime(Date loadEndTime) {
		this.loadEndTime = loadEndTime;
	}

	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}

	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}

	public String getReceiveCustomerProvCode() {
		return receiveCustomerProvCode;
	}

	public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}

	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}

	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}

	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}

	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}

	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	public String getIsSendSMS() {
		return isSendSMS;
	}

	public void setIsSendSMS(String isSendSMS) {
		this.isSendSMS = isSendSMS;
	}
	
}