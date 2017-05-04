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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/SiteGroupDto.java
 * 
 * FILE NAME        	: SiteGroupDto.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupEntity;


/**
 * 站点组以及站点组所属站点信息DTO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-31 下午3:02:29 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-31 下午3:02:29
 * @since
 * @version
 */
public class SiteGroupDto extends SiteGroupEntity {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 3811888535847067651L;
    
    /**
     * 序号.
     */
    private Integer seq;

    /**
     * 站点.
     */
    private String site;

    
    /**
     * 获取 序号.
     *
     * @return  the seq
     */
    public Integer getSeq() {
        return seq;
    }

    
    /**
     * 设置 序号.
     *
     * @param seq the seq to set
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    
    /**
     * 获取 站点.
     *
     * @return  the site
     */
    public String getSite() {
        return site;
    }

    
    /**
     * 设置 站点.
     *
     * @param site the site to set
     */
    public void setSite(String site) {
        this.site = site;
    }

    
    
}
