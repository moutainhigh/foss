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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/domain/WaybillFeeDetailEntity.java
 *  
 *  FILE NAME          :WaybillFeeDetailEntity.java
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

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 集中接货签单运单费用明细
 * @author 038300-foss-pengzhen
 * @date 2012-11-27 下午4:12:28
 */
public class WaybillFeeDetailEntity extends BaseEntity{
	
	private static final long serialVersionUID = 5328283868408479762L;
	 /**
     * 用车费用
     * BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
     */
    private BigDecimal useTruckFee;
    /**
     * 司机提成
     * BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
     */
    private BigDecimal driverRoyalty;
    /**运单号*/
	private String wayBillNo;
	/**体积*/
	private BigDecimal volume;
	/**重量*/
	private BigDecimal weight;
    /**备注*/
    private String notes;
    /**是否上楼*/
    private String isUpstairs;
    /**是否单独接货*/
    private String isSingleReceive;
    /**集中接货前单号（签单号）*/
    private String signBillNo;
    /**币种*/
    private String currencyCode;
    /**用车部门*/
    private String useTruckOrgCode;
    /**用车部门*/
    private String useTruckOrgName;
    /**客户名称*/
	private String customerName;
	/**件数*/
	private BigDecimal goodsQty;
	/**包装*/
	private String goodsPackage;
	/**是否现付*/
	private String isCashPayment;
	/**开单金额*/
	private BigDecimal totalFee;
	
    
    /**
     * 获取 运单号.
     *
     * @return the 运单号
     */
    public String getWayBillNo() {
		return wayBillNo;
	}

	/**
	 * 设置 运单号.
	 *
	 * @param wayBillNo the new 运单号
	 */
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	/**
	 * 获取 用车费用 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）.
	 *
	 * @return the 用车费用 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	public BigDecimal getUseTruckFee() {
		return useTruckFee;
	}

	/**
	 * 设置 用车费用 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）.
	 *
	 * @param useTruckFee the new 用车费用 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	public void setUseTruckFee(BigDecimal useTruckFee) {
		this.useTruckFee = useTruckFee;
	}

	/**
	 * 获取 司机提成 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）.
	 *
	 * @return the 司机提成 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	public BigDecimal getDriverRoyalty() {
		return driverRoyalty;
	}

	/**
	 * 设置 司机提成 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）.
	 *
	 * @param driverRoyalty the new 司机提成 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	public void setDriverRoyalty(BigDecimal driverRoyalty) {
		this.driverRoyalty = driverRoyalty;
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

    /**
     * 获取 是否上楼.
     *
     * @return the 是否上楼
     */
    public String getIsUpstairs() {
        return isUpstairs;
    }

    /**
     * 设置 是否上楼.
     *
     * @param isUpstairs the new 是否上楼
     */
    public void setIsUpstairs(String isUpstairs) {
        this.isUpstairs = isUpstairs;
    }

    /**
     * 获取 是否单独接货.
     *
     * @return the 是否单独接货
     */
    public String getIsSingleReceive() {
        return isSingleReceive;
    }

    /**
     * 设置 是否单独接货.
     *
     * @param isSingleReceive the new 是否单独接货
     */
    public void setIsSingleReceive(String isSingleReceive) {
        this.isSingleReceive = isSingleReceive;
    }

    /**
     * 获取 集中接货前单号（签单号）.
     *
     * @return the 集中接货前单号（签单号）
     */
    public String getSignBillNo() {
        return signBillNo;
    }

    /**
     * 设置 集中接货前单号（签单号）.
     *
     * @param signBillNo the new 集中接货前单号（签单号）
     */
    public void setSignBillNo(String signBillNo) {
        this.signBillNo = signBillNo;
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
	 * 获取 重量.
	 *
	 * @return the 重量
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * 设置 重量.
	 *
	 * @param weight the new 重量
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * 获取 用车部门.
	 *
	 * @return the 用车部门
	 */
	public String getUseTruckOrgCode() {
		return useTruckOrgCode;
	}

	/**
	 * 设置 用车部门.
	 *
	 * @param useTruckOrgCode the new 用车部门
	 */
	public void setUseTruckOrgCode(String useTruckOrgCode) {
		this.useTruckOrgCode = useTruckOrgCode;
	}

	/**
	 * 获取 客户名称.
	 *
	 * @return the 客户名称
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * 设置 客户名称.
	 *
	 * @param customerName the new 客户名称
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * 获取 件数.
	 *
	 * @return the 件数
	 */
	public BigDecimal getGoodsQty() {
		return goodsQty;
	}

	/**
	 * 设置 件数.
	 *
	 * @param goodsQty the new 件数
	 */
	public void setGoodsQty(BigDecimal goodsQty) {
		this.goodsQty = goodsQty;
	}

	/**
	 * 获取 包装.
	 *
	 * @return the 包装
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}

	/**
	 * 设置 包装.
	 *
	 * @param goodsPackage the new 包装
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	/**
	 * 获取 是否现付.
	 *
	 * @return the 是否现付
	 */
	public String getIsCashPayment() {
		return isCashPayment;
	}

	/**
	 * 设置 是否现付.
	 *
	 * @param isCashPayment the new 是否现付
	 */
	public void setIsCashPayment(String isCashPayment) {
		this.isCashPayment = isCashPayment;
	}

	/**
	 * 获取 开单金额.
	 *
	 * @return the 开单金额
	 */
	public BigDecimal getTotalFee() {
		return totalFee;
	}

	/**
	 * 设置 开单金额.
	 *
	 * @param totalFee the new 开单金额
	 */
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	/**   
	 * useTruckOrgName   
	 *   
	 * @return  the useTruckOrgName   
	 */
	
	public String getUseTruckOrgName() {
		return useTruckOrgName;
	}

	/**   
	 * @param useTruckOrgName the useTruckOrgName to set
	 * Date:2013-3-25上午11:27:51
	 */
	public void setUseTruckOrgName(String useTruckOrgName) {
		this.useTruckOrgName = useTruckOrgName;
	}
}