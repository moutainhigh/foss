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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirChangeInventoryEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.domain;


import com.deppon.foss.framework.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 合大票清单明细
 * @author 099197-foss-zhoudejun
 * @date 2012-11-12 下午12:16:27
 */
public class AirChangeInventoryEntity extends BaseEntity {
	
	private static final long serialVersionUID = 49778120162228048L;
	/**
	 * 合大票清单组建ID
	 */
	private String airPickupbillId;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 目的站
	 */
	private String arrvRegionName;
	/**
	 * 品名
	 */
	private String goodsName;
	/**
	 * 件数
	 */
	private Integer goodsQty;
	/**
	 * 重量
	 */
	private BigDecimal weight;
	/**
	 * 计费重量
	 */
	private BigDecimal billingWeight;
	/**
	 * 是否中转
	 */
	private String beTransfer;
	/**
	 * 提货方式
	 */
	private String pickupType;
	/**
	 * 送货费
	 */
	private BigDecimal deliverFee;
	/**
	 * 到付款
	 */
	private BigDecimal arrivalFee;
	/**
	 * 代收款
	 */
	private BigDecimal collectionFee;
	/**
	 * 备注
	 */
	private String notes;
	/**
	 * 正单号
	 */
	private String airWaybillNo;
	/**
	 * 币种
	 */
	private String currencyCode;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 收货人电话
	 */
	private String receiverContactPhone;
	/**
	 * 收货人地址
	 */
	private String receiverAddress;
	/**
	 * 收货人姓名
	 */
	private String receiverName;
	/**
	 * 应付单来源单据子类型
	 */
	private String billType;
	/**
	 * 代理网点编码
	 */
	private String agentCode;
	/**
	 * 目的站编码
	 */
	private String arrvRegionCode;
		
	/**
	 * 获取 合大票清单组建ID.
	 *
	 * @return the 合大票清单组建ID
	 */
	public String getAirPickupbillId() {
		return airPickupbillId;
	}

	/**
	 * 设置 合大票清单组建ID.
	 *
	 * @param airPickupbillId the new 合大票清单组建ID
	 */
	public void setAirPickupbillId(String airPickupbillId) {
		this.airPickupbillId = airPickupbillId;
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
	 * 获取 目的站.
	 *
	 * @return the 目的站
	 */
	public String getArrvRegionName() {
		return arrvRegionName;
	}

	/**
	 * 设置 目的站.
	 *
	 * @param arrvRegionName the new 目的站
	 */
	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}

	/**
	 * 获取 品名.
	 *
	 * @return the 品名
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * 设置 品名.
	 *
	 * @param goodsName the new 品名
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * 获取 件数.
	 *
	 * @return the 件数
	 */
	public Integer getGoodsQty() {
		return goodsQty;
	}

	/**
	 * 设置 件数.
	 *
	 * @param goodsQty the new 件数
	 */
	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	/**
	 * 获取 重量.
	 *
	 * @return the 重量
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * 设置 重量.
	 *
	 * @param weight the new 重量
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * 获取 计费重量.
	 *
	 * @return the 计费重量
	 */
	public BigDecimal getBillingWeight() {
		return billingWeight;
	}

	/**
	 * 设置 计费重量.
	 *
	 * @param billingWeight the new 计费重量
	 */
	public void setBillingWeight(BigDecimal billingWeight) {
		this.billingWeight = billingWeight;
	}

	/**
	 * 获取 是否中转.
	 *
	 * @return the 是否中转
	 */
	public String getBeTransfer() {
		return beTransfer;
	}

	/**
	 * 设置 是否中转.
	 *
	 * @param beTransfer the new 是否中转
	 */
	public void setBeTransfer(String beTransfer) {
		this.beTransfer = beTransfer;
	}

	/**
	 * 获取 提货方式.
	 *
	 * @return the 提货方式
	 */
	public String getPickupType() {
		return pickupType;
	}

	/**
	 * 设置 提货方式.
	 *
	 * @param pickupType the new 提货方式
	 */
	public void setPickupType(String pickupType) {
		this.pickupType = pickupType;
	}

	/**
	 * 获取 送货费.
	 *
	 * @return the 送货费
	 */
	public BigDecimal getDeliverFee() {
		return deliverFee;
	}

	/**
	 * 设置 送货费.
	 *
	 * @param deliverFee the new 送货费
	 */
	public void setDeliverFee(BigDecimal deliverFee) {
		this.deliverFee = deliverFee;
	}

	/**
	 * 获取 到付款.
	 *
	 * @return the 到付款
	 */
	public BigDecimal getArrivalFee() {
		return arrivalFee;
	}

	/**
	 * 设置 到付款.
	 *
	 * @param arrivalFee the new 到付款
	 */
	public void setArrivalFee(BigDecimal arrivalFee) {
		this.arrivalFee = arrivalFee;
	}

	/**
	 * 获取 代收款.
	 *
	 * @return the 代收款
	 */
	public BigDecimal getCollectionFee() {
		return collectionFee;
	}

	/**
	 * 设置 代收款.
	 *
	 * @param collectionFee the new 代收款
	 */
	public void setCollectionFee(BigDecimal collectionFee) {
		this.collectionFee = collectionFee;
	}

	/**
	 * 获取 备注.
	 *
	 * @return the 备注
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * 设置 备注.
	 *
	 * @param notes the new 备注
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * 获取 正单号.
	 *
	 * @return the 正单号
	 */
	public String getAirWaybillNo() {
		return airWaybillNo;
	}

	/**
	 * 设置 正单号.
	 *
	 * @param airWaybillNo the new 正单号
	 */
	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}

	/**
	 * 获取 币种.
	 *
	 * @return the 币种
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 设置 币种.
	 *
	 * @param currencyCode the new 币种
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 获取 创建时间.
	 *
	 * @return the 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置 创建时间.
	 *
	 * @param createTime the new 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取 收货人电话.
	 *
	 * @return the 收货人电话
	 */
	public String getReceiverContactPhone() {
		return receiverContactPhone;
	}

	/**
	 * 设置 收货人电话.
	 *
	 * @param receiverContactPhone the new 收货人电话
	 */
	public void setReceiverContactPhone(String receiverContactPhone) {
		this.receiverContactPhone = receiverContactPhone;
	}

	/**
	 * 获取 收货人地址.
	 *
	 * @return the 收货人地址
	 */
	public String getReceiverAddress() {
		return receiverAddress;
	}

	/**
	 * 设置 收货人地址.
	 *
	 * @param receiverAddress the new 收货人地址
	 */
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	/**
	 * 获取 收货人姓名.
	 *
	 * @return the 收货人姓名
	 */
	public String getReceiverName() {
		return receiverName;
	}

	/**
	 * 设置 收货人姓名.
	 *
	 * @param receiverName the new 收货人姓名
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getArrvRegionCode() {
		return arrvRegionCode;
	}

	public void setArrvRegionCode(String arrvRegionCode) {
		this.arrvRegionCode = arrvRegionCode;
	}
	
}