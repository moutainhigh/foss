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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/OwedLimitRegionVo.java
 * 
 * FILE NAME        	: OwedLimitRegionVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwedLimitRegionEntity;


/**
 * 临欠额度区间范围信息VO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-2-26 下午2:20:56 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-2-26 下午2:20:56
 * @since
 * @version
 */
public class OwedLimitRegionVo implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -541027987412885983L;
    
    
    /**
     * 临欠额度区间范围信息实体.
     */
    private OwedLimitRegionEntity entity;
    
    /**
     * 临欠额度区间范围信息ID集合.
     */
    private List<String> ids;
    
    /**
     * 临欠额度区间范围信息ID.
     */
    private String regionId;
    
    /**
     * 临欠额度区间范围信息集合.
     */
    private List<OwedLimitRegionEntity> regionList;

    
    
    /**
     * 获取 临欠额度区间范围信息ID.
     *
     * @return  the regionId
     */
    public String getRegionId() {
        return regionId;
    }


    
    /**
     * 设置 临欠额度区间范围信息ID.
     *
     * @param regionId the regionId to set
     */
    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }


    /**
     * 获取 临欠额度区间范围信息实体.
     *
     * @return  the entity
     */
    public OwedLimitRegionEntity getEntity() {
        return entity;
    }

    
    /**
     * 设置 临欠额度区间范围信息实体.
     *
     * @param entity the entity to set
     */
    public void setEntity(OwedLimitRegionEntity entity) {
        this.entity = entity;
    }

    
    /**
     * 获取 临欠额度区间范围信息ID集合.
     *
     * @return  the ids
     */
    public List<String> getIds() {
        return ids;
    }

    
    /**
     * 设置 临欠额度区间范围信息ID集合.
     *
     * @param ids the ids to set
     */
    public void setIds(List<String> ids) {
        this.ids = ids;
    }
    
    /**
     * 获取 临欠额度区间范围信息集合.
     *
     * @return  the regionList
     */
    public List<OwedLimitRegionEntity> getRegionList() {
        return regionList;
    }
    
    /**
     * 设置 临欠额度区间范围信息集合.
     *
     * @param regionList the regionList to set
     */
    public void setRegionList(List<OwedLimitRegionEntity> regionList) {
        this.regionList = regionList;
    }
    
    

}
