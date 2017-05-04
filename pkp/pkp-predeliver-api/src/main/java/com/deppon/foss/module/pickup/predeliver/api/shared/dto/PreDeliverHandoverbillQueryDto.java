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
import java.util.List;


/**
 * 待派送交单查询DTO
 * @author 159231 meiying
 * 2015-4-20  上午11:28:00
 */
public class PreDeliverHandoverbillQueryDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//运单数组
	private String[] waybillNoList;
	/**
	 * 运单集合
	 */
	private List<String> waybillNos;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 交接单号
	 */
	private String handoverNo;
	/**
	 * 车次号
	 */
	private String vehicleAssembleNo;
	/**
	 * 交接单号/车次号
	 */
	private String handoverOrVehicleNo;
	/**
	 * 运输性质
	 */
	private String productCode;
	/**
	 * 提货方式
	 */
	private String receiveMethod;
	/**
	 * 是否库位确认
	 */
	private String position;
	/**
	 * 货物状态
	 */
	private String stockStatus;
	/**
	 * 车辆任务状态
	 */
	private List<String> taskBillStatus;
	
	
	/**
	 * 到达部门
	 */
	private String arriveDepart;
	/**
	 * 通知情况
	 */
	private String noticeResult;
	/**
	 * 时间范围（起）
	 */
	private Date stockTimeFrom;
	/**
	 * 时间范围（止）
	 */
	private Date stockTimeTo;
	
	/**
	 * 预计送货时间（起）
	 */
	private Date preDeliverDateFrom;
	/**
	 * 预计送货时间（止）
	 */
	private Date preDeliverDateTo;

	/**
	 * 默认查询中变更状态 (WaybillRfcConstants.PRE_ACCECPT,WaybillRfcConstants.PRE_AUDIT)
	 */
	private List<String> wbrStatus;
	/**
	 * 查询方式
	 */
	private String selectType;

	/**
	 * 默认不查询运单产品
	 */
	private String[] productCodes;
	
	/**
	 * 默认运单版本
	 */
	private String active;
	
	/**
	 * 最后库存code
	 */
	private String endStockOrgCode;

	/**
	 * 库区
	 */
	private String goodsAreaCode;
	/**
	 * 快递零担库区
	 */
	private List<String> goodsAreaCodes;
	/**
	 * 送货时间段
	 */
	private String deliveryTimeInterval;
	/**
	 * 送货时间点(起)
	 */
    private String deliveryTimeStart;
    /**
     * 送货时间点(止)
     */
    private String deliveryTimeOver;
    /**
     * 无预计送货日期
     */
    private String noDeliverDate;
    /**
	 * 默认查询中派送方式
	 */
	private String receiveMethodTmp;
	 /**
     * 最终配载部门
     */
    private String lastLoadOrgCode;
    /**
     * 预计送货日期
     */
    private Date deliverDate;
    /**
     * 部门角色类型
     */
    private int OrgRoleType;
    private Date modifyTime;
    private String oldActive;
    
    /**
     * 通过运单号查询不期望的提货方式
     */
    private List<String> notReceiveMethodTmp;
   

    public String getOldActive() {
        return oldActive;
    }

    public void setOldActive(String oldActive) {
        this.oldActive = oldActive;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setOrgRoleType(int orgRoleType) {
        OrgRoleType = orgRoleType;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getOrgRoleType() {

        return OrgRoleType;
    }

    /**
	 * 获取 派送方式.
	 * 
	 * @return the 派送方式
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * 设置 派送方式.
	 * 
	 * @param receiveMethod the new 派送方式
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * 获取 到达部门.
	 * 
	 * @return the 到达部门
	 */
	public String getArriveDepart() {
		return arriveDepart;
	}

	/**
	 * 设置 到达部门.
	 * 
	 * @param arriveDepart the new 到达部门
	 */
	public void setArriveDepart(String arriveDepart) {
		this.arriveDepart = arriveDepart;
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
	 * 获取 车次号.
	 * 
	 * @return the 车次号
	 */
	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}

	/**
	 * 设置 车次号.
	 * 
	 * @param vehicleAssembleNo the new 车次号
	 */
	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
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
	 * 获取 默认查询中变更状态.
	 * 
	 * @return the 默认查询中变更状态
	 */
	public List<String> getWbrStatus() {
		return wbrStatus;
	}

	/**
	 * 设置 默认查询中变更状态.
	 * 
	 * @param wbrStatus the new 默认查询中变更状态
	 */
	public void setWbrStatus(List<String> wbrStatus) {
		this.wbrStatus = wbrStatus;
	}

	
	/**
	 * 获取 查询方式.
	 * 
	 * @return the 查询方式
	 */
	public String getSelectType() {
		return selectType;
	}

	/**
	 * 设置 查询方式.
	 * 
	 * @param selectType the new 查询方式
	 */
	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	
	/**
	 * 获取 默认不查询运单产品.
	 * 
	 * @return the 默认不查询运单产品
	 */
	public String[] getProductCodes() {
		return productCodes;
	}

	/**
	 * 设置 默认不查询运单产品.
	 * 
	 * @param productCodes the new 默认不查询运单产品
	 */
	public void setProductCodes(String[] productCodes) {
		this.productCodes = productCodes;
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
	/**
	 * 获取 通知情况.
	 * 
	 * @return the 通知情况
	 */
	public String getNoticeResult() {
		return noticeResult;
	}

	/**
	 * 设置 通知情况.
	 * 
	 * @param noticeResult the new 通知情况
	 */
	public void setNoticeResult(String noticeResult) {
		this.noticeResult = noticeResult;
	}

	/**
	 * 获取 最后库存code.
	 * 
	 * @return the 最后库存code
	 */
	public String getEndStockOrgCode() {
		return endStockOrgCode;
	}

	/**
	 * 设置 最后库存code.
	 * 
	 * @param endStockOrgCode the new 最后库存code
	 */
	public void setEndStockOrgCode(String endStockOrgCode) {
		this.endStockOrgCode = endStockOrgCode;
	}

	/**
	 * 获取 库区.
	 * 
	 * @return the 库区
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	/**
	 * 设置 库区.
	 * 
	 * @param goodsAreaCode the new 库区
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	
	public List<String> getGoodsAreaCodes() {
		return goodsAreaCodes;
	}
	public void setGoodsAreaCodes(List<String> goodsAreaCodes) {
		this.goodsAreaCodes = goodsAreaCodes;
	}

	public String[] getWaybillNoList() {
		return waybillNoList;
	}

	public void setWaybillNoList(String[] waybillNoList) {
		this.waybillNoList = waybillNoList;
	}

	public String getHandoverOrVehicleNo() {
		return handoverOrVehicleNo;
	}

	public void setHandoverOrVehicleNo(String handoverOrVehicleNo) {
		this.handoverOrVehicleNo = handoverOrVehicleNo;
	}



	

	/**
	 * 获取stockStatus  
	 * @return stockStatus stockStatus
	 */
	public String getStockStatus() {
		return stockStatus;
	}

	/**
	 * 设置stockStatus  
	 * @param stockStatus stockStatus 
	 */
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

	public List<String> getTaskBillStatus() {
		return taskBillStatus;
	}

	public void setTaskBillStatus(List<String> taskBillStatus) {
		this.taskBillStatus = taskBillStatus;
	}

	public Date getStockTimeFrom() {
		return stockTimeFrom;
	}

	public void setStockTimeFrom(Date stockTimeFrom) {
		this.stockTimeFrom = stockTimeFrom;
	}

	public Date getStockTimeTo() {
		return stockTimeTo;
	}

	public void setStockTimeTo(Date stockTimeTo) {
		this.stockTimeTo = stockTimeTo;
	}

	public Date getPreDeliverDateFrom() {
		return preDeliverDateFrom;
	}

	public void setPreDeliverDateFrom(Date preDeliverDateFrom) {
		this.preDeliverDateFrom = preDeliverDateFrom;
	}

	public Date getPreDeliverDateTo() {
		return preDeliverDateTo;
	}

	public void setPreDeliverDateTo(Date preDeliverDateTo) {
		this.preDeliverDateTo = preDeliverDateTo;
	}

	public String getDeliveryTimeInterval() {
		return deliveryTimeInterval;
	}

	public void setDeliveryTimeInterval(String deliveryTimeInterval) {
		this.deliveryTimeInterval = deliveryTimeInterval;
	}

	

	/**
	 * 获取deliveryTimeStart  
	 * @return deliveryTimeStart deliveryTimeStart
	 */
	public String getDeliveryTimeStart() {
		return deliveryTimeStart;
	}

	/**
	 * 设置deliveryTimeStart  
	 * @param deliveryTimeStart deliveryTimeStart 
	 */
	public void setDeliveryTimeStart(String deliveryTimeStart) {
		this.deliveryTimeStart = deliveryTimeStart;
	}

	/**
	 * 获取deliveryTimeOver  
	 * @return deliveryTimeOver deliveryTimeOver
	 */
	public String getDeliveryTimeOver() {
		return deliveryTimeOver;
	}

	/**
	 * 设置deliveryTimeOver  
	 * @param deliveryTimeOver deliveryTimeOver 
	 */
	public void setDeliveryTimeOver(String deliveryTimeOver) {
		this.deliveryTimeOver = deliveryTimeOver;
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
	 * 获取position  
	 * @return position position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * 设置position  
	 * @param position position 
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * 获取noDeliverDate  
	 * @return noDeliverDate noDeliverDate
	 */
	public String getNoDeliverDate() {
		return noDeliverDate;
	}

	/**
	 * 设置noDeliverDate  
	 * @param noDeliverDate noDeliverDate 
	 */
	public void setNoDeliverDate(String noDeliverDate) {
		this.noDeliverDate = noDeliverDate;
	}

	/**
	 * 获取receiveMethodTmp  
	 * @return receiveMethodTmp receiveMethodTmp
	 */
	public String getReceiveMethodTmp() {
		return receiveMethodTmp;
	}

	/**
	 * 设置receiveMethodTmp  
	 * @param receiveMethodTmp receiveMethodTmp 
	 */
	public void setReceiveMethodTmp(String receiveMethodTmp) {
		this.receiveMethodTmp = receiveMethodTmp;
	}

	/**
	 * 获取lastLoadOrgCode  
	 * @return lastLoadOrgCode lastLoadOrgCode
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	/**
	 * 设置lastLoadOrgCode  
	 * @param lastLoadOrgCode lastLoadOrgCode 
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	/**
	 * 获取deliverDate  
	 * @return deliverDate deliverDate
	 */
	public Date getDeliverDate() {
		return deliverDate;
	}

	/**
	 * 设置deliverDate  
	 * @param deliverDate deliverDate 
	 */
	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	/**
	 * 获取waybillNos  
	 * @return waybillNos waybillNos
	 */
	public List<String> getWaybillNos() {
		return waybillNos;
	}

	/**
	 * 设置waybillNos  
	 * @param waybillNos waybillNos 
	 */
	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
	}

	public List<String> getNotReceiveMethodTmp() {
		return notReceiveMethodTmp;
	}

	public void setNotReceiveMethodTmp(List<String> notReceiveMethodTmp) {
		this.notReceiveMethodTmp = notReceiveMethodTmp;
	}	
	
}