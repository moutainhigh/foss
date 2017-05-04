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
 *  FILE PATH          :/AirHandOverBillDetailEntity.java
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
/**
 * 航空交接单明细
 * @author 038300-foss-pengzhen
 * @date 2012-12-25 下午5:45:57
 */
public class AirHandOverBillDetailEntity {
	
	/**
	 * id
	 */
	private String id;
	/**
	 * 航空正单_id
	 */
    private String toid; 
    /**
    * 正单交接单id
    */
    private String airHandoverbillId;  
    /**
     * 航空公司编码
     */
    private String airLineCode;  
    /**
     * 航空公司名称
     */
    private String airLineName;  
     /**
      * 正单号
      */
    private String airWaybillNo;  
    /**
     * 件数
     */
    private Integer goodsQty;  
    /**
     * 毛重
     */
    private BigDecimal grossWeight;  
    /**
     * 计费重量
     */
    private BigDecimal billingWeight;  
    /**
     * 始发站名称
     */
    private String deptRegionName; 
    /**
     * 目的站名称
     */
    private String arrvRegionName;  
    /**
     * 始发站编码
     */
    private String deptRegionCode;  
    /**
     * 目的站编码
     */
    private String arrvRegionCode;  
    /**
     * 货物名称
     */
    private String goodsName;  
    /**
     * 包装
     */
    private String packageStruction;  
    /**
     * 币种
     */
    private String currencyCode;  
    /**
     * 运费
     */
    private Long feeTotal;  
     /**
      * 备注
      */
    private String notes;  
    /**
     * 航空运费
     */
   private BigDecimal airFee;

   //运输性质
   private String transportType;
   
   //体积
   private BigDecimal goodsVolume;
   
	public BigDecimal getAirFee() {
		return airFee;
	}

	public void setAirFee(BigDecimal airFee) {
		this.airFee = airFee;
	}

	/**
     * @return id 
     */
    public String getId() {
        return id;
    }

    /**
     * id
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 
     */
    public String getToid() {
        return toid;
    }

    /**
     * @param tOId 
     */
    public void setToid(String toid) {
        this.toid = toid;
    }

    /**
     * 获取 正单交接单id.
     *
     * @return the 正单交接单id
     */
    public String getAirHandoverbillId() {
        return airHandoverbillId;
    }

    /**
     * 设置 正单交接单id.
     *
     * @param airHandoverbillId the new 正单交接单id
     */
    public void setAirHandoverbillId(String airHandoverbillId) {
        this.airHandoverbillId = airHandoverbillId;
    }

    /**
     * 获取 航空公司编码.
     *
     * @return the 航空公司编码
     */
    public String getAirLineCode() {
        return airLineCode;
    }

    /**
     * 设置 航空公司编码.
     *
     * @param airLineCode the new 航空公司编码
     */
    public void setAirLineCode(String airLineCode) {
        this.airLineCode = airLineCode;
    }

    /**
     * 获取 航空公司名称.
     *
     * @return the 航空公司名称
     */
    public String getAirLineName() {
        return airLineName;
    }

    /**
     * 设置 航空公司名称.
     *
     * @param airLineName the new 航空公司名称
     */
    public void setAirLineName(String airLineName) {
        this.airLineName = airLineName;
    }

    /**
     * 获取 正单号.
     *
     * @return the 正单号
     */
    public String getAirWaybillNo() {
        return airWaybillNo;
    }

    /**
     * 设置 正单号.
     *
     * @param airWaybillNo the new 正单号
     */
    public void setAirWaybillNo(String airWaybillNo) {
        this.airWaybillNo = airWaybillNo;
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
     * 获取 始发站名称.
     *
     * @return the 始发站名称
     */
    public String getDeptRegionName() {
        return deptRegionName;
    }

    /**
     * 设置 始发站名称.
     *
     * @param deptRegionName the new 始发站名称
     */
    public void setDeptRegionName(String deptRegionName) {
        this.deptRegionName = deptRegionName;
    }

    /**
     * 获取 目的站名称.
     *
     * @return the 目的站名称
     */
    public String getArrvRegionName() {
        return arrvRegionName;
    }

    /**
     * 设置 目的站名称.
     *
     * @param arrvRegionName the new 目的站名称
     */
    public void setArrvRegionName(String arrvRegionName) {
        this.arrvRegionName = arrvRegionName;
    }

    /**
     * 获取 始发站编码.
     *
     * @return the 始发站编码
     */
    public String getDeptRegionCode() {
        return deptRegionCode;
    }

    /**
     * 设置 始发站编码.
     *
     * @param deptRegionCode the new 始发站编码
     */
    public void setDeptRegionCode(String deptRegionCode) {
        this.deptRegionCode = deptRegionCode;
    }

    /**
     * 获取 目的站编码.
     *
     * @return the 目的站编码
     */
    public String getArrvRegionCode() {
        return arrvRegionCode;
    }

    /**
     * 设置 目的站编码.
     *
     * @param arrvRegionCode the new 目的站编码
     */
    public void setArrvRegionCode(String arrvRegionCode) {
        this.arrvRegionCode = arrvRegionCode;
    }

    /**
     * 获取 货物名称.
     *
     * @return the 货物名称
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * 设置 货物名称.
     *
     * @param goodsName the new 货物名称
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * 获取 包装.
     *
     * @return the 包装
     */
    public String getPackageStruction() {
        return packageStruction;
    }

    /**
     * 设置 包装.
     *
     * @param packageStruction the new 包装
     */
    public void setPackageStruction(String packageStruction) {
        this.packageStruction = packageStruction;
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
     * 获取 运费.
     *
     * @return the 运费
     */
    public Long getFeeTotal() {
		return feeTotal;
	}

	/**
	 * 设置 运费.
	 *
	 * @param feeTotal the new 运费
	 */
	public void setFeeTotal(Long feeTotal) {
		this.feeTotal = feeTotal;
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

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public BigDecimal getGoodsVolume() {
		return goodsVolume;
	}

	public void setGoodsVolume(BigDecimal goodsVolume) {
		this.goodsVolume = goodsVolume;
	}

	
	
	
}