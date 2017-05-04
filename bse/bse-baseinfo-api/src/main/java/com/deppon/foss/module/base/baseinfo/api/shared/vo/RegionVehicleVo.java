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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/RegionVehicleVo.java
 * 
 * FILE NAME        	: RegionVehicleVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity;

/**
 * 车辆定区VO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-11 下午6:15:07 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-12 上午9:04:56
 * @since
 * @version
 */
public class RegionVehicleVo implements Serializable{

    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 8396305126925628457L;
    
    /**
     * 车辆定区实体类.
     */
    private RegionVehicleEntity entity;
    
    /**
     * 车辆定区信息集合.
     */
    private List<RegionVehicleEntity> regionVehicleEntities;
    
    /**
     * codes字符串.
     */
    private String codes;
    
    /**
     * 获取 车辆定区实体类.
     *
     * @return  the entity
     */
    public RegionVehicleEntity getEntity() {
        return entity;
    }
    
    /**
     * 设置 车辆定区实体类.
     *
     * @param entity the entity to set
     */
    public void setEntity(RegionVehicleEntity entity) {
        this.entity = entity;
    }
    
    /**
     * 获取 车辆定区信息集合.
     *
     * @return  the regionVehicleEntities
     */
    public List<RegionVehicleEntity> getRegionVehicleEntities() {
        return regionVehicleEntities;
    }
    
    /**
     * 设置 车辆定区信息集合.
     *
     * @param regionVehicleEntities the regionVehicleEntities to set
     */
    public void setRegionVehicleEntities(
    	List<RegionVehicleEntity> regionVehicleEntities) {
        this.regionVehicleEntities = regionVehicleEntities;
    }
    
    /**
     * 获取 codes字符串.
     *
     * @return  the codes
     */
    public String getCodes() {
        return codes;
    }
    
    /**
     * 设置 codes字符串.
     *
     * @param codes the codes to set
     */
    public void setCodes(String codes) {
        this.codes = codes;
    }
    
}
