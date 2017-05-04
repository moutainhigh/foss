/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/NotifyCustomerConditionDto.java
 * 
 * FILE NAME        	: NotifyCustomerConditionDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;


/**
 *创建派送单(新)-运单明细查询条件
 * @author 159231 meiying
 * 2015-6-2  下午7:55:18
 */
public class WaybillDetailNewQueryDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//运单数组
	private String[] waybillNoList;
	private String waybillNo;
	
	/**
	 * 运输性质
	 */
	private String productCode;
	
	/**
	 * 送货时间
	 */
	private Date preDeliverDate;
	
	/**
	 * 默认运单版本
	 */
	private String active;
	
    /**
     * 部门角色类型
     */
    private int OrgRoleType;
    /**
     * 最终配载部门
     */
    private String lastLoadOrgCode;
    private String goodsStatus; // 货物状态
    private String vehicleType; // 所属车队组
    private String specialNoType; // 特殊运单
    private String lateNo; // 晚交运单
    private String tallymanReturnReason; // 理货员退回
    /**
     * 无坐标运单
     */
    private String noCoordinateWaybill;
    /**
     * 未分区运单
     */
    private String noPartitionWaybill;
    /**
     * 车牌号
     */
    private String vehicleNo;
    /**
     * 最后库存code
     */
    private String endStockOrgCode;
    private String deliverGrandArea; // 送货大区
	private String[] deliverGrandAreas;
	private String deliverSmallArea; // 送货小区
	private String[] deliverSmallAreas;
	 /**
	 * 收货省份
	 */
	private String receiveCustomerProvCode;

	/**
	 * 收货市
	 */
	private String receiveCustomerCityCode;

	/**
	 * 收货区
	 */
	private String receiveCustomerDistCode;
	/**
	 * 默认查询送货日期30天内的数据
	 */
	private String defaultOneMonth;

    /**
     * 库区
     */
    private String goodsAreaCode;
    private String deliverDate;
    private String deliverInga; // 进仓货
    private String noDeliverInga; // 非进仓货
    private String uitraLongDelivery; // 超远派送
    private String isExhibition; //会展货
    private String pieceInspection; //逐件验货
    private String isWithPeople;//带人货
    /**
     * 送货时间段
     */
    private String deliveryTimeInterval;
    
    /**
     * 送货时间段集合
     */
    private String[] deliveryTimes;

	/**
	 * 到达时间范围
	 */
	private Date arrivedDateFrom;
	private Date arrivedDateTo;
	/**
	 * 入库时间范围
	 */
	private Date stockDateFrom;
	private Date stockDateTo;

    public void setOrgRoleType(int orgRoleType) {
        OrgRoleType = orgRoleType;
    }

    public void setLastLoadOrgCode(String lastLoadOrgCode) {
        this.lastLoadOrgCode = lastLoadOrgCode;
    }

    public void setEndStockOrgCode(String endStockOrgCode) {
        this.endStockOrgCode = endStockOrgCode;
    }

    public void setGoodsAreaCode(String goodsAreaCode) {
        this.goodsAreaCode = goodsAreaCode;
    }

    public int getOrgRoleType() {

        return OrgRoleType;
    }

    public String getLastLoadOrgCode() {
        return lastLoadOrgCode;
    }

    public String getEndStockOrgCode() {
        return endStockOrgCode;
    }

    public String getGoodsAreaCode() {
        return goodsAreaCode;
    }

	/**
	 * 获取 运输性质.
	 * 
	 * @return the 运输性质
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * 设置 运输性质.
	 * 
	 * @param productCode the new 运输性质
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	/**
	 * 获取 默认运单版本.
	 * 
	 * @return the 默认运单版本
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 设置 默认运单版本.
	 * 
	 * @param active the new 默认运单版本
	 */
	public void setActive(String active) {
		this.active = active;
	}	
	public String[] getWaybillNoList() {
		return waybillNoList;
	}

	public void setWaybillNoList(String[] waybillNoList) {
		this.waybillNoList = waybillNoList;
	}

	/**
	 * 获取waybillNo  
	 * @return waybillNo waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置waybillNo  
	 * @param waybillNo waybillNo 
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取preDeliverDate  
	 * @return preDeliverDate preDeliverDate
	 */
	public Date getPreDeliverDate() {
		return preDeliverDate;
	}

	/**
	 * 设置preDeliverDate  
	 * @param preDeliverDate preDeliverDate 
	 */
	public void setPreDeliverDate(Date preDeliverDate) {
		this.preDeliverDate = preDeliverDate;
	}

	/**
	 * 获取goodsStatus  
	 * @return goodsStatus goodsStatus
	 */
	public String getGoodsStatus() {
		return goodsStatus;
	}

	/**
	 * 设置goodsStatus  
	 * @param goodsStatus goodsStatus 
	 */
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	/**
	 * 获取vehicleType  
	 * @return vehicleType vehicleType
	 */
	public String getVehicleType() {
		return vehicleType;
	}

	/**
	 * 设置vehicleType  
	 * @param vehicleType vehicleType 
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * 获取specialNoType  
	 * @return specialNoType specialNoType
	 */
	public String getSpecialNoType() {
		return specialNoType;
	}

	/**
	 * 设置specialNoType  
	 * @param specialNoType specialNoType 
	 */
	public void setSpecialNoType(String specialNoType) {
		this.specialNoType = specialNoType;
	}

	/**
	 * 获取lateNo  
	 * @return lateNo lateNo
	 */
	public String getLateNo() {
		return lateNo;
	}

	/**
	 * 设置lateNo  
	 * @param lateNo lateNo 
	 */
	public void setLateNo(String lateNo) {
		this.lateNo = lateNo;
	}

	/**
	 * 获取tallymanReturnReason  
	 * @return tallymanReturnReason tallymanReturnReason
	 */
	public String getTallymanReturnReason() {
		return tallymanReturnReason;
	}

	/**
	 * 设置tallymanReturnReason  
	 * @param tallymanReturnReason tallymanReturnReason 
	 */
	public void setTallymanReturnReason(String tallymanReturnReason) {
		this.tallymanReturnReason = tallymanReturnReason;
	}

	/**
	 * 获取noCoordinateWaybill  
	 * @return noCoordinateWaybill noCoordinateWaybill
	 */
	public String getNoCoordinateWaybill() {
		return noCoordinateWaybill;
	}

	/**
	 * 设置noCoordinateWaybill  
	 * @param noCoordinateWaybill noCoordinateWaybill 
	 */
	public void setNoCoordinateWaybill(String noCoordinateWaybill) {
		this.noCoordinateWaybill = noCoordinateWaybill;
	}

	/**
	 * 获取noPartitionWaybill  
	 * @return noPartitionWaybill noPartitionWaybill
	 */
	public String getNoPartitionWaybill() {
		return noPartitionWaybill;
	}

	/**
	 * 设置noPartitionWaybill  
	 * @param noPartitionWaybill noPartitionWaybill 
	 */
	public void setNoPartitionWaybill(String noPartitionWaybill) {
		this.noPartitionWaybill = noPartitionWaybill;
	}

	/**
	 * 获取vehicleNo  
	 * @return vehicleNo vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置vehicleNo  
	 * @param vehicleNo vehicleNo 
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取deliverGrandArea  
	 * @return deliverGrandArea deliverGrandArea
	 */
	public String getDeliverGrandArea() {
		return deliverGrandArea;
	}

	/**
	 * 设置deliverGrandArea  
	 * @param deliverGrandArea deliverGrandArea 
	 */
	public void setDeliverGrandArea(String deliverGrandArea) {
		this.deliverGrandArea = deliverGrandArea;
	}

	/**
	 * 获取deliverGrandAreas  
	 * @return deliverGrandAreas deliverGrandAreas
	 */
	public String[] getDeliverGrandAreas() {
		return deliverGrandAreas;
	}

	/**
	 * 设置deliverGrandAreas  
	 * @param deliverGrandAreas deliverGrandAreas 
	 */
	public void setDeliverGrandAreas(String[] deliverGrandAreas) {
		this.deliverGrandAreas = deliverGrandAreas;
	}

	/**
	 * 获取deliverSmallArea  
	 * @return deliverSmallArea deliverSmallArea
	 */
	public String getDeliverSmallArea() {
		return deliverSmallArea;
	}

	/**
	 * 设置deliverSmallArea  
	 * @param deliverSmallArea deliverSmallArea 
	 */
	public void setDeliverSmallArea(String deliverSmallArea) {
		this.deliverSmallArea = deliverSmallArea;
	}

	/**
	 * 获取deliverSmallAreas  
	 * @return deliverSmallAreas deliverSmallAreas
	 */
	public String[] getDeliverSmallAreas() {
		return deliverSmallAreas;
	}

	/**
	 * 设置deliverSmallAreas  
	 * @param deliverSmallAreas deliverSmallAreas 
	 */
	public void setDeliverSmallAreas(String[] deliverSmallAreas) {
		this.deliverSmallAreas = deliverSmallAreas;
	}

	/**
	 * 获取defaultOneMonth  
	 * @return defaultOneMonth defaultOneMonth
	 */
	public String getDefaultOneMonth() {
		return defaultOneMonth;
	}

	/**
	 * 设置defaultOneMonth  
	 * @param defaultOneMonth defaultOneMonth 
	 */
	public void setDefaultOneMonth(String defaultOneMonth) {
		this.defaultOneMonth = defaultOneMonth;
	}

	/**
	 * 获取deliverDate  
	 * @return deliverDate deliverDate
	 */
	public String getDeliverDate() {
		return deliverDate;
	}

	/**
	 * 设置deliverDate  
	 * @param deliverDate deliverDate 
	 */
	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}

	/**
	 * 获取receiveCustomerProvCode  
	 * @return receiveCustomerProvCode receiveCustomerProvCode
	 */
	public String getReceiveCustomerProvCode() {
		return receiveCustomerProvCode;
	}

	/**
	 * 设置receiveCustomerProvCode  
	 * @param receiveCustomerProvCode receiveCustomerProvCode 
	 */
	public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}

	/**
	 * 获取receiveCustomerCityCode  
	 * @return receiveCustomerCityCode receiveCustomerCityCode
	 */
	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}

	/**
	 * 设置receiveCustomerCityCode  
	 * @param receiveCustomerCityCode receiveCustomerCityCode 
	 */
	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}

	/**
	 * 获取receiveCustomerDistCode  
	 * @return receiveCustomerDistCode receiveCustomerDistCode
	 */
	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}

	/**
	 * 设置receiveCustomerDistCode  
	 * @param receiveCustomerDistCode receiveCustomerDistCode 
	 */
	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}

	public String getDeliverInga() {
		return deliverInga;
	}

	public void setDeliverInga(String deliverInga) {
		this.deliverInga = deliverInga;
	}

	public String getNoDeliverInga() {
		return noDeliverInga;
	}

	public void setNoDeliverInga(String noDeliverInga) {
		this.noDeliverInga = noDeliverInga;
	}

	public String getUitraLongDelivery() {
		return uitraLongDelivery;
	}

	public void setUitraLongDelivery(String uitraLongDelivery) {
		this.uitraLongDelivery = uitraLongDelivery;
	}

	public String getIsExhibition() {
		return isExhibition;
	}

	public void setIsExhibition(String isExhibition) {
		this.isExhibition = isExhibition;
	}

	public String getPieceInspection() {
		return pieceInspection;
	}

	public void setPieceInspection(String pieceInspection) {
		this.pieceInspection = pieceInspection;
	}

	public String getIsWithPeople() {
		return isWithPeople;
	}

	public void setIsWithPeople(String isWithPeople) {
		this.isWithPeople = isWithPeople;
	}

	public String getDeliveryTimeInterval() {
		return deliveryTimeInterval;
	}

	public void setDeliveryTimeInterval(String deliveryTimeInterval) {
		this.deliveryTimeInterval = deliveryTimeInterval;
		if(StringUtils.isNotBlank(deliveryTimeInterval)){
			String[] deliveryTimeIntervals = deliveryTimeInterval.split("\\+");
			this.setDeliveryTimes(deliveryTimeIntervals);
		}
	}

	public Date getArrivedDateFrom() {
		return arrivedDateFrom;
	}

	public void setArrivedDateFrom(Date arrivedDateFrom) {
		this.arrivedDateFrom = arrivedDateFrom;
	}

	public Date getArrivedDateTo() {
		return arrivedDateTo;
	}

	public void setArrivedDateTo(Date arrivedDateTo) {
		this.arrivedDateTo = arrivedDateTo;
	}

	public Date getStockDateFrom() {
		return stockDateFrom;
	}

	public void setStockDateFrom(Date stockDateFrom) {
		this.stockDateFrom = stockDateFrom;
	}

	public Date getStockDateTo() {
		return stockDateTo;
	}

	public void setStockDateTo(Date stockDateTo) {
		this.stockDateTo = stockDateTo;
	}

	public String[] getDeliveryTimes() {
		return deliveryTimes;
	}

	public void setDeliveryTimes(String[] deliveryTimes) {
		this.deliveryTimes = deliveryTimes;
	}
	
}