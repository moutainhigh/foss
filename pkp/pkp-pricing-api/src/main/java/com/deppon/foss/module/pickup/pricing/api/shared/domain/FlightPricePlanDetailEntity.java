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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/FlightPricePlanDetailEntity.java
 * 
 * FILE NAME        	: FlightPricePlanDetailEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.domain;


import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 代理航空公司运价方案明细信息
 * @author DP-Foss-YueHongJie
 * @date 2012-10-31 下午5:38:22
 */
public class FlightPricePlanDetailEntity extends BaseEntity {
    
    
    /**
     * 
     */
    private static final long serialVersionUID = -3637056764218946511L;


    /** 运价方案ID */
    private String flightPricePlanId;

    /** 目的站 */
    private String destDistrictCode;
    
    /** 目的站 名称*/
    private String destDistrictName;
    
    /** 货物类型编码 */
    private String goodsTypeCode;

    /** 航班号*/
    private String flightNo;
    
    /** 出发机场 */
    private String departAirPort;
    
    /** 到达机场 */
    private String arriveAirPort;
    
    /** 起飞时间 */
    private Date launchTime;
    
    /** 到达时间 */
    private Date arriveTime;
    
    /**航空公司*/
    private String airlinesName;

    /** 最低运费 */
    private Long minFee;

    /** 是否激活 */
    private String active;

    /** 数据版本 */
    private Long versionNo;

    /** 创建机构编码 */
    private String createOrgCode;

    /** 修改组织机构编码 */
    private String modifyOrgCode;

    /** 币种 */
    private String currencyCode;

    /** 45公斤以下 */
    private BigDecimal down45Kg;
    
    /** 45公斤以上 */
    private BigDecimal up45Kg;

    /** 100公斤以上 */
    private BigDecimal up100Kg;

    /** 300公斤以上 */
    private BigDecimal up300Kg;

    /** 500公斤以上 */
    private BigDecimal up500Kg;

    /** 1000公斤以上 */
    private BigDecimal up1000Kg;

    /** 2000公斤以上 */
    private BigDecimal up2000Kg;

    /** 3000公斤以上 */
    private BigDecimal up3000Kg;
    
    /** 业务日期 */
    private Date billDate;

    /**
     * 计算过后的实际运费
     */
    private BigDecimal calculateFee;
    
    
    /**
     * 
     * <p>获得实际计算后运费</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-2-1 下午4:28:31
     * @return
     * @see
     */
    public BigDecimal getCalculateFee() {
        return calculateFee;
    }


    /**
     * 
     * <p>设置实际计算后运费</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-2-1 下午4:28:47
     * @param calculateFee
     * @see
     */
    public void setCalculateFee(BigDecimal calculateFee) {
        this.calculateFee = calculateFee;
    }


    /**
     * 获取 业务日期.
     *
     * @return the 业务日期
     */
    public Date getBillDate() {
        return billDate;
    }

    
    /**
     * 设置 业务日期.
     *
     * @param billDate the new 业务日期
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    /**
     * 获取 运价方案ID.
     *
     * @return the 运价方案ID
     */
    public String getFlightPricePlanId() {
        return flightPricePlanId;
    }

    /**
     * 设置 运价方案ID.
     *
     * @param flightPricePlanId the new 运价方案ID
     */
    public void setFlightPricePlanId(String flightPricePlanId) {
        this.flightPricePlanId = flightPricePlanId;
    }

    /**
     * 获取 目的站.
     *
     * @return the 目的站
     */
    public String getDestDistrictCode() {
        return destDistrictCode;
    }

    /**
     * 设置 目的站.
     *
     * @param destDistrictCode the new 目的站
     */
    public void setDestDistrictCode(String destDistrictCode) {
        this.destDistrictCode = destDistrictCode;
    }

    /**
     * 获取 货物类型编码.
     *
     * @return the 货物类型编码
     */
    public String getGoodsTypeCode() {
        return goodsTypeCode;
    }

    /**
     * 设置 货物类型编码.
     *
     * @param goodsTypeCode the new 货物类型编码
     */
    public void setGoodsTypeCode(String goodsTypeCode) {
        this.goodsTypeCode = goodsTypeCode;
    }

    /**
     * 获取 航班号.
     *
     * @return the 航班号
     */
    public String getFlightNo() {
        return flightNo;
    }

    /**
     * 设置 航班号.
     *
     * @param flightNo the new 航班号
     */
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    /**
     * 获取 最低运费.
     *
     * @return the 最低运费
     */
    public Long getMinFee() {
        return minFee;
    }

    /**
     * 设置 最低运费.
     *
     * @param minFee the new 最低运费
     */
    public void setMinFee(Long minFee) {
        this.minFee = minFee;
    }

    /**
     * 获取 是否激活.
     *
     * @return the 是否激活
     */
    public String getActive() {
        return active;
    }

    /**
     * 设置 是否激活.
     *
     * @param active the new 是否激活
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     * 获取 数据版本.
     *
     * @return the 数据版本
     */
    public Long getVersionNo() {
        return versionNo;
    }

    /**
     * 设置 数据版本.
     *
     * @param versionNo the new 数据版本
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }


    /**
     * 获取 创建机构编码.
     *
     * @return the 创建机构编码
     */
    public String getCreateOrgCode() {
        return createOrgCode;
    }

    /**
     * 设置 创建机构编码.
     *
     * @param createOrgCode the new 创建机构编码
     */
    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    /**
     * 获取 修改组织机构编码.
     *
     * @return the 修改组织机构编码
     */
    public String getModifyOrgCode() {
        return modifyOrgCode;
    }

    /**
     * 设置 修改组织机构编码.
     *
     * @param modifyOrgCode the new 修改组织机构编码
     */
    public void setModifyOrgCode(String modifyOrgCode) {
        this.modifyOrgCode = modifyOrgCode;
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
     * 获取 45公斤以上.
     *
     * @return the 45公斤以上
     */
    public BigDecimal getUp45Kg() {
        return up45Kg;
    }

    /**
     * 设置 45公斤以上.
     *
     * @param up45Kg the new 45公斤以上
     */
    public void setUp45Kg(BigDecimal up45Kg) {
        this.up45Kg = up45Kg;
    }

    /**
     * 获取 100公斤以上.
     *
     * @return the 100公斤以上
     */
    public BigDecimal getUp100Kg() {
        return up100Kg;
    }

    /**
     * 设置 100公斤以上.
     *
     * @param up100Kg the new 100公斤以上
     */
    public void setUp100Kg(BigDecimal up100Kg) {
        this.up100Kg = up100Kg;
    }

    /**
     * 获取 300公斤以上.
     *
     * @return the 300公斤以上
     */
    public BigDecimal getUp300Kg() {
        return up300Kg;
    }

    /**
     * 设置 300公斤以上.
     *
     * @param up300Kg the new 300公斤以上
     */
    public void setUp300Kg(BigDecimal up300Kg) {
        this.up300Kg = up300Kg;
    }

    /**
     * 获取 500公斤以上.
     *
     * @return the 500公斤以上
     */
    public BigDecimal getUp500Kg() {
        return up500Kg;
    }

    /**
     * 设置 500公斤以上.
     *
     * @param up500Kg the new 500公斤以上
     */
    public void setUp500Kg(BigDecimal up500Kg) {
        this.up500Kg = up500Kg;
    }

    /**
     * 获取 1000公斤以上.
     *
     * @return the 1000公斤以上
     */
    public BigDecimal getUp1000Kg() {
        return up1000Kg;
    }

    /**
     * 设置 1000公斤以上.
     *
     * @param up1000Kg the new 1000公斤以上
     */
    public void setUp1000Kg(BigDecimal up1000Kg) {
        this.up1000Kg = up1000Kg;
    }

    /**
     * 获取 2000公斤以上.
     *
     * @return the 2000公斤以上
     */
    public BigDecimal getUp2000Kg() {
        return up2000Kg;
    }

    /**
     * 设置 2000公斤以上.
     *
     * @param up2000Kg the new 2000公斤以上
     */
    public void setUp2000Kg(BigDecimal up2000Kg) {
        this.up2000Kg = up2000Kg;
    }

    /**
     * 获取 3000公斤以上.
     *
     * @return the 3000公斤以上
     */
    public BigDecimal getUp3000Kg() {
        return up3000Kg;
    }

    /**
     * 设置 3000公斤以上.
     *
     * @param up3000Kg the new 3000公斤以上
     */
    public void setUp3000Kg(BigDecimal up3000Kg) {
        this.up3000Kg = up3000Kg;
    }

    /**
     * 获取 目的站 名称.
     *
     * @return the 目的站 名称
     */
    public String getDestDistrictName() {
	return destDistrictName;
    }

    /**
     * 设置 目的站 名称.
     *
     * @param destDistrictName the new 目的站 名称
     */
    public void setDestDistrictName(String destDistrictName) {
	this.destDistrictName = destDistrictName;
    }

    /**
     * 获取 45公斤以下.
     *
     * @return the 45公斤以下
     */
    public BigDecimal getDown45Kg() {
	return down45Kg;
    }

    /**
     * 设置 45公斤以下.
     *
     * @param down45Kg the new 45公斤以下
     */
    public void setDown45Kg(BigDecimal down45Kg) {
	this.down45Kg = down45Kg;
    }
    
    /**
     * 获取 出发机场.
     *
     * @return the 出发机场
     */
    public String getDepartAirPort() {
        return departAirPort;
    }

    
    /**
     * 设置 出发机场.
     *
     * @param departAirPort the new 出发机场
     */
    public void setDepartAirPort(String departAirPort) {
        this.departAirPort = departAirPort;
    }

    
    /**
     * 获取 到达机场.
     *
     * @return the 到达机场
     */
    public String getArriveAirPort() {
        return arriveAirPort;
    }

    
    /**
     * 设置 到达机场.
     *
     * @param arriveAirPort the new 到达机场
     */
    public void setArriveAirPort(String arriveAirPort) {
        this.arriveAirPort = arriveAirPort;
    }

    
    /**
     * 获取 起飞时间.
     *
     * @return the 起飞时间
     */
    public Date getLaunchTime() {
        return launchTime;
    }

    
    /**
     * 设置 起飞时间.
     *
     * @param launchTime the new 起飞时间
     */
    public void setLaunchTime(Date launchTime) {
        this.launchTime = launchTime;
    }

    
    /**
     * 获取 到达时间.
     *
     * @return the 到达时间
     */
    public Date getArriveTime() {
        return arriveTime;
    }

    
    /**
     * 设置 到达时间.
     *
     * @param arriveTime the new 到达时间
     */
    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    
    /**
     * 获取 航空公司.
     *
     * @return the 航空公司
     */
    public String getAirlinesName() {
        return airlinesName;
    }

    
    /**
     * 设置 航空公司.
     *
     * @param airlinesName the new 航空公司
     */
    public void setAirlinesName(String airlinesName) {
        this.airlinesName = airlinesName;
    }

    
}