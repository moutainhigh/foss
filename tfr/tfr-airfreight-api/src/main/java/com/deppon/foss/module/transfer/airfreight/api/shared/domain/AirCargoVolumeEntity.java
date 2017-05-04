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
 *  FILE PATH          :/AirCargoVolumeEntity.java
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

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
/**
 * 空运货量明细
 * @author 099197-foss-zhoudejun
 * @date 2012-10-19 下午4:53:47
 */
public class AirCargoVolumeEntity extends BaseEntity {
	
	private static final long serialVersionUID = 6236045677581160307L;
	
	/**
	 * 航空公司仓位明细ID
	 */
	private String airSpaceDetailId;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 开单部门编号
	 */
	private String createOrgCode;
	/**
	 * 开单部门名称
	 */
	private String createOrgName;
	/**
	 * 开单时间
	 */
	private Date createTime;
	/**
	 * 目的站编号
	 */
	private String arrvRegionCode;
	/**
	 * 目的站名称
	 */
	private String arrvRegionName;
	/**
	 * 库存状态
	 */
	private String stockStatus;
	/**
	 * 开单方式
	 */
	private String makeWaybillWay;
	/**
	 * 航班类型
	 */
	private String flightType;
	/**
	 * 尺寸
	 */
	private String measurement;
	/**
	 * 重量
	 */
	private BigDecimal weight;
	/**
	 * 体积
	 */
	private BigDecimal volume;
	/**
	 * 货物名称
	 */
	private String goodsName;
	/**
	 * 是否开单订舱
	 */
	private String beBooking;
	/**
	 * 计费重量
	 */
	private BigDecimal billingWeight;
	/**
	 * 收货价格
	 */
	private BigDecimal billingFee;
	/**
	 * 储运事项
	 */
	private String storageNotes;
	/**
	 * 件数
	 */
	private Long goodsQty;
	/**
	 * 预配班次
	 */
	private String planFlightNo;
	/**
	 * 是否加载
	 */
	private String isLoading;
	
	/**
	 * 获取 航空公司仓位明细ID.
	 *
	 * @return the 航空公司仓位明细ID
	 */
	public String getAirSpaceDetailId() {
		return airSpaceDetailId;
	}
	
	/**
	 * 设置 航空公司仓位明细ID.
	 *
	 * @param airSpaceDetailId the new 航空公司仓位明细ID
	 */
	public void setAirSpaceDetailId(String airSpaceDetailId) {
		this.airSpaceDetailId = airSpaceDetailId;
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
	 * 获取 开单部门编号.
	 *
	 * @return the 开单部门编号
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	
	/**
	 * 设置 开单部门编号.
	 *
	 * @param createOrgCode the new 开单部门编号
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	
	/**
	 * 获取 开单部门名称.
	 *
	 * @return the 开单部门名称
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}
	
	/**
	 * 设置 开单部门名称.
	 *
	 * @param createOrgName the new 开单部门名称
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	
	/**
	 * 获取 开单时间.
	 *
	 * @return the 开单时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置 开单时间.
	 *
	 * @param createTime the new 开单时间
	 */
	@DateFormat
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 获取 目的站编号.
	 *
	 * @return the 目的站编号
	 */
	public String getArrvRegionCode() {
		return arrvRegionCode;
	}
	
	/**
	 * 设置 目的站编号.
	 *
	 * @param arrvRegionCode the new 目的站编号
	 */
	public void setArrvRegionCode(String arrvRegionCode) {
		this.arrvRegionCode = arrvRegionCode;
	}
	
	/**
	 * 获取 目的站名称.
	 *
	 * @return the 目的站名称
	 */
	public String getArrvRegionName() {
		return arrvRegionName;
	}
	
	/**
	 * 设置 目的站名称.
	 *
	 * @param arrvRegionName the new 目的站名称
	 */
	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}
	
	/**
	 * 获取 库存状态.
	 *
	 * @return the 库存状态
	 */
	public String getStockStatus() {
		return stockStatus;
	}
	
	/**
	 * 设置 库存状态.
	 *
	 * @param stockStatus the new 库存状态
	 */
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}
	
	/**
	 * 获取 开单方式.
	 *
	 * @return the 开单方式
	 */
	public String getMakeWaybillWay() {
		return makeWaybillWay;
	}
	
	/**
	 * 设置 开单方式.
	 *
	 * @param makeWaybillWay the new 开单方式
	 */
	public void setMakeWaybillWay(String makeWaybillWay) {
		this.makeWaybillWay = makeWaybillWay;
	}
	
	/**
	 * 获取 航班类型.
	 *
	 * @return the 航班类型
	 */
	public String getFlightType() {
		return flightType;
	}
	
	/**
	 * 设置 航班类型.
	 *
	 * @param flightType the new 航班类型
	 */
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}
	
	/**
	 * 获取 尺寸.
	 *
	 * @return the 尺寸
	 */
	public String getMeasurement() {
		return measurement;
	}
	
	/**
	 * 设置 尺寸.
	 *
	 * @param measurement the new 尺寸
	 */
	public void setMeasurement(String measurement) {
		this.measurement = measurement;
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
	 * 获取 货物名称.
	 *
	 * @return the 货物名称
	 */
	public String getGoodsName() {
		return goodsName;
	}
	
	/**
	 * 设置 货物名称.
	 *
	 * @param goodsName the new 货物名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	/**
	 * 获取 是否开单订舱.
	 *
	 * @return the 是否开单订舱
	 */
	public String getBeBooking() {
		return beBooking;
	}
	
	/**
	 * 设置 是否开单订舱.
	 *
	 * @param beBooking the new 是否开单订舱
	 */
	public void setBeBooking(String beBooking) {
		this.beBooking = beBooking;
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
	 * 获取 收货价格.
	 *
	 * @return the 收货价格
	 */
	public BigDecimal getBillingFee() {
		return billingFee;
	}
	
	/**
	 * 设置 收货价格.
	 *
	 * @param billingFee the new 收货价格
	 */
	public void setBillingFee(BigDecimal billingFee) {
		this.billingFee = billingFee;
	}
	
	/**
	 * 获取 储运事项.
	 *
	 * @return the 储运事项
	 */
	public String getStorageNotes() {
		return storageNotes;
	}
	
	/**
	 * 设置 储运事项.
	 *
	 * @param storageNotes the new 储运事项
	 */
	public void setStorageNotes(String storageNotes) {
		this.storageNotes = storageNotes;
	}
	
	/**
	 * 获取 件数.
	 *
	 * @return the 件数
	 */
	public Long getGoodsQty() {
		return goodsQty;
	}
	
	/**
	 * 设置 件数.
	 *
	 * @param goodsQty the new 件数
	 */
	public void setGoodsQty(Long goodsQty) {
		this.goodsQty = goodsQty;
	}
	
	/**
	 * 获取 预配班次.
	 *
	 * @return the 预配班次
	 */
	public String getPlanFlightNo() {
		return planFlightNo;
	}
	
	/**
	 * 设置 预配班次.
	 *
	 * @param planFlightNo the new 预配班次
	 */
	public void setPlanFlightNo(String planFlightNo) {
		this.planFlightNo = planFlightNo;
	}
	
	/**
	 * 获取 是否加载.
	 *
	 * @return the 是否加载
	 */
	public String getIsLoading() {
		return isLoading;
	}
	
	/**
	 * 设置 是否加载.
	 *
	 * @param isLoading the new 是否加载
	 */
	public void setIsLoading(String isLoading) {
		this.isLoading = isLoading;
	}
	
}