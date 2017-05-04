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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/EffectivePlanEntity.java
 * 
 * FILE NAME        	: EffectivePlanEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.util.Date;
import com.deppon.foss.framework.entity.BaseEntity;



/**
 * 
 * 时效方案实体
 * @author DP-Foss-YueHongJie
 * @date 2012-11-10 上午10:14:30
 */
public class EffectiveExpressPlanEntity extends BaseEntity {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 2117590078891960062L;
	private static final String YES="是"; 
    private static final String NO="否";
    
    /**
     * 方案名称
     */
    private String name;
    /**
     * 方案开始时间
     */
    private Date beginTime;
    /**
     * 方案结束时间
     */
    private Date endTime;
    /**
     * 始发区域ID
     */
    private String deptRegionId;
    /**
     * 始发区域编码
     */
    private String deptRegionCode;
    /**
     * 始发区域名称
     */
    private String deptRegionName;
    /**
     * 激活/未激活
     */
    private String active;
    /**
     * 描述
     */
    private String description;
    /**
     * 版本信息
     */
    private String versionInfo;
    /**
     * 版本号
     */
    private Long versionNo;
    /**
     * 原始方案ID
     */
    private String refId;
    /**
     * 修改用户姓名
     */
    private String modifyUserName;
    /**
     * 创建机构
     */
    private String createOrgCode;
    /**
     * 修改机构
     */
    private String modifyOrgCode;
    /**
     * 营业日期
     */
    private Date businessDate;
    
    /**
     * 是否当前版本
     */
    private String currentUsedVersion;
    /**
     * 用于判断是否立即中止标志
     */
    private boolean isPromptly;
    
    /**
     * 修改使用描述
     */
    private String updateDesTemp;
    
    public String getUpdateDesTemp() {
		return updateDesTemp;
	}

	public void setUpdateDesTemp(String updateDesTemp) {
		this.updateDesTemp = updateDesTemp;
	}

	/**
     * 
     * <p>获得否立即中止标志</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-3-26 上午9:01:41
     * @return
     * @see
     */
    public boolean getIsPromptly() {
        return isPromptly;
    }

    /**
     * 
     * <p>设置是否立即中止标志</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-3-26 上午9:01:45
     * @param isPromptly
     * @see
     */
    public void setIsPromptly(boolean isPromptly) {
        this.isPromptly = isPromptly;
    }


    /**
     *获得是否当前版本 
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
	     return YES;
	 }
	 else
	 {
	     currentUsedVersion = NO;
	     return NO;
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
     * 获取 营业日期.
     *
     * @return the 营业日期
     */
    public Date getBusinessDate() {
        return businessDate;
    }

    
    /**
     * 设置 营业日期.
     *
     * @param businessDate the new 营业日期
     */
    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
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
     * 设置 方案名称.
     *
     * @param name the new 方案名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取 方案开始时间.
     *
     * @return the 方案开始时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置 方案开始时间.
     *
     * @param beginTime the new 方案开始时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取 方案结束时间.
     *
     * @return the 方案结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置 方案结束时间.
     *
     * @param endTime the new 方案结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
     * 获取 始发区域编码.
     *
     * @return the 始发区域编码
     */
    public String getDeptRegionCode() {
        return deptRegionCode;
    }

    /**
     * 设置 始发区域编码.
     *
     * @param deptRegionCode the new 始发区域编码
     */
    public void setDeptRegionCode(String deptRegionCode) {
        this.deptRegionCode = deptRegionCode;
    }

    /**
     * 获取 激活/未激活.
     *
     * @return the 激活/未激活
     */
    public String getActive() {
        return active;
    }

    /**
     * 设置 激活/未激活.
     *
     * @param active the new 激活/未激活
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
     * 获取 版本号.
     *
     * @return the 版本号
     */
    public Long getVersionNo() {
        return versionNo;
    }

    /**
     * 设置 版本号.
     *
     * @param versionNo the new 版本号
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    /**
     * 获取 原始方案ID.
     *
     * @return the 原始方案ID
     */
    public String getRefId() {
        return refId;
    }

    /**
     * 设置 原始方案ID.
     *
     * @param refId the new 原始方案ID
     */
    public void setRefId(String refId) {
        this.refId = refId;
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
	 * 获取 修改用户姓名.
	 *
	 * @return the 修改用户姓名
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}


	/**
	 * 设置 修改用户姓名.
	 *
	 * @param modifyUserName the new 修改用户姓名
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}


	/**
	 * 获取 始发区域名称.
	 *
	 * @return the 始发区域名称
	 */
	public String getDeptRegionName() {
		return deptRegionName;
	}


	/**
	 * 设置 始发区域名称.
	 *
	 * @param deptRegionName the new 始发区域名称
	 */
	public void setDeptRegionName(String deptRegionName) {
		this.deptRegionName = deptRegionName;
	}
    
}