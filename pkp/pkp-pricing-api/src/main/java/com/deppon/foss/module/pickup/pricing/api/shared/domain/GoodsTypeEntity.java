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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/GoodsTypeEntity.java
 * 
 * FILE NAME        	: GoodsTypeEntity.java
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
 * 
 * GoodsTypeEntity
 * 
 * 货物定义类型
 * DP-Foss-YueHongJie
 * 2012-11-21 上午10:29:46
 * 
 * @version 1.0.0
 *
 */
public class GoodsTypeEntity extends BaseEntity {
    
    /**
     * 
     */
    private static final long serialVersionUID = 3557183723817752338L;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 是否激活
     */
    private String active;
    /**
     * 描述
     */
    private String mark;
    /**
     * 版本号
     */
    private Long versionNo;
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 创建机构
     */
    private String createOrgCode;
    /**
     * 修改机构
     */
    private String modifyOrgCode;
    /**
     * 修改人姓名
     */
    private String modifyUserName;
    /**
     * 创建人人姓名
     */
    private String createUserName;
    /**
     * 业务日期
     */
    private Date billDate;
    
    private String description;
    
    
    
    public String getDescription() {
    	return description;
    }
    
    
    public void setDescription(String description) {
    	this.description = description;
    }
    /**
     * 获取 编码.
     *
     * @return the 编码
     */
    public String getCode() {
        return code;
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
     * 设置 编码.
     *
     * @param code the new 编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取 名称.
     *
     * @return the 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 名称.
     *
     * @param name the new 名称
     */
    public void setName(String name) {
        this.name = name;
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
    public String getMark() {
        return mark;
    }

    /**
     * 设置 描述.
     *
     * @param mark the new 描述
     */
    public void setMark(String mark) {
        this.mark = mark;
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

	/**
	 * 获取 创建人人姓名.
	 *
	 * @return the 创建人人姓名
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * 设置 创建人人姓名.
	 *
	 * @param createUserName the new 创建人人姓名
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
    
}