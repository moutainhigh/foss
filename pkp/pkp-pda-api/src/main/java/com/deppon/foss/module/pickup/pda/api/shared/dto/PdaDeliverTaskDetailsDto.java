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
 * PROJECT NAME	: pkp-pda-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/shared/dto/PdaDeliverTaskDetailsDto.java
 * 
 * FILE NAME        	: PdaDeliverTaskDetailsDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Pda 查询送货任务明细DTO
 * @author 097972-foss-dengtingting
 * @date 2012-12-12 下午7:30:38
 */
public class PdaDeliverTaskDetailsDto implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  投诉变更状态
	 */
	private String complainStatus;
	
	public String getComplainStatus() {
		return complainStatus;
	}

	public void setComplainStatus(String complainStatus) {
		this.complainStatus = complainStatus;
	}
	/**
	 *  派送单号
	 */
	private String deliverbillNo;

	/**
	 *  运单号
	 */
	private String waybillNo;

	/**
	 *  到达联编号
	 */
	private String arriveSheetNo;
	/**
	 *  流水号列表
	 */
	private List<String> serialNum;

	/**
	 *  派送日期
	 */
	private Date submitTime;

	/**
	 *  客户姓名 收货（运单）
	 */
	private String receiveCustomerName;
	/**
	 *  联系电话 （运单）收货客户手机
	 */
	private String receiveCustomerMobilephone;
	/**
	 *  联系电话 （运单）收货客户固定电话
	 * @author yangqiang 309603
	 */
	private String receiveCustomerPhone;

	/**
	 *  客户地址 （运单）收货具体地址
	 */
	private String receiveCustomerAddress;
	
	/**
	 *  客户地址 （运单）收货具体地址备注
	 */
	private String receiveCustomerAddressNote;

	/**
	 *  到付金额 （运单）
	 */
	private BigDecimal toPayAmount;

	/**
	 *  代收货款 （运单）
	 */
	private BigDecimal codAmount;

	/**
	 *  包装 (派送单明细)
	 */
	private String goodsPackage;
	/**
	 *  件数 （到达联件数）
	 */
	private Integer arriveSheetGoodsQty;

	/**
	 *  重量（单位：千克）（派送单明细）
	 */
	private BigDecimal weight;
	/**
	 *  体积（派送单明细）
	 */
	private BigDecimal volume;
	

	/** 
	 * 是否贵重物品 （运单）
	 */
	private String preciousGoods;

	/**
	 *  备注 (派送单明细)
	 */
	private String notes;
	
	/**
	 * 是否已采集
	 */
	private String isCollect;
	
	/**
	 * 运单运行时效是否超时
	 */
	private String isDeryOvertime;
	
	/**
	 * 开单时间
	 */
	private Date billTime; 
	
	/**
	 * 出发部门Code
	 */
	private String receiveOrgCode;
	
	/**
	 * 到达部门Code
	 */
	private String customerPickupOrgCode;
	
	/**
	 * 产品Code
	 */
	private String productCode;
	
	/**
	 *  关联单号应收到付款.
	 */
	private BigDecimal oldReceiveablePayAmoout;

	/**
	 *  总金额.
	 */
	private BigDecimal totalMoney;
	
	/**
	 * 是否返货新开单的运单
	 */
	private String isNewWaybillNo;

	/**
	 * 返单类别
	 */
	private String returnBillType;

	/**
	 * 发货省份
	 */
	private String deliveryCustomerProvCode;

	/**
	 * 发货市
	 */
	private String deliveryCustomerCityCode;

	/**
	 * 发货区
	 */
	private String deliveryCustomerDistCode;

	/**
	 * 发货具体地址
	 */
	private String deliveryCustomerAddress;
	
	/**
	 * 发货具体地址备注
	 */
	private String deliveryCustomerAddressNote;
	
	/**
	 * 到达客户是否统一结算   DN201503300026.239284
	 */
	private String arriveCentralizedSettlement;
	/**
	 * 母件单号
	 */
	private String femaleWaybillNo;
	/**
	 * 子母件总件数
	 */
	private Integer twoInOneQty;
	
	/**
	 * 运单的总件数(开单件数) DN201508170015 add by 243921
	 */
	private Integer goodsQtyTotal;
	
	/**
	 * 规定兑现时间
	 */
	private Date cashTime;
	
	
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
	 * Gets the 到达联编号.
	 *
	 * @return the 到达联编号
	 */
	public String getArriveSheetNo() {
		return arriveSheetNo;
	}

	/**
	 * Sets the 到达联编号.
	 *
	 * @param arriveSheetNo the new 到达联编号
	 */
	public void setArriveSheetNo(String arriveSheetNo) {
		this.arriveSheetNo = arriveSheetNo;
	}

	/**
	 * Gets the 派送日期.
	 *
	 * @return the 派送日期
	 */
	public Date getSubmitTime() {
		return submitTime;
	}

	/**
	 * Sets the 派送日期.
	 *
	 * @param submitTime the new 派送日期
	 */
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	/**
	 * Gets the 客户姓名 收货（运单）.
	 *
	 * @return the 客户姓名 收货（运单）
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * Sets the 客户姓名 收货（运单）.
	 *
	 * @param receiveCustomerName the new 客户姓名 收货（运单）
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * Gets the 联系电话 （运单）收货客户手机.
	 *
	 * @return the 联系电话 （运单）收货客户手机
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	/**
	 * Sets the 联系电话 （运单）收货客户手机.
	 *
	 * @param receiveCustomerMobilephone the new 联系电话 （运单）收货客户手机
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * Gets the 客户地址 （运单）收货具体地址.
	 *
	 * @return the 客户地址 （运单）收货具体地址
	 */
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	/**
	 * Sets the 客户地址 （运单）收货具体地址.
	 *
	 * @param receiveCustomerAddress the new 客户地址 （运单）收货具体地址
	 */
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	/**
	 * Gets the 到付金额 （运单）.
	 *
	 * @return the 到付金额 （运单）
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * Sets the 到付金额 （运单）.
	 *
	 * @param toPayAmount the new 到付金额 （运单）
	 */
	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	/**
	 * Gets the 代收货款 （运单）.
	 *
	 * @return the 代收货款 （运单）
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * Sets the 代收货款 （运单）.
	 *
	 * @param codAmount the new 代收货款 （运单）
	 */
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * Gets the 包装 (派送单明细).
	 *
	 * @return the 包装 (派送单明细)
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}

	/**
	 * Sets the 包装 (派送单明细).
	 *
	 * @param goodsPackage the new 包装 (派送单明细)
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	/**
	 * Gets the 件数 （到达联件数）.
	 *
	 * @return the 件数 （到达联件数）
	 */
	public Integer getArriveSheetGoodsQty() {
		return arriveSheetGoodsQty;
	}

	/**
	 * Sets the 件数 （到达联件数）.
	 *
	 * @param arriveSheetGoodsQty the new 件数 （到达联件数）
	 */
	public void setArriveSheetGoodsQty(Integer arriveSheetGoodsQty) {
		this.arriveSheetGoodsQty = arriveSheetGoodsQty;
	}

	/**
	 * Gets the 重量（单位：千克）（派送单明细）.
	 *
	 * @return the 重量（单位：千克）（派送单明细）
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * Sets the 重量（单位：千克）（派送单明细）.
	 *
	 * @param weight the new 重量（单位：千克）（派送单明细）
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * Gets the 是否贵重物品 （运单）.
	 *
	 * @return the 是否贵重物品 （运单）
	 */
	public String getPreciousGoods() {
		return preciousGoods;
	}

	/**
	 * Sets the 是否贵重物品 （运单）.
	 *
	 * @param preciousGoods the new 是否贵重物品 （运单）
	 */
	public void setPreciousGoods(String preciousGoods) {
		this.preciousGoods = preciousGoods;
	}

	/**
	 * Gets the 备注 (派送单明细).
	 *
	 * @return the 备注 (派送单明细)
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Sets the 备注 (派送单明细).
	 *
	 * @param notes the new 备注 (派送单明细)
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Gets the 派送单号.
	 *
	 * @return the 派送单号
	 */
	public String getDeliverbillNo() {
		return deliverbillNo;
	}

	/**
	 * Sets the 派送单号.
	 *
	 * @param deliverbillNo the new 派送单号
	 */
	public void setDeliverbillNo(String deliverbillNo) {
		this.deliverbillNo = deliverbillNo;
	}

	/**
	 * Gets the 流水号列表.
	 *
	 * @return the 流水号列表
	 */
	public List<String> getSerialNum() {
		return serialNum;
	}

	/**
	 * Sets the 流水号列表.
	 *
	 * @param serialNum the new 流水号列表
	 */
	public void setSerialNum(List<String> serialNum) {
		this.serialNum = serialNum;
	}

	/**
	 * Gets the 体积（派送单明细）.
	 *
	 * @return the 体积（派送单明细）
	 */
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * Sets the 体积（派送单明细）.
	 *
	 * @param volume the new 体积（派送单明细）
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}

	public String getIsDeryOvertime() {
		return isDeryOvertime;
	}

	public void setIsDeryOvertime(String isDeryOvertime) {
		this.isDeryOvertime = isDeryOvertime;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}

	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}

	public BigDecimal getOldReceiveablePayAmoout() {
		return oldReceiveablePayAmoout;
	}

	public void setOldReceiveablePayAmoout(BigDecimal oldReceiveablePayAmoout) {
		this.oldReceiveablePayAmoout = oldReceiveablePayAmoout;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getIsNewWaybillNo() {
		return isNewWaybillNo;
	}

	public void setIsNewWaybillNo(String isNewWaybillNo) {
		this.isNewWaybillNo = isNewWaybillNo;
	}

	public String getReturnBillType() {
		return returnBillType;
	}

	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	public String getDeliveryCustomerProvCode() {
		return deliveryCustomerProvCode;
	}

	public void setDeliveryCustomerProvCode(String deliveryCustomerProvCode) {
		this.deliveryCustomerProvCode = deliveryCustomerProvCode;
	}

	public String getDeliveryCustomerCityCode() {
		return deliveryCustomerCityCode;
	}

	public void setDeliveryCustomerCityCode(String deliveryCustomerCityCode) {
		this.deliveryCustomerCityCode = deliveryCustomerCityCode;
	}

	public String getDeliveryCustomerDistCode() {
		return deliveryCustomerDistCode;
	}

	public void setDeliveryCustomerDistCode(String deliveryCustomerDistCode) {
		this.deliveryCustomerDistCode = deliveryCustomerDistCode;
	}

	public String getDeliveryCustomerAddress() {
		return deliveryCustomerAddress;
	}

	public void setDeliveryCustomerAddress(String deliveryCustomerAddress) {
		this.deliveryCustomerAddress = deliveryCustomerAddress;
	}

	public String getDeliveryCustomerAddressNote() {
		return deliveryCustomerAddressNote;
	}

	public void setDeliveryCustomerAddressNote(String deliveryCustomerAddressNote) {
		this.deliveryCustomerAddressNote = deliveryCustomerAddressNote;
	}

	public String getArriveCentralizedSettlement() {
		return arriveCentralizedSettlement;
	}

	public void setArriveCentralizedSettlement(String arriveCentralizedSettlement) {
		this.arriveCentralizedSettlement = arriveCentralizedSettlement;
	}
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public Integer getTwoInOneQty() {
		return twoInOneQty;
	}

	public void setTwoInOneQty(Integer twoInOneQty) {
		this.twoInOneQty = twoInOneQty;
	}
	public String getFemaleWaybillNo() {
		return femaleWaybillNo;
	}

	public void setFemaleWaybillNo(String femaleWaybillNo) {
		this.femaleWaybillNo = femaleWaybillNo;
	}

	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	public Date getCashTime() {
		return cashTime;
	}

	public void setCashTime(Date cashTime) {
		this.cashTime = cashTime;
	}
}