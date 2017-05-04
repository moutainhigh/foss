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
 *  FILE PATH          :/AirWaybillDetailEntity.java
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
 * 航空正单明细
 * @author 099197-foss-zhoudejun
 * @date 2012-10-19 下午3:35:14
 */
public class AirWaybillDetailEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6695534601262490629L;
	
	/**
	 * 航空正单Id
	 */
	private String airWaybillId;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 航空正单号
	 */
	private String airWaybillNo;
	/**
	 * 开单方式
	 */
	private String makeWaybillWay;
	/**
	 * 库存状态
	 */
	private String stockStatus;
	/**
	 * 目的站
	 */
	private String arrvRegionName;
	/**
	 * 预配班次
	 */
	private String planFlightNo;
	/**
	 * 是否开单订舱
	 */
	private String beBooking;
	/**
	 * 尺寸
	 */
	private String measurement;
	/**
	 * 毛重
	 */
	private BigDecimal grossWeight;
	/**
	 * 计费重量
	 */
	private BigDecimal  billingWeight;
	/**
	 * 体积
	 */
	private BigDecimal volume;
	/**
	 * 件数
	 */
	private Integer goodsQty;
	/**
	 * 清单件数
	 */
	private Integer airPickQty;
	/**
	 * 开单总件数
	 */
	private Integer goodsQtyTotal;
	/**
	 * 清单总件数
	 */
	private Integer airPickQtyTotal;
	/**
	 * 品名
	 */
	private String goodsName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 送货费
	 */
	private BigDecimal deliverFee;
	/**
	 * 到付费
	 */
	private BigDecimal arrivalFee;
	/**
	 * 提货方式
	 */
	private String pickupType;
	/**
	 * 代收款
	 */
	private BigDecimal collectionFee;
	/**
	 * 收货人电话	
	 */
	private String receiverContactPhone;
	/**
	 * 收货地址
	 */
	private String receiverAddress;
	/**
	 * 收货人姓名
	 */
	private String receiverName;
	/**
	 * 是否配载
	 */
	private String isLoading;
	
	/**
	 * 重量
	 */
	private String weight;
	
	/**
	 * 运单流水号
	 */
	private String serialNo; 
	
	/**
	 * 包装说明
	 */
	private String goodsPackage;
	
	/**
	 * 开单方式
	 */
	private String freightMethod;
	
	/**
	 * 储运事项
	 */
	private String transportRemark;
	
	/**
	 * 收货部门
	 */
	private String receiveOrgName;
	
	/**
	 * 备注
	 */
	private String notes;
	
	/**
	 * 交接单出发时间
	 * */
    private Date departTime;
    
    /**
     * 运单开单费率
     * */
    private double unitPrice;
	
    
    /**
	 *锁票备注
	 * */
    private String lockRemark;
    
    /**
     * 锁票状态
     * */
    private String lockStatus;
    
    /**
     * 总调库存件数
     * */
    private int stockQty;
    /**
     * 运输性质
     */
    private String transportType;
    
    /**
     * 产品类型
     */
    private String productCode;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    /**
	 * 
	 */
	public AirWaybillDetailEntity(){
		
	}
	/**
	 * 获取 航空正单Id.
	 *
	 * @return the 航空正单Id
	 */
	public String getAirWaybillId() {
		return airWaybillId;
	}
	
	/**
	 * 设置 航空正单Id.
	 *
	 * @param airWaybillId the new 航空正单Id
	 */
	public void setAirWaybillId(String airWaybillId) {
		this.airWaybillId = airWaybillId;
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
	 * 获取 毛重.
	 *
	 * @return the 毛重
	 */
	public BigDecimal getGrossWeight() {
		return grossWeight;
	}
	
	/**
	 * 设置 毛重.
	 *
	 * @param grossWeight the new 毛重
	 */
	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
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
	@DateFormat
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * 获取 到付费.
	 *
	 * @return the 到付费
	 */
	public BigDecimal getArrivalFee() {
		return arrivalFee;
	}
	
	/**
	 * 设置 到付费.
	 *
	 * @param arrivalFee the new 到付费
	 */
	public void setArrivalFee(BigDecimal arrivalFee) {
		this.arrivalFee = arrivalFee;
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
	 * 获取 收货地址.
	 *
	 * @return the 收货地址
	 */
	public String getReceiverAddress() {
		return receiverAddress;
	}
	
	/**
	 * 设置 收货地址.
	 *
	 * @param receiverAddress the new 收货地址
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
	
	/**
	 * 获取 是否配载.
	 *
	 * @return the 是否配载
	 */
	public String getIsLoading() {
		return isLoading;
	}
	
	/**
	 * 设置 是否配载.
	 * @param isLoading the new 是否配载
	 */
	public void setIsLoading(String isLoading) {
		this.isLoading = isLoading;
	}
	
	/**
	 * @return 
	 */
	public String getWeight() {
		return weight;
	}
	
	/**
	 * @param weight 
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * 获取 运单流水号.
	 *
	 * @return the 运单流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * 设置 运单流水号.
	 *
	 * @param serialNo the new 运单流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	 * 获取 包装说明.
	 *
	 * @return the 包装说明
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}
	
	/**
	 * 设置 包装说明.
	 *
	 * @param goodsPackage the new 包装说明
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}
	
	/**
	 * 获取 开单方式.
	 *
	 * @return the 开单方式
	 */
	public String getFreightMethod() {
		return freightMethod;
	}
	
	/**
	 * 设置 开单方式.
	 *
	 * @param freightMethod the new 开单方式
	 */
	public void setFreightMethod(String freightMethod) {
		this.freightMethod = freightMethod;
	}
	/**
	 * 获取 开单总件数.
	 *
	 * @param freightMethod the new 开单总件数
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	/**
	 * 获取 开单总件数.
	 *
	 * @param freightMethod the new 开单总件数
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	/**
	 * 储运事项
	 */
	public String getTransportRemark() {
		return transportRemark;
	}
	
	public void setTransportRemark(String transportRemark) {
		this.transportRemark = transportRemark;
	}
	/**
	 * 收货部门
	 */
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	
	/**
	 * 备注
	 */
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	/**
	 * 交接单出发时间
	 * */
	@DateFormat
	public Date getDepartTime() {
		return departTime;
	}
	
	@DateFormat
	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}
	
	/**
	 * 运单开单费率
	 * */
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	/**
	 * 
	 * */
	public String getLockRemark() {
		return lockRemark;
	}
	public void setLockRemark(String lockRemark) {
		this.lockRemark = lockRemark;
	}
	/**
	 * 
	 * */
	public String getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}
	public int getStockQty() {
		return stockQty;
	}
	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}

	public Integer getAirPickQty() {
		return airPickQty;
	}

	public void setAirPickQty(Integer airPickQty) {
		this.airPickQty = airPickQty;
	}

	public Integer getAirPickQtyTotal() {
		return airPickQtyTotal;
	}

	public void setAirPickQtyTotal(Integer airPickQtyTotal) {
		this.airPickQtyTotal = airPickQtyTotal;
	}

	public String getAirWaybillNo() {
		return airWaybillNo;
	}

	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}

}