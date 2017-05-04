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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/FlightPricePlanDto.java
 * 
 * FILE NAME        	: FlightPricePlanDto.java
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
 * 代理航空公司运价DTO
 * @author DP-Foss-YueHongJie
 * @date 2012-11-1 上午8:25:49
 */
public class FlightPricePlanDto implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -7273510947256230454L;
    /* 标识列 */
    /**
     * 
     */
    private String id;
    
    /* 航空公司编码 */
    /**
     * 
     */
    private String airlinesCode;

    /* 运价号 */
    /**
     * 
     */
    private String priceNo;

    /* 配载部门编码 */
    /**
     * 
     */
    private String loadOrgCode;

    /* 开始时间 */
    /**
     * 
     */
    private Date beginTime;

    /* 结束时间 */
    /**
     * 
     */
    private Date endTime;

    /* 是否激活 */
    /**
     * 
     */
    private String active;

    /* 描述 */
    /**
     * 
     */
    private String description;

    /* 数据版本号 */
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

    /* 创建用户编号 */
    /**
     * 
     */
    private String createUserCode;

    /* 修改用户编号 */
    /**
     * 
     */
    private String modifyUserCode;

    /* 创建部门编号 */
    /**
     * 
     */
    private String createOrgCode;

    /* 修改部门编号 */
    /**
     * 
     */
    private String modifyOrgCode;

    /* 币种 */
    /**
     * 
     */
    private String currencyCode;
    
    /* 录入单时间 */
    /**
     * 
     */
    private Date billDate;

    
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
    public String getAirlinesCode() {
        return airlinesCode;
    }

    
    /**
     * 
     *
     * @param airlinesCode 
     */
    public void setAirlinesCode(String airlinesCode) {
        this.airlinesCode = airlinesCode;
    }

    
    /**
     * 
     *
     * @return 
     */
    public String getPriceNo() {
        return priceNo;
    }

    
    /**
     * 
     *
     * @param priceNo 
     */
    public void setPriceNo(String priceNo) {
        this.priceNo = priceNo;
    }

    
    /**
     * 
     *
     * @return 
     */
    public String getLoadOrgCode() {
        return loadOrgCode;
    }

    
    /**
     * 
     *
     * @param loadOrgCode 
     */
    public void setLoadOrgCode(String loadOrgCode) {
        this.loadOrgCode = loadOrgCode;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Date getBeginTime() {
        return beginTime;
    }

    
    /**
     * 
     *
     * @param beginTime 
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Date getEndTime() {
        return endTime;
    }

    
    /**
     * 
     *
     * @param endTime 
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
    public String getDescription() {
        return description;
    }

    
    /**
     * 
     *
     * @param description 
     */
    public void setDescription(String description) {
        this.description = description;
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
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
    
}