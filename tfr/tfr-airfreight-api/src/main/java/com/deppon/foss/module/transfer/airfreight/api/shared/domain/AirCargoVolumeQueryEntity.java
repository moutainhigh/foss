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
 *  FILE PATH          :/AirCargoVolumeQueryEntity.java
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
/**
 * 空运货量明细
 * @author 099197-foss-liming
 * @date 2012-10-19 下午4:53:47
 */
public class AirCargoVolumeQueryEntity  extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6481817826710660439L;
	
	/**
	 * id
	 */
	private String id;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 开单部门
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
	 * 目的站
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
	private String goodsSize;
	/**
	 * 重量
	 */
	private BigDecimal goodsWeight;
	/**
	 * 体积
	 */
	private BigDecimal goodsVolume;
	/**
	 * 物品名称
	 */
	private String goodsName;
	/**
	 * 总重量
	 */
	private BigDecimal goodsWeightTotal;
	/**
	 * 总体积
	 */
	private BigDecimal goodsVolumeTotal;
	/**
	 * 计费重量
	 */
	private BigDecimal billWeight;
	/**
	 * 航班号
	 */
	private String flightNo;
	/**
	 * 提货方式
	 */
	private String pickupType;
	/**
	 * 到达网点
	 */
	private String deptOrgName;
	/**
	 * 正单号
	 */
	private String airWaybillNo;
	/**
	 * 运费
	 */
	private String fee;
	/**
	 * 配载时间
	 */
	private String handoverTime;
	
	/**
	 * 收货部门
	 * 
	 * **/
	
	private String receviceDept;
	
	
	/**
	 * 代收货款
	 * */
	
	private String codAmount;
	
	/** 
	 * 获取 id
	 */
	public String getId() {
		return id;
	}
	
	/** 
	 * 设置  id
	 */
	public void setId(String id) {
		this.id = id;
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
	 * 获取 开单部门.
	 *
	 * @return the 开单部门
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}
	
	/**
	 * 设置 开单部门.
	 *
	 * @param createOrgName the new 开单部门
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
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * 获取 开单部门.
	 *
	 * @return the 开单部门
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * 设置 开单部门.
	 *
	 * @param createOrgCode the new 开单部门
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * 获取 目的站.
	 *
	 * @return the 目的站
	 */
	public String getArrvRegionCode() {
		return arrvRegionCode;
	}

	/**
	 * 设置 目的站.
	 *
	 * @param arrvRegionCode the new 目的站
	 */
	public void setArrvRegionCode(String arrvRegionCode) {
		this.arrvRegionCode = arrvRegionCode;
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
	public String getGoodsSize() {
		return goodsSize;
	}
	
	/**
	 * 设置 尺寸.
	 *
	 * @param goodsSize the new 尺寸
	 */
	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}
	
	/**
	 * 获取 重量.
	 *
	 * @return the 重量
	 */
	public BigDecimal getGoodsWeight() {
		return goodsWeight;
	}
	
	/**
	 * 设置 重量.
	 *
	 * @param goodsWeight the new 重量
	 */
	public void setGoodsWeight(BigDecimal goodsWeight) {
		this.goodsWeight = goodsWeight;
	}
	
	/**
	 * 获取 体积.
	 *
	 * @return the 体积
	 */
	public BigDecimal getGoodsVolume() {
		return goodsVolume;
	}
	
	/**
	 * 设置 体积.
	 *
	 * @param goodsVolume the new 体积
	 */
	public void setGoodsVolume(BigDecimal goodsVolume) {
		this.goodsVolume = goodsVolume;
	}
	
	/**
	 * 获取 物品名称.
	 *
	 * @return the 物品名称
	 */
	public String getGoodsName() {
		return goodsName;
	}
	
	/**
	 * 设置 物品名称.
	 *
	 * @param goodsName the new 物品名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	/**
	 * 获取 总重量.
	 *
	 * @return the 总重量
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}
	
	/**
	 * 设置 总重量.
	 *
	 * @param goodsWeightTotal the new 总重量
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}
	
	/**
	 * 获取 总体积.
	 *
	 * @return the 总体积
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}
	
	/**
	 * 设置 总体积.
	 *
	 * @param goodsVolumeTotal the new 总体积
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}
	
	/**
	 * 
	 *
	 * @param anotherString 
	 * @return 
	 */
	public int compareTo(String anotherString) {
		return arrvRegionName.compareTo(anotherString);
	}

	/**
	 * 获取 计费重量.
	 *
	 * @return the 计费重量
	 */
	public BigDecimal getBillWeight() {
		return billWeight;
	}

	/**
	 * 设置 计费重量.
	 *
	 * @param billWeight the new 计费重量
	 */
	public void setBillWeight(BigDecimal billWeight) {
		this.billWeight = billWeight;
	}

	/**
	 * 获取 航班号.
	 *
	 * @return the 航班号
	 */
	public String getFlightNo() {
		return flightNo;
	}

	/**
	 * 设置 航班号.
	 *
	 * @param flightNo the new 航班号
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
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
	 * 获取 到达网点.
	 *
	 * @return the 到达网点
	 */
	public String getDeptOrgName() {
		return deptOrgName;
	}

	/**
	 * 设置 到达网点.
	 *
	 * @param deptOrgName the new 到达网点
	 */
	public void setDeptOrgName(String deptOrgName) {
		this.deptOrgName = deptOrgName;
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
	 * 获取 运费.
	 *
	 * @return the 运费
	 */
	public String getFee() {
		return fee;
	}

	/**
	 * 设置 运费.
	 *
	 * @param fee the new 运费
	 */
	public void setFee(String fee) {
		this.fee = fee;
	}

	/**
	 * 获取 配载时间.
	 *
	 * @return the 配载时间
	 */
	public String getHandoverTime() {
		return handoverTime;
	}

	/**
	 * 设置 配载时间.
	 *
	 * @param handoverTime the new 配载时间
	 */
	public void setHandoverTime(String handoverTime) {
		this.handoverTime = handoverTime;
	}
    /**
     * 收货部门
     * */
	public String getReceviceDept() {
		return receviceDept;
	}

	public void setReceviceDept(String receviceDept) {
		this.receviceDept = receviceDept;
	}

    /**
     * 代收货款
     * */
	
	public String getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(String codAmount) {
		this.codAmount = codAmount;
	}


	
}