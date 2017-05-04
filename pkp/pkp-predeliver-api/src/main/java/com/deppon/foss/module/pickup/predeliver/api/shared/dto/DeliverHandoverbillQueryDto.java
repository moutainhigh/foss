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


/**
 * 派送交单查询DTO
 * @author 159231 meiying
 * 2015-4-20  上午11:28:00
 */
public class DeliverHandoverbillQueryDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//运单数组
	private String[] waybillNoList;
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 运输性质
	 */
	private String productCode;
	/**
	 * 提货方式
	 */
	private String receiveMethod;
	
	/**
	 * 通知情况
	 */
	private String noticeResult;
	/**
	 * 预计送货时间（起）
	 */
	private Date preDeliverDateFrom;
	/**
	 * 预计送货时间（止）
	 */
	private Date preDeliverDateTo;

	/**
	 * 默认不查询运单产品
	 */
	private String[] productCodes;
	
	/**
	 * 默认运单版本
	 */
	private String active;
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
	 * 交单时间(起)
	 */
	private Date handoverbillTimeStart;
	/**
	 * 交单时间(止)
	 */
    private Date handoverbillTimeEnd;
    /**
     * 排单状态
     */
    private String deliverbillStatus;
    /**
     * 部门角色类型
     */
    private int OrgRoleType;
    /**
     * 最终配载部门
     */
    private String lastLoadOrgCode;
    /**
     * 最后库存code
     */
    private String endStockOrgCode;

    /**
     * 库区
     */
    private String goodsAreaCode;

	/**
	 * 交接单号
	 */
	private String handoverNo;
	/**
	 * 车次号
	 */
	private String vehicleAssembleNo;
	/**
	 * 未排单运单
	 */
	private String noArrageBill;


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

	
	public String[] getWaybillNoList() {
		return waybillNoList;
	}

	public void setWaybillNoList(String[] waybillNoList) {
		this.waybillNoList = waybillNoList;
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

    public Date getHandoverbillTimeStart() {
        return handoverbillTimeStart;
    }

    public void setHandoverbillTimeStart(Date handoverbillTimeStart) {
        this.handoverbillTimeStart = handoverbillTimeStart;
    }

    public Date getHandoverbillTimeEnd() {
		return handoverbillTimeEnd;
	}

	public void setHandoverbillTimeEnd(Date handoverbillTimeEnd) {
		this.handoverbillTimeEnd = handoverbillTimeEnd;
	}

	public String getDeliverbillStatus() {
		return deliverbillStatus;
	}

	public void setDeliverbillStatus(String deliverbillStatus) {
		this.deliverbillStatus = deliverbillStatus;
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

	public String getHandoverNo() {
		return handoverNo;
	}

	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}

	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}

	public String getNoArrageBill() {
		return noArrageBill;
	}

	public void setNoArrageBill(String noArrageBill) {
		this.noArrageBill = noArrageBill;
	}
}