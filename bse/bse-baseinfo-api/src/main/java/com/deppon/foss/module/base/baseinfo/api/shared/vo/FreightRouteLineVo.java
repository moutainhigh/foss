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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/FreightRouteLineVo.java
 * 
 * FILE NAME        	: FreightRouteLineVo.java
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
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LineDeptDto;


/**
 * 
 * 走货路径线路信息VO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-12-13 上午11:31:14 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-12-13 上午11:31:14
 * @since
 * @version
 */
public class FreightRouteLineVo implements Serializable {
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 3620511539330421565L;

    /**
     * 走货路径线路实体.
     */
    private FreightRouteLineEntity entity;
    
    /**
     * 走货路径线路信息集合.
     */
    List<FreightRouteLineEntity> entityList;
    
    /**
     * 走货路径的虚拟编码.
     */
    private String freightRouteVirtualCode;
    
    /**
     * 出发/到达站编码、名称集合.
     */
    private List<LineDeptDto> lineDeptList;

    
    /**
     * 获取 走货路径线路实体.
     *
     * @return  the entity
     */
    public FreightRouteLineEntity getEntity() {
        return entity;
    }

    
    /**
     * 设置 走货路径线路实体.
     *
     * @param entity the entity to set
     */
    public void setEntity(FreightRouteLineEntity entity) {
        this.entity = entity;
    }

    
    /**
     * 获取 走货路径线路信息集合.
     *
     * @return  the entityList
     */
    public List<FreightRouteLineEntity> getEntityList() {
        return entityList;
    }

    
    /**
     * 设置 走货路径线路信息集合.
     *
     * @param entityList the entityList to set
     */
    public void setEntityList(List<FreightRouteLineEntity> entityList) {
        this.entityList = entityList;
    }

    
    /**
     * 获取 走货路径的虚拟编码.
     *
     * @return  the freightRouteVirtualCode
     */
    public String getFreightRouteVirtualCode() {
        return freightRouteVirtualCode;
    }

    
    /**
     * 设置 走货路径的虚拟编码.
     *
     * @param freightRouteVirtualCode the freightRouteVirtualCode to set
     */
    public void setFreightRouteVirtualCode(String freightRouteVirtualCode) {
        this.freightRouteVirtualCode = freightRouteVirtualCode;
    }

    
    /**
     * 获取 出发/到达站编码、名称集合.
     *
     * @return  the lineDeptList
     */
    public List<LineDeptDto> getLineDeptList() {
        return lineDeptList;
    }

    
    /**
     * 设置 出发/到达站编码、名称集合.
     *
     * @param lineDeptList the lineDeptList to set
     */
    public void setLineDeptList(List<LineDeptDto> lineDeptList) {
        this.lineDeptList = lineDeptList;
    }
    
    
    

}
