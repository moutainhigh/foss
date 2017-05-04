/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/QueryPricePlanDetailBean.java
 * 
 * FILE NAME        	: QueryPricePlanDetailBean.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 查询价格方案明细bean
 * QueryPricePlanDetailBean
 * DP-Foss-YueHongJie
 * 2012-12-3 下午12:28:29
 * 
 * @version 1.0.0
 *
 */
public class QueryPricePlanDetailBean implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 5644109886836083535L;
    /** 
     * 始发区域ID 
     */
    private String deptRegionId;
    /** 
     * 到达区域ID 
     */
    private String arrvRegionId;
    /** 
     * 计费规则类型 
     */
    private String valuationType;
    /** 
     * 数据状态 
     */
    private String active;
    /** 
     * 当前日期
     */
    private Date currentDate;
    /** 
     * 产品编码 
     */
    private String productCode;
    /** 
     * 产品名称 
     */
    private String productName;
    /** 
     * 货物编码 
     */
    private String goodsTypeCode;
    /** 
     * 货物成名 
     */
    private String goodsTypeName;
    /** 
     * 价格方案ID
     */
    private String pricePlanId;
    /** 
     * 产品条目ID 
     */
    private String productItemId;
    /** 
     * 产品条目Code 
     */
    private String productItemCode;
    /** 
     * 航班类型Code
     */
    private String flightTypeCode;
    /**
     * 计费规则ID
     */
    private String valuationId;
    
    
    /**
     * 获取 计费规则ID.
     *
     * @return the 计费规则ID
     */
    public String getValuationId() {
        return valuationId;
    }


    
    /**
     * 设置 计费规则ID.
     *
     * @param valuationId the new 计费规则ID
     */
    public void setValuationId(String valuationId) {
        this.valuationId = valuationId;
    }



    /**
     * 设置 航班类型Code.
     *
     * @param flightTypeCode the new 航班类型Code
     */
    public void setFlightTypeCode(String flightTypeCode) {
        this.flightTypeCode = flightTypeCode;
    }


    
    /**
     * 获取 航班类型Code.
     *
     * @return the 航班类型Code
     */
    public String getFlightTypeCode() {
        return flightTypeCode;
    }


    /**
     * 获取 产品条目Code.
     *
     * @return the 产品条目Code
     */
    public String getProductItemCode() {
        return productItemCode;
    }

    
    /**
     * 设置 产品条目Code.
     *
     * @param productItemCode the new 产品条目Code
     */
    public void setProductItemCode(String productItemCode) {
        this.productItemCode = productItemCode;
    }

    /**
     * 获取 产品条目ID.
     *
     * @return the 产品条目ID
     */
    public String getProductItemId() {
        return productItemId;
    }
    
    /**
     * 设置 产品条目ID.
     *
     * @param productItemId the new 产品条目ID
     */
    public void setProductItemId(String productItemId) {
        this.productItemId = productItemId;
    }




    /**
     * 获取 价格方案ID.
     *
     * @return the 价格方案ID
     */
    public String getPricePlanId() {
        return pricePlanId;
    }



    
    /**
     * 设置 价格方案ID.
     *
     * @param pricePlanId the new 价格方案ID
     */
    public void setPricePlanId(String pricePlanId) {
        this.pricePlanId = pricePlanId;
    }



    /**
     * 获取 产品名称.
     *
     * @return the 产品名称
     */
    public String getProductName() {
        return productName;
    }


    
    /**
     * 设置 产品名称.
     *
     * @param productName the new 产品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }


    
    /**
     * 获取 货物成名.
     *
     * @return the 货物成名
     */
    public String getGoodsTypeName() {
        return goodsTypeName;
    }


    
    /**
     * 设置 货物成名.
     *
     * @param goodsTypeName the new 货物成名
     */
    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }


    /**
     * 获取 产品编码.
     *
     * @return the 产品编码
     */
    public String getProductCode() {
        return productCode;
    }

    
    /**
     * 设置 产品编码.
     *
     * @param productCode the new 产品编码
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    
    /**
     * 获取 货物编码.
     *
     * @return the 货物编码
     */
    public String getGoodsTypeCode() {
        return goodsTypeCode;
    }

    
    /**
     * 设置 货物编码.
     *
     * @param goodsTypeCode the new 货物编码
     */
    public void setGoodsTypeCode(String goodsTypeCode) {
        this.goodsTypeCode = goodsTypeCode;
    }

    /**
     * 获取 始发区域ID.
     *
     * @return the 始发区域ID
     */
    public String getDeptRegionId() {
        return deptRegionId;
    }
    
    /**
     * 设置 始发区域ID.
     *
     * @param deptRegionId the new 始发区域ID
     */
    public void setDeptRegionId(String deptRegionId) {
        this.deptRegionId = deptRegionId;
    }
    
    /**
     * 获取 到达区域ID.
     *
     * @return the 到达区域ID
     */
    public String getArrvRegionId() {
        return arrvRegionId;
    }
    
    /**
     * 设置 到达区域ID.
     *
     * @param arrvRegionId the new 到达区域ID
     */
    public void setArrvRegionId(String arrvRegionId) {
        this.arrvRegionId = arrvRegionId;
    }
    
    /**
     * 获取 计费规则类型.
     *
     * @return the 计费规则类型
     */
    public String getValuationType() {
        return valuationType;
    }
    
    /**
     * 设置 计费规则类型.
     *
     * @param valuationType the new 计费规则类型
     */
    public void setValuationType(String valuationType) {
        this.valuationType = valuationType;
    }
    
    /**
     * 获取 数据状态.
     *
     * @return the 数据状态
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 数据状态.
     *
     * @param active the new 数据状态
     */
    public void setActive(String active) {
        this.active = active;
    }


    
    /**
     * 获取 当前日期.
     *
     * @return the 当前日期
     */
    public Date getCurrentDate() {
        return currentDate;
    }


    
    /**
     * 设置 当前日期.
     *
     * @param currentDate the new 当前日期
     */
    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }
    
  
  
}