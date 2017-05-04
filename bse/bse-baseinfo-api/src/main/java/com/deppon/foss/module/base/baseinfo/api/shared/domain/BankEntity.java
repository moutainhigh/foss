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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/BankEntity.java
 * 
 * FILE NAME        	: BankEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 银行实体类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-30
 * 下午3:32:41
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-30 下午3:32:41
 * @since
 * @version
 */
public class BankEntity extends BaseEntity {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -3967231350567691062L;

    /**
     * 银行编码.
     */
    private String code;

    /**
     * 银行名称.
     */
    private String name;

    /**
     * 上级银行.
     */
    private String parentBank;

    /**
     * 省份.
     */
    private String provId;

    /**
     * 省份名称.
     */
    private String provName;

    /**
     * 城市.
     */
    private String cityCode;

    /**
     * 城市名称.
     */
    private String cityName;
    
    /**
     * 是否总行.
     */
    private String headOffice;

    /**
     * 是否支持即日退.
     */
    private String intraDayType;

    /**
     * 是否启用.
     */
    private String active;
    
    /**
     * 版本号.
     */
    private Long versionNo;
    /**
     * 精确查询
     */
    private String exactQuery;
    
    /**
     * 获取精确查询
     * @return
     */
    public String getExactQuery() {
		return exactQuery;
	}
    /**
     * 设置精确查询
     * @return
     */
	public void setExactQuery(String exactQuery) {
		this.exactQuery = exactQuery;
	}
    
    /**
     * 获取 银行编码.
     *
     * @return  the code
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 设置 银行编码.
     *
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * 获取 银行名称.
     *
     * @return  the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * 设置 银行名称.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 获取 上级银行.
     *
     * @return  the parentBank
     */
    public String getParentBank() {
        return parentBank;
    }
    
    /**
     * 设置 上级银行.
     *
     * @param parentBank the parentBank to set
     */
    public void setParentBank(String parentBank) {
        this.parentBank = parentBank;
    }
    
    /**
     * 获取 省份.
     *
     * @return  the provId
     */
    public String getProvId() {
        return provId;
    }
    
    /**
     * 设置 省份.
     *
     * @param provId the provId to set
     */
    public void setProvId(String provId) {
        this.provId = provId;
    }
    
    /**
     * 获取 省份名称.
     *
     * @return  the provName
     */
    public String getProvName() {
        return provName;
    }
    
    /**
     * 设置 省份名称.
     *
     * @param provName the provName to set
     */
    public void setProvName(String provName) {
        this.provName = provName;
    }
    
    /**
     * 获取 城市.
     *
     * @return  the cityCode
     */
    public String getCityCode() {
        return cityCode;
    }
    
    /**
     * 设置 城市.
     *
     * @param cityCode the cityCode to set
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    
    /**
     * 获取 城市名称.
     *
     * @return  the cityName
     */
    public String getCityName() {
        return cityName;
    }
    
    /**
     * 设置 城市名称.
     *
     * @param cityName the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    /**
     * 获取 是否总行.
     *
     * @return  the headOffice
     */
    public String getHeadOffice() {
        return headOffice;
    }
    
    /**
     * 设置 是否总行.
     *
     * @param headOffice the headOffice to set
     */
    public void setHeadOffice(String headOffice) {
        this.headOffice = headOffice;
    }
    
    /**
     * 获取 是否支持即日退.
     *
     * @return  the intraDayType
     */
    public String getIntraDayType() {
        return intraDayType;
    }
    
    /**
     * 设置 是否支持即日退.
     *
     * @param intraDayType the intraDayType to set
     */
    public void setIntraDayType(String intraDayType) {
        this.intraDayType = intraDayType;
    }
    
    /**
     * 获取 是否启用.
     *
     * @return  the active
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 是否启用.
     *
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }
    
    /**
     * 获取 版本号.
     *
     * @return  the versionNo
     */
    public Long getVersionNo() {
        return versionNo;
    }
    
    /**
     * 设置 版本号.
     *
     * @param versionNo the versionNo to set
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }
    
    

}
