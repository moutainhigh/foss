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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/WaybillDto.java
 * 
 * FILE NAME        	: WaybillDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 运单基本信息 Dto
 * @author foss-meiying
 * @date 2012-11-28 上午11:39:56
 * @since
 * @version
 */
public class WaybillDto implements Serializable {
	private static final long serialVersionUID = -2821763880540892569L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 收货人(收货客户名称)
	 */
	private String receiveCustomerName;
	/**
	 *收货客户编码 
	 */
	private String receiveCustomerCode;
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
	 * 收货客户具体地址
	 */
	private String receiveCustomerAddress;
	/**
	 * 收货客户具体地址
	 */
	private String receiveCustomerAddressNote;
	/**
	 * 收货客户电话
	 */
	private String receiveCustomerPhone;
	/**
	 * 到达时间
	 */
	private Date arriveTime;
	/**
	 * 是否通知成功
	 */
	private String notificationResult;
	/**
	 * 上次通知时间
	 */
	private Date notificationTime;
	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmount;
	/**
	 * 代收货款（开单时）
	 */
	private BigDecimal codAmount;
	/**
	 * 运费
	 */
	private BigDecimal transportFee;
	/**
	 * 送货费
	 */
	private BigDecimal deliveryGoodsFee;
	/**
	 * 保价费
	 */
	private BigDecimal insuranceFee;
	/**
	 * 付款方式（出发部门）
	 */
	private String paidMethod;
	/**
	 * 其他费用
	 */
	private BigDecimal otherFee;
	/**
	 * 货物价值
	 */
	private BigDecimal insuranceAmount;
	/**
	 * 库存状态
	 */
	private String stockStatus;
	/**
	 * 派送方式
	 */
	private String receiveMethod;
	/**
	 * 结清状态
	 */
	private String settleStatus;
	/**
	 * 出发时间
	 */
	private Date createTime;
	/**
	 * 始发站
	 */
	private String deliveryCustomerCityCode;
	/**
	 * 始发站名称
	 */
	private String deliveryCustomerCityName;
	/**
	 * 始发部门(收货部门(出发部门))
	 */
	private String receiveOrgCode;
	/**
	 * 始发部门(收货部门(出发部门)) 名称
	 */
	private String receiveOrgName;
	/**
	 * 货物名称
	 */
	private String goodsName;
	/**
	 * 是否整车运单
	 */
	private String isWholeVehicle;
	/**
	 * 件数
	 */
	private Integer goodsQtyTotal;
	/**
	 * 重量
	 */
	private BigDecimal goodsWeightTotal;
	/**
	 * 体积
	 */
	private BigDecimal goodsVolumeTotal;
	/**
	 * 包装
	 */
	private String goodsPackage;
	/**
	 * 发货人(发货客户名称)
	 */
	private String deliveryCustomerName;
	/**
	 * 运输类型
	 */
	private String transportType;
	/**
	 * 运输性质
	 */
	private String productCode;
	/**
	 * 仓储费
	 */
	private BigDecimal storageCharge;
	/**
	 * 已经产生小票记录的仓储费
	 */
	private BigDecimal storageChargeSum;
	/**
	 * 运单的发票标记
	 */
	private String invoice;
	/**
	 * 修改前仓储费
	 */
	private BigDecimal storageChargeOld;
	/**
	 * 修改仓储费原因备注
	 */
	private String updateStorageChargeReason;
	/**
	 * 收货人手机
	 */
	private String receiveCustomerMobilephone;
	/**
	 * 发货人手机
	 */
	private String deliveryCustomerMobilephone;
	/**
	 * 并发控制  --到达未出库件数
	 */
	private Integer oldArriveNotoutGoodsQty;
	/**
	 * 已生成到达联件数
	 */
	private Integer generateGoodsQty;
	/**
	 * 服务器当前时间
	 */
	 private String serviceTime;

	/**
	 * 是否快递
	 */
    private  String isExpress;
    /**
	 * 返单类型
	 */
    /**
     * 返单类别
     */
    private String returnbillType;
    /**
     * 返单状态
     */
    private String returnbillStatus;
    
    /**
	 * 提货网点
	 */
    private String customerpickuporgcode;
    /**
	 * 始发、到达【是否统一结算】、【合同部门】、【催款部门】
	 */
	private String startCentralizedSettlement;
	 /**
     * 返单运单号
     */
    private String returnWaybillNo;
	private String arriveCentralizedSettlement;
	private String  startContractOrgCode;
	private String  arriveContractOrgCode;
	private String  startContractOrgName;
	private String  arriveContractOrgName;
	private String  startReminderOrgCode;
	private String  arriveReminderOrgCode;
	
	/**
	 * 是否是一票多件
	 */
	private String isOneInMore;

  	public String getIsOneInMore() {
		return isOneInMore;
	}

	public void setIsOneInMore(String isOneInMore) {
		this.isOneInMore = isOneInMore;
	}
	
	/**
	 * 收货乡镇(街道)
	 */
	private String receiveCustomerVillageCode;
	
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
	 * Gets the 收货人(收货客户名称).
	 *
	 * @return the 收货人(收货客户名称)
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * Sets the 收货人(收货客户名称).
	 *
	 * @param receiveCustomerName the new 收货人(收货客户名称)
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * Gets the 收货客户具体地址.
	 *
	 * @return the 收货客户具体地址
	 */
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	/**
	 * Sets the 收货客户具体地址.
	 *
	 * @param receiveCustomerAddress the new 收货客户具体地址
	 */
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	/**
	 * Gets the 收货客户电话.
	 *
	 * @return the 收货客户电话
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * Sets the 收货客户电话.
	 *
	 * @param receiveCustomerPhone the new 收货客户电话
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}


	/**
	 * Gets the 送货费.
	 *
	 * @return the 送货费
	 */
	public BigDecimal getDeliveryGoodsFee() {
		return deliveryGoodsFee;
	}

	/**
	 * Sets the 送货费.
	 *
	 * @param deliveryGoodsFee the new 送货费
	 */
	public void setDeliveryGoodsFee(BigDecimal deliveryGoodsFee) {
		this.deliveryGoodsFee = deliveryGoodsFee;
	}

	/**
	 * Gets the 保价费.
	 *
	 * @return the 保价费
	 */
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	/**
	 * Sets the 保价费.
	 *
	 * @param insuranceFee the new 保价费
	 */
	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	/**
	 * Gets the 货物名称.
	 *
	 * @return the 货物名称
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * Sets the 货物名称.
	 *
	 * @param goodsName the new 货物名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * Gets the 件数.
	 *
	 * @return the 件数
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * Sets the 件数.
	 *
	 * @param goodsQtyTotal the new 件数
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * Gets the 重量.
	 *
	 * @return the 重量
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	/**
	 * Sets the 重量.
	 *
	 * @param goodsWeightTotal the new 重量
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	/**
	 * Gets the 体积.
	 *
	 * @return the 体积
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * Sets the 体积.
	 *
	 * @param goodsVolumeTotal the new 体积
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}


	/**
	 * Gets the 派送方式.
	 *
	 * @return the 派送方式
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * Sets the 派送方式.
	 *
	 * @param receiveMethod the new 派送方式
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * Gets the 运输性质.
	 *
	 * @return the 运输性质
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the 运输性质.
	 *
	 * @param productCode the new 运输性质
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * Gets the 到达时间.
	 *
	 * @return the 到达时间
	 */
	public Date getArriveTime() {
		return arriveTime;
	}

	/**
	 * Sets the 到达时间.
	 *
	 * @param arriveTime the new 到达时间
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	

	/**
	 * Gets the 上次通知时间.
	 *
	 * @return the 上次通知时间
	 */
	public Date getNotificationTime() {
		return notificationTime;
	}

	/**
	 * Sets the 上次通知时间.
	 *
	 * @param notificationTime the new 上次通知时间
	 */
	public void setNotificationTime(Date notificationTime) {
		this.notificationTime = notificationTime;
	}

	/**
	 * Gets the 付款方式（出发部门）.
	 *
	 * @return the 付款方式（出发部门）
	 */
	public String getPaidMethod() {
		return paidMethod;
	}

	/**
	 * Sets the 付款方式（出发部门）.
	 *
	 * @param paidMethod the new 付款方式（出发部门）
	 */
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	/**
	 * Gets the 其他费用.
	 *
	 * @return the 其他费用
	 */
	public BigDecimal getOtherFee() {
		return otherFee;
	}

	/**
	 * Sets the 其他费用.
	 *
	 * @param otherFee the new 其他费用
	 */
	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	/**
	 * Gets the 货物价值.
	 *
	 * @return the 货物价值
	 */
	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	/**
	 * Sets the 货物价值.
	 *
	 * @param insuranceAmount the new 货物价值
	 */
	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	/**
	 * Gets the 库存状态.
	 *
	 * @return the 库存状态
	 */
	public String getStockStatus() {
		return stockStatus;
	}

	/**
	 * Sets the 库存状态.
	 *
	 * @param stockStatus the new 库存状态
	 */
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

	/**
	 * Gets the 出发时间.
	 *
	 * @return the 出发时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Sets the 出发时间.
	 *
	 * @param createTime the new 出发时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * Gets the 始发站.
	 *
	 * @return the 始发站
	 */
	public String getDeliveryCustomerCityCode() {
		return deliveryCustomerCityCode;
	}

	/**
	 * Sets the 始发站.
	 *
	 * @param deliveryCustomerCityCode the new 始发站
	 */
	public void setDeliveryCustomerCityCode(String deliveryCustomerCityCode) {
		this.deliveryCustomerCityCode = deliveryCustomerCityCode;
	}

	/**
	 * Gets the 始发部门(收货部门(出发部门)).
	 *
	 * @return the 始发部门(收货部门(出发部门))
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * Sets the 始发部门(收货部门(出发部门)).
	 *
	 * @param receiveOrgCode the new 始发部门(收货部门(出发部门))
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * Gets the 运输类型.
	 *
	 * @return the 运输类型
	 */
	public String getTransportType() {
		return transportType;
	}

	/**
	 * Sets the 运输类型.
	 *
	 * @param transportType the new 运输类型
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	/**
	 * Gets the 是否整车运单.
	 *
	 * @return the 是否整车运单
	 */
	public String getIsWholeVehicle() {
		return isWholeVehicle;
	}

	/**
	 * Sets the 是否整车运单.
	 *
	 * @param isWholeVehicle the new 是否整车运单
	 */
	public void setIsWholeVehicle(String isWholeVehicle) {
		this.isWholeVehicle = isWholeVehicle;
	}

	/**
	 * Gets the 仓储费.
	 *
	 * @return the 仓储费
	 */
	public BigDecimal getStorageCharge() {
		return storageCharge;
	}

	/**
	 * Sets the 仓储费.
	 *
	 * @param storageCharge the new 仓储费
	 */
	public void setStorageCharge(BigDecimal storageCharge) {
		this.storageCharge = storageCharge;
	}

	/**
	 * Gets the 结清状态.
	 *
	 * @return the 结清状态
	 */
	public String getSettleStatus() {
		return settleStatus;
	}

	/**
	 * Sets the 结清状态.
	 *
	 * @param settleStatus the new 结清状态
	 */
	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}

	/**
	 * Gets the 发货人(发货客户名称).
	 *
	 * @return the 发货人(发货客户名称)
	 */
	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	/**
	 * Sets the 发货人(发货客户名称).
	 *
	 * @param deliveryCustomerName the new 发货人(发货客户名称)
	 */
	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	/**
	 * Gets the 收货人手机.
	 *
	 * @return the 收货人手机
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	/**
	 * Sets the 收货人手机.
	 *
	 * @param receiveCustomerMobilephone the new 收货人手机
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * Gets the 发货人手机.
	 *
	 * @return the 发货人手机
	 */
	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	/**
	 * Sets the 发货人手机.
	 *
	 * @param deliveryCustomerMobilephone the new 发货人手机
	 */
	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	/**
	 * Gets the 代收货款（开单时）.
	 *
	 * @return the 代收货款（开单时）
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * Sets the 代收货款（开单时）.
	 *
	 * @param codAmount the new 代收货款（开单时）
	 */
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * Gets the 运费.
	 *
	 * @return the 运费
	 */
	public BigDecimal getTransportFee() {
		return transportFee;
	}

	/**
	 * Sets the 运费.
	 *
	 * @param transportFee the new 运费
	 */
	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}

	/**
	 * Gets the 是否通知成功.
	 *
	 * @return the 是否通知成功
	 */
	public String getNotificationResult() {
		return notificationResult;
	}

	/**
	 * Sets the 是否通知成功.
	 *
	 * @param notificationResult the new 是否通知成功
	 */
	public void setNotificationResult(String notificationResult) {
		this.notificationResult = notificationResult;
	}

	/**
	 * Gets the 始发站名称.
	 *
	 * @return the 始发站名称
	 */
	public String getDeliveryCustomerCityName() {
		return deliveryCustomerCityName;
	}

	/**
	 * Sets the 始发站名称.
	 *
	 * @param deliveryCustomerCityName the new 始发站名称
	 */
	public void setDeliveryCustomerCityName(String deliveryCustomerCityName) {
		this.deliveryCustomerCityName = deliveryCustomerCityName;
	}

	/**
	 * Gets the 始发部门(收货部门(出发部门)) 名称.
	 *
	 * @return the 始发部门(收货部门(出发部门)) 名称
	 */
	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	/**
	 * Sets the 始发部门(收货部门(出发部门)) 名称.
	 *
	 * @param receiveOrgName the new 始发部门(收货部门(出发部门)) 名称
	 */
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	/**
	 * Gets the 到付金额.
	 *
	 * @return the 到付金额
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * Sets the 到付金额.
	 *
	 * @param toPayAmount the new 到付金额
	 */
	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	/**
	 * Gets the 包装.
	 *
	 * @return the 包装
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}

	/**
	 * Sets the 包装.
	 *
	 * @param goodsPackage the new 包装
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	/**
	 * Gets the 并发控制  --到达未出库件数.
	 *
	 * @return the 并发控制  --到达未出库件数
	 */
	public Integer getOldArriveNotoutGoodsQty() {
		return oldArriveNotoutGoodsQty;
	}

	/**
	 * Sets the 并发控制  --到达未出库件数.
	 *
	 * @param oldArriveNotoutGoodsQty the new 并发控制  --到达未出库件数
	 */
	public void setOldArriveNotoutGoodsQty(Integer oldArriveNotoutGoodsQty) {
		this.oldArriveNotoutGoodsQty = oldArriveNotoutGoodsQty;
	}

	/**
	 * Gets the 收货省份.
	 *
	 * @return the 收货省份
	 */
	public String getReceiveCustomerProvCode() {
		return receiveCustomerProvCode;
	}

	/**
	 * Sets the 收货省份.
	 *
	 * @param receiveCustomerProvCode the new 收货省份
	 */
	public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}

	/**
	 * Gets the 收货市.
	 *
	 * @return the 收货市
	 */
	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}

	/**
	 * Sets the 收货市.
	 *
	 * @param receiveCustomerCityCode the new 收货市
	 */
	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}

	/**
	 * Gets the 收货区.
	 *
	 * @return the 收货区
	 */
	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}

	/**
	 * Sets the 收货区.
	 *
	 * @param receiveCustomerDistCode the new 收货区
	 */
	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}

	/**
	 * Gets the 已生成到达联件数.
	 *
	 * @return the 已生成到达联件数
	 */
	public Integer getGenerateGoodsQty() {
		return generateGoodsQty;
	}

	/**
	 * Sets the 已生成到达联件数.
	 *
	 * @param generateGoodsQty the new 已生成到达联件数
	 */
	public void setGenerateGoodsQty(Integer generateGoodsQty) {
		this.generateGoodsQty = generateGoodsQty;
	}

	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	public BigDecimal getStorageChargeSum() {
		return storageChargeSum;
	}

	public void setStorageChargeSum(BigDecimal storageChargeSum) {
		this.storageChargeSum = storageChargeSum;
	}

	public BigDecimal getStorageChargeOld() {
		return storageChargeOld;
	}

	public void setStorageChargeOld(BigDecimal storageChargeOld) {
		this.storageChargeOld = storageChargeOld;
	}

	public String getUpdateStorageChargeReason() {
		return updateStorageChargeReason;
	}

	public void setUpdateStorageChargeReason(String updateStorageChargeReason) {
		this.updateStorageChargeReason = updateStorageChargeReason;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}
	
	public String getStartCentralizedSettlement() {
		return startCentralizedSettlement;
	}

	public void setStartCentralizedSettlement(String startCentralizedSettlement) {
		this.startCentralizedSettlement = startCentralizedSettlement;
	}

	public String getArriveCentralizedSettlement() {
		return arriveCentralizedSettlement;
	}

	public void setArriveCentralizedSettlement(String arriveCentralizedSettlement) {
		this.arriveCentralizedSettlement = arriveCentralizedSettlement;
	}

	public String getStartContractOrgCode() {
		return startContractOrgCode;
	}

	public void setStartContractOrgCode(String startContractOrgCode) {
		this.startContractOrgCode = startContractOrgCode;
	}

	public String getArriveContractOrgCode() {
		return arriveContractOrgCode;
	}

	public void setArriveContractOrgCode(String arriveContractOrgCode) {
		this.arriveContractOrgCode = arriveContractOrgCode;
	}

	public String getStartContractOrgName() {
		return startContractOrgName;
	}

	public void setStartContractOrgName(String startContractOrgName) {
		this.startContractOrgName = startContractOrgName;
	}

	public String getArriveContractOrgName() {
		return arriveContractOrgName;
	}

	public void setArriveContractOrgName(String arriveContractOrgName) {
		this.arriveContractOrgName = arriveContractOrgName;
	}

	public String getStartReminderOrgCode() {
		return startReminderOrgCode;
	}

	public void setStartReminderOrgCode(String startReminderOrgCode) {
		this.startReminderOrgCode = startReminderOrgCode;
	}

	public String getArriveReminderOrgCode() {
		return arriveReminderOrgCode;
	}

	public void setArriveReminderOrgCode(String arriveReminderOrgCode) {
		this.arriveReminderOrgCode = arriveReminderOrgCode;
	}
	
	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}

	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}

	public String getReturnbillType() {
		return returnbillType;
	}

	public void setReturnbillType(String returnbillType) {
		this.returnbillType = returnbillType;
	}

	public String getReceiveCustomerVillageCode() {
		return receiveCustomerVillageCode;
	}

	public void setReceiveCustomerVillageCode(String receiveCustomerVillageCode) {
		this.receiveCustomerVillageCode = receiveCustomerVillageCode;
	}

	public String getReturnWaybillNo() {
		return returnWaybillNo;
	}

	public void setReturnWaybillNo(String returnWaybillNo) {
		this.returnWaybillNo = returnWaybillNo;
	}
	
	public String getCustomerpickuporgcode() {
		return customerpickuporgcode;
	}

	public void setCustomerpickuporgcode(String customerpickuporgcode) {
		this.customerpickuporgcode = customerpickuporgcode;
	}

	public String getReturnbillStatus() {
		return returnbillStatus;
	}

	public void setReturnbillStatus(String returnbillStatus) {
		this.returnbillStatus = returnbillStatus;
	}

}