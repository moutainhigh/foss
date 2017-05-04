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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/AirlinesValueAddDto.java
 * 
 * FILE NAME        	: AirlinesValueAddDto.java
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
 * 航空公司代理增值服务费用DTO
 * @author DP-Foss-YueHongJie
 * @date 2012-11-2 上午11:54:06
 */
public class AirlinesValueAddDto implements Serializable {
   
    /**
     * 
     */
    private static final long serialVersionUID = 7633714586744221474L;

    /**
     * 
     */
    private String id;

    /**
     * 
     */
    private String loadOrgCode;

    /**
     * 
     */
    private String  airlinesCode;
    
    /**
     * 
     */
    private String  airPort;

    /**
     * 
     */
    private Date beginTime;

    /**
     * 
     */
    private Date endTime;

    /**
     * 
     */
    private String active;

    /**
     * 
     */
    private Long versionNo;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date modifyTime;

    /**
     * 
     */
    private String createUserCode;

    /**
     * 
     */
    private String modifyUserCode;

    /**
     * 
     */
    private String createOrgCode;

    /**
     * 
     */
    private String modifyOrgCode;

    /**
     * 
     */
    private String currencyCode;

    /**
     * 
     */
    private Long oilAddFee;

    /**
     * 
     */
    private Long minOilAddFee;

    /**
     * 
     */
    private Long groundTrsFee;

    /**
     * 
     */
    private Long minGroundTrsFee;

    /**
     * 
     */
    private Long insuranceFee;

    /**
     * 
     */
    private Long minInsuranceFee;

    /**
     * 
     */
    private Long minTotalFee;

    /**
     * 
     */
    private String description;

    
    private String currentUsedVersion;//是否当前版本
    
    /**
     * 
     *
     * @return 
     */
    public String getCurrentUsedVersion() {
        return currentUsedVersion;
    }

    
    /**
     * 
     *
     * @param currentUsedVersion 
     */
    public void setCurrentUsedVersion(String currentUsedVersion) {
        this.currentUsedVersion = currentUsedVersion;
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
     * @return 
     */
    public String getAirPort() {
        return airPort;
    }


    
    /**
     * 
     *
     * @param airPort 
     */
    public void setAirport(String airPort) {
        this.airPort = airPort;
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
    public Long getOilAddFee() {
        return oilAddFee;
    }

    
    /**
     * 
     *
     * @param oilAddFee 
     */
    public void setOilAddFee(Long oilAddFee) {
        this.oilAddFee = oilAddFee;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Long getMinOilAddFee() {
        return minOilAddFee;
    }

    
    /**
     * 
     *
     * @param minOilAddFee 
     */
    public void setMinOilAddFee(Long minOilAddFee) {
        this.minOilAddFee = minOilAddFee;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Long getGroundTrsFee() {
        return groundTrsFee;
    }

    
    /**
     * 
     *
     * @param groundTrsFee 
     */
    public void setGroundTrsFee(Long groundTrsFee) {
        this.groundTrsFee = groundTrsFee;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Long getMinGroundTrsFee() {
        return minGroundTrsFee;
    }

    
    /**
     * 
     *
     * @param minGroundTrsFee 
     */
    public void setMinGroundTrsFee(Long minGroundTrsFee) {
        this.minGroundTrsFee = minGroundTrsFee;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Long getInsuranceFee() {
        return insuranceFee;
    }

    
    /**
     * 
     *
     * @param insuranceFee 
     */
    public void setInsuranceFee(Long insuranceFee) {
        this.insuranceFee = insuranceFee;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Long getMinInsuranceFee() {
        return minInsuranceFee;
    }

    
    /**
     * 
     *
     * @param minInsuranceFee 
     */
    public void setMinInsuranceFee(Long minInsuranceFee) {
        this.minInsuranceFee = minInsuranceFee;
    }

    
    /**
     * 
     *
     * @return 
     */
    public Long getMinTotalFee() {
        return minTotalFee;
    }

    
    /**
     * 
     *
     * @param minTotalFee 
     */
    public void setMinTotalFee(Long minTotalFee) {
        this.minTotalFee = minTotalFee;
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
     */
    private String priceNo;
    
    /**
     * 
     */
    private Date billDate;
    
}