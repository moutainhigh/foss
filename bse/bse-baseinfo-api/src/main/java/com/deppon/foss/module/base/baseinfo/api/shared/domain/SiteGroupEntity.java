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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/SiteGroupEntity.java
 * 
 * FILE NAME        	: SiteGroupEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 站点组实体类
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-11 上午11:18:13
 * @since
 * @version
 */
public class SiteGroupEntity extends BaseEntity{
   
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 3261300269328431285L;

    /**
     * 站点组名称.
     */
    private String name;

    /**
     * 所属部门.
     */
    private String orgCode;
    
    /**
     * 所属部门名称.
     */
    private String orgName;

    /**
     * 站点组类型.
     */
    private String type;

    /**
     * 是否启用.
     */
    private String active;

    /**
     * 备注.
     */
    private String notes;
    
    /**
     * 站点LIST.
     */
    private List<SiteGroupSiteEntity> siteGroupSiteEntityList;
    
    /**
     * 虚拟编码.
     */
    private String virtualCode; 

    /**
     * 获取 所属部门名称.
     *
     * @return  the orgName
     */
    public String getOrgName() {
        return orgName;
    }
    
    /**
     * 设置 所属部门名称.
     *
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 获取 站点组名称.
     *
     * @return  the name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 站点组名称.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 获取 所属部门.
     *
     * @return  the orgCode
     */
    public String getOrgCode() {
        return orgCode;
    }
    
    /**
     * 设置 所属部门.
     *
     * @param orgCode the orgCode to set
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
    /**
     * 获取 站点组类型.
     *
     * @return  the type
     */
    public String getType() {
        return type;
    }
    
    /**
     * 设置 站点组类型.
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
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
     * 获取 备注.
     *
     * @return  the notes
     */
    public String getNotes() {
        return notes;
    }
    
    /**
     * 设置 备注.
     *
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    /**
     * 获取 站点LIST.
     *
     * @return  the siteGroupSiteEntityList
     */
    public List<SiteGroupSiteEntity> getSiteGroupSiteEntityList() {
        return siteGroupSiteEntityList;
    }
    
    /**
     * 设置 站点LIST.
     *
     * @param siteGroupSiteEntityList the siteGroupSiteEntityList to set
     */
    public void setSiteGroupSiteEntityList(
    	List<SiteGroupSiteEntity> siteGroupSiteEntityList) {
        this.siteGroupSiteEntityList = siteGroupSiteEntityList;
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
}