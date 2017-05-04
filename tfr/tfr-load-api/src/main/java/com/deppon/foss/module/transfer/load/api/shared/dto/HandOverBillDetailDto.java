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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/HandOverBillDetailDto.java
 *  
 *  FILE NAME          :HandOverBillDetailDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
/**
 * HandOverBillDetailDto
 * @author dp-duyi
 * @date 2012-12-24 上午11:03:22
 */
public class HandOverBillDetailDto extends HandOverBillDetailEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SELF_PICKUP 自提（不含机场提货费）、
	 * FREE_PICKUP免费自提、
	 * AIRPORT_PICKUP 机场自提、
	 * DELIVERY 送货、
	 * INNER_PICKUP 内部带货自提
	 * 提货方式
	 */
	private String receiveMethod;	
	/**目的站*/
	private String receiveOrgCode;	
	/**收货客户名称*/
	private String receiveCustomerName;	
	/**收货联系人*/
	private String receiveCustomerContact;
	/**收货客户手机*/
	private String receiveCustomerMobilephone;	
	/**收货客户电话*/
	private String receiveCustomerPhone;
	/**收货人省份*/
    private String receiveCustomerProvCode;
    /**收货人城市*/
    private String receiveCustomerCityCode;
    /**收货人地区*/
    private String receiveCustomerDistCode;
	/**收货客户地址*/
	private String receiveCustomerAddress;	
	/**到付金额*/
	private BigDecimal toPayAmount;	
	
	/**货物尺寸*/
	private String goodsSize;
	
	/**代理运单号*/
	private String agentWaybillNo;
	/**发货人*/
	private String  deliveryCustomerName;
	/**发货客户联系人**/
	private String  deliveryCustomerContact;
	
	
	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	public String getAgentWaybillNo() {
		return agentWaybillNo;
	}

	public void setAgentWaybillNo(String agentWaybillNo) {
		this.agentWaybillNo = agentWaybillNo;
	}

	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}
	public String getGoodsSize() {
		return goodsSize;
	}

	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
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

	/**订单号*/
	private String orderNo;	
	/**司机*/
	private String driverName;
	/**司机电话*/
	private String driverPhone;
	/**创建人*/
	private String creatorCode;
	/**货物类型*/
	private String goodsType;
	

    
	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getCreatorCode() {
		return creatorCode;
	}

	public void setCreatorCode(String creatorCode) {
		this.creatorCode = creatorCode;
	}

	/**
	 * Gets the 订单号.
	 *
	 * @return the 订单号
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * Sets the 订单号.
	 *
	 * @param orderNo the new 订单号
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 *现金-CASH；
	 *到付-COLLECT；
	 *月结-MONTHLY；
	 *银行卡-BANK_CARD；
	 *临时欠款-TEMPORARY_ARREARS；
	 *临时欠款等收款放货-TEMPORARY_ARREARS_THEN_RELEASE；
	 *支票-CHECK；
	 *电汇-TELEGRAPHIC_TRANSFER；
	 *开单付款方式
	 ***/
	private String paidMethod;		
	/**开单付款方式中文名称*/
	private String paidMethodCN;				
	/**新增交接单，调整走货路径时使用*
	/**出发部门*/
	private String origOrgCode;
	/**到达部门*/
	private String destOrgCode;
	/**是否整车*/
	private String beJoinCar;
	/**车牌号*/
	private String vehicleNo;
	
	/**是否需要代打木架, 大于零说明需要**/
	private Long isPacked;
	
	/**
	 * Gets the sELF_PICKUP 自提（不含机场提货费）、 FREE_PICKUP免费自提、 AIRPORT_PICKUP 机场自提、 DELIVERY 送货、 INNER_PICKUP 内部带货自提 提货方式.
	 *
	 * @return the sELF_PICKUP 自提（不含机场提货费）、 FREE_PICKUP免费自提、 AIRPORT_PICKUP 机场自提、 DELIVERY 送货、 INNER_PICKUP 内部带货自提 提货方式
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}
	
	/**
	 * Sets the sELF_PICKUP 自提（不含机场提货费）、 FREE_PICKUP免费自提、 AIRPORT_PICKUP 机场自提、 DELIVERY 送货、 INNER_PICKUP 内部带货自提 提货方式.
	 *
	 * @param receiveMethod the new sELF_PICKUP 自提（不含机场提货费）、 FREE_PICKUP免费自提、 AIRPORT_PICKUP 机场自提、 DELIVERY 送货、 INNER_PICKUP 内部带货自提 提货方式
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}
	
	/**
	 * Gets the 收货客户手机.
	 *
	 * @return the 收货客户手机
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}
	
	/**
	 * Sets the 收货客户手机.
	 *
	 * @param receiveCustomerMobilephone the new 收货客户手机
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
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
	 *现金-CASH；</br>
	 *到付-COLLECT；</br>
	 *月结-MONTHLY；</br>
	 *银行卡-BANK_CARD；</br>
	 *临时欠款-TEMPORARY_ARREARS；</br>
	 *临时欠款等收款放货-TEMPORARY_ARREARS_THEN_RELEASE；</br>
	 *支票-CHECK；</br>
	 *电汇-TELEGRAPHIC_TRANSFER；</br>
	 ***/
	public String getPaidMethod() {
		return paidMethod;
	}
	
	/**
	 * Sets the 现金-CASH； 到付-COLLECT； 月结-MONTHLY； 银行卡-BANK_CARD； 临时欠款-TEMPORARY_ARREARS； 临时欠款等收款放货-TEMPORARY_ARREARS_THEN_RELEASE； 支票-CHECK； 电汇-TELEGRAPHIC_TRANSFER； 开单付款方式 *.
	 *
	 * @param paidMethod the new 现金-CASH； 到付-COLLECT； 月结-MONTHLY； 银行卡-BANK_CARD； 临时欠款-TEMPORARY_ARREARS； 临时欠款等收款放货-TEMPORARY_ARREARS_THEN_RELEASE； 支票-CHECK； 电汇-TELEGRAPHIC_TRANSFER； 开单付款方式 *
	 */
	public void setPaidMethod(String paidMethod) {
		if(StringUtils.equals(paidMethod, "CASH")) {
			setPaidMethodCN("现金");
		} else if (StringUtils.equals(paidMethod, "COLLECT")) {
			setPaidMethodCN("到付");
		} else if (StringUtils.equals(paidMethod, "MONTHLY")) {
			setPaidMethodCN("月结");
		} else if (StringUtils.equals(paidMethod, "BANK_CARD")) {
			setPaidMethodCN("银行卡");
		} else if (StringUtils.equals(paidMethod, "TEMPORARY_ARREARS")) {
			setPaidMethodCN("临时欠款");
		} else if (StringUtils.equals(paidMethod, "TEMPORARY_ARREARS_THEN_RELEASE")) {
			setPaidMethodCN("临时欠款等收款放货");
		} else if (StringUtils.equals(paidMethod, "CHECK")) {
			setPaidMethodCN("支票");
		} else if (StringUtils.equals(paidMethod, "TELEGRAPHIC_TRANSFER")) {
			setPaidMethodCN("电汇");
		}
		this.paidMethod = paidMethod;
	}
	
	/**
	 * Gets the 目的站.
	 *
	 * @return the 目的站
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}
	
	/**
	 * Sets the 目的站.
	 *
	 * @param receiveOrgCode the new 目的站
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}
	
	/**
	 * Gets the 收货客户名称.
	 *
	 * @return the 收货客户名称
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}
	
	/**
	 * Sets the 收货客户名称.
	 *
	 * @param receiveCustomerName the new 收货客户名称
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}
	
	/**
	 * Gets the 收货客户地址.
	 *
	 * @return the 收货客户地址
	 */
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}
	
	/**
	 * Sets the 收货客户地址.
	 *
	 * @param receiveCustomerAddress the new 收货客户地址
	 */
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
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
	 * Gets the 开单付款方式中文名称.
	 *
	 * @return the 开单付款方式中文名称
	 */
	public String getPaidMethodCN() {
		return paidMethodCN;
	}
	
	/**
	 * Sets the 开单付款方式中文名称.
	 *
	 * @param paidMethodCN the new 开单付款方式中文名称
	 */
	public void setPaidMethodCN(String paidMethodCN) {
		this.paidMethodCN = paidMethodCN;
	}
	
	
	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author dp-duyi
	 * @date 2013-3-15 下午12:33:44
	 * @see com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity#getOrigOrgCode()
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	
	
	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author dp-duyi
	 * @date 2013-3-15 下午12:33:44
	 * @see com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity#setOrigOrgCode(java.lang.String)
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	
	/**
	 * Gets the 到达部门.
	 *
	 * @return the 到达部门
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}
	
	/**
	 * Sets the 到达部门.
	 *
	 * @param destOrgCode the new 到达部门
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	
	/**
	 * Gets the 是否整车.
	 *
	 * @return the 是否整车
	 */
	public String getBeJoinCar() {
		return beJoinCar;
	}
	
	/**
	 * Sets the 是否整车.
	 *
	 * @param beJoinCar the new 是否整车
	 */
	public void setBeJoinCar(String beJoinCar) {
		this.beJoinCar = beJoinCar;
	}
	
	/**
	 * Gets the 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * Sets the 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * Gets the 是否需要代打木架, 大于零说明需要*.
	 *
	 * @return the 是否需要代打木架, 大于零说明需要*
	 */
	public Long getIsPacked() {
		return isPacked;
	}

	/**
	 * Sets the 是否需要代打木架, 大于零说明需要*.
	 *
	 * @param isPacked the new 是否需要代打木架, 大于零说明需要*
	 */
	public void setIsPacked(Long isPacked) {
		this.isPacked = isPacked;
	}

	/**   
	 * goodsType   
	 *   
	 * @return  the goodsType   
	 */
	
	public String getGoodsType() {
		return goodsType;
	}

	/**   
	 * @param goodsType the goodsType to set
	 * Date:2013-8-2下午6:10:05
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	/**
	 * 
	 * @return 发货客户联系人名称
	 * @author 257220
	 * @date 2015-8-11上午9:08:58
	 */
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}
	/**
	 * 设置发货客户联系人名称
	 * @param deliveryCustomerContact
	 * @author 257220
	 * @date 2015-8-11上午9:09:43
	 */
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}
}