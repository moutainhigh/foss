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
 *  FILE PATH          :/AirTransPickupbillEntity.java
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
 * 中转提货清单
 * @author 099197-foss-zhoudejun
 * @date 2012-11-12 下午12:13:25
 */
public class AirTransPickupbillEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 44785270765202736L;
	/**
	 * 中转单号
	 */
	private String airTransferPickupbillNo;
	/**
	 * 目的站编号
	 */
	private String arrvRegionCode;
	/**
	 * 目的站名称
	 */
	private String arrvRegionName;
	/**
	 * 到达网点编号
	 */
	private String destOrgCode;
	/**
	 * 到达网点名称
	 */
	private String destOrgName;
	/**
	 * 到达网点名称
	 */
	private String transferFlightNo;
	/**
	 * 中转日期
	 */
	private Date transferDate;
	/**
	 * 航空公司编号
	 */
	private String airLineCode;
	/**
	 * 航空公司名称
	 */
	private String airLineName;
	/**
	 * 正单号
	 */
	private String airWaybillNo;
	/**
	 * 制单人编号
	 */
	private String createUserCode;
	/**
	 * 制单人名称
	 */
	private String createUserName;
	/**
	 * 制单部门编号
	 */
	private String createOrgCode;
	/**
	 * 制单部门名称
	 */
	private String createOrgName;
	/**
	 * 制单时间
	 */
	private Date createTime;
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
	private Date modifyTime;
	/**
	 * 总票数
	 */
	private Integer waybillQtyTotal;
	/**
	 * 总件数	
	 */
	private Integer goodsQtyTotal;
	/**
	 * 总毛重
	 */
	private BigDecimal grossWeightTotal;
	/**
	 * 总送货费
	 */
	private BigDecimal deliverFeeTotal;
	/**
	 * 总到付款
	 */
	private BigDecimal arrivalFeeTotal;
	/**
	 * 总代收款
	 */
	private BigDecimal collectionFeeTotal;
	/**
	 * 币种
	 */
	private String currencyCode;
	
	public AirTransPickupbillEntity (){
		
	}
		
	/**
	 * 
	 *
	 * @param airPickupbillEntity 
	 * @param airTransferPickupbillNo 
	 */
	public AirTransPickupbillEntity(AirPickupbillEntity airPickupbillEntity,String airTransferPickupbillNo) {
		super();
		this.airTransferPickupbillNo = airTransferPickupbillNo;
		this.arrvRegionCode = airPickupbillEntity.getArrvRegionCode();
		this.arrvRegionName = airPickupbillEntity.getArrvRegionName();
		this.destOrgCode = airPickupbillEntity.getDestOrgCode();
		this.destOrgName = airPickupbillEntity.getDestOrgName();
		this.transferFlightNo = airPickupbillEntity.getFlightNo();
		this.transferDate = airPickupbillEntity.getFlightDate();
		this.airLineCode = airPickupbillEntity.getAirLineCode();
		this.airLineName = airPickupbillEntity.getAirLineName();
		this.airWaybillNo = airPickupbillEntity.getAirWaybillNo();
		this.createUserCode = airPickupbillEntity.getCreateUserCode();
		this.createUserName = airPickupbillEntity.getCreateUserName();
		this.createOrgCode = airPickupbillEntity.getOrigOrgCode();
		this.createOrgName = airPickupbillEntity.getOrigOrgName();
		this.createTime = new Date();
		this.modifyUserCode = airPickupbillEntity.getModifyUserCode();
		this.modifyUserName = airPickupbillEntity.getModifyUserName();
		this.modifyTime = airPickupbillEntity.getModifyTime();
		this.waybillQtyTotal = airPickupbillEntity.getWaybillQtyTotal();
		this.goodsQtyTotal = airPickupbillEntity.getGoodsQtyTotal();
		this.grossWeightTotal = airPickupbillEntity.getGrossWeightTotal();
		this.deliverFeeTotal = airPickupbillEntity.getDeliverFeeTotal();
		this.arrivalFeeTotal = airPickupbillEntity.getArrivalFeeTotal();
		this.collectionFeeTotal = airPickupbillEntity.getCollectionFeeTotal();
		this.currencyCode = airPickupbillEntity.getCurrencyCode();
	}

	/**
	 * 获取 中转单号.
	 *
	 * @return the 中转单号
	 */
	public String getAirTransferPickupbillNo() {
		return airTransferPickupbillNo;
	}

	/**
	 * 设置 中转单号.
	 *
	 * @param airTransferPickupbillNo the new 中转单号
	 */
	public void setAirTransferPickupbillNo(String airTransferPickupbillNo) {
		this.airTransferPickupbillNo = airTransferPickupbillNo;
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
	 * 获取 到达网点编号.
	 *
	 * @return the 到达网点编号
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * 设置 到达网点编号.
	 *
	 * @param destOrgCode the new 到达网点编号
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * 获取 到达网点名称.
	 *
	 * @return the 到达网点名称
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * 设置 到达网点名称.
	 *
	 * @param destOrgName the new 到达网点名称
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * 获取 到达网点名称.
	 *
	 * @return the 到达网点名称
	 */
	public String getTransferFlightNo() {
		return transferFlightNo;
	}

	/**
	 * 设置 到达网点名称.
	 *
	 * @param transferFlightNo the new 到达网点名称
	 */
	public void setTransferFlightNo(String transferFlightNo) {
		this.transferFlightNo = transferFlightNo;
	}

	/**
	 * 获取 中转日期.
	 *
	 * @return the 中转日期
	 */
	public Date getTransferDate() {
		return transferDate;
	}

	/**
	 * 设置 中转日期.
	 *
	 * @param transferDate the new 中转日期
	 */
	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	/**
	 * 获取 航空公司编号.
	 *
	 * @return the 航空公司编号
	 */
	public String getAirLineCode() {
		return airLineCode;
	}

	/**
	 * 设置 航空公司编号.
	 *
	 * @param airLineCode the new 航空公司编号
	 */
	public void setAirLineCode(String airLineCode) {
		this.airLineCode = airLineCode;
	}

	/**
	 * 获取 航空公司名称.
	 *
	 * @return the 航空公司名称
	 */
	public String getAirLineName() {
		return airLineName;
	}

	/**
	 * 设置 航空公司名称.
	 *
	 * @param airLineName the new 航空公司名称
	 */
	public void setAirLineName(String airLineName) {
		this.airLineName = airLineName;
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
	 * 获取 制单人编号.
	 *
	 * @return the 制单人编号
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * 设置 制单人编号.
	 *
	 * @param createUserCode the new 制单人编号
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * 获取 制单人名称.
	 *
	 * @return the 制单人名称
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * 设置 制单人名称.
	 *
	 * @param createUserName the new 制单人名称
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * 获取 制单部门编号.
	 *
	 * @return the 制单部门编号
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * 设置 制单部门编号.
	 *
	 * @param createOrgCode the new 制单部门编号
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * 获取 制单部门名称.
	 *
	 * @return the 制单部门名称
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}

	/**
	 * 设置 制单部门名称.
	 *
	 * @param createOrgName the new 制单部门名称
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	/**
	 * 获取 制单时间.
	 *
	 * @return the 制单时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置 制单时间.
	 *
	 * @param createTime the new 制单时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取 修改人编号.
	 *
	 * @return the 修改人编号
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * 设置 修改人编号.
	 *
	 * @param modifyUserCode the new 修改人编号
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * 获取 修改人名称.
	 *
	 * @return the 修改人名称
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * 设置 修改人名称.
	 *
	 * @param modifyUserName the new 修改人名称
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**
	 * 获取 修改时间.
	 *
	 * @return the 修改时间
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置 修改时间.
	 *
	 * @param modifyTime the new 修改时间
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 获取 总票数.
	 *
	 * @return the 总票数
	 */
	public Integer getWaybillQtyTotal() {
		return waybillQtyTotal;
	}

	/**
	 * 设置 总票数.
	 *
	 * @param waybillQtyTotal the new 总票数
	 */
	public void setWaybillQtyTotal(Integer waybillQtyTotal) {
		this.waybillQtyTotal = waybillQtyTotal;
	}

	/**
	 * 获取 总件数.
	 *
	 * @return the 总件数
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * 设置 总件数.
	 *
	 * @param goodsQtyTotal the new 总件数
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * 获取 总毛重.
	 *
	 * @return the 总毛重
	 */
	public BigDecimal getGrossWeightTotal() {
		return grossWeightTotal;
	}

	/**
	 * 设置 总毛重.
	 *
	 * @param grossWeightTotal the new 总毛重
	 */
	public void setGrossWeightTotal(BigDecimal grossWeightTotal) {
		this.grossWeightTotal = grossWeightTotal;
	}

	/**
	 * 获取 总送货费.
	 *
	 * @return the 总送货费
	 */
	public BigDecimal getDeliverFeeTotal() {
		return deliverFeeTotal;
	}

	/**
	 * 设置 总送货费.
	 *
	 * @param deliverFeeTotal the new 总送货费
	 */
	public void setDeliverFeeTotal(BigDecimal deliverFeeTotal) {
		this.deliverFeeTotal = deliverFeeTotal;
	}

	/**
	 * 获取 总到付款.
	 *
	 * @return the 总到付款
	 */
	public BigDecimal getArrivalFeeTotal() {
		return arrivalFeeTotal;
	}

	/**
	 * 设置 总到付款.
	 *
	 * @param arrivalFeeTotal the new 总到付款
	 */
	public void setArrivalFeeTotal(BigDecimal arrivalFeeTotal) {
		this.arrivalFeeTotal = arrivalFeeTotal;
	}

	/**
	 * 获取 总代收款.
	 *
	 * @return the 总代收款
	 */
	public BigDecimal getCollectionFeeTotal() {
		return collectionFeeTotal;
	}

	/**
	 * 设置 总代收款.
	 *
	 * @param collectionFeeTotal the new 总代收款
	 */
	public void setCollectionFeeTotal(BigDecimal collectionFeeTotal) {
		this.collectionFeeTotal = collectionFeeTotal;
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

}