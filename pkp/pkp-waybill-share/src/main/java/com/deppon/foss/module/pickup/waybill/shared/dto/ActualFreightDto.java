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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/ActualFreightDto.java
 * 
 * FILE NAME        	: ActualFreightDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 
 *实际货物 dto
 * @author foss-meiying
 * @date 2013-3-6 上午10:35:01
 * @since
 * @version
 */
public class ActualFreightDto implements Serializable {
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = -4373946475199407922L;
	
	/**
	 * waybillNo
	 */
	private String waybillNo;
	/**
	 * 到达未出库件数
	 */
	private Integer arriveNotoutGoodsQty;
	/**
	 * 并发控制  --到达未出库件数
	 */
	private Integer oldArriveNotoutGoodsQty;
	/**
	 * 并发控制--已生成到达联件数
	 */
	private Integer oldGenerateGoodsQty;
	/**
	 * 排单件数
	 */
	private Integer arrangeGoodsQty;
	/**
	 * 已生成到达联件数
	 */
	private Integer generateGoodsQty;
	/**
	 * 到达件数
	 */
	private Integer arriveGoodsQty;
	/**
	 * 通知客户产生的付款方式
	 */
	private String paymentType;
	/**
	 * 开单件数
	 */
	private Integer goodsQty;
	/**
	 * 签收件数
	 */
	private Integer signQty;
	/**
	 * 库存件数
	 */
	private Integer stockQty;
	/**
	 * 最后入库时间
	 */
	private Date inStockDate;
	/**
	 * 运单状态
	 */
	private List<String> waybillStatus;
	
	private Date modifyTime;
	//运单数组
	private String[] waybillNoList;
	/**
     * 预计送货日期
     */
    private Date deliverDate;
    /**
     * 相减派送交单件数
     */
    private Integer deliverBillQtyReduce;
    /**
     * 派送交单件数为零
     */
    private Integer deliverBillQtyZero;
    /**
     * 相加派送交单件数
     */
    private Integer deliverBillQtyAdd;
    /**
     * 修改预计送货日期部门
     */
    private String upDeliverDateOrgCode;
    /**
     * 原交单件数
     */
    private Integer oldDeliverBillQty;
    /**
     * 通知操作部门
     */
    private String notificationOrgCode;
	/**
	 * 无参构造方法
	 * @author foss-meiying
	 * @date 2013-3-6 上午10:42:51
	 */
	public ActualFreightDto() {
		super();
	}
	/**
	 * 有参构造方法
	 * @author foss-meiying
	 * @date 2013-3-6 上午10:42:41
	 * @param waybillNo
	 * @param arriveNotoutGoodsQty
	 * @param paymentType
	 */
	public ActualFreightDto(String waybillNo, Integer arriveNotoutGoodsQty, String paymentType) {
		super();
		this.waybillNo = waybillNo;
		this.arriveNotoutGoodsQty = arriveNotoutGoodsQty;
		this.paymentType = paymentType;
	}
	/**
	 * 有参构造方法
	 * @author foss-meiying
	 * @date 2013-3-6 上午10:42:24
	 * @param waybillNo 
	 * @param arriveNotoutGoodsQty
	 * @param oldArriveNotoutGoodsQty
	 */
	public ActualFreightDto(String waybillNo, Integer arriveNotoutGoodsQty, Integer oldArriveNotoutGoodsQty) {
		super();
		this.waybillNo = waybillNo;
		this.arriveNotoutGoodsQty = arriveNotoutGoodsQty;
		this.oldArriveNotoutGoodsQty = oldArriveNotoutGoodsQty;
	}
	/**
	 * 有参构造方法(waybillNo,arriveNotoutGoodsQty)
	 * @author foss-meiying
	 * @date 2013-3-6 上午11:01:53
	 * @param waybillNo
	 * @param arriveNotoutGoodsQty
	 */
	public ActualFreightDto(String waybillNo, Integer arriveNotoutGoodsQty) {
		super();
		this.waybillNo = waybillNo;
		this.arriveNotoutGoodsQty = arriveNotoutGoodsQty;
	}

    public Integer getDeliverBillQtyZero() {
        return deliverBillQtyZero;
    }

    public void setDeliverBillQtyZero(Integer deliverBillQtyZero) {
        this.deliverBillQtyZero = deliverBillQtyZero;
    }

    /**
	 * Gets the waybillNo.
	 *
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	
	/**
	 * Sets the waybillNo.
	 *
	 * @param waybillNo the new waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	/**
	 * Gets the 到达未出库件数.
	 *
	 * @return the 到达未出库件数
	 */
	public Integer getArriveNotoutGoodsQty() {
		return arriveNotoutGoodsQty;
	}
	
	/**
	 * Sets the 到达未出库件数.
	 *
	 * @param arriveNotoutGoodsQty the new 到达未出库件数
	 */
	public void setArriveNotoutGoodsQty(Integer arriveNotoutGoodsQty) {
		this.arriveNotoutGoodsQty = arriveNotoutGoodsQty;
	}
	
	/**
	 * Gets the 并发控制  --到达未出库件数.
	 *
	 * @return the 并发控制  --到达未出库件数
	 */
	public Integer getOldArriveNotoutGoodsQty() {
		return oldArriveNotoutGoodsQty;
	}
	
	/**
	 * Sets the 并发控制  --到达未出库件数.
	 *
	 * @param oldArriveNotoutGoodsQty the new 并发控制  --到达未出库件数
	 */
	public void setOldArriveNotoutGoodsQty(Integer oldArriveNotoutGoodsQty) {
		this.oldArriveNotoutGoodsQty = oldArriveNotoutGoodsQty;
	}
	
	/**
	 * Gets the 通知客户产生的付款方式.
	 *
	 * @return the 通知客户产生的付款方式
	 */
	public String getPaymentType() {
		return paymentType;
	}
	
	/**
	 * Sets the 通知客户产生的付款方式.
	 *
	 * @param paymentType the new 通知客户产生的付款方式
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	/**
	 * Gets the 并发控制--已生成到达联件数.
	 *
	 * @return the 并发控制--已生成到达联件数
	 */
	public Integer getOldGenerateGoodsQty() {
		return oldGenerateGoodsQty;
	}
	
	/**
	 * Sets the 并发控制--已生成到达联件数.
	 *
	 * @param oldGenerateGoodsQty the new 并发控制--已生成到达联件数
	 */
	public void setOldGenerateGoodsQty(Integer oldGenerateGoodsQty) {
		this.oldGenerateGoodsQty = oldGenerateGoodsQty;
	}
	
	/**
	 * Gets the 排单件数.
	 *
	 * @return the 排单件数
	 */
	public Integer getArrangeGoodsQty() {
		return arrangeGoodsQty;
	}
	
	/**
	 * Sets the 排单件数.
	 *
	 * @param arrangeGoodsQty the new 排单件数
	 */
	public void setArrangeGoodsQty(Integer arrangeGoodsQty) {
		this.arrangeGoodsQty = arrangeGoodsQty;
	}
	
	/**
	 * Gets the 已生成到达联件数.
	 *
	 * @return the 已生成到达联件数
	 */
	public Integer getGenerateGoodsQty() {
		return generateGoodsQty;
	}
	
	/**
	 * Sets the 已生成到达联件数.
	 *
	 * @param generateGoodsQty the new 已生成到达联件数
	 */
	public void setGenerateGoodsQty(Integer generateGoodsQty) {
		this.generateGoodsQty = generateGoodsQty;
	}
	
	/**
	 * Gets the 到达件数.
	 *
	 * @return the 到达件数
	 */
	public Integer getArriveGoodsQty() {
		return arriveGoodsQty;
	}
	
	/**
	 * Sets the 到达件数.
	 *
	 * @param arriveGoodsQty the new 到达件数
	 */
	public void setArriveGoodsQty(Integer arriveGoodsQty) {
		this.arriveGoodsQty = arriveGoodsQty;
	}
	public Integer getGoodsQty() {
		return goodsQty;
	}
	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}
	public Integer getSignQty() {
		return signQty;
	}
	public void setSignQty(Integer signQty) {
		this.signQty = signQty;
	}
	public Integer getStockQty() {
		return stockQty;
	}
	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}
	public Date getInStockDate() {
		return inStockDate;
	}
	public void setInStockDate(Date inStockDate) {
		this.inStockDate = inStockDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<String> getWaybillStatus() {
		return waybillStatus;
	}
	public void setWaybillStatus(List<String> waybillStatus) {
		this.waybillStatus = waybillStatus;
	}
	public Date getModifyTime() {
		return modifyTime;
	}	
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取waybillNoList  
	 * @return waybillNoList waybillNoList
	 */
	public String[] getWaybillNoList() {
		return waybillNoList;
	}
	/**
	 * 设置waybillNoList  
	 * @param waybillNoList waybillNoList 
	 */
	public void setWaybillNoList(String[] waybillNoList) {
		this.waybillNoList = waybillNoList;
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
	 * 获取deliverBillQtyReduce  
	 * @return deliverBillQtyReduce deliverBillQtyReduce
	 */
	public Integer getDeliverBillQtyReduce() {
		return deliverBillQtyReduce;
	}
	/**
	 * 设置deliverBillQtyReduce  
	 * @param deliverBillQtyReduce deliverBillQtyReduce 
	 */
	public void setDeliverBillQtyReduce(Integer deliverBillQtyReduce) {
		this.deliverBillQtyReduce = deliverBillQtyReduce;
	}
	/**
	 * 获取deliverBillQtyAdd  
	 * @return deliverBillQtyAdd deliverBillQtyAdd
	 */
	public Integer getDeliverBillQtyAdd() {
		return deliverBillQtyAdd;
	}
	/**
	 * 设置deliverBillQtyAdd  
	 * @param deliverBillQtyAdd deliverBillQtyAdd 
	 */
	public void setDeliverBillQtyAdd(Integer deliverBillQtyAdd) {
		this.deliverBillQtyAdd = deliverBillQtyAdd;
	}
	/**
	 * 获取upDeliverDateOrgCode  
	 * @return upDeliverDateOrgCode upDeliverDateOrgCode
	 */
	public String getUpDeliverDateOrgCode() {
		return upDeliverDateOrgCode;
	}
	/**
	 * 设置upDeliverDateOrgCode  
	 * @param upDeliverDateOrgCode upDeliverDateOrgCode 
	 */
	public void setUpDeliverDateOrgCode(String upDeliverDateOrgCode) {
		this.upDeliverDateOrgCode = upDeliverDateOrgCode;
	}
	/**
	 * 获取oldDeliverBillQty  
	 * @return oldDeliverBillQty oldDeliverBillQty
	 */
	public Integer getOldDeliverBillQty() {
		return oldDeliverBillQty;
	}
	/**
	 * 设置oldDeliverBillQty  
	 * @param oldDeliverBillQty oldDeliverBillQty 
	 */
	public void setOldDeliverBillQty(Integer oldDeliverBillQty) {
		this.oldDeliverBillQty = oldDeliverBillQty;
	}
	/**
	 * 获取notificationOrgCode  
	 * @return notificationOrgCode notificationOrgCode
	 */
	public String getNotificationOrgCode() {
		return notificationOrgCode;
	}
	/**
	 * 设置notificationOrgCode  
	 * @param notificationOrgCode notificationOrgCode 
	 */
	public void setNotificationOrgCode(String notificationOrgCode) {
		this.notificationOrgCode = notificationOrgCode;
	}
	
}