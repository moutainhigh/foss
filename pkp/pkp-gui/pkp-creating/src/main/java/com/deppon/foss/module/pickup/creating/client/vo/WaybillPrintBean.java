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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/vo/WaybillPrintBean.java
 * 
 * FILE NAME        	: WaybillPrintBean.java
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
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;

/**
 * (描述类的职责打印实体类bean 调用打印时需要组装这个类)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:jiangfei,date:2012-10-17 下午4:14:14,
 * </p>
 * 
 * @author jiangfei
 * @date 2012-10-17 下午4:14:14
 * @since
 * @version
 */
public class WaybillPrintBean extends BaseEntity {

	private static final long serialVersionUID = 4344063714676262330L;

	// 运单号
	private String waybillNo;

	// 发货人编码
	private String deliveryCustomerCode;

	// 发货人名字
	private String deliveryCustomerName;
	
	// 发货客户联系人
	private String deliveryCustomerContact;

	// 发货人手机号
	private String deliveryCustomerMobilephone;

	// 发货人电话
	private String deliveryCustomerPhone;

	// 发货人地址
	private String deliveryCustomerAddress;
	
	// 发货人地址
	private String deliveryCustomerAddressNote;
	
	// 发货人地址只是封装省
	private String deliveryCustomerProvice;
	// 发货人地址只是封装市
	private String deliveryCustomerCity;
	

	// ........................



	public String getDeliveryCustomerProvice() {
		return deliveryCustomerProvice;
	}

	public void setDeliveryCustomerProvice(String deliveryCustomerProvice) {
		this.deliveryCustomerProvice = deliveryCustomerProvice;
	}

	public String getDeliveryCustomerCity() {
		return deliveryCustomerCity;
	}

	public void setDeliveryCustomerCity(String deliveryCustomerCity) {
		this.deliveryCustomerCity = deliveryCustomerCity;
	}

	// 收货人
	private String receiveCustomerName;
	
	//收货人编码
	private String receiveCustomerCode;
	
	// 收货客户联系人
	private String receiveCustomerContact;

	// 收货人手机号
	private String receiveCustomerMobilephone;
	
	// 收货人电话
	private String receiveCustomerPhone;

	// 收货人地址
	private String receiveCustomerAddress;
	
	// 收货人地址
	private String receiveCustomerAddressNote;

	// ........................

	// 交货方式
	private DataDictionaryValueVo receiveMethod;

	// 保险声明价值
	private BigDecimal insuranceAmount;

	// 代收货款
	private BigDecimal codAmount;

	// 付款方式
	private DataDictionaryValueVo paidMethod;

	// 签收单
	private DataDictionaryValueVo returnBillType;

	// 户名
	private String accountName;

	// 开户行账号
	private String accountCode;

	// 开户银行
	private String accountBank;

	// 运输性质
	private ProductEntityVo productCode;

	// ........................

	// 网络订单号
	private String onlineOrderNo;

	// 包装
	private String goodsPackage;

	// 上门接货
	private Boolean pickupToDoor;

	// 货物品名
	private String goodsName;

	// 件数
	private Integer goodsQtyTotal;

	// 货物重量
	private BigDecimal goodsWeightTotal;

	// 货物体积
	private BigDecimal goodsVolumeTotal;

	// 始发站
	private String deliverOrgCode;

	// 目的站
	private String targetOrgCode;

	// 储运注意事项
	private String storageMatter;

	// ........................

	// 计费重量
	private BigDecimal billWeight;

	// 预付货款
	private BigDecimal prePayAmount;

	// 到付金额
	private BigDecimal toPayAmount;

	// 保价费
	private BigDecimal insuranceFee;

	// 其他费用
	private List<OtherFeeBean> otherFeeList;

	// 运费计费费率
	private BigDecimal unitPrice;

	// 运费单价
	private BigDecimal deliverPrice;

	// 运费
	private BigDecimal transportFee;

	// ........................

	// 开单时间
	private Date createTime;

	// 开单人
	private String createUserCode;

	// 发货网点 出发部门名称
	private String createOrgCode;

	// 出发部门地址
	private String createOrgCodeAddr;

	// 出发部门对外电话号码
	private String createOrgCodePhone;

	// 提货网点
	private String customerPickupOrgName;

	// 到达部门地址
	private String customerPickupOrgAddr;

	// 到达部门外电话号码
	private String customerPickupOrgPhone;

	// 代收货款手续费
	private BigDecimal codFee;
	
	// 退款类型
	private String refundTypeName;
	
	// 优惠金额
	private BigDecimal promotionsFee;
	
	/**
	 * 
	 * 获取代收手续费
	 * @author 025000-FOSS-helong
	 * @date 2013-5-31
	 */
	public BigDecimal getCodFee() {
		return codFee;
	}

	/**
	 * 设置代收手续费
	 * @author 025000-FOSS-helong
	 * @date 2013-5-31
	 */
	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}

	public String getRefundTypeName() {
		return refundTypeName;
	}

	public void setRefundTypeName(String refundTypeName) {
		this.refundTypeName = refundTypeName;
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
	 * @return the receiveMethod
	 */
	public DataDictionaryValueVo getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * @param receiveMethod the receiveMethod to set
	 */
	public void setReceiveMethod(DataDictionaryValueVo receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * @return the insuranceAmount
	 */
	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	/**
	 * @param insuranceAmount the insuranceAmount to set
	 */
	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	/**
	 * @return the codAmount
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * @param codAmount the codAmount to set
	 */
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * @return the paidMethod
	 */
	public DataDictionaryValueVo getPaidMethod() {
		return paidMethod;
	}

	/**
	 * @param paidMethod the paidMethod to set
	 */
	public void setPaidMethod(DataDictionaryValueVo paidMethod) {
		this.paidMethod = paidMethod;
	}

	/**
	 * @return the returnBillType
	 */
	public DataDictionaryValueVo getReturnBillType() {
		return returnBillType;
	}

	/**
	 * @param returnBillType the returnBillType to set
	 */
	public void setReturnBillType(DataDictionaryValueVo returnBillType) {
		this.returnBillType = returnBillType;
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
	 * @return the productCode
	 */
	public ProductEntityVo getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(ProductEntityVo productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the onlineOrderNo
	 */
	public String getOnlineOrderNo() {
		return onlineOrderNo;
	}

	/**
	 * @param onlineOrderNo the onlineOrderNo to set
	 */
	public void setOnlineOrderNo(String onlineOrderNo) {
		this.onlineOrderNo = onlineOrderNo;
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
	 * @return the pickupTo Door
	 */
	public Boolean getPickupToDoor() {
		return pickupToDoor;
	}

	/**
	 * @param pickupTo Door the pickupTo Door to set
	 */
	public void setPickupToDoor(Boolean pickupToDoor) {
		this.pickupToDoor = pickupToDoor;
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
	 * @return the deliverOrgCode
	 */
	public String getDeliverOrgCode() {
		return deliverOrgCode;
	}

	/**
	 * @param deliverOrgCode the deliverOrgCode to set
	 */
	public void setDeliverOrgCode(String deliverOrgCode) {
		this.deliverOrgCode = deliverOrgCode;
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
	 * @return the storageMatter
	 */
	public String getStorageMatter() {
		return storageMatter;
	}

	/**
	 * @param storageMatter the storageMatter to set
	 */
	public void setStorageMatter(String storageMatter) {
		this.storageMatter = storageMatter;
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
	 * @return the prePayAmount
	 */
	public BigDecimal getPrePayAmount() {
		return prePayAmount;
	}

	/**
	 * @param prePayAmount the prePayAmount to set
	 */
	public void setPrePayAmount(BigDecimal prePayAmount) {
		this.prePayAmount = prePayAmount;
	}

	/**
	 * @return the toPayAmount
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * @param toPayAmount the toPayAmount to set
	 */
	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	/**
	 * @return the insuranceFee
	 */
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	/**
	 * @param insuranceFee the insuranceFee to set
	 */
	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	/**
	 * @return the otherFeeList
	 */
	public List<OtherFeeBean> getOtherFeeList() {
		return otherFeeList;
	}

	/**
	 * @param otherFeeList the otherFeeList to set
	 */
	public void setOtherFeeList(List<OtherFeeBean> otherFeeList) {
		this.otherFeeList = otherFeeList;
	}

	/**
	 * @return the unitPrice
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the deliverPrice
	 */
	public BigDecimal getDeliverPrice() {
		return deliverPrice;
	}

	/**
	 * @param deliverPrice the deliverPrice to set
	 */
	public void setDeliverPrice(BigDecimal deliverPrice) {
		this.deliverPrice = deliverPrice;
	}

	/**
	 * @return the transportFee
	 */
	public BigDecimal getTransportFee() {
		return transportFee;
	}

	/**
	 * @param transportFee the transportFee to set
	 */
	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * @return the createOrgCodeAddr
	 */
	public String getCreateOrgCodeAddr() {
		return createOrgCodeAddr;
	}

	/**
	 * @param createOrgCodeAddr the createOrgCodeAddr to set
	 */
	public void setCreateOrgCodeAddr(String createOrgCodeAddr) {
		this.createOrgCodeAddr = createOrgCodeAddr;
	}

	/**
	 * @return the createOrgCodePhone
	 */
	public String getCreateOrgCodePhone() {
		return createOrgCodePhone;
	}

	/**
	 * @param createOrgCodePhone the createOrgCodePhone to set
	 */
	public void setCreateOrgCodePhone(String createOrgCodePhone) {
		this.createOrgCodePhone = createOrgCodePhone;
	}

	/**
	 * @return the customerPickupOrgName
	 */
	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}

	/**
	 * @param customerPickupOrgName the customerPickupOrgName to set
	 */
	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}

	/**
	 * @return the customerPickupOrgAddr
	 */
	public String getCustomerPickupOrgAddr() {
		return customerPickupOrgAddr;
	}

	/**
	 * @param customerPickupOrgAddr the customerPickupOrgAddr to set
	 */
	public void setCustomerPickupOrgAddr(String customerPickupOrgAddr) {
		this.customerPickupOrgAddr = customerPickupOrgAddr;
	}

	/**
	 * @return the customerPickupOrgPhone
	 */
	public String getCustomerPickupOrgPhone() {
		return customerPickupOrgPhone;
	}

	/**
	 * @param customerPickupOrgPhone the customerPickupOrgPhone to set
	 */
	public void setCustomerPickupOrgPhone(String customerPickupOrgPhone) {
		this.customerPickupOrgPhone = customerPickupOrgPhone;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	 * 
	 * 获得发货客户联系人
	 * @author 025000-FOSS-helong
	 * @date 2013-5-27
	 */
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	/**
	 * 
	 * 设置发货客户联系人
	 * @author 025000-FOSS-helong
	 * @date 2013-5-27
	 */
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	/**
	 * 
	 * 获得收货客户联系人
	 * @author 025000-FOSS-helong
	 * @date 2013-5-27
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}
	
	/**
	 * 设置收货客户联系人
	 * @author 025000-FOSS-helong
	 * @date 2013-5-27
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}
	
	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	public String getDeliveryCustomerAddressNote() {
		return deliveryCustomerAddressNote;
	}

	public void setDeliveryCustomerAddressNote(String deliveryCustomerAddressNote) {
		this.deliveryCustomerAddressNote = deliveryCustomerAddressNote;
	}

	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}

	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}

	public BigDecimal getPromotionsFee() {
		return promotionsFee;
	}

	public void setPromotionsFee(BigDecimal promotionsFee) {
		this.promotionsFee = promotionsFee;
	}

}