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

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverHandoverbillEntity;


/**
 * 待派送交单查询结果DTO
 * @author 159231 meiying
 * 2015-4-20  上午11:28:00
 */
public class PreDeliverHandoverbillDto extends DeliverHandoverbillEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 已交单件数
	 */
	private Integer deliverBillQty;
	/**
	 * 到达件数
	 */
	private Integer arriveGoodsQty;
	/**
	 * 库存件数
	 */
	private Integer stockGoodsQty;
	/**
	 * 货物状态
	 */
	private String stockStatus;
	/**
	 * 通知情况
	 */
	private String noticeResult;
    /**
     *调度退回原因
     */
	private String deliverReturnReason;
	/**
	 * 调度退回操作人
	 */
	private String deliverReturnOperate;
	/**
	 * 调度退回时间
	 */
	private Date deliverReturnTime;
	/**
	 * 有异常未处理
	 */
	private String hasException;
	/**
	 * 更改单未受理
	 */
	private String waybillRfcStatus;
	/**
	 * 预计到达时间
	 */
	private Date planArriveTime;
	/**
	 *入库时间
	 */
	private Date inStockTime;
	/**
	 * 运单处理类型
	 */
	private String pendingType;
	/**
	 *开单付款方式
	 */
	private String paidMethod;
    /**
     * 部门角色类型
     */
    private int OrgRoleType;
    /**
     * 人工可交未入库的运单（Y：是，N否）
     */
    private String artificialMark;
    /**
     * 查询类型
     */
    private String selectRoleType;
    /**
     * 退回原因备注
     */
    private String deliverReturnReasoNotes;
    /**
     * 开单付款方式为网上支付且未支付运单   Y
     */
    private String isOLNotPaid;
	/**
	 * 规定兑现时间
	 */
    private Date cashTime;
    
    public Date getNowDate() {
        return nowDate;
    }

    public void setNowDate(Date nowDate) {
        this.nowDate = nowDate;
    }

    /**
     * 当前日期
     */
    private Date nowDate;

    public int getOrgRoleType() {
        return OrgRoleType;
    }

    public void setOrgRoleType(int orgRoleType) {
        OrgRoleType = orgRoleType;
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



	public Date getPlanArriveTime() {
		return planArriveTime;
	}

	public void setPlanArriveTime(Date planArriveTime) {
		this.planArriveTime = planArriveTime;
	}

	public String getStockStatus() {
		return stockStatus;
	}

	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

	
	

	/**
	 * 获取deliverReturnReasoNotes  
	 * @return deliverReturnReasoNotes deliverReturnReasoNotes
	 */
	public String getDeliverReturnReasoNotes() {
		return deliverReturnReasoNotes;
	}

	/**
	 * 设置deliverReturnReasoNotes  
	 * @param deliverReturnReasoNotes deliverReturnReasoNotes 
	 */
	public void setDeliverReturnReasoNotes(String deliverReturnReasoNotes) {
		this.deliverReturnReasoNotes = deliverReturnReasoNotes;
	}

	public Integer getArriveGoodsQty() {
		return arriveGoodsQty;
	}

	public void setArriveGoodsQty(Integer arriveGoodsQty) {
		this.arriveGoodsQty = arriveGoodsQty;
	}

	public Integer getStockGoodsQty() {
		return stockGoodsQty;
	}

	public void setStockGoodsQty(Integer stockGoodsQty) {
		this.stockGoodsQty = stockGoodsQty;
	}

	public String getDeliverReturnReason() {
		return deliverReturnReason;
	}

	public void setDeliverReturnReason(String deliverReturnReason) {
		this.deliverReturnReason = deliverReturnReason;
	}

	public String getDeliverReturnOperate() {
		return deliverReturnOperate;
	}

	public void setDeliverReturnOperate(String deliverReturnOperate) {
		this.deliverReturnOperate = deliverReturnOperate;
	}

	public Date getDeliverReturnTime() {
		return deliverReturnTime;
	}

	public void setDeliverReturnTime(Date deliverReturnTime) {
		this.deliverReturnTime = deliverReturnTime;
	}

	public String getHasException() {
		return hasException;
	}

	public void setHasException(String hasException) {
		this.hasException = hasException;
	}


	public String getWaybillRfcStatus() {
		return waybillRfcStatus;
	}

	public void setWaybillRfcStatus(String waybillRfcStatus) {
		this.waybillRfcStatus = waybillRfcStatus;
	}

	public Date getInStockTime() {
		return inStockTime;
	}

	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}


	/**
	 * 获取pendingType  
	 * @return pendingType pendingType
	 */
	public String getPendingType() {
		return pendingType;
	}

	/**
	 * 设置pendingType  
	 * @param pendingType pendingType 
	 */
	public void setPendingType(String pendingType) {
		this.pendingType = pendingType;
	}

	/**
	 * 获取deliverBillQty  
	 * @return deliverBillQty deliverBillQty
	 */
	public Integer getDeliverBillQty() {
		return deliverBillQty;
	}

	/**
	 * 设置deliverBillQty  
	 * @param deliverBillQty deliverBillQty 
	 */
	public void setDeliverBillQty(Integer deliverBillQty) {
		this.deliverBillQty = deliverBillQty;
	}

	/**
	 * 获取paidMethod  
	 * @return paidMethod paidMethod
	 */
	public String getPaidMethod() {
		return paidMethod;
	}

	/**
	 * 设置paidMethod  
	 * @param paidMethod paidMethod 
	 */
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	/**
	 * 获取artificialMark  
	 * @return artificialMark artificialMark
	 */
	public String getArtificialMark() {
		return artificialMark;
	}

	/**
	 * 设置artificialMark  
	 * @param artificialMark artificialMark 
	 */
	public void setArtificialMark(String artificialMark) {
		this.artificialMark = artificialMark;
	}

	/**
	 * 获取selectRoleType  
	 * @return selectRoleType selectRoleType
	 */
	public String getSelectRoleType() {
		return selectRoleType;
	}

	/**
	 * 设置selectRoleType  
	 * @param selectRoleType selectRoleType 
	 */
	public void setSelectRoleType(String selectRoleType) {
		this.selectRoleType = selectRoleType;
	}

	/**
	 * 获取isOLNotPaid  
	 * @return isOLNotPaid isOLNotPaid
	 */
	public String getIsOLNotPaid() {
		return isOLNotPaid;
	}

	/**
	 * 设置isOLNotPaid  
	 * @param isOLNotPaid isOLNotPaid 
	 */
	public void setIsOLNotPaid(String isOLNotPaid) {
		this.isOLNotPaid = isOLNotPaid;
	}

	public Date getCashTime() {
		return cashTime;
	}

	public void setCashTime(Date cashTime) {
		this.cashTime = cashTime;
	}



	
}