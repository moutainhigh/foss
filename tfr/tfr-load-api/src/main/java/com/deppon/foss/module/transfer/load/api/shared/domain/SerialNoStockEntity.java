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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/SerialNoStockEntity.java
 *  
 *  FILE NAME          :SerialNoStockEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/** 
 * @className: SerialNoStockEntity
 * @author: ShiWei shiwei@outlook.com
 * @description: 定义流水号库存(件库存)实体类
 * @date: 2012-10-11 下午3:29:47
 * 
 */
public class SerialNoStockEntity extends BaseEntity {

	private static final long serialVersionUID = -1277461386127105600L;
	/**
	 * 件货物流水号
	 */
	private String serialNo;
	
	/**
	 * 库存部门
	 */
	private String storageDept;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	//重量
	private BigDecimal weight;
	//体积
	private BigDecimal volumn;
	/**
	 * 入库时间
	 */
	private Date instorageDate;
	
	/**
	 * 是否在库（考虑到预配共用此实体，需要添加该属性）
	 */
	private int isInStorage;//0,不在库，1，在库
	
	 /**
     * 库区code
     */
    private String goodsAreaCode;
    
    /**
     * 是否合车
     */
    private String isJoinCar;
    
    /**
     * 是否预配
     */
    private String isPreHandOver;
    
    /**
     * 运输性质
     * **/
    private String productCode;
    
    //是否计泡机扫描
    private String beScan;
    
    
	public String getIsPreHandOver() {
		return isPreHandOver;
	}
	public void setIsPreHandOver(String isPreHandOver) {
		this.isPreHandOver = isPreHandOver;
	}
	public String getIsJoinCar() {
		return isJoinCar;
	}
	public void setIsJoinCar(String isJoinCar) {
		this.isJoinCar = isJoinCar;
	}
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	public int getIsInStorage() {
		return isInStorage;
	}

	public void setIsInStorage(int isInStorage) {
		this.isInStorage = isInStorage;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getStorageDept() {
		return storageDept;
	}

	public void setStorageDept(String storageDept) {
		this.storageDept = storageDept;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Date getInstorageDate() {
		return instorageDate;
	}

	public void setInstorageDate(Date instorageDate) {
		this.instorageDate = instorageDate;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getVolumn() {
		return volumn;
	}
	public void setVolumn(BigDecimal volumn) {
		this.volumn = volumn;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getBeScan() {
		return beScan;
	}
	public void setBeScan(String beScan) {
		this.beScan = beScan;
	}
	
}