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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/BigZoneDto.java
 * 
 * FILE NAME        	: BigZoneDto.java
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
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;


/**
 * 集中接送货大区以及大区包含小区信息DTO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-25 上午11:40:57 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-25 上午11:40:57
 * @since
 * @version
 */
public class BigZoneDto extends BigZoneEntity {
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 2173521890962964171L;

    /**
     * 集中接送货小区编码.
     */
    private String smallRegionCode;
    
    /**
     * 集中接送货小区名称.
     */
    private String smallRegionName;
    
    /**
     * 获取 集中接送货小区编码.
     *
     * @return  the smallRegionCode
     */
    public String getSmallRegionCode() {
        return smallRegionCode;
    }
    
    /**
     * 设置 集中接送货小区编码.
     *
     * @param smallRegionCode the smallRegionCode to set
     */
    public void setSmallRegionCode(String smallRegionCode) {
        this.smallRegionCode = smallRegionCode;
    }
    
    /**
     * 获取 集中接送货小区名称.
     *
     * @return  the smallRegionName
     */
    public String getSmallRegionName() {
        return smallRegionName;
    }
    
    /**
     * 设置 集中接送货小区名称.
     *
     * @param smallRegionName the smallRegionName to set
     */
    public void setSmallRegionName(String smallRegionName) {
        this.smallRegionName = smallRegionName;
    }

    

}
