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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/OriginatingLineVo.java
 * 
 * FILE NAME        	: OriginatingLineVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBigRegionDistrEntity;
/**
 * 
 * 快递大区与行政区域映射关系VO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-25 下午3:09:59 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-25 下午3:09:59
 * @since
 * @version
 */
public class ExpressRegionDistrVo implements Serializable{
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -4684643859509285726L;

    /**
     * 快递大区与行政区域映射关系实体类 .
     */
    private ExpressBigRegionDistrEntity entity;
    
    /**
     * 快递大区与行政区域映射关系实体集合.
     */
    private List<ExpressBigRegionDistrEntity> entityList;
    
    /**
     * 新增数据.
     */
    private List<ExpressBigRegionDistrEntity> addList;
    
    /**
     * 删除数据.
     */
    private List<ExpressBigRegionDistrEntity> deleteList;
    
    /**
     * 获取 新增数据.
     *
     * @return  the addList
     */
    public List<ExpressBigRegionDistrEntity> getAddList() {
        return addList;
    }



    
    /**
     * 设置 新增数据.
     *
     * @param addList the addList to set
     */
    public void setAddList(List<ExpressBigRegionDistrEntity> addList) {
        this.addList = addList;
    }



    
    /**
     * 获取 删除数据.
     *
     * @return  the deleteList
     */
    public List<ExpressBigRegionDistrEntity> getDeleteList() {
        return deleteList;
    }



    
    /**
     * 设置 删除数据.
     *
     * @param deleteList the deleteList to set
     */
    public void setDeleteList(List<ExpressBigRegionDistrEntity> deleteList) {
        this.deleteList = deleteList;
    }



    /**
     * 快递大区与行政区域映射关系ID集合.
     */
    private List<String> codeList;


    
    /**
     * 获取 快递大区与行政区域映射关系实体类 .
     *
     * @return  the entity
     */
    public ExpressBigRegionDistrEntity getEntity() {
        return entity;
    }


    
    /**
     * 设置 快递大区与行政区域映射关系实体类 .
     *
     * @param entity the entity to set
     */
    public void setEntity(ExpressBigRegionDistrEntity entity) {
        this.entity = entity;
    }


    
    /**
     * 获取 快递大区与行政区域映射关系实体集合.
     *
     * @return  the entityList
     */
    public List<ExpressBigRegionDistrEntity> getEntityList() {
        return entityList;
    }


    
    /**
     * 设置 快递大区与行政区域映射关系实体集合.
     *
     * @param entityList the entityList to set
     */
    public void setEntityList(List<ExpressBigRegionDistrEntity> entityList) {
        this.entityList = entityList;
    }


    
    /**
     * 获取 快递大区与行政区域映射关系ID集合.
     *
     * @return  the codeList
     */
    public List<String> getCodeList() {
        return codeList;
    }


    
    /**
     * 设置 快递大区与行政区域映射关系ID集合.
     *
     * @param codeList the codeList to set
     */
    public void setCodeList(List<String> codeList) {
        this.codeList = codeList;
    }
    
    
    
}
