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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/DiscountProgramEntity.java
 * 
 * FILE NAME        	: DiscountProgramEntity.java
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
 */
@SuppressWarnings("serial")
public class DiscountProgramEntity extends BaseEntity {
    
    /**
     * 
     */
    private String code;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String programFullPath;

    /**
     * 
     */
    private String methods;

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
    private Long versionNo;

    /**
     * 
     */
    private String active;

    /**
     * 
     */
    private String createUserCode;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private String createOrgCode;

    /**
     * 
     */
    private String modifyUserCode;

    /**
     * 
     */
    private Date modifyTime;

    /**
     * 
     */
    private String modifyOrgCode;
    
    /**
     * 
     */
    private String tSrvPriceRegionId;

    /**
     * 
     *
     * @return 
     */
    public String gettSrvPriceRegionId() {
		return tSrvPriceRegionId;
	}

	/**
	 * 
	 *
	 * @param tSrvPriceRegionId 
	 */
	public void settSrvPriceRegionId(String tSrvPriceRegionId) {
		this.tSrvPriceRegionId = tSrvPriceRegionId;
	}

/*	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/

    /**
 * 
 *
 * @return 
 */
public String getCode() {
        return code;
    }

    /**
     * 
     *
     * @param code 
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     *
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     *
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     *
     * @return 
     */
    public String getProgramFullPath() {
        return programFullPath;
    }

    /**
     * 
     *
     * @param programFullPath 
     */
    public void setProgramFullPath(String programFullPath) {
        this.programFullPath = programFullPath;
    }

    /**
     * 
     *
     * @return 
     */
    public String getMethods() {
        return methods;
    }

    /**
     * 
     *
     * @param methods 
     */
    public void setMethods(String methods) {
        this.methods = methods;
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
}