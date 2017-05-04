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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity;
/**
 * 汇率信息VO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-4-16 下午5:17:03 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-4-16 下午5:17:03
 * @since
 * @version
 */
public class ExchangeRateVo implements Serializable {

    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -4966458103568614210L;

    /**
     * 汇率维护信息实体类.
     */
    private ExchangeRateEntity entity;
    
    /**
     * 汇率维护信息虚拟编码集合.
     */
    private List<String> codeList;
    
    /**
     * 汇率维护信息虚拟编码.
     */
    private String virtualCode;
    
    /**
     * 汇率维护信息信息集合.
     */
    private List<ExchangeRateEntity> entityList;

    
    /**
     * 获取 汇率维护信息实体类.
     *
     * @return  the entity
     */
    public ExchangeRateEntity getEntity() {
        return entity;
    }

    
    /**
     * 设置 汇率维护信息实体类.
     *
     * @param entity the entity to set
     */
    public void setEntity(ExchangeRateEntity entity) {
        this.entity = entity;
    }

    
    /**
     * 获取 汇率维护信息虚拟编码集合.
     *
     * @return  the codeList
     */
    public List<String> getCodeList() {
        return codeList;
    }

    
    /**
     * 设置 汇率维护信息虚拟编码集合.
     *
     * @param codeList the codeList to set
     */
    public void setCodeList(List<String> codeList) {
        this.codeList = codeList;
    }

    
    /**
     * 获取 汇率维护信息虚拟编码.
     *
     * @return  the virtualCode
     */
    public String getVirtualCode() {
        return virtualCode;
    }

    
    /**
     * 设置 汇率维护信息虚拟编码.
     *
     * @param virtualCode the virtualCode to set
     */
    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }

    
    /**
     * 获取 汇率维护信息信息集合.
     *
     * @return  the entityList
     */
    public List<ExchangeRateEntity> getEntityList() {
        return entityList;
    }

    
    /**
     * 设置 汇率维护信息信息集合.
     *
     * @param entityList the entityList to set
     */
    public void setEntityList(List<ExchangeRateEntity> entityList) {
        this.entityList = entityList;
    }
    
    
    
    
    

}
