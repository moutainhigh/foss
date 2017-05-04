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
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/domain/SendSignBillEntity.java
 *  
 *  FILE NAME          :SendSignBillEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 派送签单entity
 * 
 * @author 099197-foss-liming
 * @date 2012-11-29 上午9:49:47
 */
public class SendSignBillEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2211970171652884081L;
	/**id*/
	private String id;

	/**	  
	 * 签单编号
	 */
	private String signBillNo;

	/**
	 * 交接单号
	 */
	private String handoverNo; 

	/**
	 *  用车部门
	 */
	private String useTruckOrgCode;

	/**
	 * 用车部门名称
	 */
	private String useTruckOrgName; 

	/**
	 * 司机
	 */
	private String driverCode;

	/**
	 * 司机姓名	
	 */
	private String driverName; 

	/**
	 * 车牌号
	 */
	private String vehicleNo;

	/**
	 * 车型
	 */
	private String vehicleTypeLength;

	/**
	 *  转货里程
	 */
	private BigDecimal transferDistance;

	/**
	 * 派送票数
	 */
	private Long sendBillQty;

	/**
	 *  体积
	 */
	private BigDecimal volume;

	/**
	 * 重量
	 */
	private BigDecimal weight;

	/**
	 * 进仓票数
	 */
	private BigDecimal inStockBillQty;

	/**
	 * 上楼票数
	 */
	private BigDecimal upstairsBillQty;

	/**
	 * 里程
	 */
	private BigDecimal distance;

	/**
	 * 单独配送
	 */
	private String isSingleSend; 

	/**
	 * 上楼费
	 */
	private BigDecimal upstairsFee;

	/**
	 * 非进仓票数费用
	 */
	private BigDecimal noInStockBillFee; 

	/**
	 * 进仓票数费用
	 */
	private BigDecimal inStockBillFee;

	/**
	 * 司机实际提成总额
	 */
	private BigDecimal driverRoyaltyAmount;	

	/**
	 *  用车费用划分
	 */
	private BigDecimal useTruckFee;

	/**
	 * 其它费用
	 */
	private BigDecimal otherFee; 

	/**
	 * 日期
	 */
	private Date signBillDate;
	
	/**
	 * 接货员
	 */
	private String receiverCode;
	
	/**
	 * 接货员姓名
	 */
	private String receiverName;
	
	/**
	 * 派送进仓编号
	 */
	private String sendNo;
	
	/**
	 * 拉回票数
	 */
	private Long haulBackBillQty;

    /**
     *  上楼费提成
     */
    private BigDecimal upstairsFeeRoyalty;
  
    /**
     * 币种
     */
    private String currencyCode;
	 
    /**
	 *车型 名称
	 */
	private String vehicleTypeLengthName; 
	
	/**
	 * 新增部门
	 */
	private String orgCode;
	
	/**
	 * 新增部门
	 */
	private String orgName;

	/**
	 * 获取 车型 名称.
	 *
	 * @return the 车型 名称
	 */
	public String getVehicleTypeLengthName() {
		return vehicleTypeLengthName;
	}

	/**
	 * 设置 车型 名称.
	 *
	 * @param vehicleTypeLengthName the new 车型 名称
	 */
	public void setVehicleTypeLengthName(String vehicleTypeLengthName) {
		this.vehicleTypeLengthName = vehicleTypeLengthName;
	}
	  
	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 签单编号.
	 *
	 * @return the 签单编号
	 */
	public String getSignBillNo() {
		return signBillNo;
	}

	/**
	 * 设置 签单编号.
	 *
	 * @param signBillNo the new 签单编号
	 */
	public void setSignBillNo(String signBillNo) {
		this.signBillNo = signBillNo;
	}

	/**
	 * 获取 交接单号.
	 *
	 * @return the 交接单号
	 */
	public String getHandoverNo() {
		return handoverNo;
	}

	/**
	 * 设置 交接单号.
	 *
	 * @param handoverNo the new 交接单号
	 */
	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	/**
	 * 获取 用车部门.
	 *
	 * @return the 用车部门
	 */
	public String getUseTruckOrgCode() {
		return useTruckOrgCode;
	}

	/**
	 * 设置 用车部门.
	 *
	 * @param useTruckOrgCode the new 用车部门
	 */
	public void setUseTruckOrgCode(String useTruckOrgCode) {
		this.useTruckOrgCode = useTruckOrgCode;
	}

	/**
	 * 获取 用车部门名称.
	 *
	 * @return the 用车部门名称
	 */
	public String getUseTruckOrgName() {
		return useTruckOrgName;
	}

	/**
	 * 设置 用车部门名称.
	 *
	 * @param useTruckOrgName the new 用车部门名称
	 */
	public void setUseTruckOrgName(String useTruckOrgName) {
		this.useTruckOrgName = useTruckOrgName;
	}

	/**
	 * 获取 司机.
	 *
	 * @return the 司机
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * 设置 司机.
	 *
	 * @param driverCode the new 司机
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * 获取 司机姓名.
	 *
	 * @return the 司机姓名
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * 设置 司机姓名.
	 *
	 * @param driverName the new 司机姓名
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * 获取 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 车型.
	 *
	 * @return the 车型
	 */
	public String getVehicleTypeLength() {
		return vehicleTypeLength;
	}

	/**
	 * 设置 车型.
	 *
	 * @param vehicleTypeLength the new 车型
	 */
	public void setVehicleTypeLength(String vehicleTypeLength) {
		this.vehicleTypeLength = vehicleTypeLength;
	}

	/**
	 * 获取 转货里程.
	 *
	 * @return the 转货里程
	 */
	public BigDecimal getTransferDistance() {
		return transferDistance;
	}

	/**
	 * 设置 转货里程.
	 *
	 * @param transferDistance the new 转货里程
	 */
	public void setTransferDistance(BigDecimal transferDistance) {
		this.transferDistance = transferDistance;
	}

	/**
	 * 获取 派送票数.
	 *
	 * @return the 派送票数
	 */
	public Long getSendBillQty() {
		return sendBillQty;
	}

	/**
	 * 设置 派送票数.
	 *
	 * @param sendBillQty the new 派送票数
	 */
	public void setSendBillQty(Long sendBillQty) {
		this.sendBillQty = sendBillQty;
	}

	/**
	 * 获取 体积.
	 *
	 * @return the 体积
	 */
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * 设置 体积.
	 *
	 * @param volume the new 体积
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
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
	 * 获取 进仓票数.
	 *
	 * @return the 进仓票数
	 */
	public BigDecimal getInStockBillQty() {
		return inStockBillQty;
	}

	/**
	 * 设置 进仓票数.
	 *
	 * @param inStockBillQty the new 进仓票数
	 */
	public void setInStockBillQty(BigDecimal inStockBillQty) {
		this.inStockBillQty = inStockBillQty;
	}

	/**
	 * 获取 上楼票数.
	 *
	 * @return the 上楼票数
	 */
	public BigDecimal getUpstairsBillQty() {
		return upstairsBillQty;
	}

	/**
	 * 设置 上楼票数.
	 *
	 * @param upstairsBillQty the new 上楼票数
	 */
	public void setUpstairsBillQty(BigDecimal upstairsBillQty) {
		this.upstairsBillQty = upstairsBillQty;
	}

	/**
	 * 获取 里程.
	 *
	 * @return the 里程
	 */
	public BigDecimal getDistance() {
		return distance;
	}

	/**
	 * 设置 里程.
	 *
	 * @param distance the new 里程
	 */
	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	/**
	 * 获取 单独配送.
	 *
	 * @return the 单独配送
	 */
	public String getIsSingleSend() {
		return isSingleSend;
	}

	/**
	 * 设置 单独配送.
	 *
	 * @param isSingleSend the new 单独配送
	 */
	public void setIsSingleSend(String isSingleSend) {
		this.isSingleSend = isSingleSend;
	}

	/**
	 * 获取 上楼费.
	 *
	 * @return the 上楼费
	 */
	public BigDecimal getUpstairsFee() {
		return upstairsFee;
	}

	/**
	 * 设置 上楼费.
	 *
	 * @param upstairsFee the new 上楼费
	 */
	public void setUpstairsFee(BigDecimal upstairsFee) {
		this.upstairsFee = upstairsFee;
	}

	/**
	 * 获取 非进仓票数费用.
	 *
	 * @return the 非进仓票数费用
	 */
	public BigDecimal getNoInStockBillFee() {
		return noInStockBillFee;
	}

	/**
	 * 设置 非进仓票数费用.
	 *
	 * @param noInStockBillFee the new 非进仓票数费用
	 */
	public void setNoInStockBillFee(BigDecimal noInStockBillFee) {
		this.noInStockBillFee = noInStockBillFee;
	}

	/**
	 * 获取 进仓票数费用.
	 *
	 * @return the 进仓票数费用
	 */
	public BigDecimal getInStockBillFee() {
		return inStockBillFee;
	}

	/**
	 * 设置 进仓票数费用.
	 *
	 * @param inStockBillFee the new 进仓票数费用
	 */
	public void setInStockBillFee(BigDecimal inStockBillFee) {
		this.inStockBillFee = inStockBillFee;
	}

	/**
	 * 获取 司机实际提成总额.
	 *
	 * @return the 司机实际提成总额
	 */
	public BigDecimal getDriverRoyaltyAmount() {
		return driverRoyaltyAmount;
	}

	/**
	 * 设置 司机实际提成总额.
	 *
	 * @param driverRoyaltyAmount the new 司机实际提成总额
	 */
	public void setDriverRoyaltyAmount(BigDecimal driverRoyaltyAmount) {
		this.driverRoyaltyAmount = driverRoyaltyAmount;
	}

	/**
	 * 获取 用车费用划分.
	 *
	 * @return the 用车费用划分
	 */
	public BigDecimal getUseTruckFee() {
		return useTruckFee;
	}

	/**
	 * 设置 用车费用划分.
	 *
	 * @param useTruckFee the new 用车费用划分
	 */
	public void setUseTruckFee(BigDecimal useTruckFee) {
		this.useTruckFee = useTruckFee;
	}

	/**
	 * 获取 其它费用.
	 *
	 * @return the 其它费用
	 */
	public BigDecimal getOtherFee() {
		return otherFee;
	}

	/**
	 * 设置 其它费用.
	 *
	 * @param otherFee the new 其它费用
	 */
	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	/**
	 * 获取 日期.
	 *
	 * @return the 日期
	 */
	public Date getSignBillDate() {
		return signBillDate;
	}

	/**
	 * 设置 日期.
	 *
	 * @param signBillDate the new 日期
	 */
	public void setSignBillDate(Date signBillDate) {
		this.signBillDate = signBillDate;
	}

	/**
	 * 获取 接货员.
	 *
	 * @return the 接货员
	 */
	public String getReceiverCode() {
		return receiverCode;
	}

	/**
	 * 设置 接货员.
	 *
	 * @param receiverCode the new 接货员
	 */
	public void setReceiverCode(String receiverCode) {
		this.receiverCode = receiverCode;
	}

	/**
	 * 获取 接货员姓名.
	 *
	 * @return the 接货员姓名
	 */
	public String getReceiverName() {
		return receiverName;
	}

	/**
	 * 设置 接货员姓名.
	 *
	 * @param receiverName the new 接货员姓名
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	/**
	 * 获取 派送进仓编号.
	 *
	 * @return the 派送进仓编号
	 */
	public String getSendNo() {
		return sendNo;
	}

	/**
	 * 设置 派送进仓编号.
	 *
	 * @param sendNo the new 派送进仓编号
	 */
	public void setSendNo(String sendNo) {
		this.sendNo = sendNo;
	}

	/**
	 * 获取 拉回票数.
	 *
	 * @return the 拉回票数
	 */
	public Long getHaulBackBillQty() {
		return haulBackBillQty;
	}

	/**
	 * 设置 拉回票数.
	 *
	 * @param haulBackBillQty the new 拉回票数
	 */
	public void setHaulBackBillQty(Long haulBackBillQty) {
		this.haulBackBillQty = haulBackBillQty;
	}

	/**
	 * 获取 上楼费提成.
	 *
	 * @return the 上楼费提成
	 */
	public BigDecimal getUpstairsFeeRoyalty() {
		return upstairsFeeRoyalty;
	}

	/**
	 * 设置 上楼费提成.
	 *
	 * @param upstairsFeeRoyalty the new 上楼费提成
	 */
	public void setUpstairsFeeRoyalty(BigDecimal upstairsFeeRoyalty) {
		this.upstairsFeeRoyalty = upstairsFeeRoyalty;
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
	 * orgCode   
	 *   
	 * @return  the orgCode   
	 */
	
	public String getOrgCode() {
		return orgCode;
	}

	/**   
	 * @param orgCode the orgCode to set
	 * Date:2013-5-7下午2:34:38
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**   
	 * orgName   
	 *   
	 * @return  the orgName   
	 */
	
	public String getOrgName() {
		return orgName;
	}

	/**   
	 * @param orgName the orgName to set
	 * Date:2013-5-7下午2:34:38
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}