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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/FlightPricePlanDetailDto.java
 * 
 * FILE NAME        	: FlightPricePlanDetailDto.java
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
 * 代理航空公司运价明细DTO
 * @author DP-Foss-YueHongJie
 * @date 2012-11-1 上午8:25:49
 */
public class FlightPricePlanDetailDto implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -8961943439647447644L;
    
    /* ID */
    /**
     * 
     */
    private String id;

    /* 运价方案ID */
    /**
     * 
     */
    private String flightPricePlanId;

    /* 目的站 */
    /**
     * 
     */
    private String destDistrictCode;
    
    /* 货物类型编码 */
    /**
     * 
     */
    private String goodsTypeCode;

    /* 航班号*/
    /**
     * 
     */
    private String flightNo;

    /* 最低运费 */
    /**
     * 
     */
    private Long minFee;

    /* 是否激活 */
    /**
     * 
     */
    private String active;

    /* 数据版本 */
    /**
     * 
     */
    private Long versionNo;

    /* 创建时间 */
    /**
     * 
     */
    private Date createTime;

    /* 修改时间 */
    /**
     * 
     */
    private Date modifyTime;

    /* 创建人编码 */
    /**
     * 
     */
    private String createUserCode;

    /* 修改人编码 */
    /**
     * 
     */
    private String modifyUserCode;

    /* 创建机构编码 */
    /**
     * 
     */
    private String createOrgCode;

    /* 修改组织机构编码 */
    /**
     * 
     */
    private String modifyOrgCode;

    /* 币种 */
    /**
     * 
     */
    private String currencyCode;

    /* 45公斤以上 */
    /**
     * 
     */
    private Long up45Kg;

    /* 100公斤以上 */
    /**
     * 
     */
    private Long up100Kg;

    /* 300公斤以上 */
    /**
     * 
     */
    private Long up300Kg;

    /* 500公斤以上 */
    /**
     * 
     */
    private Long up500Kg;

    /* 1000公斤以上 */
    /**
     * 
     */
    private Long up1000Kg;

    /* 2000公斤以上 */
    /**
     * 
     */
    private Long up2000Kg;

    /* 3000公斤以上 */
    /**
     * 
     */
    private Long up3000Kg;
    
    /* 录单时间 */
    /**
     * 
     */
    private Date billDate;
    
    /* 最终运价信息费用*/
    /**
     * 
     */
    private Long freight;
    
    
    /**
     * 
     *
     * @return 
     */
    public Long getFreight() {
        return freight;
    }


    
    /**
     * 
     *
     * @param freight 
     */
    public void setFreight(Long freight) {
        this.freight = freight;
    }


    /**
     * 
     *
     * @return 
     */
    public Date getBillDate() {
        return billDate;
    }

    
    /**
     * 
     *
     * @param billDate 
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }


    /**
     * 
     *
     * @return 
     */
    public String getId() {
        return id;
    }

    
    /**
     * 
     *
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
    }

    
    /**
     * 
     *
     * @return 
     */
    public String getFlightPricePlanId() {
        return flightPricePlanId;
    }

    
    /**
     * 
     *
     * @param flightPricePlanId 
     */
    public void setFlightPricePlanId(String flightPricePlanId) {
        this.flightPricePlanId = flightPricePlanId;
    }

    
    /**
     * 
     *
     * @return 
     */
    public String getDestDistrictCode() {
        return destDistrictCode;
    }

    
    /**
     * 
     *
     * @param destDistrictCode 
     */
    public void setDestDistrictCode(String destDistrictCode) {
        this.destDistrictCode = destDistrictCode;
    }

    
    /**
     * 
     *
     * @return 
     */
    public String getGoodsTypeCode() {
        return goodsTypeCode;
    }

    
    /**
     * 
     *
     * @param goodsTypeCode 
     */
    public void setGoodsTypeCode(String goodsTypeCode) {
        this.goodsTypeCode = goodsTypeCode;
    }

    
    /**
     * 
     *
     * @return 
     */
    public String getFlightNo() {
        return flightNo;
    }

    
    /**
     * 
     *
     * @param flightNo 
     */
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Long getMinFee() {
        return minFee;
    }

    
    /**
     * 
     *
     * @param minFee 
     */
    public void setMinFee(Long minFee) {
        this.minFee = minFee;
    }

    
    /**
     * 
     *
     * @return 
     */
    public String getActive() {
        return active;
    }

    
    /**
     * 
     *
     * @param active 
     */
    public void setActive(String active) {
        this.active = active;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Long getVersionNo() {
        return versionNo;
    }

    
    /**
     * 
     *
     * @param versionNo 
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Date getCreateTime() {
        return createTime;
    }

    
    /**
     * 
     *
     * @param createTime 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    
    /**
     * 
     *
     * @param modifyTime 
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    
    /**
     * 
     *
     * @return 
     */
    public String getCreateUserCode() {
        return createUserCode;
    }

    
    /**
     * 
     *
     * @param createUserCode 
     */
    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode;
    }

    
    /**
     * 
     *
     * @return 
     */
    public String getModifyUserCode() {
        return modifyUserCode;
    }

    
    /**
     * 
     *
     * @param modifyUserCode 
     */
    public void setModifyUserCode(String modifyUserCode) {
        this.modifyUserCode = modifyUserCode;
    }

    
    /**
     * 
     *
     * @return 
     */
    public String getCreateOrgCode() {
        return createOrgCode;
    }

    
    /**
     * 
     *
     * @param createOrgCode 
     */
    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    
    /**
     * 
     *
     * @return 
     */
    public String getModifyOrgCode() {
        return modifyOrgCode;
    }

    
    /**
     * 
     *
     * @param modifyOrgCode 
     */
    public void setModifyOrgCode(String modifyOrgCode) {
        this.modifyOrgCode = modifyOrgCode;
    }

    
    /**
     * 
     *
     * @return 
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    
    /**
     * 
     *
     * @param currencyCode 
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Long getUp45Kg() {
        return up45Kg;
    }

    
    /**
     * 
     *
     * @param up45Kg 
     */
    public void setUp45Kg(Long up45Kg) {
        this.up45Kg = up45Kg;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Long getUp100Kg() {
        return up100Kg;
    }

    
    /**
     * 
     *
     * @param up100Kg 
     */
    public void setUp100Kg(Long up100Kg) {
        this.up100Kg = up100Kg;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Long getUp300Kg() {
        return up300Kg;
    }

    
    /**
     * 
     *
     * @param up300Kg 
     */
    public void setUp300Kg(Long up300Kg) {
        this.up300Kg = up300Kg;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Long getUp500Kg() {
        return up500Kg;
    }

    
    /**
     * 
     *
     * @param up500Kg 
     */
    public void setUp500Kg(Long up500Kg) {
        this.up500Kg = up500Kg;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Long getUp1000Kg() {
        return up1000Kg;
    }

    
    /**
     * 
     *
     * @param up1000Kg 
     */
    public void setUp1000Kg(Long up1000Kg) {
        this.up1000Kg = up1000Kg;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Long getUp2000Kg() {
        return up2000Kg;
    }

    
    /**
     * 
     *
     * @param up2000Kg 
     */
    public void setUp2000Kg(Long up2000Kg) {
        this.up2000Kg = up2000Kg;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Long getUp3000Kg() {
        return up3000Kg;
    }

    
    /**
     * 
     *
     * @param up3000Kg 
     */
    public void setUp3000Kg(Long up3000Kg) {
        this.up3000Kg = up3000Kg;
    }

    
    /**
     * 
     *
     * @return 
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    
}