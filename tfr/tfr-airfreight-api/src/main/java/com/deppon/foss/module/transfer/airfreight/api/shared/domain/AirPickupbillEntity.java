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
 *  FILE PATH          :/AirPickupbillEntity.java
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
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.define.FossConstants;
/**
 * 合大票清单
 * @author 099197-foss-zhoudejun
 * @date 2012-11-12 上午11:55:03
 */
public class AirPickupbillEntity extends BaseEntity {
	private static final long serialVersionUID = 89902712846027616L;
	/**
	 * ID
	 */
	private String id;
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
	 * 提交状态
	 */
	private String status;
	/**
	 * 到达网点编号
	 */
	private String destOrgCode;
	/**
	 * 到达网点名称	
	 */
	private String destOrgName;
	/**
	 * 航班号	
	 */
	private String flightNo;
	/**
	 * 目的站编号
	 */
	private String arrvRegionCode;
	/**
	 * 目的站名称	
	 */
	private String arrvRegionName;
	/**
	 * 航班日期	
	 */
	private Date flightDate;
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
	private String origOrgCode;
	/**
	 * 制单部门名称
	 */
	private String origOrgName;
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
	 * 清单总件数
	 */
	private Integer airPickQtyTotal;
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
	
	/**
	 * 
	 */
	public AirPickupbillEntity(){
		
	}
	//有参构造函数
	/**
	 * 
	 *
	 * @param airWaybillEntity 
	 * @param airTranDataCollectionEntity 
	 * @param collectionFeeTotal 
	 */
	public AirPickupbillEntity(AirWaybillEntity airWaybillEntity,
			AirTranDataCollectionEntity airTranDataCollectionEntity,
			BigDecimal collectionFeeTotal) {
		super();
		this.airLineCode = airWaybillEntity.getAirLineTwoletter();
		this.airLineName = "22";
		this.airWaybillNo = airWaybillEntity.getAirWaybillNo();
		this.destOrgCode = airTranDataCollectionEntity.getDestOrgCode();
		this.destOrgName = airTranDataCollectionEntity.getDestOrgName();
		this.flightNo = airWaybillEntity.getFlightNo();
		this.arrvRegionCode = airWaybillEntity.getArrvRegionCode();
		this.arrvRegionName = airWaybillEntity.getArrvRegionName();
		this.flightDate = airTranDataCollectionEntity.getTransferDate();
		this.createUserCode = FossUserContext.getCurrentInfo().getEmpCode();
		this.createUserName = airTranDataCollectionEntity.getCreateUserName();
		this.createTime = new Date();
		this.modifyUserCode = FossUserContext.getCurrentInfo().getEmpCode();
		this.modifyUserName = FossUserContext.getCurrentInfo().getEmpName();
		this.modifyTime = new Date();
		this.waybillQtyTotal = airTranDataCollectionEntity.getBillNoTotal();
		this.goodsQtyTotal = airTranDataCollectionEntity.getAirPickQtyTotal();
		this.grossWeightTotal = airTranDataCollectionEntity.getGrossWeightTotal();
		this.deliverFeeTotal = airTranDataCollectionEntity.getDeliverFeeTotal();
		this.arrivalFeeTotal = airTranDataCollectionEntity.getArrivalFeeTotal();
		this.collectionFeeTotal = collectionFeeTotal;
		this.currencyCode = FossConstants.CURRENCY_CODE_RMB;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	 * 获取 航班日期.
	 *
	 * @return the 航班日期
	 */
	public Date getFlightDate() {
		return flightDate;
	}
	
	/**
	 * 设置 航班日期.
	 *
	 * @param flightDate the new 航班日期
	 */
	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
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
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	
	/**
	 * 设置 制单部门编号.
	 *
	 * @param origOrgCode the new 制单部门编号
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	
	/**
	 * 获取 制单部门名称.
	 *
	 * @return the 制单部门名称
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}
	
	/**
	 * 设置 制单部门名称.
	 *
	 * @param origOrgName the new 制单部门名称
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
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
	/**
	 * @return airPickQtyTotal : return the property airPickQtyTotal.
	 * @author 269701-foss-lln
	 * @update 2016年5月29日 下午12:06:53
	 * @version V1.0
	 */
	public Integer getAirPickQtyTotal() {
		return airPickQtyTotal;
	}
	/**
	 * @param airPickQtyTotal : set the property airPickQtyTotal.
	 * @author 269701-foss-lln
	 * @update 2016年5月29日 下午12:06:54
	 * @version V1.0
	 */
	
	public void setAirPickQtyTotal(Integer airPickQtyTotal) {
		this.airPickQtyTotal = airPickQtyTotal;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
}