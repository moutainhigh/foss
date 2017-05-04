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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/vo/SalesDeptWaybillVo.java
 * 
 * FILE NAME        	: SalesDeptWaybillVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.creating.client.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 运单销售部门
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:jiangfei,date:2012-10-17 下午4:11:08,
 * </p>
 * 
 * @author jiangfei
 * @date 2012-10-17 下午4:11:08
 * @since
 * @version
 */
public class SalesDeptWaybillVo {
	
	//混合单号
	private String mixNo;
	
	// 运单号
    private String waybillNo;

    // 订单号
    private String orderNo;

    // 发货客户ID
    private String deliveryCustomerId;

    // 发货客户编码
    private String deliveryCustomerCode;

    // 发货客户名称
    private String deliveryCustomerName;

    // 发货客户手机
    private String deliveryCustomerMobilephone;

    // 发货客户电话
    private String deliveryCustomerPhone;

    // 发货客户联系人
    private String deliveryCustomerContact;

    // 发货国家
    private String deliveryCustomerNationCode;

    // 发货省份
    private String deliveryCustomerProvCode;

    // 发货市
    private String deliveryCustomerCityCode;

    // 发货区
    private String deliveryCustomerCountyCode;

    // 发货具体地址
    private String deliveryCustomerAddress;

    // 收货客户ID
    private String receiveCustomerId;

    // 收货客户编码
    private String receiveCustomerCode;

    // 收货客户名称
    private String receiveCustomerName;

    // 收货客户手机
    private String receiveCustomerMobilephone;

    // 收货客户电话
    private String receiveCustomerPhone;

    // 收货客户联系人
    private String receiveCustomerContact;

    // 收货国家
    private String receiveCustomerNationCode;

    // 收货省份
    private String receiveCustomerProvCode;

    // 收货市
    private String receiveCustomerCityCode;

    // 收货区
    private String receiveCustomerCountyCode;

    // 收货具体地址
    private String receiveCustomerAddress;

    // 收货部门
    private String receiveOrgCode;

    // 产品ID
    private String productId;

    // 运输性质
    private String productCode;

    // 提货方式
    private String receiveMethod;

    // 提货网点
    private String customerPickupOrgCode;

    // 配载类型
    private String loadMethod;

    // 目的站
    private String targetOrgCode;

    // 是否上门接货
    private String pickupToDoor;

    // 司机
    private String driverCode;

    // 是否集中接货
    private String pickupCentralized;

    // 配载线路
    private String loadLineCode;

    // 配载部门
    private String loadOrgCode;

    // 最终配载部门
    private String lastLoadOrgCode;

    // 预计出发时间
    private Date preDepartureTime;

    // 预计派送/提货时间
    private Date preCustomerPickupTime;

    // 是否大车直送
    private String carDirectDelivery;

    // 货物名称
    private String goodsName;

    // 货物总件数
    private Integer goodsQtyTotal;

    // 货物总重量
    private BigDecimal goodsWeightTotal;

    // 货物总体积
    private BigDecimal goodsVolumeTotal;

    // 货物尺寸
    private String goodsSize;

    // 货物类型
    private String goodsTypeCode;

    // 是否贵重物品
    private String preciousGoods;

    // 是否异形物品
    private String specialShapedGoods;

    // 对外备注
    private String outerNotes;

    // 对内备注
    private String innerNotes;

    // 货物包装
    private String goodsPackage;

    // 保价声明价值
    private Long insuranceAmount;

    // 保价费率
    private BigDecimal insuranceRate;

    // 保价费
    private Long insuranceFee;

    // 代收货款
    private Long codAmount;

    // 代收费率
    private BigDecimal codRate;

    // 代收货款手续费
    private Long codFee;

    // 退款类型
    private String refundType;

    // 返单类别
    private String returnBillType;

    // 预付费保密
    private String secretPrepaid;

    // 到付金额
    private Long toPayAmount;

    // 预付金额
    private Long prePayAmount;

    // 送货费
    private Long deliveryGoodsFee;

    // 其他费用
    private Long otherFee;

    // 包装手续费
    private Long packageFee;

    // 优惠费用
    private Long promotionsFee;

    // 运费计费类型
    private String billingType;

    // 运费计费费率
    private Long unitPrice;

    // 公布价运费
    private Long transportFee;

    // 增值费用
    private Long valueAddFee;

    // 开单付款方式
    private String paidMethod;

    // 到达类型
    private String arriveType;

    // 运单状态
    private String active;

    // 禁行
    private String forbiddenLine;

    // 合票方式
    private String freightMethod;

    // 航班时间
    private String flightShift;

    // 总费用
    private Long totalFee;

    // 优惠编码
    private String promotionsCode;

    // 生效日期
    private Date beginTime;

    // 结束日期
    private Date endTime;

    //Foss提交开始时间
    private Date createStartTime;
    
    //Foss提交结束时间
    private Date createEndTime;

    // 更新时间
    private Date modifyTime;

    // 开单开始时间
    private Date billStartTime;
    
    // 开单结束时间
    private Date billEndTime;

    // 开单人
    private String createUserCode;

    // 更新人
    private String modifyUserCode;

    // 开单组织
    private String createOrgCode;

    // 更新组织
    private String modifyOrgCode;

    // 原运单ID
    private String refId;

    // 原运单号
    private String refCode;

    // 币种
    private String currencyCode;

    // 是否整车运单
    private String isWholeVehicle;

    // 整车约车报价
    private Long wholeVehicleAppfee;

    // 整车开单报价
    private Long wholeVehicleActualfee;

    // 代打木架部门
    private String packageOrgCode;

    // 打木架货物件数
    private Integer standGoodsNum;

    // 代打木架要求
    private String standRequirement;

    // 打木架货物尺寸
    private String standGoodsSize;

    // 打木架货物体积
    private BigDecimal standGoodsVolume;

    // 打木箱货物件数
    private Integer boxGoodsNum;

    // 代打木箱要求
    private String boxRequirement;

    // 打木箱货物尺寸
    private String boxGoodsSize;

    // 打木箱货物体积
    private BigDecimal boxGoodsVolume;

    // 返款帐户开户名称
    private String accountName;

    // 返款帐户开户账户
    private String accountCode;

    // 返款帐户开户银行
    private String accountBank;

    // 计费重量
    private BigDecimal billWeight;

    // 接货费
    private Long pickupFee;

    // 装卸费
    private Long serviceFee;

    // 预计到达时间
    private Date preArriveTime;



    // 打印次数
    private Integer printTimes;

    // 新增时间
    private Date addTime;
    
    /**
     * 电子面单新增字段
     */
    //扫描开始时间
    private Date beginScanTime;
    
    //扫描结束时间
    private Date endScanTime;
    
    //是否已经扫描
    private String isScan;
    
    //订单来源
    private String orderChannel;
    
    //订单状态
    private String orderStatus;
    
    //运单状态
    private String waybillStatus;

	/**
	 * @return the mixNo
	 */
	public String getMixNo() {
		return mixNo;
	}

	/**
	 * @param mixNo the mixNo to set
	 */
	public void setMixNo(String mixNo) {
		this.mixNo = mixNo;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the deliveryCustomerId
	 */
	public String getDeliveryCustomerId() {
		return deliveryCustomerId;
	}

	/**
	 * @param deliveryCustomerId the deliveryCustomerId to set
	 */
	public void setDeliveryCustomerId(String deliveryCustomerId) {
		this.deliveryCustomerId = deliveryCustomerId;
	}

	/**
	 * @return the deliveryCustomerCode
	 */
	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	/**
	 * @param deliveryCustomerCode the deliveryCustomerCode to set
	 */
	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	/**
	 * @return the deliveryCustomerName
	 */
	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	/**
	 * @param deliveryCustomerName the deliveryCustomerName to set
	 */
	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	/**
	 * @return the deliveryCustomerMobilephone
	 */
	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	/**
	 * @param deliveryCustomerMobilephone the deliveryCustomerMobilephone to set
	 */
	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	/**
	 * @return the deliveryCustomerPhone
	 */
	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}

	/**
	 * @param deliveryCustomerPhone the deliveryCustomerPhone to set
	 */
	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}

	/**
	 * @return the deliveryCustomerContact
	 */
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	/**
	 * @param deliveryCustomerContact the deliveryCustomerContact to set
	 */
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	/**
	 * @return the deliveryCustomerNationCode
	 */
	public String getDeliveryCustomerNationCode() {
		return deliveryCustomerNationCode;
	}

	/**
	 * @param deliveryCustomerNationCode the deliveryCustomerNationCode to set
	 */
	public void setDeliveryCustomerNationCode(String deliveryCustomerNationCode) {
		this.deliveryCustomerNationCode = deliveryCustomerNationCode;
	}

	/**
	 * @return the deliveryCustomerProvCode
	 */
	public String getDeliveryCustomerProvCode() {
		return deliveryCustomerProvCode;
	}

	/**
	 * @param deliveryCustomerProvCode the deliveryCustomerProvCode to set
	 */
	public void setDeliveryCustomerProvCode(String deliveryCustomerProvCode) {
		this.deliveryCustomerProvCode = deliveryCustomerProvCode;
	}

	/**
	 * @return the deliveryCustomerCityCode
	 */
	public String getDeliveryCustomerCityCode() {
		return deliveryCustomerCityCode;
	}

	/**
	 * @param deliveryCustomerCityCode the deliveryCustomerCityCode to set
	 */
	public void setDeliveryCustomerCityCode(String deliveryCustomerCityCode) {
		this.deliveryCustomerCityCode = deliveryCustomerCityCode;
	}

	/**
	 * @return the deliveryCustomerCountyCode
	 */
	public String getDeliveryCustomerCountyCode() {
		return deliveryCustomerCountyCode;
	}

	/**
	 * @param deliveryCustomerCountyCode the deliveryCustomerCountyCode to set
	 */
	public void setDeliveryCustomerCountyCode(String deliveryCustomerCountyCode) {
		this.deliveryCustomerCountyCode = deliveryCustomerCountyCode;
	}

	/**
	 * @return the deliveryCustomerAddress
	 */
	public String getDeliveryCustomerAddress() {
		return deliveryCustomerAddress;
	}

	/**
	 * @param deliveryCustomerAddress the deliveryCustomerAddress to set
	 */
	public void setDeliveryCustomerAddress(String deliveryCustomerAddress) {
		this.deliveryCustomerAddress = deliveryCustomerAddress;
	}

	/**
	 * @return the receiveCustomerId
	 */
	public String getReceiveCustomerId() {
		return receiveCustomerId;
	}

	/**
	 * @param receiveCustomerId the receiveCustomerId to set
	 */
	public void setReceiveCustomerId(String receiveCustomerId) {
		this.receiveCustomerId = receiveCustomerId;
	}

	/**
	 * @return the receiveCustomerCode
	 */
	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	/**
	 * @param receiveCustomerCode the receiveCustomerCode to set
	 */
	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	/**
	 * @return the receiveCustomerName
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * @param receiveCustomerName the receiveCustomerName to set
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
	 * @param receiveCustomerMobilephone the receiveCustomerMobilephone to set
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * @return the receiveCustomerPhone
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * @param receiveCustomerPhone the receiveCustomerPhone to set
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * @return the receiveCustomerContact
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	/**
	 * @param receiveCustomerContact the receiveCustomerContact to set
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	/**
	 * @return the receiveCustomerNationCode
	 */
	public String getReceiveCustomerNationCode() {
		return receiveCustomerNationCode;
	}

	/**
	 * @param receiveCustomerNationCode the receiveCustomerNationCode to set
	 */
	public void setReceiveCustomerNationCode(String receiveCustomerNationCode) {
		this.receiveCustomerNationCode = receiveCustomerNationCode;
	}

	/**
	 * @return the receiveCustomerProvCode
	 */
	public String getReceiveCustomerProvCode() {
		return receiveCustomerProvCode;
	}

	/**
	 * @param receiveCustomerProvCode the receiveCustomerProvCode to set
	 */
	public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}

	/**
	 * @return the receiveCustomerCityCode
	 */
	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}

	/**
	 * @param receiveCustomerCityCode the receiveCustomerCityCode to set
	 */
	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}

	/**
	 * @return the receiveCustomerCountyCode
	 */
	public String getReceiveCustomerCountyCode() {
		return receiveCustomerCountyCode;
	}

	/**
	 * @param receiveCustomerCountyCode the receiveCustomerCountyCode to set
	 */
	public void setReceiveCustomerCountyCode(String receiveCustomerCountyCode) {
		this.receiveCustomerCountyCode = receiveCustomerCountyCode;
	}

	/**
	 * @return the receiveCustomerAddress
	 */
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	/**
	 * @param receiveCustomerAddress the receiveCustomerAddress to set
	 */
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	/**
	 * @return the receiveOrgCode
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * @param receiveOrgCode the receiveOrgCode to set
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the receiveMethod
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * @param receiveMethod the receiveMethod to set
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * @return the customerPickupOrgCode
	 */
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	/**
	 * @param customerPickupOrgCode the customerPickupOrgCode to set
	 */
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	/**
	 * @return the loadMethod
	 */
	public String getLoadMethod() {
		return loadMethod;
	}

	/**
	 * @param loadMethod the loadMethod to set
	 */
	public void setLoadMethod(String loadMethod) {
		this.loadMethod = loadMethod;
	}

	/**
	 * @return the targetOrgCode
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	/**
	 * @param targetOrgCode the targetOrgCode to set
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	/**
	 * @return the pickupTo Door
	 */
	public String getPickupToDoor() {
		return pickupToDoor;
	}

	/**
	 * @param pickupTo Door the pickupTo Door to set
	 */
	public void setPickupToDoor(String pickupToDoor) {
		this.pickupToDoor = pickupToDoor;
	}

	/**
	 * @return the driverCode
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * @param driverCode the driverCode to set
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * @return the pickupCentralized
	 */
	public String getPickupCentralized() {
		return pickupCentralized;
	}

	/**
	 * @param pickupCentralized the pickupCentralized to set
	 */
	public void setPickupCentralized(String pickupCentralized) {
		this.pickupCentralized = pickupCentralized;
	}

	/**
	 * @return the loadLineCode
	 */
	public String getLoadLineCode() {
		return loadLineCode;
	}

	/**
	 * @param loadLineCode the loadLineCode to set
	 */
	public void setLoadLineCode(String loadLineCode) {
		this.loadLineCode = loadLineCode;
	}

	/**
	 * @return the loadOrgCode
	 */
	public String getLoadOrgCode() {
		return loadOrgCode;
	}

	/**
	 * @param loadOrgCode the loadOrgCode to set
	 */
	public void setLoadOrgCode(String loadOrgCode) {
		this.loadOrgCode = loadOrgCode;
	}

	/**
	 * @return the lastLoadOrgCode
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	/**
	 * @param lastLoadOrgCode the lastLoadOrgCode to set
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	/**
	 * @return the preDepartureTime
	 */
	public Date getPreDepartureTime() {
		return preDepartureTime;
	}

	/**
	 * @param preDepartureTime the preDepartureTime to set
	 */
	public void setPreDepartureTime(Date preDepartureTime) {
		this.preDepartureTime = preDepartureTime;
	}

	/**
	 * @return the preCustomerPickupTime
	 */
	public Date getPreCustomerPickupTime() {
		return preCustomerPickupTime;
	}

	/**
	 * @param preCustomerPickupTime the preCustomerPickupTime to set
	 */
	public void setPreCustomerPickupTime(Date preCustomerPickupTime) {
		this.preCustomerPickupTime = preCustomerPickupTime;
	}

	/**
	 * @return the carDirectDelivery
	 */
	public String getCarDirectDelivery() {
		return carDirectDelivery;
	}

	/**
	 * @param carDirectDelivery the carDirectDelivery to set
	 */
	public void setCarDirectDelivery(String carDirectDelivery) {
		this.carDirectDelivery = carDirectDelivery;
	}

	/**
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * @return the goodsQtyTotal
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * @param goodsQtyTotal the goodsQtyTotal to set
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * @return the goodsWeightTotal
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	/**
	 * @param goodsWeightTotal the goodsWeightTotal to set
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	/**
	 * @return the goodsVolumeTotal
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * @param goodsVolumeTotal the goodsVolumeTotal to set
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * @return the goodsSize
	 */
	public String getGoodsSize() {
		return goodsSize;
	}

	/**
	 * @param goodsSize the goodsSize to set
	 */
	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	/**
	 * @return the goodsTypeCode
	 */
	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}

	/**
	 * @param goodsTypeCode the goodsTypeCode to set
	 */
	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}

	/**
	 * @return the preciousGoods
	 */
	public String getPreciousGoods() {
		return preciousGoods;
	}

	/**
	 * @param preciousGoods the preciousGoods to set
	 */
	public void setPreciousGoods(String preciousGoods) {
		this.preciousGoods = preciousGoods;
	}

	/**
	 * @return the specialShapedGoods
	 */
	public String getSpecialShapedGoods() {
		return specialShapedGoods;
	}

	/**
	 * @param specialShapedGoods the specialShapedGoods to set
	 */
	public void setSpecialShapedGoods(String specialShapedGoods) {
		this.specialShapedGoods = specialShapedGoods;
	}

	/**
	 * @return the outerNotes
	 */
	public String getOuterNotes() {
		return outerNotes;
	}

	/**
	 * @param outerNotes the outerNotes to set
	 */
	public void setOuterNotes(String outerNotes) {
		this.outerNotes = outerNotes;
	}

	/**
	 * @return the innerNotes
	 */
	public String getInnerNotes() {
		return innerNotes;
	}

	/**
	 * @param innerNotes the innerNotes to set
	 */
	public void setInnerNotes(String innerNotes) {
		this.innerNotes = innerNotes;
	}

	/**
	 * @return the goodsPackage
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}

	/**
	 * @param goodsPackage the goodsPackage to set
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	/**
	 * @return the insuranceAmount
	 */
	public Long getInsuranceAmount() {
		return insuranceAmount;
	}

	/**
	 * @param insuranceAmount the insuranceAmount to set
	 */
	public void setInsuranceAmount(Long insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	/**
	 * @return the insuranceRate
	 */
	public BigDecimal getInsuranceRate() {
		return insuranceRate;
	}

	/**
	 * @param insuranceRate the insuranceRate to set
	 */
	public void setInsuranceRate(BigDecimal insuranceRate) {
		this.insuranceRate = insuranceRate;
	}

	/**
	 * @return the insuranceFee
	 */
	public Long getInsuranceFee() {
		return insuranceFee;
	}

	/**
	 * @param insuranceFee the insuranceFee to set
	 */
	public void setInsuranceFee(Long insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	/**
	 * @return the codAmount
	 */
	public Long getCodAmount() {
		return codAmount;
	}

	/**
	 * @param codAmount the codAmount to set
	 */
	public void setCodAmount(Long codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * @return the codRate
	 */
	public BigDecimal getCodRate() {
		return codRate;
	}

	/**
	 * @param codRate the codRate to set
	 */
	public void setCodRate(BigDecimal codRate) {
		this.codRate = codRate;
	}

	/**
	 * @return the codFee
	 */
	public Long getCodFee() {
		return codFee;
	}

	/**
	 * @param codFee the codFee to set
	 */
	public void setCodFee(Long codFee) {
		this.codFee = codFee;
	}

	/**
	 * @return the refundType
	 */
	public String getRefundType() {
		return refundType;
	}

	/**
	 * @param refundType the refundType to set
	 */
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	/**
	 * @return the returnBillType
	 */
	public String getReturnBillType() {
		return returnBillType;
	}

	/**
	 * @param returnBillType the returnBillType to set
	 */
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	/**
	 * @return the secretPrepaid
	 */
	public String getSecretPrepaid() {
		return secretPrepaid;
	}

	/**
	 * @param secretPrepaid the secretPrepaid to set
	 */
	public void setSecretPrepaid(String secretPrepaid) {
		this.secretPrepaid = secretPrepaid;
	}

	/**
	 * @return the toPayAmount
	 */
	public Long getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * @param toPayAmount the toPayAmount to set
	 */
	public void setToPayAmount(Long toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	/**
	 * @return the prePayAmount
	 */
	public Long getPrePayAmount() {
		return prePayAmount;
	}

	/**
	 * @param prePayAmount the prePayAmount to set
	 */
	public void setPrePayAmount(Long prePayAmount) {
		this.prePayAmount = prePayAmount;
	}

	/**
	 * @return the deliveryGoodsFee
	 */
	public Long getDeliveryGoodsFee() {
		return deliveryGoodsFee;
	}

	/**
	 * @param deliveryGoodsFee the deliveryGoodsFee to set
	 */
	public void setDeliveryGoodsFee(Long deliveryGoodsFee) {
		this.deliveryGoodsFee = deliveryGoodsFee;
	}

	/**
	 * @return the otherFee
	 */
	public Long getOtherFee() {
		return otherFee;
	}

	/**
	 * @param otherFee the otherFee to set
	 */
	public void setOtherFee(Long otherFee) {
		this.otherFee = otherFee;
	}

	/**
	 * @return the packageFee
	 */
	public Long getPackageFee() {
		return packageFee;
	}

	/**
	 * @param packageFee the packageFee to set
	 */
	public void setPackageFee(Long packageFee) {
		this.packageFee = packageFee;
	}

	/**
	 * @return the promotionsFee
	 */
	public Long getPromotionsFee() {
		return promotionsFee;
	}

	/**
	 * @param promotionsFee the promotionsFee to set
	 */
	public void setPromotionsFee(Long promotionsFee) {
		this.promotionsFee = promotionsFee;
	}

	/**
	 * @return the billingType
	 */
	public String getBillingType() {
		return billingType;
	}

	/**
	 * @param billingType the billingType to set
	 */
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	/**
	 * @return the unitPrice
	 */
	public Long getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(Long unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the transportFee
	 */
	public Long getTransportFee() {
		return transportFee;
	}

	/**
	 * @param transportFee the transportFee to set
	 */
	public void setTransportFee(Long transportFee) {
		this.transportFee = transportFee;
	}

	/**
	 * @return the valueAddFee
	 */
	public Long getValueAddFee() {
		return valueAddFee;
	}

	/**
	 * @param valueAddFee the valueAddFee to set
	 */
	public void setValueAddFee(Long valueAddFee) {
		this.valueAddFee = valueAddFee;
	}

	/**
	 * @return the paidMethod
	 */
	public String getPaidMethod() {
		return paidMethod;
	}

	/**
	 * @param paidMethod the paidMethod to set
	 */
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	/**
	 * @return the arriveType
	 */
	public String getArriveType() {
		return arriveType;
	}

	/**
	 * @param arriveType the arriveType to set
	 */
	public void setArriveType(String arriveType) {
		this.arriveType = arriveType;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the forbiddenLine
	 */
	public String getForbiddenLine() {
		return forbiddenLine;
	}

	/**
	 * @param forbiddenLine the forbiddenLine to set
	 */
	public void setForbiddenLine(String forbiddenLine) {
		this.forbiddenLine = forbiddenLine;
	}

	/**
	 * @return the freightMethod
	 */
	public String getFreightMethod() {
		return freightMethod;
	}

	/**
	 * @param freightMethod the freightMethod to set
	 */
	public void setFreightMethod(String freightMethod) {
		this.freightMethod = freightMethod;
	}

	/**
	 * @return the flightShift
	 */
	public String getFlightShift() {
		return flightShift;
	}

	/**
	 * @param flightShift the flightShift to set
	 */
	public void setFlightShift(String flightShift) {
		this.flightShift = flightShift;
	}

	/**
	 * @return the totalFee
	 */
	public Long getTotalFee() {
		return totalFee;
	}

	/**
	 * @param totalFee the totalFee to set
	 */
	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}

	/**
	 * @return the promotionsCode
	 */
	public String getPromotionsCode() {
		return promotionsCode;
	}

	/**
	 * @param promotionsCode the promotionsCode to set
	 */
	public void setPromotionsCode(String promotionsCode) {
		this.promotionsCode = promotionsCode;
	}

	/**
	 * @return the beginTime
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime the beginTime to set
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the createStartTime
	 */
	public Date getCreateStartTime() {
		return createStartTime;
	}

	/**
	 * @param createStartTime the createStartTime to set
	 */
	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}

	/**
	 * @return the createEndTime
	 */
	public Date getCreateEndTime() {
		return createEndTime;
	}

	/**
	 * @param createEndTime the createEndTime to set
	 */
	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the billStartTime
	 */
	public Date getBillStartTime() {
		return billStartTime;
	}

	/**
	 * @param billStartTime the billStartTime to set
	 */
	public void setBillStartTime(Date billStartTime) {
		this.billStartTime = billStartTime;
	}

	/**
	 * @return the billEndTime
	 */
	public Date getBillEndTime() {
		return billEndTime;
	}

	/**
	 * @param billEndTime the billEndTime to set
	 */
	public void setBillEndTime(Date billEndTime) {
		this.billEndTime = billEndTime;
	}

	/**
	 * @return the createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param createUserCode the createUserCode to set
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return the modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param modifyUserCode the modifyUserCode to set
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * @return the createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @param createOrgCode the createOrgCode to set
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * @return the modifyOrgCode
	 */
	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	/**
	 * @param modifyOrgCode the modifyOrgCode to set
	 */
	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}

	/**
	 * @return the refId
	 */
	public String getRefId() {
		return refId;
	}

	/**
	 * @param refId the refId to set
	 */
	public void setRefId(String refId) {
		this.refId = refId;
	}

	/**
	 * @return the refCode
	 */
	public String getRefCode() {
		return refCode;
	}

	/**
	 * @param refCode the refCode to set
	 */
	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the isWholeVehicle
	 */
	public String getIsWholeVehicle() {
		return isWholeVehicle;
	}

	/**
	 * @param isWholeVehicle the isWholeVehicle to set
	 */
	public void setIsWholeVehicle(String isWholeVehicle) {
		this.isWholeVehicle = isWholeVehicle;
	}

	/**
	 * @return the wholeVehicleAppfee
	 */
	public Long getWholeVehicleAppfee() {
		return wholeVehicleAppfee;
	}

	/**
	 * @param wholeVehicleAppfee the wholeVehicleAppfee to set
	 */
	public void setWholeVehicleAppfee(Long wholeVehicleAppfee) {
		this.wholeVehicleAppfee = wholeVehicleAppfee;
	}

	/**
	 * @return the wholeVehicleActualfee
	 */
	public Long getWholeVehicleActualfee() {
		return wholeVehicleActualfee;
	}

	/**
	 * @param wholeVehicleActualfee the wholeVehicleActualfee to set
	 */
	public void setWholeVehicleActualfee(Long wholeVehicleActualfee) {
		this.wholeVehicleActualfee = wholeVehicleActualfee;
	}

	/**
	 * @return the packageOrgCode
	 */
	public String getPackageOrgCode() {
		return packageOrgCode;
	}

	/**
	 * @param packageOrgCode the packageOrgCode to set
	 */
	public void setPackageOrgCode(String packageOrgCode) {
		this.packageOrgCode = packageOrgCode;
	}

	/**
	 * @return the standGoodsNum
	 */
	public Integer getStandGoodsNum() {
		return standGoodsNum;
	}

	/**
	 * @param standGoodsNum the standGoodsNum to set
	 */
	public void setStandGoodsNum(Integer standGoodsNum) {
		this.standGoodsNum = standGoodsNum;
	}

	/**
	 * @return the standRequirement
	 */
	public String getStandRequirement() {
		return standRequirement;
	}

	/**
	 * @param standRequirement the standRequirement to set
	 */
	public void setStandRequirement(String standRequirement) {
		this.standRequirement = standRequirement;
	}

	/**
	 * @return the standGoodsSize
	 */
	public String getStandGoodsSize() {
		return standGoodsSize;
	}

	/**
	 * @param standGoodsSize the standGoodsSize to set
	 */
	public void setStandGoodsSize(String standGoodsSize) {
		this.standGoodsSize = standGoodsSize;
	}

	/**
	 * @return the standGoodsVolume
	 */
	public BigDecimal getStandGoodsVolume() {
		return standGoodsVolume;
	}

	/**
	 * @param standGoodsVolume the standGoodsVolume to set
	 */
	public void setStandGoodsVolume(BigDecimal standGoodsVolume) {
		this.standGoodsVolume = standGoodsVolume;
	}

	/**
	 * @return the boxGoodsNum
	 */
	public Integer getBoxGoodsNum() {
		return boxGoodsNum;
	}

	/**
	 * @param boxGoodsNum the boxGoodsNum to set
	 */
	public void setBoxGoodsNum(Integer boxGoodsNum) {
		this.boxGoodsNum = boxGoodsNum;
	}

	/**
	 * @return the boxRequirement
	 */
	public String getBoxRequirement() {
		return boxRequirement;
	}

	/**
	 * @param boxRequirement the boxRequirement to set
	 */
	public void setBoxRequirement(String boxRequirement) {
		this.boxRequirement = boxRequirement;
	}

	/**
	 * @return the boxGoodsSize
	 */
	public String getBoxGoodsSize() {
		return boxGoodsSize;
	}

	/**
	 * @param boxGoodsSize the boxGoodsSize to set
	 */
	public void setBoxGoodsSize(String boxGoodsSize) {
		this.boxGoodsSize = boxGoodsSize;
	}

	/**
	 * @return the boxGoodsVolume
	 */
	public BigDecimal getBoxGoodsVolume() {
		return boxGoodsVolume;
	}

	/**
	 * @param boxGoodsVolume the boxGoodsVolume to set
	 */
	public void setBoxGoodsVolume(BigDecimal boxGoodsVolume) {
		this.boxGoodsVolume = boxGoodsVolume;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the accountCode
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * @param accountCode the accountCode to set
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * @return the accountBank
	 */
	public String getAccountBank() {
		return accountBank;
	}

	/**
	 * @param accountBank the accountBank to set
	 */
	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	/**
	 * @return the billWeight
	 */
	public BigDecimal getBillWeight() {
		return billWeight;
	}

	/**
	 * @param billWeight the billWeight to set
	 */
	public void setBillWeight(BigDecimal billWeight) {
		this.billWeight = billWeight;
	}

	/**
	 * @return the pickupFee
	 */
	public Long getPickupFee() {
		return pickupFee;
	}

	/**
	 * @param pickupFee the pickupFee to set
	 */
	public void setPickupFee(Long pickupFee) {
		this.pickupFee = pickupFee;
	}

	/**
	 * @return the serviceFee
	 */
	public Long getServiceFee() {
		return serviceFee;
	}

	/**
	 * @param serviceFee the serviceFee to set
	 */
	public void setServiceFee(Long serviceFee) {
		this.serviceFee = serviceFee;
	}

	/**
	 * @return the preArriveTime
	 */
	public Date getPreArriveTime() {
		return preArriveTime;
	}

	/**
	 * @param preArriveTime the preArriveTime to set
	 */
	public void setPreArriveTime(Date preArriveTime) {
		this.preArriveTime = preArriveTime;
	}

	/**
	 * @return the printTimes
	 */
	public Integer getPrintTimes() {
		return printTimes;
	}

	/**
	 * @param printTimes the printTimes to set
	 */
	public void setPrintTimes(Integer printTimes) {
		this.printTimes = printTimes;
	}

	/**
	 * @return the addTime
	 */
	public Date getAddTime() {
		return addTime;
	}

	/**
	 * @param addTime the addTime to set
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getBeginScanTime() {
		return beginScanTime;
	}

	public void setBeginScanTime(Date beginScanTime) {
		this.beginScanTime = beginScanTime;
	}

	public Date getEndScanTime() {
		return endScanTime;
	}

	public void setEndScanTime(Date endScanTime) {
		this.endScanTime = endScanTime;
	}

	public String getIsScan() {
		return isScan;
	}

	public void setIsScan(String isScan) {
		this.isScan = isScan;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getWaybillStatus() {
		return waybillStatus;
	}

	public void setWaybillStatus(String waybillStatus) {
		this.waybillStatus = waybillStatus;
	}
}