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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/ProvinceCityEntity.java
 * 
 * FILE NAME        	: ProvinceCityEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 银行省市信息实体类
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-11-19 下午5:43:13
 * @since
 * @version
 */
public class ProvinceCityEntity extends BaseEntity {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 6681775674241153946L;
    
    /**
     * 行政区域编码.
     */
    private String districtCode;
    
    /**
     * 区域全称.
     */
    private String districtName;
    
    /**
     * 上级行政区域编码.
     */
    private String parentDistrictCode;
    
    /**
     * 行政区域级别.
     */
    private String districtDegree;
    
    /**
     * 是否有效.
     */
    private String active;
    
    /**
     * 获取 行政区域编码.
     *
     * @return  the districtCode
     */
    public String getDistrictCode() {
        return districtCode;
    }
    
    /**
     * 设置 行政区域编码.
     *
     * @param districtCode the districtCode to set
     */
    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }
    
    /**
     * 获取 是否有效.
     *
     * @return  the active
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 是否有效.
     *
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     * 获取 区域全称.
     *
     * @return  the districtName
     */
    public String getDistrictName() {
        return districtName;
    }
    
    /**
     * 设置 区域全称.
     *
     * @param districtName the districtName to set
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
    
    /**
     * 获取 上级行政区域编码.
     *
     * @return  the parentDistrictCode
     */
    public String getParentDistrictCode() {
        return parentDistrictCode;
    }
    
    /**
     * 设置 上级行政区域编码.
     *
     * @param parentDistrictCode the parentDistrictCode to set
     */
    public void setParentDistrictCode(String parentDistrictCode) {
        this.parentDistrictCode = parentDistrictCode;
    }
    
    /**
     * 获取 行政区域级别.
     *
     * @return  the districtDegree
     */
    public String getDistrictDegree() {
        return districtDegree;
    }
    
    /**
     * 设置 行政区域级别.
     *
     * @param districtDegree the districtDegree to set
     */
    public void setDistrictDegree(String districtDegree) {
        this.districtDegree = districtDegree;
    }
}