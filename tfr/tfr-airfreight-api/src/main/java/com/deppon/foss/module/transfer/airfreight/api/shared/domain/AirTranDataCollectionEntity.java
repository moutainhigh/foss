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
 *  FILE PATH          :/AirTranDataCollectionEntity.java
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
 * 制作航空正单数据收集实体
 * @author 099197-foss-zhoudejun
 * @date 2012-11-14 上午10:36:34
 */
public class AirTranDataCollectionEntity extends BaseEntity {

	private static final long serialVersionUID = -6488495159239610483L;
	/**
	 * 航空正单明细id
	 */
	private String ids;
	/**
	 * 中转单号
	 */
	private String airTransferPickupbillNo;
	/**
	 * 目的站
	 */
	private String arrvRegionName;
	/**
	 * 目的站编码
	 */
	private String arrvRegionCode;
	/**
	 * 到达网点
	 */
	private String destOrgName;
	/**
	 * 到达网点编码
	 */
	private String destOrgCode;
	/**
	 * 中转单号
	 */
	private String transferFlightNo;
	/**
	 * 中转日期
	 */
	private Date transferDate;
	/**
	 * 总票数
	 */
	private Integer billNoTotal;
	/**
	 * 总件数
	 */
	private Integer goodsQtyTotal;
	/**
	 * 清单总件数
	 */
	private Integer airPickQtyTotal;
	/**
	 * 总重量
	 */
	private BigDecimal grossWeightTotal;
	/**
	 * 送货费
	 */
	private BigDecimal deliverFeeTotal;
	/**
	 * 到付费
	 */
	private BigDecimal arrivalFeeTotal;
	/**
	 * 代收款
	 */
	private BigDecimal collectionFeeTotal;
	/**
	 * 制单人
	 */
	private String createUserName;
	/**
	 * 制单时间
	 */
	private Date createTime;
	/**
	 * 航空正单组建id
	 */
	private String airWaybillId;
	/**
	 * 航空正单号
	 */
	private String airWaybillNo;
	/**
	 * 合大票id
	 */
	private String airPickupbillId;
	/**
	 * 备注
	 */
	private String notes;
	/**
	 * 提交状态
	 */
	private String status;
	/**
	 * 获取 航空正单明细id.
	 *
	 * @return the 航空正单明细id
	 */
	public String getIds() {
		return ids;
	}
	
	/**
	 * 设置 航空正单明细id.
	 *
	 * @param ids the new 航空正单明细id
	 */
	public void setIds(String ids) {
		this.ids = ids;
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
	 * 获取 到达网点.
	 *
	 * @return the 到达网点
	 */
	public String getDestOrgName() {
		return destOrgName;
	}
	
	/**
	 * 设置 到达网点.
	 *
	 * @param destOrgName the new 到达网点
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}
	
	/**
	 * 获取 中转单号.
	 *
	 * @return the 中转单号
	 */
	public String getTransferFlightNo() {
		return transferFlightNo;
	}
	
	/**
	 * 设置 中转单号.
	 *
	 * @param transferFlightNo the new 中转单号
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
	 * 获取 总票数.
	 *
	 * @return the 总票数
	 */
	public Integer getBillNoTotal() {
		return billNoTotal;
	}
	
	/**
	 * 设置 总票数.
	 *
	 * @param billNoTotal the new 总票数
	 */
	public void setBillNoTotal(Integer billNoTotal) {
		this.billNoTotal = billNoTotal;
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
	 * 获取 总重量.
	 *
	 * @return the 总重量
	 */
	public BigDecimal getGrossWeightTotal() {
		return grossWeightTotal;
	}
	
	/**
	 * 设置 总重量.
	 *
	 * @param grossWeightTotal the new 总重量
	 */
	public void setGrossWeightTotal(BigDecimal grossWeightTotal) {
		this.grossWeightTotal = grossWeightTotal;
	}
	
	/**
	 * 获取 送货费.
	 *
	 * @return the 送货费
	 */
	public BigDecimal getDeliverFeeTotal() {
		return deliverFeeTotal;
	}
	
	/**
	 * 设置 送货费.
	 *
	 * @param deliverFeeTotal the new 送货费
	 */
	public void setDeliverFeeTotal(BigDecimal deliverFeeTotal) {
		this.deliverFeeTotal = deliverFeeTotal;
	}
	
	/**
	 * 获取 到付费.
	 *
	 * @return the 到付费
	 */
	public BigDecimal getArrivalFeeTotal() {
		return arrivalFeeTotal;
	}
	
	/**
	 * 设置 到付费.
	 *
	 * @param arrivalFeeTotal the new 到付费
	 */
	public void setArrivalFeeTotal(BigDecimal arrivalFeeTotal) {
		this.arrivalFeeTotal = arrivalFeeTotal;
	}
	
	
	public BigDecimal getCollectionFeeTotal() {
		return collectionFeeTotal;
	}

	public void setCollectionFeeTotal(BigDecimal collectionFeeTotal) {
		this.collectionFeeTotal = collectionFeeTotal;
	}

	/**
	 * 获取 制单人.
	 *
	 * @return the 制单人
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	
	/**
	 * 设置 制单人.
	 *
	 * @param createUserName the new 制单人
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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
	 * 获取 航空正单组建id.
	 *
	 * @return the 航空正单组建id
	 */
	public String getAirWaybillId() {
		return airWaybillId;
	}
	
	/**
	 * 设置 航空正单组建id.
	 *
	 * @param airWaybillId the new 航空正单组建id
	 */
	public void setAirWaybillId(String airWaybillId) {
		this.airWaybillId = airWaybillId;
	}
	
	
	public String getAirWaybillNo() {
		return airWaybillNo;
	}

	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}

	public String getAirPickupbillId() {
		return airPickupbillId;
	}

	public void setAirPickupbillId(String airPickupbillId) {
		this.airPickupbillId = airPickupbillId;
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

	public String getArrvRegionCode() {
		return arrvRegionCode;
	}

	public void setArrvRegionCode(String arrvRegionCode) {
		this.arrvRegionCode = arrvRegionCode;
	}

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @return airPickQtyTotal : return the property airPickQtyTotal.
	 * @author 269701-foss-lln
	 * @update 2016年5月29日 下午2:48:21
	 * @version V1.0
	 */
	public Integer getAirPickQtyTotal() {
		return airPickQtyTotal;
	}

	/**
	 * @param airPickQtyTotal : set the property airPickQtyTotal.
	 * @author 269701-foss-lln
	 * @update 2016年5月29日 下午2:48:21
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