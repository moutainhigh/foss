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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/FlightPricePlanEntity.java
 * 
 * FILE NAME        	: FlightPricePlanEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 代理航空公司运价方案实体
 * @author DP-Foss-YueHongJie
 * @date 2012-10-31 下午5:33:16
 */
public class FlightPricePlanEntity extends BaseEntity {
    
    
    private static final long serialVersionUID = 1144472286575499297L;
    private static final String YES="是";
    private static final String NO="否";
    
    /** 航空公司编码 */
    private String airlinesCode;
    /** 航空公司名称 */
    private String airlinesName;
    /**
     * 出发机场
     */
    private String  airport;
    /** 运价号 */
    private String priceNo;
    /**
     * 创建人名称
     */
    private String createUserName;
    /**
     * 修改人
     */
    private String modifyUserName;
    /** 配载部门编码 */
    private String loadOrgCode;
    /** 配载部门名臣 */
    private String loadOrgName;

    /** 开始时间 */
    private Date beginTime;

    /** 结束时间 */
    private Date endTime;

    /** 是否激活 */
    private String active;

    /** 描述 */
    private String description;

    /** 数据版本号 */
    private Long versionNo;
    
    /** 创建部门编号 */
    private String createOrgCode;

    /** 修改部门编号 */
    private String modifyOrgCode;

    /** 币种 */
    private String currencyCode;
    
    /**业务日期*/
    private Date billDate;
    
    /**
     * 是否当前版本
     */
    private String currentUsedVersion;
    
    
    /**
     *获得是否当前版本 
     */
    public String getCurrentUsedVersion() {
	Date now =new Date();
	if(null!=currentUsedVersion&&!"".equals(currentUsedVersion)){//查询条件时用到
		return currentUsedVersion;// Y 或 N 
	}else if(this.getBeginTime()!=null&&this.getEndTime()!=null)//查询结果时用到
	{
	 if(now.compareTo(this.getBeginTime())>=0 &&now.compareTo(this.getEndTime())<0)  
	 {
	     currentUsedVersion = YES;
	     return YES;
	 }
	 else
	 {
	     currentUsedVersion = NO;
	     return NO;
	 }
	}else //查询结果时用到
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
     * 获取 航空公司编码.
     *
     * @return the 航空公司编码
     */
    public String getAirlinesCode() {
        return airlinesCode;
    }

    /**
     * 设置 航空公司编码.
     *
     * @param airlinesCode the new 航空公司编码
     */
    public void setAirlinesCode(String airlinesCode) {
        this.airlinesCode = airlinesCode;
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
     * 获取 配载部门编码.
     *
     * @return the 配载部门编码
     */
    public String getLoadOrgCode() {
        return loadOrgCode;
    }

    /**
     * 设置 配载部门编码.
     *
     * @param loadOrgCode the new 配载部门编码
     */
    public void setLoadOrgCode(String loadOrgCode) {
        this.loadOrgCode = loadOrgCode;
    }

    /**
     * 获取 开始时间.
     *
     * @return the 开始时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置 开始时间.
     *
     * @param beginTime the new 开始时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取 结束时间.
     *
     * @return the 结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置 结束时间.
     *
     * @param endTime the new 结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
    }

    /**
     * 获取 数据版本号.
     *
     * @return the 数据版本号
     */
    public Long getVersionNo() {
        return versionNo;
    }

    /**
     * 设置 数据版本号.
     *
     * @param versionNo the new 数据版本号
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    /**
     * 获取 创建部门编号.
     *
     * @return the 创建部门编号
     */
    public String getCreateOrgCode() {
        return createOrgCode;
    }

    /**
     * 设置 创建部门编号.
     *
     * @param createOrgCode the new 创建部门编号
     */
    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    /**
     * 获取 修改部门编号.
     *
     * @return the 修改部门编号
     */
    public String getModifyOrgCode() {
        return modifyOrgCode;
    }

    /**
     * 设置 修改部门编号.
     *
     * @param modifyOrgCode the new 修改部门编号
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
	 * 获取 配载部门名臣.
	 *
	 * @return the 配载部门名臣
	 */
	public String getLoadOrgName() {
		return loadOrgName;
	}

	/**
	 * 设置 配载部门名臣.
	 *
	 * @param loadOrgName the new 配载部门名臣
	 */
	public void setLoadOrgName(String loadOrgName) {
		this.loadOrgName = loadOrgName;
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
	 * 获取 修改人.
	 *
	 * @return the 修改人
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * 设置 修改人.
	 *
	 * @param modifyUserName the new 修改人
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
    
}