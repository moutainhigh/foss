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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/domain/DeliverbillEntity.java
 * 
 * FILE NAME        	: DeliverbillEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 派送单实体.
 *
 * @author ibm-
 * 		wangxiexu
 * @date 2012-10-28 
 * 		下午5:15:23
 * @since
 * @version
 */
public class DeliverbillEntity extends BaseEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 67456062296967139L;

	/** 派送单号. */
	private String deliverbillNo;

	/** 车牌号. */
	private String vehicleNo;

	/** 司机工号. */
	private String driverCode;

	/** 司机姓名. */
	private String driverName;

	/** 总票数. */
	private Integer waybillQtyTotal;

	/** 总件数. */
	private Integer goodsQtyTotal;

	/** 总到付金额. */
	private BigDecimal payAmountTotal;

	/** 总重量. */
	private BigDecimal weightTotal;

	/** 总体积. */
	private BigDecimal volumeTotal;

	/** 创建人. */
	private String createUserName;

	/** 创建人编码. */
	private String createUserCode;

	/** 创建时间(提交时间). */
	private Date submitTime;

	/** 车辆放行ID. */
	private String tOptTruckDepartId;

	/** 状态. */
	private String status;
	
	/** 派送单状态. */
	private String delStatus;

	public String getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	/** 创建部门. */
	private String createOrgName;

	/** 创建部门编码. */
	private String createOrgCode;

	/** 操作人. */
	private String operator;

	/** 操作人编码. */
	private String operatorCode;

	/** 操作部门. */
	private String operateOrgName;

	/** 操作部门编码. */
	private String operateOrgCode;

	/** 操作时间(确认时间). */
	private Date operateTime;

	/** 卡货票数. */
	private Integer fastWaybillQty;

	/** 车队服务外场. */
	private String transferCenter;

	/** 币种. */
	private String currencyCode;

	/** 派送部. */
	private String deliveryDepartment;
	
	/** 司机电话号码. */
	private String driverTel;
	
	/** 车队. */
	private String motorcade;
	
	/** 装载率(重量/体积). */
	private String loadingRate;
	
	
	/**
	 * 派车类型
	 */
	private String deliverType;
	
	/**
	 * 是否为快递派送单
	 */
	private String isExpress;
	
	/**
	 * 是否发送过短信 0：没有      1：已发送
	 */
	private String isSendSMS;
	
	/**
	 * 是否是新派送单 Y：是   null：不是
 	 */
	private String createType;
	
	/**
	 * 是否打印到达联 Y：是   null：不是
 	 */
	private String isArriveSheet;
	
	/**
	 * 预计送货时间
	 */
	private Date deliverDate;
	
	/**
	 * 排程二期-出车任务
	 */
	private String carTaskinfo;
	
	/**
	 * 排程二期-班次
	 */
	private Integer frequencyno;
	
	/**
	 * 排程二期-预计出车时间
	 */
	private String preCartaskTime;
	
	/**
	 * 排程二期-带货部门编码
	 */
	private String takeGoodsDeptcode;
	
	/**
	 * 排程二期-带货部门
	 */
	private String takeGoodsDeptname;
	
	/**
	 * 排程二期-带货(F)
	 */
	private BigDecimal expectedbringvolume;
	
	/**
	 * 排程二期-转货部门编码
	 */
	private String transferDeptcode;
	
	/**
	 * 排程二期-转货部门
	 */
	private String transferDeptname;

	/**
	 * 排序总距离
	 */
	private double totalDistance;
	
	/**
	 * 排序方式
	 */
	private String autoSortCalculateType;
	
	/**
	 * 派送单修改时的时间
	 */
	private Date modifyTime;
	
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
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
	 * @param vehicleNo the vehicleNo to see
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
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
	 * @param driverCode the driverCode to see
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
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
	 * @param driverName the driverName to see
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * Gets the waybill qty total.
	 *
	 * @return the waybillQtyTotal
	 */
	public Integer getWaybillQtyTotal() {
		return waybillQtyTotal;
	}

	/**
	 * Sets the waybill qty total.
	 *
	 * @param waybillQtyTotal the waybillQtyTotal to see
	 */
	public void setWaybillQtyTotal(Integer waybillQtyTotal) {
		this.waybillQtyTotal = waybillQtyTotal;
	}

	/**
	 * Gets the goods qty total.
	 *
	 * @return the goodsQtyTotal
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * Sets the goods qty total.
	 *
	 * @param goodsQtyTotal the goodsQtyTotal to see
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * Gets the pay amount total.
	 *
	 * @return the payAmountTotal
	 */
	public BigDecimal getPayAmountTotal() {
		return payAmountTotal;
	}

	/**
	 * Sets the pay amount total.
	 *
	 * @param payAmountTotal the payAmountTotal to see
	 */
	public void setPayAmountTotal(BigDecimal payAmountTotal) {
		this.payAmountTotal = payAmountTotal;
	}

	/**
	 * Gets the weight total.
	 *
	 * @return the weightTotal
	 */
	public BigDecimal getWeightTotal() {
		return weightTotal;
	}

	/**
	 * Sets the weight total.
	 *
	 * @param weightTotal the weightTotal to see
	 */
	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}

	/**
	 * Gets the volume total.
	 *
	 * @return the volumeTotal
	 */
	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}

	/**
	 * Sets the volume total.
	 *
	 * @param volumeTotal the volumeTotal to see
	 */
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
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
	 * @param createUserName the createUserName to see
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * Gets the create user code.
	 *
	 * @return the createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * Sets the create user code.
	 *
	 * @param createUserCode the createUserCode to see
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
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
	 * @param submitTime the submitTime to see
	 */
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	/**
	 * Gets the t opt truck depart id.
	 *
	 * @return the tOptTruckDepartId
	 */
	public String gettOptTruckDepartId() {
		return tOptTruckDepartId;
	}

	/**
	 * Sets the t opt truck depart id.
	 *
	 * @param tOptTruckDepartId the tOptTruckDepartId to see
	 */
	public void settOptTruckDepartId(String tOptTruckDepartId) {
		this.tOptTruckDepartId = tOptTruckDepartId;
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
	 * @param status the status to see
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the create org name.
	 *
	 * @return the createOrgName
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}

	/**
	 * Sets the create org name.
	 *
	 * @param createOrgName the createOrgName to see
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	/**
	 * Gets the create org code.
	 *
	 * @return the createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * Sets the create org code.
	 *
	 * @param createOrgCode the createOrgCode to see
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * Gets the operator.
	 *
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * Sets the operator.
	 *
	 * @param operator the operator to see
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * Gets the operator code.
	 *
	 * @return the operatorCode
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * Sets the operator code.
	 *
	 * @param operatorCode the operatorCode to see
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * Gets the operate org name.
	 *
	 * @return the operateOrgName
	 */
	public String getOperateOrgName() {
		return operateOrgName;
	}

	/**
	 * Sets the operate org name.
	 *
	 * @param operateOrgName the operateOrgName to see
	 */
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	/**
	 * Gets the operate org code.
	 *
	 * @return the operateOrgCode
	 */
	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	/**
	 * Sets the operate org code.
	 *
	 * @param operateOrgCode the operateOrgCode to see
	 */
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
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
	 * @param operateTime the operateTime to see
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * Gets the fast waybill qty.
	 *
	 * @return the fastWaybillQty
	 */
	public Integer getFastWaybillQty() {
		return fastWaybillQty;
	}

	/**
	 * Sets the fast waybill qty.
	 *
	 * @param fastWaybillQty the fastWaybillQty to see
	 */
	public void setFastWaybillQty(Integer fastWaybillQty) {
		this.fastWaybillQty = fastWaybillQty;
	}

	/**
	 * Gets the delivery department.
	 *
	 * @return the deliveryDepartment
	 */
	public String getDeliveryDepartment() {
		return deliveryDepartment;
	}

	/**
	 * Sets the delivery department.
	 *
	 * @param deliveryDepartment the deliveryDepartment to see
	 */
	public void setDeliveryDepartment(String deliveryDepartment) {
		this.deliveryDepartment = deliveryDepartment;
	}

	/**
	 * Gets the driver tel.
	 *
	 * @return the driverTel
	 */
	public String getDriverTel() {
		return driverTel;
	}

	/**
	 * Sets the driver tel.
	 *
	 * @param driverTel the driverTel to see
	 */
	public void setDriverTel(String driverTel) {
		this.driverTel = driverTel;
	}

	/**
	 * Gets the motorcade.
	 *
	 * @return the motorcade
	 */
	public String getMotorcade() {
		return motorcade;
	}

	/**
	 * Sets the motorcade.
	 *
	 * @param motorcade the motorcade to see
	 */
	public void setMotorcade(String motorcade) {
		this.motorcade = motorcade;
	}

	/**
	 * Gets the loading rate.
	 *
	 * @return the loadingRate
	 */
	public String getLoadingRate() {
		return loadingRate;
	}

	/**
	 * Sets the loading rate.
	 *
	 * @param loadingRate the loadingRate to see
	 */
	public void setLoadingRate(String loadingRate) {
		this.loadingRate = loadingRate;
	}

	/**
	 * Gets the transfer center.
	 *
	 * @return the transferCenter
	 */
	public String getTransferCenter() {
		return transferCenter;
	}

	/**
	 * Sets the transfer center.
	 *
	 * @param transferCenter the transferCenter to see
	 */
	public void setTransferCenter(String transferCenter) {
		this.transferCenter = transferCenter;
	}

	/**
	 * Gets the currency code.
	 *
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * Sets the currency code.
	 *
	 * @param currencyCode the currencyCode to see
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getDeliverType() {
		return deliverType;
	}

	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}

	public String getIsSendSMS() {
		return isSendSMS;
	}

	public void setIsSendSMS(String isSendSMS) {
		this.isSendSMS = isSendSMS;
	}

	public String getCreateType() {
		return createType;
	}

	public void setCreateType(String createType) {
		this.createType = createType;
	}

	public String getIsArriveSheet() {
		return isArriveSheet;
	}

	public void setIsArriveSheet(String isArriveSheet) {
		this.isArriveSheet = isArriveSheet;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getCarTaskinfo() {
		return carTaskinfo;
	}

	public void setCarTaskinfo(String carTaskinfo) {
		this.carTaskinfo = carTaskinfo;
	}

	public Integer getFrequencyno() {
		return frequencyno;
	}

	public void setFrequencyno(Integer frequencyno) {
		this.frequencyno = frequencyno;
	}

	public String getPreCartaskTime() {
		return preCartaskTime;
	}

	public void setPreCartaskTime(String preCartaskTime) {
		this.preCartaskTime = preCartaskTime;
	}

	public String getTakeGoodsDeptcode() {
		return takeGoodsDeptcode;
	}

	public void setTakeGoodsDeptcode(String takeGoodsDeptcode) {
		this.takeGoodsDeptcode = takeGoodsDeptcode;
	}

	public String getTakeGoodsDeptname() {
		return takeGoodsDeptname;
	}

	public void setTakeGoodsDeptname(String takeGoodsDeptname) {
		this.takeGoodsDeptname = takeGoodsDeptname;
	}

	public BigDecimal getExpectedbringvolume() {
		return expectedbringvolume;
	}

	public void setExpectedbringvolume(BigDecimal expectedbringvolume) {
		this.expectedbringvolume = expectedbringvolume;
	}

	public String getTransferDeptcode() {
		return transferDeptcode;
	}

	public void setTransferDeptcode(String transferDeptcode) {
		this.transferDeptcode = transferDeptcode;
	}

	public String getTransferDeptname() {
		return transferDeptname;
	}

	public void setTransferDeptname(String transferDeptname) {
		this.transferDeptname = transferDeptname;
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public String getAutoSortCalculateType() {
		return autoSortCalculateType;
	}

	public void setAutoSortCalculateType(String autoSortCalculateType) {
		this.autoSortCalculateType = autoSortCalculateType;
	}
	
}