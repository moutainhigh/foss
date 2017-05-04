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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/AirlinesValueAddEntity.java
 * 
 * FILE NAME        	: AirlinesValueAddEntity.java
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
 * 代理航空公司增值服务费用，包括燃油附加费，地面运输费，保费，最低总金额
 * @author DP-Foss-YueHongJie
 * @date 2012-11-2 上午11:10:14
 */

public class AirlinesValueAddEntity extends BaseEntity {
    
    /**
     * 序列化号
     */
    private static final long serialVersionUID = 836681321460470776L;
    private static final String YES="是";
    private static final String NO="否";
    
    
    /**
     * 配载部门
     */
    private String loadOrgCode;
    /**
     * 配载部门名称
     */
    private String loadOrgName;
    /**
     * 航空公司
     */
    private String  airlinesCode;
    /**
     * 航空公司名称
     */
    private String  airlinesName;
    /**
     * 修改人姓名
     */
    private String modifyUserName;
    /**
     * 出发机场
     */
    private String  airport;
    /**
     * 出发机场名称
     */
    private String  airportName;
    /**
     * 生效日期
     */
    private Date beginTime;
    /**
     * 截止日期
     */
    private Date endTime;
    /**
     * 数据状态
     */
    private String active;
    /**
     * 版本信息
     */
    private Long versionNo;
    /**
     * 创建机构
     */
    private String createOrgCode;
    /**
     * 修改机构
     */
    private String modifyOrgCode;
    /**
     *  币种
     */
    private String currencyCode;
    /**
     * 燃油附加费
     */
    private BigDecimal oilAddFee;
    /**最低燃油附加费
     * 
     */
    private BigDecimal minOilAddFee;
    /**
     * 地面运输费
     */
    private BigDecimal groundTrsFee;
    /**
     * 最低地面运输费
     */
    private BigDecimal minGroundTrsFee;
    /**
     * 保费
     * 
     */
    private BigDecimal insuranceFee;
    /**
     * 最低保费
     */
    private BigDecimal minInsuranceFee;
    /**
     * 最低总金额
     */
    private BigDecimal minTotalFee;
    /**
     * 备注
     */
    private String description;
    /**
     * 运价号
     */
    private String priceNo;
    
    
    /**
     * 是否当前版本
     */
    private String currentUsedVersion;
    
    
    /**
     *获得是否当前版本 
     */
    public String getCurrentUsedVersion() {
	Date now =new Date();
	if(this.getBeginTime()!=null&&this.getEndTime()!=null)
	{
	 if(now.compareTo(this.getBeginTime())>=0 &&now.compareTo(this.getEndTime())<0)  
	 {
	     currentUsedVersion = YES;
	     return currentUsedVersion;
	 }
	 else
	 {
	     currentUsedVersion = NO;
	     return currentUsedVersion;
	 }
	}else
	{
	    currentUsedVersion = "";
	    return currentUsedVersion;
	}
    }

    /**
     *设置是否当前版本 
     */
    public void setCurrentUsedVersion(String currentUsedVersion) {
        this.currentUsedVersion = currentUsedVersion;
    }
    

    /**
     * 获取 航空公司名称.
     *
     * @return the 航空公司名称
     */
    public String getAirlinesName() {
		return airlinesName;
	}

	/**
	 * 设置 航空公司名称.
	 *
	 * @param airlinesName the new 航空公司名称
	 */
	public void setAirlinesName(String airlinesName) {
		this.airlinesName = airlinesName;
	}

	/**
	 * 获取 生效日期.
	 *
	 * @return the 生效日期
	 */
	public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置 生效日期.
     *
     * @param beginTime the new 生效日期
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取 截止日期.
     *
     * @return the 截止日期
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置 截止日期.
     *
     * @param endTime the new 截止日期
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
     * 获取 版本信息.
     *
     * @return the 版本信息
     */
    public Long getVersionNo() {
        return versionNo;
    }

    /**
     * 设置 版本信息.
     *
     * @param versionNo the new 版本信息
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    /**
     * 获取 创建机构.
     *
     * @return the 创建机构
     */
    public String getCreateOrgCode() {
        return createOrgCode;
    }

    /**
     * 设置 创建机构.
     *
     * @param createOrgCode the new 创建机构
     */
    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    /**
     * 获取 修改机构.
     *
     * @return the 修改机构
     */
    public String getModifyOrgCode() {
        return modifyOrgCode;
    }

    /**
     * 设置 修改机构.
     *
     * @param modifyOrgCode the new 修改机构
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
     * 获取 燃油附加费.
     *
     * @return the 燃油附加费
     */
    public BigDecimal getOilAddFee() {
        return oilAddFee;
    }

    /**
     * 设置 燃油附加费.
     *
     * @param oilAddFee the new 燃油附加费
     */
    public void setOilAddFee(BigDecimal oilAddFee) {
        this.oilAddFee = oilAddFee;
    }

    /**
     * 获取 最低燃油附加费.
     *
     * @return the 最低燃油附加费
     */
    public BigDecimal getMinOilAddFee() {
        return minOilAddFee;
    }

    /**
     * 设置 最低燃油附加费.
     *
     * @param minOilAddFee the new 最低燃油附加费
     */
    public void setMinOilAddFee(BigDecimal minOilAddFee) {
        this.minOilAddFee = minOilAddFee;
    }

    /**
     * 获取 地面运输费.
     *
     * @return the 地面运输费
     */
    public BigDecimal getGroundTrsFee() {
        return groundTrsFee;
    }

    /**
     * 设置 地面运输费.
     *
     * @param groundTrsFee the new 地面运输费
     */
    public void setGroundTrsFee(BigDecimal groundTrsFee) {
        this.groundTrsFee = groundTrsFee;
    }

    /**
     * 获取 最低地面运输费.
     *
     * @return the 最低地面运输费
     */
    public BigDecimal getMinGroundTrsFee() {
        return minGroundTrsFee;
    }

    /**
     * 设置 最低地面运输费.
     *
     * @param minGroundTrsFee the new 最低地面运输费
     */
    public void setMinGroundTrsFee(BigDecimal minGroundTrsFee) {
        this.minGroundTrsFee = minGroundTrsFee;
    }

    /**
     * 获取 保费.
     *
     * @return the 保费
     */
    public BigDecimal getInsuranceFee() {
        return insuranceFee;
    }

    /**
     * 设置 保费.
     *
     * @param insuranceFee the new 保费
     */
    public void setInsuranceFee(BigDecimal insuranceFee) {
        this.insuranceFee = insuranceFee;
    }

    /**
     * 获取 最低保费.
     *
     * @return the 最低保费
     */
    public BigDecimal getMinInsuranceFee() {
        return minInsuranceFee;
    }

    /**
     * 设置 最低保费.
     *
     * @param minInsuranceFee the new 最低保费
     */
    public void setMinInsuranceFee(BigDecimal minInsuranceFee) {
        this.minInsuranceFee = minInsuranceFee;
    }

    /**
     * 获取 最低总金额.
     *
     * @return the 最低总金额
     */
    public BigDecimal getMinTotalFee() {
        return minTotalFee;
    }

    /**
     * 设置 最低总金额.
     *
     * @param minTotalFee the new 最低总金额
     */
    public void setMinTotalFee(BigDecimal minTotalFee) {
        this.minTotalFee = minTotalFee;
    }

    /**
     * 获取 备注.
     *
     * @return the 备注
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置 备注.
     *
     * @param description the new 备注
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取 运价号.
     *
     * @return the 运价号
     */
    public String getPriceNo() {
        return priceNo;
    }

    /**
     * 设置 运价号.
     *
     * @param priceNo the new 运价号
     */
    public void setPriceNo(String priceNo) {
        this.priceNo = priceNo;
    }
    
    /**
     * 获取 配载部门.
     *
     * @return the 配载部门
     */
    public String getLoadOrgCode() {
        return loadOrgCode;
    }

    
    /**
     * 设置 配载部门.
     *
     * @param loadOrgCode the new 配载部门
     */
    public void setLoadOrgCode(String loadOrgCode) {
        this.loadOrgCode = loadOrgCode;
    }

    
    /**
     * 获取 航空公司.
     *
     * @return the 航空公司
     */
    public String getAirlinesCode() {
        return airlinesCode;
    }

    
    /**
     * 设置 航空公司.
     *
     * @param airlinesCode the new 航空公司
     */
    public void setAirlinesCode(String airlinesCode) {
        this.airlinesCode = airlinesCode;
    }

    
    /**
     * 获取 出发机场.
     *
     * @return the 出发机场
     */
    public String getAirport() {
        return airport;
    }

    
    /**
     * 设置 出发机场.
     *
     * @param airport the new 出发机场
     */
    public void setAirport(String airport) {
        this.airport = airport;
    }

	/**
	 * 获取 修改人姓名.
	 *
	 * @return the 修改人姓名
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * 设置 修改人姓名.
	 *
	 * @param modifyUserName the new 修改人姓名
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public String getLoadOrgName() {
		return loadOrgName;
	}

	public void setLoadOrgName(String loadOrgName) {
		this.loadOrgName = loadOrgName;
	}
    
}