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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/SmallZoneVo.java
 * 
 * FILE NAME        	: SmallZoneVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ServiceZoneDto;

/**
 * (集中接送货小区VO)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-11 下午6:15:07 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-12 上午9:02:10
 * @since
 * @version
 */
public class SmallZoneVo implements Serializable{

    
    /**
     * 序列化
     */
    private static final long serialVersionUID = 4480452754031715805L;
    //集中接送货小区实体类
    private SmallZoneEntity entity;
    //集中接送货小区信息集合
    private List<SmallZoneEntity> smallZoneEntities;
    //codes字符串
    private String codes;
    //接送货大小区
    private List<ServiceZoneDto> serviceZoneDtos;
    public SmallZoneEntity getEntity() {
        return entity;
    }
    
    public void setEntity(SmallZoneEntity entity) {
        this.entity = entity;
    }
    
    public List<SmallZoneEntity> getSmallZoneEntities() {
        return smallZoneEntities;
    }
    
    public void setSmallZoneEntities(List<SmallZoneEntity> smallZoneEntities) {
        this.smallZoneEntities = smallZoneEntities;
    }
    
    public String getCodes() {
        return codes;
    }
    
    public void setCodes(String codes) {
        this.codes = codes;
    }

	/**
	 *getter
	 */
	public List<ServiceZoneDto> getServiceZoneDtos() {
		return serviceZoneDtos;
	}

	/**
	 *setter
	 */
	public void setServiceZoneDtos(List<ServiceZoneDto> serviceZoneDtos) {
		this.serviceZoneDtos = serviceZoneDtos;
	} 
    
}
