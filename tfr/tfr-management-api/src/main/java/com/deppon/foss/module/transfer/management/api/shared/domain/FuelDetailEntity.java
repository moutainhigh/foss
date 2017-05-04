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
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/domain/FuelDetailEntity.java
 *  
 *  FILE NAME          :FuelDetailEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

public class FuelDetailEntity extends BaseEntity {

	private static final long serialVersionUID = -6087206280121535401L;
	
    /**
     * 发车信息_ID
     */
    private String departureId;  

	/**
     * 加油升数
     */
    private BigDecimal fuelQty;  

    /**
     * 单价
     */
    private BigDecimal unitPrice;  

    /**
     * 油费
     */
    private BigDecimal fuelFee;  

    /**
     * 加油方式
     */
    private String fuelType;  

    /**
     * 加油供应商
     */
    private String fuelSupplier;  

    /**
     * 加油标号
     */
    private String fuelGrade;  

    /**
     * 加油地点
     */
    private String fuelAddress;  

    /**
     * 油卡余额
     */
    private BigDecimal balance;  

    /**
     * 加油付款类型
     */
    private String fuelPayType;  
    
    /**
     * 币种
     */
    //private String currencyCode; 
    
    /**
     * 加油时间
     */
    private Date fuelTime; 


    /**
	 * @return the departureId
	 */
	public String getDepartureId() {
		return departureId;
	}

	/**
	 * @param departureId the departureId to set
	 */
	public void setDepartureId(String departureId) {
		this.departureId = departureId;
	}

    /**
     * 获取 加油升数.
     *
     * @return the 加油升数
     */
    public BigDecimal getFuelQty() {
        return fuelQty;
    }

    /**
     * 设置 加油升数.
     *
     * @param fuelQty the new 加油升数
     */
    public void setFuelQty(BigDecimal fuelQty) {
        this.fuelQty = fuelQty;
    }

    /**
     * 获取 单价.
     *
     * @return the 单价
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * 设置 单价.
     *
     * @param unitPrice the new 单价
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * 获取 油费.
     *
     * @return the 油费
     */
    public BigDecimal getFuelFee() {
        return fuelFee;
    }

    /**
     * 设置 油费.
     *
     * @param fuelFee the new 油费
     */
    public void setFuelFee(BigDecimal fuelFee) {
        this.fuelFee = fuelFee;
    }

    /**
     * 获取 加油方式.
     *
     * @return the 加油方式
     */
    public String getFuelType() {
        return fuelType;
    }

    /**
     * 设置 加油方式.
     *
     * @param fuelType the new 加油方式
     */
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    /**
     * 获取 加油供应商.
     *
     * @return the 加油供应商
     */
    public String getFuelSupplier() {
        return fuelSupplier;
    }

    /**
     * 设置 加油供应商.
     *
     * @param fuelSupplier the new 加油供应商
     */
    public void setFuelSupplier(String fuelSupplier) {
        this.fuelSupplier = fuelSupplier;
    }

    /**
     * 获取 加油标号.
     *
     * @return the 加油标号
     */
    public String getFuelGrade() {
        return fuelGrade;
    }

    /**
     * 设置 加油标号.
     *
     * @param fuelGrade the new 加油标号
     */
    public void setFuelGrade(String fuelGrade) {
        this.fuelGrade = fuelGrade;
    }

    /**
     * 获取 加油地点.
     *
     * @return the 加油地点
     */
    public String getFuelAddress() {
        return fuelAddress;
    }

    /**
     * 设置 加油地点.
     *
     * @param fuelAddress the new 加油地点
     */
    public void setFuelAddress(String fuelAddress) {
        this.fuelAddress = fuelAddress;
    }

    /**
     * 获取 油卡余额.
     *
     * @return the 油卡余额
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 设置 油卡余额.
     *
     * @param balance the new 油卡余额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 获取 加油付款类型.
     *
     * @return the 加油付款类型
     */
    public String getFuelPayType() {
        return fuelPayType;
    }

    /**
     * 设置 加油付款类型.
     *
     * @param fuelPayType the new 加油付款类型
     */
    public void setFuelPayType(String fuelPayType) {
        this.fuelPayType = fuelPayType;
    }

	/**
	 * 获取 加油时间.
	 *
	 * @return the 加油时间
	 */
	@DateFormat
	public Date getFuelTime() {
		return fuelTime;
	}

	/**
	 * 设置 加油时间.
	 *
	 * @param fuelTime the new 加油时间
	 */
	@DateFormat
	public void setFuelTime(Date fuelTime) {
		this.fuelTime = fuelTime;
	}

}