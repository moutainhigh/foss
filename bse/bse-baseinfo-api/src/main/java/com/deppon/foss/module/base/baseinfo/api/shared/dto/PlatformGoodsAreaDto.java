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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/AgingDto.java
 * 
 * FILE NAME        	: AgingDto.java
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

import java.io.Serializable;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;


/**
 * 发车标准时间Dto
 * @author foss-zhujunyong
 * @date Nov 10, 2012 10:16:58 AM
 * @version 1.0
 */
public class PlatformGoodsAreaDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8064705475867562335L;
    
    /**
     * 月台到库区的距离(米)
     */
    private Double distance;

    /**
     * 月台实体
     */
    private PlatformEntity platform;
    
    /**
     * 库区实体
     */
    private GoodsAreaEntity goodsArea;
    
    
    /**
     * @return  the platform
     */
    public PlatformEntity getPlatform() {
        return platform;
    }


    
    /**
     * @param platform the platform to set
     */
    public void setPlatform(PlatformEntity platform) {
        this.platform = platform;
    }


    
    /**
     * @return  the goodsArea
     */
    public GoodsAreaEntity getGoodsArea() {
        return goodsArea;
    }


    
    /**
     * @param goodsArea the goodsArea to set
     */
    public void setGoodsArea(GoodsAreaEntity goodsArea) {
        this.goodsArea = goodsArea;
    }


    /**
     * @return  the distance
     */
    public Double getDistance() {
        return distance;
    }

    
    /**
     * @param distance the distance to set
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }

}
