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
 *  FILE PATH          :/AirWaybillEntity.java
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

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
* @description 航空正单信息实体
* @version 1.0
* @author 269701-foss-lln
* @update 2016年5月17日 下午7:18:37
 */
public class OPPNeedAirWaybillEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2501190323492848731L;
	/**
	 * id
	 */
	private String id;
	
	/**
	 * 航空公司二字码
	 */
	private String airLineTwoletter;
	/**
	 * 正单号
	 */
	private String airWaybillNo;
	/**
	 * 目的站编码
	 */
	private String arrvRegionCode;
	/**
	 * 目的站名称
	 */
	private String arrvRegionName;
	/**
	 * 配载类型
	 */
	private String airAssembleType;
	/**
	 * 到达网点编码
	 */
	private String destOrgCode;
	/**
	 * 到达网点名称             
	 */
	private String destOrgName;
	/**
	 * 收货人编码
	 */
	private String receiverCode;
	/**
	 * 收货人名称
	 */
	private String receiverName;
	/**
	 * 收货人电话
	 */
	private String receiverContactPhone;

	/**
	 * 收货人地址
	 */
	private String receiverAddress;
	/**
	 * 航班号
	 */
	private String flightNo;
	/**
	 * 航班日期
	 */
	private String flightDate; 
	/**
	 * 付款方式
	 */
	private String paymentType;
	/**
	 * 毛重
	 */
	private BigDecimal grossWeight;
	/**
	 * 计费重量
	 */
	private BigDecimal billingWeight;
	/**
	 * 承运人/外发代理_编号
	 */
	private String agenctCode;
	/**
	 * 承运人/外发代理_名称
	 */
	private String agencyName;
	/**
	 * 件数
	 */
	private Integer goodsQty;
	/**
	 * 体积
	 */
	private BigDecimal volume;

	/**
	 * 货物名称
	 */
	private String goodsName;

	/**
	 * 总金额
	 */
	private BigDecimal feeTotal;
	/**
	 * 提货方式
	 */
	private String pickupType;
	/**
	 * 制单部门编号
	 */
	private String createOrgCode;
	/**
	 * 制单部门名称
	 */
	private String createOrgName;
	/**制单人部门编号
	 * 
	 */
	private String createUserCode;
	/**
	 * 制单人名称
	 */
	private String createUserName;
	/**
	 * 制单时间
	 */
	private String createTime;
	/**
	 * 修改人编号
	 */
	private String modifyUserCode;
	/**
	 * 修改人名称
	 */
	private String modifyUserName;
	/**
	 * 修改时间
	 */
	private String modifyTime;
	
	/**
	 * 币种
	 */
	private String currencyCode;
	
	/**
	 * 合票号
	 */
	private String jointTicketNo;
    
    /**
	 * 运输性质
	 */
	private String productCode;
	
	/**
	 * 运输性质名称
	 */
	private String productName;

	//是否推送完成--推送中(N)/推送完成(Y)
	private String pushStatus;
	
	// 操作状态--新增（INSERT）/修改（UPDATE）/删除（DELETE）
	private String operStatus;
	
	
	
	public String getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(String operStatus) {
		this.operStatus = operStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAirLineTwoletter() {
		return airLineTwoletter;
	}

	public void setAirLineTwoletter(String airLineTwoletter) {
		this.airLineTwoletter = airLineTwoletter;
	}

	public String getAirWaybillNo() {
		return airWaybillNo;
	}

	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}

	public String getArrvRegionCode() {
		return arrvRegionCode;
	}

	public void setArrvRegionCode(String arrvRegionCode) {
		this.arrvRegionCode = arrvRegionCode;
	}

	public String getArrvRegionName() {
		return arrvRegionName;
	}

	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}

	public String getAirAssembleType() {
		return airAssembleType;
	}

	public void setAirAssembleType(String airAssembleType) {
		this.airAssembleType = airAssembleType;
	}

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	public String getDestOrgName() {
		return destOrgName;
	}

	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	public String getReceiverCode() {
		return receiverCode;
	}

	public void setReceiverCode(String receiverCode) {
		this.receiverCode = receiverCode;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverContactPhone() {
		return receiverContactPhone;
	}

	public void setReceiverContactPhone(String receiverContactPhone) {
		this.receiverContactPhone = receiverContactPhone;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}


	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getBillingWeight() {
		return billingWeight;
	}

	public void setBillingWeight(BigDecimal billingWeight) {
		this.billingWeight = billingWeight;
	}

	public String getAgenctCode() {
		return agenctCode;
	}

	public void setAgenctCode(String agenctCode) {
		this.agenctCode = agenctCode;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public Integer getGoodsQty() {
		return goodsQty;
	}

	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getFeeTotal() {
		return feeTotal;
	}

	public void setFeeTotal(BigDecimal feeTotal) {
		this.feeTotal = feeTotal;
	}

	public String getPickupType() {
		return pickupType;
	}

	public void setPickupType(String pickupType) {
		this.pickupType = pickupType;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getJointTicketNo() {
		return jointTicketNo;
	}

	public void setJointTicketNo(String jointTicketNo) {
		this.jointTicketNo = jointTicketNo;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}

	/**
	 * @return flightDate : return the property flightDate.
	 * @author 269701-foss-lln
	 * @update 2016年6月1日 上午10:14:30
	 * @version V1.0
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate : set the property flightDate.
	 * @author 269701-foss-lln
	 * @update 2016年6月1日 上午10:14:30
	 * @version V1.0
	 */
	
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return createTime : return the property createTime.
	 * @author 269701-foss-lln
	 * @update 2016年6月1日 上午10:14:30
	 * @version V1.0
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime : set the property createTime.
	 * @author 269701-foss-lln
	 * @update 2016年6月1日 上午10:14:30
	 * @version V1.0
	 */
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return modifyTime : return the property modifyTime.
	 * @author 269701-foss-lln
	 * @update 2016年6月1日 上午10:14:30
	 * @version V1.0
	 */
	public String getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime : set the property modifyTime.
	 * @author 269701-foss-lln
	 * @update 2016年6月1日 上午10:14:30
	 * @version V1.0
	 */
	
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

}