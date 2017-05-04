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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/PricePlanEntity.java
 * 
 * FILE NAME        	: PricePlanEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * PricePlanEntity
 * 价格方案实体
 * DP-Foss-YueHongJie
 * 2012-11-20 下午3:13:08.
 *
 * @version 1.0.0
 */
public class PricePlanEntity extends BaseEntity {

    /**
     * 序列化号.
     */
    private static final long serialVersionUID = 393325597080833089L;
    
    /**
     * 
     */
    private static final String YES="是";
    
    /**
     * 
     */
    private static final String NO="否";
    
    /**
     * 始发区域ID.
     */
    private String priceRegionId;
    
    /**
     * 始发区域CODE.
     */
    private String priceRegionCode;
    
    /**
     * 始发区域名称.
     */
    private String priceRegionName;
    
    /**
     * 方案名称.
     */
    private String name;
    
    /**
     * 生效日期.
     */
    private Date beginTime;
    
    /**
     * 结束日期.
     */
    private Date endTime;
    
    /**
     * 数据状态.
     */
    private String active;
    
    /**
     * 描述.
     */
    private String description;
    
    /**
     * 描述.
     */
    private String descNote;
    
    /**
     * 版本信息.
     */
    private String versionInfo;
    
    /**
     * 数据版本.
     */
    private Long versionNo;
    
    /**
     * 原价格方案ID.
     */
    private String refId;
    
    /**
     * 创建组织.
     */
    private String createOrgCode;
    
    /**
     * 更新组织.
     */
    private String modifyOrgCode;
    
    /**
     * 创建人名称.
     */
    private String createUserName;
    
    /**
     * 修改人.
     */
    private String modifyUserName; 

    /**
     * 币种.
     */
    private String currencyCode;
    
    /**
     * 运输标志.
     */
    private String transportFlag;
    
    /**
     * 是否当前版本.
     */
    private String currentUsedVersion;
    
    /**
     * 用于判断是否立即中止标志.
     */
    private boolean isPromptly;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 当前时间
     */
    private Date currentDate;
    
    
    public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	/**
     * 获取客户名称
     * @return
     */
    
    
    public String getCustomerName() {
		return customerName;
	}

    /**
     * 设置客户名称
     * @param customerName
     */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * 获取客户编码
	 * @return
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * 设置客户名称
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}


	/**
     * 获取 描述.
     *
     * @return  the descNote
     */
    public String getDescNote() {
        return descNote;
    }

    
    /**
     * 设置 描述.
     *
     * @param descNote the descNote to set
     */
    public void setDescNote(String descNote) {
        this.descNote = descNote;
        if(StringUtils.isNotBlank(this.descNote)){
            this.description = this.descNote;
        }
    }

    /**
     * <p>获得否立即中止标志</p>.
     *
     * @return the 用于判断是否立即中止标志
     * @author DP-Foss-YueHongJie
     * @date 2013-3-26 上午9:01:41
     * @see
     */
    public boolean getIsPromptly() {
        return isPromptly;
    }

    /**
     * <p>设置是否立即中止标志</p>.
     *
     * @param isPromptly the new 用于判断是否立即中止标志
     * @author DP-Foss-YueHongJie
     * @date 2013-3-26 上午9:01:45
     * @see
     */
    public void setIsPromptly(boolean isPromptly) {
        this.isPromptly = isPromptly;
    }

    /**
     * 获得是否当前版本.
     *
     * @return the 是否当前版本
     */
    public String getCurrentUsedVersion() {
	Date now =new Date();
	if(null!=currentUsedVersion&&!"".equals(currentUsedVersion)){//查询条件时用到
		return currentUsedVersion;// Y 或 N 
	}else if(this.getBeginTime()!=null&&this.getEndTime()!=null)
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
     * 设置是否当前版本.
     *
     * @param currentUsedVersion the new 是否当前版本
     */
    public void setCurrentUsedVersion(String currentUsedVersion) {
        this.currentUsedVersion = currentUsedVersion;
    }

    /**
     * 获取 始发区域名称.
     *
     * @return the 始发区域名称
     */
    public String getPriceRegionName() {
        return priceRegionName;
    }
    
    /**
     * 设置 始发区域名称.
     *
     * @param priceRegionName the new 始发区域名称
     */
    public void setPriceRegionName(String priceRegionName) {
        this.priceRegionName = priceRegionName;
    }
    
    /**
     * 获取 创建人名称.
     *
     * @return the 创建人名称
     */
    public String getCreateUserName() {
        return createUserName;
    }


    
    /**
     * 设置 创建人名称.
     *
     * @param createUserName the new 创建人名称
     */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

 
    /**
     * 获取 方案名称.
     *
     * @return the 方案名称
     */
    public String getName() {
        return name;
    }

    
    /**
     * 获取 始发区域ID.
     *
     * @return the 始发区域ID
     */
    public String getPriceRegionId() {
        return priceRegionId;
    }

    
    /**
     * 设置 始发区域ID.
     *
     * @param priceRegionId the new 始发区域ID
     */
    public void setPriceRegionId(String priceRegionId) {
        this.priceRegionId = priceRegionId;
    }

    
    /**
     * 获取 始发区域CODE.
     *
     * @return the 始发区域CODE
     */
    public String getPriceRegionCode() {
        return priceRegionCode;
    }

    
    /**
     * 设置 始发区域CODE.
     *
     * @param priceRegionCode the new 始发区域CODE
     */
    public void setPriceRegionCode(String priceRegionCode) {
        this.priceRegionCode = priceRegionCode;
    }

    /**
     * 设置 方案名称.
     *
     * @param name the new 方案名称
     */
    public void setName(String name) {
        this.name = name;
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
     * 获取 结束日期.
     *
     * @return the 结束日期
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置 结束日期.
     *
     * @param endTime the new 结束日期
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
     * 获取 描述.
     *
     * @return the 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置 描述.
     *
     * @param description the new 描述
     */
    public void setDescription(String description) {
        this.description = description;
        if(StringUtils.isNotBlank(this.description)){
            this.descNote = this.description;
        }
    }

    /**
     * 获取 版本信息.
     *
     * @return the 版本信息
     */
    public String getVersionInfo() {
        return versionInfo;
    }

    /**
     * 设置 版本信息.
     *
     * @param versionInfo the new 版本信息
     */
    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
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
     * 获取 原价格方案ID.
     *
     * @return the 原价格方案ID
     */
    public String getRefId() {
        return refId;
    }

    /**
     * 设置 原价格方案ID.
     *
     * @param refId the new 原价格方案ID
     */
    public void setRefId(String refId) {
        this.refId = refId;
    }

    /**
     * 获取 创建组织.
     *
     * @return the 创建组织
     */
    public String getCreateOrgCode() {
        return createOrgCode;
    }

    /**
     * 设置 创建组织.
     *
     * @param createOrgCode the new 创建组织
     */
    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    /**
     * 获取 更新组织.
     *
     * @return the 更新组织
     */
    public String getModifyOrgCode() {
        return modifyOrgCode;
    }

    /**
     * 设置 更新组织.
     *
     * @param modifyOrgCode the new 更新组织
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
     * <p>获取方案标志，目前支持汽运与空运</p>.
     *
     * @return the 运输标志
     * @author DP-Foss-YueHongJie
     * @date 2013-1-9 下午2:32:28
     * @see
     */
    public String getTransportFlag() {
	return transportFlag;
    }

    /**
     * <p>方案标志，目前支持汽运与空运</p>.
     *
     * @param transportFlag the new 运输标志
     * @author DP-Foss-YueHongJie
     * @date 2013-1-9 下午2:31:53
     * @see
     */
    public void setTransportFlag(String transportFlag) {
	this.transportFlag = transportFlag;
    }
    
    /**
     * <p>获取修改人名称</p>.
     *
     * @return the 修改人
     * @author DP-Foss-YueHongJie
     * @date 2013-1-9 下午2:31:17
     * @see
     */
    public String getModifyUserName() {
        return modifyUserName;
    }

    /**
     * <p>设置修改人名称</p>.
     *
     * @param modifyUserName the new 修改人
     * @author DP-Foss-YueHongJie
     * @date 2013-1-9 下午2:31:38
     * @see
     */
    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }
    
    
}