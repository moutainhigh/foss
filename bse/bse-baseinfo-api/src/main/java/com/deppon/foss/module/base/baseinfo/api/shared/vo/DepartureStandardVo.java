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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/DepartureStandardVo.java
 * 
 * FILE NAME        	: DepartureStandardVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;
/**
 * 发车标准VO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-14 下午2:44:59 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-14 下午2:44:59
 * @since
 * @version
 */
public class DepartureStandardVo implements Serializable{
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 8245223388419860655L;
    
    /**
     * 发车标准实体List.
     */
    private List<DepartureStandardEntity> departureStandardEntityList;
    
    /**
     * 发车标准实体.
     */
    private DepartureStandardEntity startStandardEntity;
    
    /**
     * 获取 发车标准实体List.
     *
     * @return  the departureStandardEntityList
     */
    public List<DepartureStandardEntity> getDepartureStandardEntityList() {
        return departureStandardEntityList;
    }
    
    /**
     * 设置 发车标准实体List.
     *
     * @param departureStandardEntityList the departureStandardEntityList to set
     */
    public void setDepartureStandardEntityList(
    	List<DepartureStandardEntity> departureStandardEntityList) {
        this.departureStandardEntityList = departureStandardEntityList;
    }
    
    /**
     * 获取 发车标准实体.
     *
     * @return  the startStandardEntity
     */
    public DepartureStandardEntity getStartStandardEntity() {
        return startStandardEntity;
    }
    
    /**
     * 设置 发车标准实体.
     *
     * @param startStandardEntity the startStandardEntity to set
     */
    public void setStartStandardEntity(DepartureStandardEntity startStandardEntity) {
        this.startStandardEntity = startStandardEntity;
    }
    
    
    
    
}
