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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/AbandonedGoodsDetailDto.java
 * 
 * FILE NAME        	: AbandonedGoodsDetailDto.java
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


/**
 * 综合派送DTO
 * 
 * @author ibm-meiying
 * @date 2013-06-24 下午3:41:41
 */
public class ArriveDeliverDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String waybillNo;/*单号*/
	private String  arrivesheetNo;/*到达联编号*/
	private String  receiveMethod;/*开单提货方式*/
	private Date arriveTime;/*到达时间*/
	private Date inStockTime; /*入库时间*/
    private String  receiveCustomerContact;/*收货人*/
    private Integer goodsQtyTotal;/*开单件数*/
    private BigDecimal goodsWeightTotal; /*开单重量*/
    private BigDecimal goodsVolumeTotal; /*开单体积*/
    private BigDecimal codAmount; /*代收货款*/
    private BigDecimal toPayAmount; /*到付金额*/
    private BigDecimal repaymentCodeAmount; /*已付金额 结算付的金额，如有多次是多次的总和，没有就是显示为0*/
    private String settleStatus; /*是否已结清*/
    private String receiveOrgCode;/*出发部门*/ 
    private String driverName;/*送货人或代理*/ 
    private Date billTime;/*收货日期*/
    private String receiveCustomerAddress;/*收货具体地址*/
    private String  receiveCustomerPhone; /*收货人电话*/
    private String  receiveCustomerMobilePhone; /*收货人手机*/
    private Integer arriveGoodsQty; /*到达件数*/
    private String stockType;/*库存状态*/
    private String  orgCode; /*库存部门*/
    private String  notificationResult; /*是否已经通知*/
    private String deliveryMan; /*取货人*/
    private String  returnBillType; /*返单类型*/
    private String  returnBillStatus; /*返单情况*/
    private String  isArrange ; /*是否已排单*/
    private String  deliverbillNo; /*派送单单号*/
    private String  isSign; /*是否签收*/
    private Date signTime; /*签收时间*/
    private String receiveCustomerAddressNote;/*收货具体地址备注*/
    /**
	 * 签收情况
	 */
	private String situation;
	/**
	 * 签收备注
	 */
	private String signNote;
    private BigDecimal deliverGoodsFee; /*送货费*/
    private BigDecimal otherFee; /*其他费用*/
	
	private String cargoName;//货物品名
	private String productCode;/*运输性质*/
	private String insuranceValue;//保价声明价值
 	private String insuranceFee;//保价费
 	
    private String deliverRequire;/*送货要求*/
    private String transportType;//运输类型
    private Integer stockGoodsQty;//库存件数
    /** 收货省份. */
	private String receiveCustomerProvCode;
	
	/** 收货市. */
	private String receiveCustomerCityCode;
	
	/** 收货区. */
	private String receiveCustomerDistCode;
	/**
	 * 收货人省市区具体地址
	 */
	private String receivePCDAddress;
	private BigDecimal prePayAmount; /*预付金额*/
    private Date operateTime;/*送货时间*/
    /** 目的站 */
   	private String targetOrgCode;
   	
    /** 派送情况 */
   	private String deliverbillStatus;
   	private BigDecimal codToPayAmount; /*收款总额*/
   	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getArrivesheetNo() {
		return arrivesheetNo;
	}
	public void setArrivesheetNo(String arrivesheetNo) {
		this.arrivesheetNo = arrivesheetNo;
	}
	public String getReceiveMethod() {
		return receiveMethod;
	}
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}
	public Date getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	public Date getInStockTime() {
		return inStockTime;
	}
	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}
	public BigDecimal getCodAmount() {
		return codAmount;
	}
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}
	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}
	public BigDecimal getRepaymentCodeAmount() {
		return repaymentCodeAmount;
	}
	public void setRepaymentCodeAmount(BigDecimal repaymentCodeAmount) {
		this.repaymentCodeAmount = repaymentCodeAmount;
	}
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}
	public String getReceiveCustomerMobilePhone() {
		return receiveCustomerMobilePhone;
	}
	public void setReceiveCustomerMobilePhone(String receiveCustomerMobilePhone) {
		this.receiveCustomerMobilePhone = receiveCustomerMobilePhone;
	}
	public Integer getArriveGoodsQty() {
		return arriveGoodsQty;
	}
	public void setArriveGoodsQty(Integer arriveGoodsQty) {
		this.arriveGoodsQty = arriveGoodsQty;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getNotificationResult() {
		return notificationResult;
	}
	public void setNotificationResult(String notificationResult) {
		this.notificationResult = notificationResult;
	}
	public String getReturnBillType() {
		return returnBillType;
	}
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}
	public String getReturnBillStatus() {
		return returnBillStatus;
	}
	public void setReturnBillStatus(String returnBillStatus) {
		this.returnBillStatus = returnBillStatus;
	}
	public String getIsArrange() {
		return isArrange;
	}
	public void setIsArrange(String isArrange) {
		this.isArrange = isArrange;
	}
	public String getDeliverbillNo() {
		return deliverbillNo;
	}
	public void setDeliverbillNo(String deliverbillNo) {
		this.deliverbillNo = deliverbillNo;
	}
	public String getIsSign() {
		return isSign;
	}
	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public BigDecimal getDeliverGoodsFee() {
		return deliverGoodsFee;
	}
	public void setDeliverGoodsFee(BigDecimal deliverGoodsFee) {
		this.deliverGoodsFee = deliverGoodsFee;
	}
	public BigDecimal getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}
	public String getDeliveryMan() {
		return deliveryMan;
	}
	public void setDeliveryMan(String deliveryMan) {
		this.deliveryMan = deliveryMan;
	}
	public String getSettleStatus() {
		return settleStatus;
	}
	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}
	public String getStockType() {
		return stockType;
	}
	public void setStockType(String stockType) {
		this.stockType = stockType;
	}
	public String getDeliverRequire() {
		return deliverRequire;
	}
	public void setDeliverRequire(String deliverRequire) {
		this.deliverRequire = deliverRequire;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
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
	public String getReceivePCDAddress() {
		return receivePCDAddress;
	}
	public void setReceivePCDAddress(String receivePCDAddress) {
		this.receivePCDAddress = receivePCDAddress;
	}
	public Date getBillTime() {
		return billTime;
	}
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}
	public Integer getStockGoodsQty() {
		return stockGoodsQty;
	}
	public void setStockGoodsQty(Integer stockGoodsQty) {
		this.stockGoodsQty = stockGoodsQty;
	}
	public BigDecimal getPrePayAmount() {
		return prePayAmount;
	}
	public void setPrePayAmount(BigDecimal prePayAmount) {
		this.prePayAmount = prePayAmount;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getTargetOrgCode() {
		return targetOrgCode;
	}
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}
	public String getDeliverbillStatus() {
		return deliverbillStatus;
	}
	public void setDeliverbillStatus(String deliverbillStatus) {
		this.deliverbillStatus = deliverbillStatus;
	}
	public BigDecimal getCodToPayAmount() {
		return codToPayAmount;
	}
	public void setCodToPayAmount(BigDecimal codToPayAmount) {
		this.codToPayAmount = codToPayAmount;
	}
	public String getSituation() {
		return situation;
	}
	public void setSituation(String situation) {
		this.situation = situation;
	}
	public String getSignNote() {
		return signNote;
	}
	public void setSignNote(String signNote) {
		this.signNote = signNote;
	}

	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getCargoName() {
		return cargoName;
	}
	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}
	public String getInsuranceValue() {
		return insuranceValue;
	}
	public void setInsuranceValue(String insuranceValue) {
		this.insuranceValue = insuranceValue;
	}
	public String getInsuranceFee() {
		return insuranceFee;
	}
	public void setInsuranceFee(String insuranceFee) {
		this.insuranceFee = insuranceFee;
	}
	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}
	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}  
}