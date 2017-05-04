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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/SiteGroupSiteEntity.java
 * 
 * FILE NAME        	: SiteGroupSiteEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 站点组站点实体类
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-11 上午11:34:24
 * @since
 * @version
 */
public class SiteGroupSiteEntity extends BaseEntity{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -7160001663487926947L;

    /**
     * 序号.
     */
    private Integer seq;

    /**
     * 站点.
     */
    private String site;
    
    /**
     * 站点编码.
     */
    private String siteCode;  

    /**
     * 所属站点组.
     */
    private String parentOrgCode;

    /**
     * 是否启用.
     */
    private String active;
    
    /**
     * 虚拟编码.
     */
    private String virtualCode; 
    
    /**
     * 所属站点组.
     */
    private SiteGroupEntity sitGroupEntity;

    
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
    
    /**
     * 获取 站点编码.
     *
     * @return  the siteCode
     */
    public String getSiteCode() {
        return siteCode;
    }
    
    /**
     * 设置 站点编码.
     *
     * @param siteCode the siteCode to set
     */
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
    
    /**
     * 获取 所属站点组.
     *
     * @return  the parentOrgCode
     */
    public String getParentOrgCode() {
        return parentOrgCode;
    }

    /**
     * 设置 所属站点组.
     *
     * @param parentOrgCode the parentOrgCode to set
     */
    public void setParentOrgCode(String parentOrgCode) {
        this.parentOrgCode = parentOrgCode;
    }
    
    /**
     * 获取 是否启用.
     *
     * @return  the active
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 是否启用.
     *
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }
    
    /**
     * 获取 虚拟编码.
     *
     * @return  the virtualCode
     */
    public String getVirtualCode() {
        return virtualCode;
    }
    
    /**
     * 设置 虚拟编码.
     *
     * @param virtualCode the virtualCode to set
     */
    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }
    
    /**
     * 获取 所属站点组.
     *
     * @return  the sitGroupEntity
     */
    public SiteGroupEntity getSitGroupEntity() {
        return sitGroupEntity;
    }
    
    /**
     * 设置 所属站点组.
     *
     * @param sitGroupEntity the sitGroupEntity to set
     */
    public void setSitGroupEntity(SiteGroupEntity sitGroupEntity) {
        this.sitGroupEntity = sitGroupEntity;
    }  
}