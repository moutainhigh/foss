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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/DistanceEntity.java
 * 
 * FILE NAME        	: DistanceEntity.java
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
 * 库位到月台的距离实体
 * @author foss-zhujunyong
 * @date Oct 17, 2012 4:03:12 PM
 * @version 1.0
 */
public class DistanceEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -8687018732092321228L;

    // 库位虚拟代码
    private String storageVirtualCode;

    // 月台虚拟代码
    private String platformVirtualCode;

    // 库位到月台的距离
    private Integer distance;

    // 是否有效
    private String active;

    
    /**
     * @return  the storageVirtualCode
     */
    public String getStorageVirtualCode() {
        return storageVirtualCode;
    }

    
    /**
     * @param storageVirtualCode the storageVirtualCode to set
     */
    public void setStorageVirtualCode(String storageVirtualCode) {
        this.storageVirtualCode = storageVirtualCode;
    }

    
    /**
     * @return  the platformVirtualCode
     */
    public String getPlatformVirtualCode() {
        return platformVirtualCode;
    }

    
    /**
     * @param platformVirtualCode the platformVirtualCode to set
     */
    public void setPlatformVirtualCode(String platformVirtualCode) {
        this.platformVirtualCode = platformVirtualCode;
    }

    
    /**
     * @return  the distance
     */
    public Integer getDistance() {
        return distance;
    }

    
    /**
     * @param distance the distance to set
     */
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    
    /**
     * @return  the active
     */
    public String getActive() {
        return active;
    }

    
    /**
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }

    
      
}
